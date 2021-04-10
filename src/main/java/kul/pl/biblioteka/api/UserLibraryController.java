package kul.pl.biblioteka.api;

import kul.pl.biblioteka.service.UserLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/library/users")
public class UserLibraryController {
    private final UserLibraryService service;

    @Autowired
    public UserLibraryController(UserLibraryService service) {
        this.service = service;
    }
}
