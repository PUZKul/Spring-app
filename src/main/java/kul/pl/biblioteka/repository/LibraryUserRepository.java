package kul.pl.biblioteka.repository;

import kul.pl.biblioteka.model.LibraryBook;
import kul.pl.biblioteka.model.LibraryUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface LibraryUserRepository extends CrudRepository<LibraryUser, UUID> {

    @Query("SELECT u FROM LibraryUser u WHERE u.username = :username")
    LibraryUser getUserByUsername(@Param("username") String username);

    @Query(value = "SELECT count(*) FROM users u WHERE email = ?", nativeQuery = true)
    int isEmailExist(String email);


}
