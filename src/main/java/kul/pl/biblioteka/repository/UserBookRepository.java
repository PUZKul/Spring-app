package kul.pl.biblioteka.repository;

import kul.pl.biblioteka.model.UserBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface UserBookRepository extends JpaRepository<UserBook, Long> {

  @Query(value = "SELECT * FROM borrow WHERE book_copy_id = ? AND date_return IS NULL", nativeQuery = true)
  Optional<UserBook> getLastBorrow(long copyId);

  @Query("SELECT b FROM UserBook b WHERE b.userId = :userID")
  Collection<UserBook> findAllByUserName(@Param("userID") UUID userID);

  @Query("select b from UserBook b WHERE b.userId = :userId")
  Page<UserBook> findAllByUserId(UUID userId, Pageable pageable);
}