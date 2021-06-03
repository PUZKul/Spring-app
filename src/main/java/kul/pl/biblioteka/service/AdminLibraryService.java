package kul.pl.biblioteka.service;

import kul.pl.biblioteka.holder.EditUserHolder;

import lombok.RequiredArgsConstructor;

import kul.pl.biblioteka.exception.AlreadyBorrowedException;
import kul.pl.biblioteka.exception.ResourceNotFoundException;
import kul.pl.biblioteka.holder.IncreaseLimit;
import kul.pl.biblioteka.holder.ReservationHolder;
import kul.pl.biblioteka.holder.UserBookHolder;
import kul.pl.biblioteka.model.*;
import kul.pl.biblioteka.repository.*;
import kul.pl.biblioteka.schedule.Scheduler;
import kul.pl.biblioteka.utils.LibraryPage;
import kul.pl.biblioteka.utils.MessageStatus;
import kul.pl.biblioteka.utils.ReservationState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static kul.pl.biblioteka.utils.BlackListStatus.BLOCKED;
import static kul.pl.biblioteka.utils.Helper.addDaysFromToday;
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
  private final MessageRepository messageRepository;
  private final BlackListRepository blackListRepository;
  private final Scheduler scheduler;

  public void activateAccount(String username) {
    UUID userId = getUserId(username);
    Optional<BlackList> blackList = blackListRepository.findByUserId(userId);
    if(blackList.isEmpty()) throw new ResourceNotFoundException("User is not banned");
    blackListRepository.removeUser(userId);
    userRepository.cleanWarnings(username);
    userRepository.setUnBanned(username);
  }


  public void deactivateAccount(String username, String message) {
    UUID userId = getUserId(username);
    Optional<BlackList> repository = blackListRepository.findByUserId(userId);
    if(repository.isPresent()) throw new AlreadyBorrowedException("User is already banned");
    var blackList = createBlackListElement(userId, message);
    blackListRepository.save(blackList);
    userRepository.setBanned(userId);
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

  public long confirmBorrow(long reservationId) {
    Optional<BookReservation> reservation = reservationRepository.findById(reservationId);
    if (reservation.isEmpty())
      throw new ResourceNotFoundException("Reservation not exist");
    if (reservation.get().getState() == ReservationState.BORROWED)
      throw new AlreadyBorrowedException("Reservation already realized");

    if (!isUserUnderLimit(reservation.get().getUserId())) {
      throw new IllegalStateException("User reached the limit");
    }

    UserBook userBook = new UserBook(
        reservation.get().getUserId(),
        reservation.get().getBookCopy(),
        new Date(),
        null,
        reservationId,
        addDaysFromToday(30));
    UserBook save = userBookRepository.save(userBook);
    reservationRepository.changeStatus(reservation.get().getId(), ReservationState.BORROWED);
    return save.getId();
  }

  private boolean isUserUnderLimit(UUID userId) {
    int limit = userRepository.getBookLimit(userId);
    int current = userBookRepository.getCurrentBooksNumber(userId);
    int reserveNo = reservationRepository.getCurrentReservationNumber(userId);
    return (current + reserveNo) < limit;
  }


  public void confirmBookReturn(long borrowId) {
    Optional<UserBook> userBook = userBookRepository.findById(borrowId);
    if (userBook.isEmpty())
      throw new ResourceNotFoundException("Resource not found");
    userBookRepository.setReturnDate(borrowId, new Date());
    bookCopiesRepository.markAsFree(userBook.get().getBookCopy().getId());
  }

  public void editUserData(EditUserHolder user, String username) {
    var userDataUpdater = new UserDataUpdater(userRepository, userRepository);
    userDataUpdater.update(user, username);
  }

  public List<ReservationHolder> getReservationList(int offset, int limit, String username) {
    scheduler.checkReservations();
    Pageable pageable = new LibraryPage(offset, limit);
    Page<BookReservation> reservations;
    if (isNullOrEmpty(username))
      reservations = reservationRepository.findAllByState(WAITING, pageable);
    else
      reservations =
          reservationRepository.findByStateAndUserId(WAITING, getUserId(username), pageable);

    return createHolder(reservations);
  }

  public List<UserBookHolder> getBookRental(int offset, int limit, String username) {
    scheduler.checkBorrowedBooks();

    Pageable pageable = new LibraryPage(offset, limit);
    Page<UserBook> rentals;
    if (isNullOrEmpty(username))
      rentals = userBookRepository.findCurrent(pageable);
    else
      rentals = userBookRepository.findCurrentByUserId(getUserId(username), pageable);

    return createBookHolder(rentals);
  }

  private List<UserBookHolder> createBookHolder(Page<UserBook> rentals) {
    return rentals.stream()
        .map(e -> UserBookHolder.builder()
            .id(e.getId())
            .bookCopyId(e.getBookCopy().getId())
            .dateIssued(e.getDateIssued())
            .userId(e.getUserId())
            .bookId(e.getBookCopy().getBookId())
            .expectedDate(e.getExpectedTime())
            .username(userRepository.getUsername(e.getUserId()))
            .title(bookRepository.getBookTitle(e.getBookCopy().getBookId()))
            .imageUrl(bookRepository.getBookImage(e.getBookCopy().getBookId()))
            .build())
        .collect(Collectors.toList());
  }

  public Optional<ReservationHolder> getReservation(long id) {
    Optional<BookReservation> reservation = reservationRepository.findById(id);
    if (reservation.isEmpty())
      throw new ResourceNotFoundException("Reservation not found!");
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

  public void cancelReservation(long reservationId) {
    Optional<BookReservation> reservation = reservationRepository.findById(reservationId);
    if (reservation.isEmpty())
      throw new ResourceNotFoundException("Reservation does not exist");
    if (reservation.get().getState() == ReservationState.BORROWED)
      throw new AlreadyBorrowedException("Reservation already realized");
    if (reservation.get().getState() == ReservationState.CANCELED)
      throw new IllegalArgumentException("Reservation already canceled");

    reservationRepository.changeStatus(reservation.get().getId(), ReservationState.CANCELED);
  }

  public Optional<LibraryUser> getUserDataById(UUID id) {
    return userRepository.findById(id);
  }

  public UUID getUserId(String username) {
    LibraryUser user = userRepository.getUserByUsername(username);
    if (user == null)
      throw new ResourceNotFoundException("Given username not exist");
    return user.getId();
  }

  public List<Message> getLimitRequests(int offset, int limit, String username) {
    Pageable pageable = new LibraryPage(offset, limit);
    Page<Message> allRequests;
    if (isNullOrEmpty(username))
      allRequests = messageRepository.findAllRequests(pageable);
    else
      allRequests = messageRepository.findAllRequestsByUsername(username, pageable);

    if(allRequests.getContent().size() == 0) throw new ResourceNotFoundException("Not found");
    return allRequests.getContent();
  }

  public void rejectRequest(long id) {
    Optional<Message> message = messageRepository.findById(id);
    if(message.isEmpty()) throw new ResourceNotFoundException("Not found");
    if(message.get().getStatus() == MessageStatus.REFUSED) throw new IllegalArgumentException("Already Rejected");

    messageRepository.changeStatus(MessageStatus.REFUSED, id);
  }

  public void confirmRequest(long id) {
    Optional<Message> message = messageRepository.findById(id);
    if(message.isEmpty()) throw new ResourceNotFoundException("Not found");
    if(message.get().getStatus() == MessageStatus.APPROVED) throw new IllegalArgumentException("Already confirmed");

    messageRepository.changeStatus(MessageStatus.APPROVED, id);
  }

  public void increaseLimit(IncreaseLimit holder, String username) {
    LibraryUser user = userRepository.getUserByUsername(username);
    if (user == null)
      throw new ResourceNotFoundException("Username not found");

    if (holder.getLimit() > 20) throw new IllegalArgumentException("Limit cannot be greater than 20");
    UUID userId = getUserId(username);
    userRepository.changeBookLimit(holder.getLimit(), userId);
    if (!isNullOrEmpty(holder.getComment()))
      userRepository.setComment(holder.getComment(), userId);
  }

  public List<LibraryUser> getUsers(int offset, int limit, String username) {
    Pageable pageable = new LibraryPage(offset, limit);
    Page<LibraryUser> all;
    if (isNullOrEmpty(username))
      all =  userRepository.findAll(pageable);
    else
      all = userRepository.findAllByName(username, pageable);
    if(all.getContent().size() == 0) throw new ResourceNotFoundException("Not found");
    return all.getContent();
  }



}
