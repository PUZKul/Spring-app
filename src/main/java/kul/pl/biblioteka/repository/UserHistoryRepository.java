package kul.pl.biblioteka.repository;

import kul.pl.biblioteka.model.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {

  @Query(value = "SELECT * FROM borrow WHERE book_copy_id = ? AND date_return IS NULL", nativeQuery = true)
  Optional<UserHistory> getLastBorrow(long copyId);

  @Query("SELECT b FROM UserHistory b WHERE b.userId = :userID")
  Collection<UserHistory> findAllByUserName(@Param("userID") UUID userID);
}
