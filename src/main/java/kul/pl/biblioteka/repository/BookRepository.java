package kul.pl.biblioteka.repository;

import kul.pl.biblioteka.model.LibraryBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<LibraryBook, Long> {

  @Query(value = "SELECT * FROM books WHERE title ilike %?% AND author ilike %?% AND publisher ilike %?%", nativeQuery = true)
  Page<LibraryBook> searchBooks(
      String titleQuery, String authorQuery, String publisherQuery, Pageable pageable);

  @Query(value = "SELECT title FROM books WHERE id = ?", nativeQuery = true)
  String getBookTitle(long id);

  @Query(value = "SELECT image_url FROM books WHERE id = ?", nativeQuery = true)
  String getBookImage(long id);
}
