package kul.pl.biblioteka.api;


import kul.pl.biblioteka.holder.EditUserHolder;
import kul.pl.biblioteka.holder.UserBookHolder;
import kul.pl.biblioteka.holder.ReservationHolder;
import kul.pl.biblioteka.model.LibraryUser;
import kul.pl.biblioteka.model.UserBookDetails;
import kul.pl.biblioteka.service.UserLibraryService;
import kul.pl.biblioteka.utils.JSONFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

import static kul.pl.biblioteka.utils.Helper.isNullOrEmpty;

@RestController
@RequestMapping("api/library/users")
public class UserLibraryController {

    private final UserLibraryService service;

    @Autowired
    public UserLibraryController(UserLibraryService service) {
        this.service = service;
    }

    @GetMapping
    public MappingJacksonValue getUserByUsername(Principal principal){
        Optional<LibraryUser> user = service.getUserByUsername(principal.getName());

        return new JSONFilter.Builder(user)
                   .exclude("role", "comment", "enabled", "banned")
                   .setFilter("userFilter")
                   .build()
                   .getMapper();
    }

    @PutMapping(path = "/edit")
    public int editUser(@RequestBody @Valid EditUserHolder user, Principal principal){
        if(isNullOrEmpty(user.getOldPassword()))
            throw new IllegalArgumentException("Old password is required");
        return service.editUser(user, principal.getName());
    }

    @GetMapping(path = "/bookDetails")
    public UserBookDetails getUserBookDetails(Principal principal){
        return service.getUserBookDetails(principal.getName());
    }

    @GetMapping(path = "/history")
    public Page<UserBookHolder> getHistory(@RequestParam(value = "limit") int limit,
                                           @RequestParam(value = "page") int page,
                                          Principal principal){
        int offset = page * limit;
        return service.getUserHistory(offset, limit, principal.getName());
    }

  @GetMapping(path = "/reservations")
  public Page<ReservationHolder> getReservations( @RequestParam(value = "limit") int limit,
                                                  @RequestParam(value = "page") int page,
      Principal principal){
    int offset = page * limit;
    return service.getUserReservations(offset, limit, principal.getName());
  }

  @GetMapping(path = "/currentBooks")
  public Page<UserBookHolder> getCurrentUserBooks(@RequestParam(value = "limit") int limit,
                                                  @RequestParam(value = "page") int page,
      Principal principal){
    int offset = page * limit;
    return service.getCurrentUserBooks(offset, limit, principal.getName());
  }

  @GetMapping(path = "/reserve/{bookCopyId}")
  public long makeReservation(@PathVariable("bookCopyId") long bookCopyId, Principal principal){
      return service.reserveBook(bookCopyId, principal.getName());
  }

  @GetMapping(path = "/reservations/cancel/{reservationId}")
  public long cancelReservation(@PathVariable("reservationId") long reservationId){
    return service.cancelReservation(reservationId);
  }
}
