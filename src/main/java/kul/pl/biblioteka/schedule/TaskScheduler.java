package kul.pl.biblioteka.schedule;

import kul.pl.biblioteka.model.BlackList;
import kul.pl.biblioteka.repository.*;
import kul.pl.biblioteka.utils.BlackListStatus;
import lombok.RequiredArgsConstructor;

import kul.pl.biblioteka.model.BookReservation;
import kul.pl.biblioteka.model.LibraryUser;
import kul.pl.biblioteka.utils.ReservationState;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static kul.pl.biblioteka.utils.BlackListStatus.BLOCKED;

@Slf4j
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
  private final BlackListRepository blackListRepository;

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
        .filter(userBook -> userBook.getExpectedTime().compareTo(new Date()) < 0)
        .forEach(e -> addWarning(e.getUserId()));
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
    var blackList = createBlackListElement(userId, comment);
    blackListRepository.save(blackList);
  }

  private BlackList createBlackListElement(UUID userId, String comment) {
    Optional<LibraryUser> user = userRepository.findById(userId);
    return new BlackList(
            user.get().getId(),
            new Date(),
            null,
            comment,
            BLOCKED
    );
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
