package kul.pl.biblioteka.api;

import kul.pl.biblioteka.holder.ReservationHolder;
import kul.pl.biblioteka.service.AdminLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
  public List<ReservationHolder> getWaitingReservations() {
    return service.getReservationList();
  }

  @PostMapping("/reservations/cancel/{reservationId}")
  public void cancelReservation(@PathVariable("reservationId") long reservationId) {
    service.cancelReservation(reservationId);
  }
}
