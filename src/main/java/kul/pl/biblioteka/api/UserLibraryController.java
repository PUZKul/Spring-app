package kul.pl.biblioteka.api;


import kul.pl.biblioteka.holder.EditUserHolder;
import kul.pl.biblioteka.model.LibraryUser;
import kul.pl.biblioteka.holder.UserHolder;
import kul.pl.biblioteka.service.UserLibraryService;
import kul.pl.biblioteka.utils.JSONFilter;
import org.springframework.beans.factory.annotation.Autowired;
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

//    @GetMapping(path = "/history")
//    public Page<UserHistory> getCopies(){
//        return service.getUserHistory();
//    }

}
