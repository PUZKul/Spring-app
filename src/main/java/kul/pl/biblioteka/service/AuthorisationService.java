package kul.pl.biblioteka.service;

import kul.pl.biblioteka.model.LibraryUser;
import kul.pl.biblioteka.repository.LibraryUserRepository;
import kul.pl.biblioteka.security.UserAuthorisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorisationService implements UserDetailsService {

    private final LibraryUserRepository userRepository;

    @Autowired
    public AuthorisationService(LibraryUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LibraryUser user = userRepository.getUserByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException(String.format("Could not find %s user", username));

        return new UserAuthorisation(user);
    }
}
