package kul.pl.biblioteka.schedule;

import lombok.RequiredArgsConstructor;

import kul.pl.biblioteka.model.BookReservation;
import kul.pl.biblioteka.model.LibraryUser;
import kul.pl.biblioteka.repository.BookCopiesRepository;
import kul.pl.biblioteka.repository.LibraryUserRepository;
import kul.pl.biblioteka.repository.ReservationRepository;
import kul.pl.biblioteka.repository.UserBookRepository;
import kul.pl.biblioteka.utils.ReservationState;
import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;


@RequiredArgsConstructor
class TaskScheduler implements Scheduler {

  private final static String BLACK_LIST_COMMENT = "User exceeded the maximum number of warnings";
  private final static int MAX_WARNINGS = 3;
  private static boolean RESERVATION_CHECKED = false;
  private static boolean BORROWED_CHECKED = false;

  private final LibraryUserRepository userRepository;
  private final ReservationRepository reservationRepository;
  private final UserBookRepository userBookRepository;
  private final BookCopiesRepository copiesRepository;

  @Override
  public void checkReservations() {
    if(RESERVATION_CHECKED) return;
    var waiting = reservationRepository.findAllByState(ReservationState.WAITING);
    waiting.stream()
        .filter(e -> isAfterTime(e.getDateReservation(), 3))
        .forEach(e->{
          cancelReservation(e);
          addWarning(e.getUserId());
        });
    RESERVATION_CHECKED = true;
  }

  @Override
  public void checkBorrowedBooks() {
    if(BORROWED_CHECKED) return;
    var borrowed = userBookRepository.findAllBorrowed();
    borrowed.stream()
        .filter(userBook -> userBook.getDateIssued().compareTo(userBook.getExpectedTime()) > 0)
        .forEach(userBook -> addWarning(userBook.getUserId()));
    BORROWED_CHECKED = true;
  }

  private void addWarning(UUID userId) {
    userRepository.addWarning(userId);
    checkWarningNumber(userId);
  }

  private void checkWarningNumber(UUID userId) {
    Optional<LibraryUser> user = userRepository.findById(userId);
    int warnings = user.get().getWarnings();
    if(warnings > MAX_WARNINGS) moveUserToBlackList(userId, BLACK_LIST_COMMENT);
  }

  private void moveUserToBlackList(UUID userId, String comment) {
    userRepository.setBanned(userId);
    // TODO add black list model and repository
  }

  private void cancelReservation(BookReservation e) {
    reservationRepository.changeStatus(e.getId(), ReservationState.CANCELED);
    var bookCopy = e.getBookCopy();
    copiesRepository.markAsFree(bookCopy.getId());
  }

  private static boolean isAfterTime(Date date, int days){
    int time = Days.daysBetween(LocalDate.fromDateFields(date), LocalDate.now()).getDays();
    return time > days;
  }
}
