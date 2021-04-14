package kul.pl.biblioteka.repository;

import kul.pl.biblioteka.model.BookCopy;
import kul.pl.biblioteka.model.LibraryBook;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface BookCopiesRepository extends JpaRepository<BookCopy, Long> {

    @Query(value = "SELECT count(id) from book_copies where book_id = ?", nativeQuery = true)
    int availableCopies(long bookId);

    @Query(value = "SELECT * FROM book_copies WHERE book_id = ?", nativeQuery = true)
    Page<BookCopy> getCopiesByBookId(long bookId, Pageable pageable);
}
