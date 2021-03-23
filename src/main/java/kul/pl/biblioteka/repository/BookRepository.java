package kul.pl.biblioteka.repository;

import kul.pl.biblioteka.model.LibraryBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<LibraryBook, Integer> {

}
