package kul.pl.biblioteka.service;

import kul.pl.biblioteka.exception.AuthorisationException;
import kul.pl.biblioteka.exception.ResourceNotFoundException;
import kul.pl.biblioteka.holder.EditUserHolder;
import kul.pl.biblioteka.holder.UserHolder;
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
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import javax.persistence.EntityExistsException;
import java.util.Optional;
import java.util.UUID;

import static kul.pl.biblioteka.utils.Helper.isNullOrEmpty;


@Service
public class UserLibraryService {
    private final LibraryUserRepository userRepository;
    private final UserHistoryRepository historyRepository;

    @Autowired
    public UserLibraryService(LibraryUserRepository userRepository, UserHistoryRepository historyRepository) {
        this.userRepository = userRepository;
        this.historyRepository = historyRepository;
    }

    public int registerNewUser(UserHolder newUser) {
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

    public int editUser(EditUserHolder user, String username) {
        Optional<LibraryUser> repoUser = getUserByUsername(username);
        if(repoUser.isEmpty()) throw new ResourceNotFoundException("User not exist");
        if(!confirmPassword(user.getOldPassword(), repoUser.get().getPassword())) throw new AuthorisationException("Incorrect password");

        if(isNullOrEmpty(user.getEmail()))
            user.setEmail(repoUser.get().getEmail());
        else
            if(!Validator.email(user.getEmail())) throw new IllegalArgumentException(String.format("Given email '%s' is invalid", user.getEmail()));

        if(isNullOrEmpty(user.getNewPassword()))
            user.setNewPassword(repoUser.get().getPassword());
        else{
            if(!Validator.password(user.getNewPassword()))
                throw new IllegalArgumentException("Given password is invalid");

            String encode = PasswordConfig.encoder().encode(user.getNewPassword());
            user.setNewPassword(encode);
        }
        return userRepository.editUserData(user.getEmail(), user.getNewPassword(), username);
    }


    public Page<UserHistory> getUserHistory() {
        Pageable pageable = new LibraryPage(0, 50);
        return historyRepository.findAll(pageable);
    }

    public Optional<LibraryUser> getUserByUsername(String name) {
        return Optional.ofNullable(userRepository.getUserByUsername(name));
    }





    // editUserAccount
    // reserveBook
    // getOwnedBook
    // getUserHistory
    // getRanking


    private boolean confirmPassword(String receivedPassword, String currentPassword){
        return PasswordConfig.encoder().matches(receivedPassword, currentPassword);
    }

    private LibraryUser setNewUser(UserHolder newUser) {
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

}
