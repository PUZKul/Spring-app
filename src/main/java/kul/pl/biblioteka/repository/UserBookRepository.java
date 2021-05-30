package kul.pl.biblioteka.repository;

import kul.pl.biblioteka.model.UserBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;
import javax.transaction.Transactional;

public interface UserBookRepository extends JpaRepository<UserBook, Long> {

  @Query(value = "SELECT * FROM borrow WHERE book_copy_id = ? AND date_return IS NULL", nativeQuery = true)
  Optional<UserBook> getLastBorrow(long copyId);

  @Query("SELECT b FROM UserBook b WHERE b.userId = :userID")
  Collection<UserBook> findAllByUserName(@Param("userID") UUID userID);

  @Query("select b from UserBook b WHERE b.userId = :userId")
  Page<UserBook> findAllByUserId(UUID userId, Pageable pageable);

  @Query("select b from UserBook b WHERE b.dateReturn is null")
  List<UserBook> findAllBorrowed();

  @Query("select b.expectedTime from UserBook b WHERE b.id = :id")
  Date getExpectedDate(@Param("id") long id);

  @Transactional
  @Modifying
  @Query("UPDATE UserBook b SET b.expectedTime = :date WHERE b.id = :id")
  void setExpectedDate(@Param("id") long id, @Param("date") Date date);

  @Query("select b from UserBook b WHERE b.userId = :userId AND b.dateReturn is null")
  Page<UserBook> findCurrentByUserId(@Param("userId")UUID userId, Pageable pageable);

  @Query("select b from UserBook b WHERE b.userId = :userId AND b.dateReturn is not null")
  Page<UserBook> findReturnedByUserId(@Param("userId")UUID userId, Pageable pageable);

  @Query(value = "select count(*) from borrow where user_id = ? and date_return is null", nativeQuery = true)
  int getCurrentBooksNumber(UUID userId);

  @Transactional
  @Modifying
  @Query("UPDATE UserBook b SET b.dateReturn = :date WHERE b.id = :id")
  void setReturnDate(@Param("id") long borrowId, @Param("date") Date date);

  @Query("select b from UserBook b WHERE b.dateReturn is null")
  Page<UserBook> findCurrent(Pageable pageable);
}
