package kul.pl.biblioteka.api;


import kul.pl.biblioteka.model.BookCopy;
import com.monitorjbl.json.*;
import kul.pl.biblioteka.model.LibraryUser;
import kul.pl.biblioteka.model.UserHistory;
import kul.pl.biblioteka.service.UserLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("api/library/users")
public class UserLibraryController {

    private final UserLibraryService service;

    @Autowired
    public UserLibraryController(UserLibraryService service) {
        this.service = service;
    }

    @GetMapping
    public Optional<LibraryUser> getUserByUsername(Principal principal){
        return service.getUserByUsername(principal.getName());
    }

    @GetMapping(path = "/history")
    public Page<UserHistory> getCopies(){
        return service.getUserHistory();
    }



}
