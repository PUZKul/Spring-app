package kul.pl.biblioteka.service;

import kul.pl.biblioteka.exception.AlreadyBorrowedException;
import kul.pl.biblioteka.exception.ResourceNotFoundException;
import kul.pl.biblioteka.holder.ReservationHolder;
import kul.pl.biblioteka.model.BookReservation;
import kul.pl.biblioteka.model.UserBook;
import kul.pl.biblioteka.repository.*;
import kul.pl.biblioteka.utils.ReservationState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminLibraryService {
    private final LibraryUserRepository userRepository;
    private final BookRepository bookRepository;
    private final UserBookRepository userBookRepository;
    private final BookCopiesRepository bookCopiesRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public AdminLibraryService(LibraryUserRepository userRepository, UserBookRepository userBookRepository, BookRepository bookRepository, BookCopiesRepository bookCopiesRepository, ReservationRepository reservationRepository) {
        this.userRepository = userRepository;
        this.userBookRepository = userBookRepository;
        this.bookRepository = bookRepository;
        this.bookCopiesRepository = bookCopiesRepository;
        this.reservationRepository = reservationRepository;
    }

    public long confirmBorrow(long reservationId){
        Optional<BookReservation> reservation = reservationRepository.findById(reservationId);
        if(reservation.isEmpty()) throw new ResourceNotFoundException("Reservation not exist");
        if(reservation.get().getState() == ReservationState.BORROWED) throw new AlreadyBorrowedException("Reservation already realized");

        UserBook userBook = new UserBook(
                reservation.get().getUserId(),
                reservation.get().getBookCopy(),
                new Date(),
                null);
        UserBook save = userBookRepository.save(userBook);
        reservationRepository.changeStatus(reservation.get().getId(), ReservationState.BORROWED);
        return save.getId();
    }

    public List<ReservationHolder> getReservationList(){
      List<BookReservation> all = (List<BookReservation>) reservationRepository.findAll();

      return all.stream()
          .filter(e -> e.getState() == ReservationState.WAITING)
          .map(e -> ReservationHolder.builder()
              .id(e.getId())
              .bookCopyId(e.getBookCopy().getId())
              .dateReservation(e.getDateReservation())
              .userId(e.getUserId())
              .bookId(e.getBookCopy().getBookId())
              .title(bookRepository.getBookTitle(e.getBookCopy().getBookId()))
              .imageUrl(bookRepository.getBookImage(e.getBookCopy().getBookId()))
              .build())
          .collect(Collectors.toList());
    }

    public void cancelReservation(long reservationId){
      Optional<BookReservation> reservation = reservationRepository.findById(reservationId);
      if(reservation.isEmpty()) throw new ResourceNotFoundException("Reservation does not exist");
      if(reservation.get().getState() == ReservationState.BORROWED) throw new AlreadyBorrowedException("Reservation already realized");
      if(reservation.get().getState() == ReservationState.CANCELED) throw new IllegalArgumentException("Reservation already canceled");

      reservationRepository.changeStatus(reservation.get().getId(), ReservationState.CANCELED);
    }

}
