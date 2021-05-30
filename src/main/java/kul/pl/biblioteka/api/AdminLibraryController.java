package kul.pl.biblioteka.api;

import kul.pl.biblioteka.holder.EditUserHolder;
import kul.pl.biblioteka.holder.IncreaseLimit;
import kul.pl.biblioteka.holder.ReservationHolder;
import kul.pl.biblioteka.holder.UserBookHolder;
import kul.pl.biblioteka.model.LibraryUser;
import kul.pl.biblioteka.model.Message;
import kul.pl.biblioteka.service.AdminLibraryService;
import kul.pl.biblioteka.utils.JSONFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/library/admins")

public class AdminLibraryController {

  private final AdminLibraryService service;

  @Autowired
  public AdminLibraryController(AdminLibraryService service) {
    this.service = service;
  }

  @PostMapping("/confirmBorrow/{reservationId}")
  public long confirmBorrow(@PathVariable("reservationId") long reservationId) {
    return service.confirmBorrow(reservationId);
  }

  @PostMapping("/confirmReturn/{borrowId}")
  public void confirmReturn(@PathVariable("borrowId") long borrowId) {
    service.confirmBookReturn(borrowId);
  }

  @GetMapping("/reservations")
  public List<ReservationHolder> getWaitingReservations(
      @RequestParam("limit") int limit,
      @RequestParam("page") int page,
      @RequestParam(value = "username", required = false) String username) {
    int offset = page * limit;
    return service.getReservationList(offset, limit, Objects.requireNonNullElse(username, ""));
  }

  @GetMapping("/reservations/{reservationId}")
  public Optional<ReservationHolder> getReservation(
      @PathVariable("reservationId") long reservationId) {
    return service.getReservation(reservationId);
  }

  @PostMapping("/reservations/cancel/{reservationId}")
  public void cancelReservation(@PathVariable("reservationId") long reservationId) {
    service.cancelReservation(reservationId);
  }

  @GetMapping("/users")
  public List<LibraryUser> getUsers(
      @RequestParam("limit") int limit,
      @RequestParam("page") int page,
      @RequestParam(value = "username", required = false) String username) {
    int offset = page * limit;
    return service.getUsers(offset, limit, Objects.requireNonNullElse(username, ""));
  }

  @GetMapping("/users/{userId}")
  public Optional<LibraryUser> getUserData(@PathVariable("userId") UUID userId) {
    return service.getUserDataById(userId);
  }

  @PostMapping("/users/edit/{username}")
  public void editUserData(
      @RequestBody EditUserHolder holder, @PathVariable("username") String username) {
    service.editUserData(holder, username);
  }

  @GetMapping("/requests")
  public List<Message> getLimitRequests(
      @RequestParam("limit") int limit,
      @RequestParam("page") int page,
      @RequestParam(value = "username", required = false) String username) {
    int offset = page * limit;
    return service.getLimitRequests(offset, limit, username);
  }

  @PostMapping("/users/{username}/bookLimit}")
  public void increaseLimit(
      @RequestBody IncreaseLimit holder, @PathVariable("username") String username) {
    service.increaseLimit(holder, username);
  }

  @GetMapping("/rental")
  public List<UserBookHolder> getCurrentBooks(
      @RequestParam("limit") int limit,
      @RequestParam("page") int page,
      @RequestParam(value = "username", required = false) String username) {
    int offset = page * limit;
    return service.getBookRental(offset, limit, username);
  }

}
