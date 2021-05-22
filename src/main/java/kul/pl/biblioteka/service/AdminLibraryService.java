package kul.pl.biblioteka.service;

import lombok.RequiredArgsConstructor;

import kul.pl.biblioteka.exception.AlreadyBorrowedException;
import kul.pl.biblioteka.exception.ResourceNotFoundException;
import kul.pl.biblioteka.holder.ReservationHolder;
import kul.pl.biblioteka.model.BookReservation;
import kul.pl.biblioteka.model.LibraryUser;
import kul.pl.biblioteka.model.UserBook;
import kul.pl.biblioteka.repository.*;
import kul.pl.biblioteka.schedule.Scheduler;
import kul.pl.biblioteka.utils.LibraryPage;
import kul.pl.biblioteka.utils.ReservationState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static kul.pl.biblioteka.utils.Helper.isNullOrEmpty;
import static kul.pl.biblioteka.utils.ReservationState.WAITING;

@Service
@RequiredArgsConstructor
public class AdminLibraryService {
    private final LibraryUserRepository userRepository;
    private final BookRepository bookRepository;
    private final UserBookRepository userBookRepository;
    private final BookCopiesRepository bookCopiesRepository;
    private final ReservationRepository reservationRepository;
    private final Scheduler scheduler;

    public long confirmBorrow(long reservationId){
        Optional<BookReservation> reservation = reservationRepository.findById(reservationId);
        if(reservation.isEmpty()) throw new ResourceNotFoundException("Reservation not exist");
        if(reservation.get().getState() == ReservationState.BORROWED) throw new AlreadyBorrowedException("Reservation already realized");

        UserBook userBook = new UserBook(
                reservation.get().getUserId(),
                reservation.get().getBookCopy(),
                new Date(),
                null,
                addDaysFromToday(30));
        UserBook save = userBookRepository.save(userBook);
        reservationRepository.changeStatus(reservation.get().getId(), ReservationState.BORROWED);
        return save.getId();
    }

  private Date addDaysFromToday(int days) {
      Calendar c= Calendar.getInstance();
      c.add(Calendar.DATE, days);
      return c.getTime();
  }

  public List<ReservationHolder> getReservationList(int offset, int limit, String username){
      scheduler.checkReservations();
      Pageable pageable = new LibraryPage(offset, limit);
      Page<BookReservation> reservations;
      if(isNullOrEmpty(username))
        reservations = reservationRepository.findAllByState(WAITING, pageable);
      else
        reservations = reservationRepository.findByStateAndUserId(WAITING, getUserId(username), pageable);

      return createHolder(reservations);
    }

  public Optional<ReservationHolder> getReservation(long id){
    Optional<BookReservation> reservation = reservationRepository.findById(id);
    if (reservation.isEmpty()) throw new ResourceNotFoundException("Reservation not found!");
    BookReservation e = reservation.get();

    return Optional.of(ReservationHolder.builder()
        .id(e.getId())
        .bookCopyId(e.getBookCopy().getId())
        .dateReservation(e.getDateReservation())
        .userId(e.getUserId())
        .bookId(e.getBookCopy().getBookId())
        .username(userRepository.getUsername(e.getUserId()))
        .title(bookRepository.getBookTitle(e.getBookCopy().getBookId()))
        .imageUrl(bookRepository.getBookImage(e.getBookCopy().getBookId()))
        .build());
  }

  private List<ReservationHolder> createHolder(Page<BookReservation> reservations) {
    return reservations.stream()
        .map(e -> ReservationHolder.builder()
            .id(e.getId())
            .bookCopyId(e.getBookCopy().getId())
            .dateReservation(e.getDateReservation())
            .userId(e.getUserId())
            .bookId(e.getBookCopy().getBookId())
            .username(userRepository.getUsername(e.getUserId()))
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

    public Optional<LibraryUser> getUserDataById(UUID id){
      return userRepository.findById(id);
    }

    private UUID getUserId(String username){
      LibraryUser user = userRepository.getUserByUsername(username);
      if(user == null) throw new ResourceNotFoundException("Given username not exist");
      return user.getId();
    }

}
