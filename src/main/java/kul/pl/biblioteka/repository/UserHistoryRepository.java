package kul.pl.biblioteka.repository;

import kul.pl.biblioteka.model.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {
    @Query(value = "SELECT * FROM borrow WHERE book_copy_id = ? and date_return is null", nativeQuery = true)
    Optional<UserHistory> getLastBorrow(long copyId);


}
