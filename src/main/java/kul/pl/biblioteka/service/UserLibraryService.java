package kul.pl.biblioteka.service;

import kul.pl.biblioteka.exception.ResourceNotFoundException;
import kul.pl.biblioteka.model.*;
import kul.pl.biblioteka.repository.LibraryUserRepository;
import kul.pl.biblioteka.repository.UserHistoryRepository;
import kul.pl.biblioteka.security.LibraryUserRole;
import kul.pl.biblioteka.security.PasswordConfig;
import kul.pl.biblioteka.utils.LibraryPage;
import kul.pl.biblioteka.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserLibraryService {
    private final LibraryUserRepository userRepository;
    private final UserHistoryRepository historyRepository;

    @Autowired
    public UserLibraryService(LibraryUserRepository userRepository, UserHistoryRepository historyRepository) {
        this.userRepository = userRepository;
        this.historyRepository = historyRepository;
    }

    public int registerNewUser(NewUserHolder newUser) {
        //validate user
        if(!Validator.username(newUser.getUsername()))
            throw new IllegalArgumentException(String.format("Given username '%s' is invalid", newUser.getUsername()));
        if(!Validator.email(newUser.getEmail()))
            throw new IllegalArgumentException(String.format("Given email '%s' is invalid", newUser.getEmail()));
        if(!Validator.password(newUser.getPassword()))
            throw new IllegalArgumentException("Given password is invalid");

        if(userRepository.getUserByUsername(newUser.getUsername()) != null) throw new EntityExistsException("User with given username already exist");
        if(userRepository.isEmailExist(newUser.getEmail()) > 0) throw new EntityExistsException("User with given email already exist");

        LibraryUser user = setNewUser(newUser);

        userRepository.save(user);
        return 1;
    }

    private LibraryUser setNewUser(NewUserHolder newUser) {
        return new LibraryUser(
                UUID.randomUUID(),
                newUser.getUsername(),
                PasswordConfig.encoder().encode(newUser.getPassword()),
                newUser.getEmail(),
                3,
                0,
                0,
                false,
                true,
                LibraryUserRole.USER.name());
    }

    public Page<UserHistory> getUserHistory() {
        Pageable pageable = new LibraryPage(0, 50);
        return historyRepository.findAll(pageable);
    }

    public Optional<LibraryUser> getUserByUsername(String name) {
        return Optional.ofNullable(userRepository.getUserByUsername(name));
    }

    // getUserById
    // editUserAccount
    // reserveBook
    // register
    // getOwnedBook
    // getUserHistory
    // getRanking

}
