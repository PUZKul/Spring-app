package kul.pl.biblioteka.api;

import kul.pl.biblioteka.holder.ReservationHolder;
import kul.pl.biblioteka.holder.UserBookHolder;
import kul.pl.biblioteka.model.LibraryUser;
import kul.pl.biblioteka.service.AdminLibraryService;
import kul.pl.biblioteka.utils.JSONFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

  @GetMapping("/reservations")
  public List<ReservationHolder> getWaitingReservations(@RequestParam("limit") int limit,
                                                        @RequestParam("page") int page,
                                                        @RequestParam(value = "username", required = false) String username) {
    int offset = page * limit;
    return service.getReservationList(offset, limit, Objects.requireNonNullElse(username, ""));
  }

  @GetMapping("/reservations/{reservationId}")
  public Optional<ReservationHolder> getReservation(@PathVariable("reservationId") long reservationId){
    return service.getReservation(reservationId);
  }

  @PostMapping("/reservations/cancel/{reservationId}")
  public void cancelReservation(@PathVariable("reservationId") long reservationId) {
    service.cancelReservation(reservationId);
  }

  @GetMapping("/users/{userId}")
  public MappingJacksonValue getUserData(@PathVariable("userId") UUID userId){
    Optional<LibraryUser> user = service.getUserDataById(userId);
    return new JSONFilter.Builder(user)
        .exclude("enabled")
        .setFilter("userFilter")
        .build()
        .getMapper();
  }

}
