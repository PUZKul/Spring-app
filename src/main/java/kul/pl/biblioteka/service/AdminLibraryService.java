package kul.pl.biblioteka.service;

import kul.pl.biblioteka.exception.ResourceNotFoundException;
import kul.pl.biblioteka.model.BookReservation;
import kul.pl.biblioteka.model.UserBook;
import kul.pl.biblioteka.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

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

        UserBook userBook = new UserBook(
                reservation.get().getUserId(),
                reservation.get().getBookCopy(),
                new Date(),
                null);
        UserBook save = userBookRepository.save(userBook);
        return save.getId();
    }


}
