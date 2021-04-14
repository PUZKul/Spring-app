package kul.pl.biblioteka.repository;

import kul.pl.biblioteka.model.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {

}
