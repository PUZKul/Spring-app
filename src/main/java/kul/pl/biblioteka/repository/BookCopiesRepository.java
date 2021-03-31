package kul.pl.biblioteka.repository;

import kul.pl.biblioteka.model.BookCopy;
import kul.pl.biblioteka.model.LibraryBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookCopiesRepository extends JpaRepository<BookCopy, Long> {

    @Query(value = "SELECT count(id) from book_copies where book_id = ?", nativeQuery = true)
    int availableCopies(long bookId);
}
