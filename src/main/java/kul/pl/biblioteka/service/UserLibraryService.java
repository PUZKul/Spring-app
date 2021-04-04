package kul.pl.biblioteka.service;

import kul.pl.biblioteka.repository.LibraryUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLibraryService {
    private final LibraryUserRepository userRepository;

    @Autowired
    public UserLibraryService(LibraryUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // getUserById
    // editUserAccount
    // reserveBook
    // register
    // getOwnedBook
    // getUserHistory
    // getRanking

}
