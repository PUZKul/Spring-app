package kul.pl.biblioteka.api;

import kul.pl.biblioteka.model.LibraryUser;
import kul.pl.biblioteka.model.NewUserHolder;
import kul.pl.biblioteka.service.UserLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RegisterUserController {
    private final UserLibraryService service;

    @Autowired
    public RegisterUserController(UserLibraryService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public int registerNewUser(@Validated @RequestBody NewUserHolder newUser){
        // TODO check for nulls automatically
        if(newUser.getUsername() == null) throw new IllegalArgumentException("username cannot be null");
        if(newUser.getEmail() == null) throw new IllegalArgumentException("email cannot be null");
        if(newUser.getPassword() == null) throw new IllegalArgumentException("password cannot be null");
        return service.registerNewUser(newUser);
    }

}
