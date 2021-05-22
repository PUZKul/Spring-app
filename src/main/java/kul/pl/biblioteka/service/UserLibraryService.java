package kul.pl.biblioteka.service;

import kul.pl.biblioteka.exception.AuthorisationException;
import kul.pl.biblioteka.exception.BookIsOccupiedException;
import kul.pl.biblioteka.exception.ResourceNotFoundException;
import kul.pl.biblioteka.holder.EditUserHolder;
import kul.pl.biblioteka.holder.UserBookHolder;
import kul.pl.biblioteka.holder.ReservationHolder;
import kul.pl.biblioteka.holder.UserHolder;
import kul.pl.biblioteka.model.*;
import kul.pl.biblioteka.repository.*;
import kul.pl.biblioteka.schedule.Scheduler;
import kul.pl.biblioteka.security.LibraryUserRole;
import kul.pl.biblioteka.security.PasswordConfig;
import kul.pl.biblioteka.utils.LibraryPage;
import kul.pl.biblioteka.utils.ReservationState;
import kul.pl.biblioteka.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class UserLibraryService {

  private final LibraryUserRepository userRepository;
  private final UserBookRepository historyRepository;
  private final BookRepository bookRepository;
  private final BookCopiesRepository bookCopiesRepository;
  private final ReservationRepository reservationRepository;
  private final Scheduler scheduler;

  @Autowired
  public UserLibraryService(
      LibraryUserRepository userRepository, UserBookRepository historyRepository,
      BookRepository bookRepository,
      BookCopiesRepository bookCopiesRepository,
      ReservationRepository reservationRepository, Scheduler scheduler) {
    this.userRepository = userRepository;
    this.historyRepository = historyRepository;
    this.bookRepository = bookRepository;
    this.bookCopiesRepository = bookCopiesRepository;
    this.reservationRepository = reservationRepository;
    this.scheduler = scheduler;
  }

  public int registerNewUser(UserHolder newUser) {
    if (!Validator.username(newUser.getUsername()))
      throw new IllegalArgumentException(
          String.format("Given username '%s' is invalid", newUser.getUsername()));
    if (!Validator.email(newUser.getEmail()))
      throw new IllegalArgumentException(
          String.format("Given email '%s' is invalid", newUser.getEmail()));
    if (!Validator.password(newUser.getPassword()))
      throw new IllegalArgumentException("Given password is invalid");

    if (userRepository.getUserByUsername(newUser.getUsername()) != null)
      throw new EntityExistsException("User with given username already exist");
    if (userRepository.isEmailExist(newUser.getEmail()) > 0)
      throw new EntityExistsException("User with given email already exist");
    LibraryUser user = setNewUser(newUser);

    userRepository.save(user);
    return 1;
  }

  public int editUser(EditUserHolder user, String username){
    Optional<LibraryUser> repoUser = getUserByUsername(username);
    if (!confirmPassword(user.getOldPassword(), repoUser.get().getPassword()))
      throw new AuthorisationException("Incorrect password");

    var userDataUpdater = new UserDataUpdater(userRepository);
    userDataUpdater.update(user, username);
    return 1;
  }

  public long reserveBook(long bookCopyId, String username){
    Optional<BookCopy> copy = bookCopiesRepository.findById(bookCopyId);
    if (copy.get().isBorrowed()) throw new BookIsOccupiedException("This book is already borrowed");
    BookReservation reservation = BookReservation.builder()
        .userId(getUserId(username))
        .bookCopy(copy.get())
        .state(ReservationState.WAITING)
        .dateReservation(new Date())
        .dateBorrow(null)
        .build();

    BookReservation save = reservationRepository.save(reservation);
    bookCopiesRepository.markAsBorrowed(bookCopyId);
    return save.getId();
  }

  public int cancelReservation(long reservationId){
    Optional<BookReservation> reservation = reservationRepository.findById(reservationId);
    if (reservation.isEmpty()) throw new ResourceNotFoundException("There is no reservation with this ID in the database");
    if (reservation.get().getState() != ReservationState.WAITING) throw new IllegalArgumentException("Reservation was already canceled or completed");
    BookCopy bookCopy = reservation.get().getBookCopy();
    bookCopiesRepository.markAsFree(bookCopy.getId());
    reservationRepository.changeStatus(reservationId, ReservationState.CANCELED);
    return 1;
  }

  public Page<ReservationHolder> getUserReservations(int offset, int limit, String username){
    scheduler.checkReservations();
    Pageable pageable = new LibraryPage(offset, limit);
    var reservations = reservationRepository.findAllByUserId(getUserId(username), pageable);
    var reservationHolder = createReservationHolder(reservations);
    return new PageImpl<>(reservationHolder, pageable, limit);
  }

  private List<ReservationHolder> createReservationHolder(Page<BookReservation> reservations) {
    return reservations.stream()
        .filter(e->e.getState()==ReservationState.WAITING)
        .map(e -> {
          long bookId = e.getBookCopy().getBookId();
          return ReservationHolder.builder()
              .id(e.getId())
              .bookCopyId(e.getBookCopy().getId())
              .userId(e.getUserId())
              .bookId(bookId)
              .dateReservation(e.getDateReservation())
              .dateBorrow(e.getDateBorrow())
              .title(bookRepository.getBookTitle(bookId))
              .imageUrl(bookRepository.getBookImage(bookId))
              .build();
        }
    ).collect(Collectors.toList());
  }

  public Page<UserBookHolder> getUserHistory(int offset, int limit, String username) {
    Pageable pageable = new LibraryPage(offset, limit);
    Page<UserBook> histories = historyRepository.findAllByUserId(getUserId(username), pageable);
    List<UserBookHolder> userBookHolder = createHistoryHolder(histories);
    return new PageImpl<>(userBookHolder, pageable, limit);
  }

  public Page<UserBookHolder> getCurrentUserBooks(int offset, int limit, String username) {
    Pageable pageable = new LibraryPage(offset, limit);
    Page<UserBook> histories = historyRepository.findAllByUserId(getUserId(username), pageable);
    List<UserBookHolder> userBookHolder = createCurrentBooksHolder(histories);
    return new PageImpl<>(userBookHolder, pageable, limit);
  }

  private List<UserBookHolder> createCurrentBooksHolder(Page<UserBook> histories) {
    return histories.stream()
        .filter(e-> e.getDateReturn()==null)
        .map(this::createUserBookHolder)
        .collect(Collectors.toList());
  }

  private List<UserBookHolder> createHistoryHolder(Page<UserBook> histories) {
    return histories.stream()
        .filter(e-> e.getDateReturn()!=null)
        .map(this::createUserBookHolder)
        .collect(Collectors.toList());
  }

  private UserBookHolder createUserBookHolder(UserBook e){
    long bookId = e.getBookCopy().getBookId();
    return UserBookHolder.builder()
        .id(e.getId())
        .bookCopyId(e.getBookCopy().getId())
        .userId(e.getUserId())
        .bookId(bookId)
        .dateIssued(e.getDateIssued())
        .dateReturn(e.getDateReturn())
        .title(bookRepository.getBookTitle(bookId))
        .imageUrl(bookRepository.getBookImage(bookId))
        .build();
  }

  public Optional<LibraryUser> getUserByUsername(String name) {
    return Optional.ofNullable(userRepository.getUserByUsername(name));
  }

  public String getBookTitle(long id){
    return bookRepository.getBookTitle(id);
  }

  public String getBookImage(long id){
    return bookRepository.getBookImage(id);
  }

  public UserBookDetails getUserBookDetails(String username) {
    Collection<UserBook> allByUserName = historyRepository.findAllByUserName(getUserId(username));

    long countCurrent = allByUserName.stream().filter(e -> e.getDateReturn() == null).count();
    long totalBooks = allByUserName.size() - countCurrent;
    return new UserBookDetails(totalBooks, countCurrent);
  }

  private UUID getUserId(String username){
    LibraryUser user = userRepository.getUserByUsername(username);
    return user.getId();
  }


  private boolean confirmPassword(String receivedPassword, String currentPassword) {
    return PasswordConfig.encoder().matches(receivedPassword, currentPassword);
  }

  private LibraryUser setNewUser(UserHolder newUser) {
    return new LibraryUser(
        UUID.randomUUID(),
        newUser.getUsername(),
        PasswordConfig.encoder().encode(newUser.getPassword()),
        newUser.getEmail(),
        3,
        0,
        0,
        false,
        true,
        LibraryUserRole.USER.name());
  }

}
