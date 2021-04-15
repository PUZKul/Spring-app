package kul.pl.biblioteka.api;


import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import kul.pl.biblioteka.model.BookCopy;
import com.monitorjbl.json.*;
import kul.pl.biblioteka.model.LibraryUser;
import kul.pl.biblioteka.model.UserHistory;
import kul.pl.biblioteka.service.UserLibraryService;
import kul.pl.biblioteka.utils.JSONFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.converter.json.MappingJacksonValue;
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
    public MappingJacksonValue getUserByUsername(Principal principal){
        Optional<LibraryUser> user = service.getUserByUsername(principal.getName());

        return new JSONFilter.Builder(user)
                   .exclude("role", "comment", "enabled", "banned")
                   .setFilter("userFilter")
                   .build()
                   .getMapper();
    }

//    @GetMapping(path = "/history")
//    public Page<UserHistory> getCopies(){
//        return service.getUserHistory();
//    }

}
