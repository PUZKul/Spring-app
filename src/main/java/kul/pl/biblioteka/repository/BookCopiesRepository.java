package kul.pl.biblioteka.repository;

import kul.pl.biblioteka.model.BookCopy;
import kul.pl.biblioteka.model.LibraryBook;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;

public interface BookCopiesRepository extends JpaRepository<BookCopy, Long> {

    @Query(value = "SELECT count(id) from book_copies where book_id = ?", nativeQuery = true)
    int availableCopies(long bookId);

    @Query(value = "SELECT * FROM book_copies WHERE book_id = ?", nativeQuery = true)
    List<BookCopy> getCopiesByBookId(long bookId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE book_copies SET borrowed = TRUE WHERE id = ?", nativeQuery = true)
    void markAsBorrowed(long bookCopyId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE book_copies SET borrowed = FALSE WHERE id = ?", nativeQuery = true)
    void markAsFree(long bookCopyId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE book_copies SET approximate = FALSE WHERE id = ?", nativeQuery = true)
    void setApproximateDate(Date addDaysFromToday);
}
