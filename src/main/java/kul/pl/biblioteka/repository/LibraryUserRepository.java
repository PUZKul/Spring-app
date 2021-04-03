package kul.pl.biblioteka.repository;

import kul.pl.biblioteka.model.LibraryBook;
import kul.pl.biblioteka.model.LibraryUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LibraryUserRepository extends JpaRepository<LibraryUser, UUID> {
}
