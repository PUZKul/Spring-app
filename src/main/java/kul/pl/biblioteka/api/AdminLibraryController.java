package kul.pl.biblioteka.api;

import kul.pl.biblioteka.service.AdminLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/library/admins")

public class AdminLibraryController {

    private final AdminLibraryService service;

    @Autowired
    public AdminLibraryController(AdminLibraryService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("confirmBorrow/{reservationId}")
    public long confirmBorrow(@PathVariable("reservationId") long reservationId){
        return service.confirmBorrow(reservationId);
    }
}
