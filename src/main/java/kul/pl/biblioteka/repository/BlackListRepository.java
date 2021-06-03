package kul.pl.biblioteka.repository;

import kul.pl.biblioteka.model.BlackList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;

public interface BlackListRepository extends JpaRepository<BlackList, Long> {

  @Transactional
  @Modifying
  @Query("UPDATE BlackList b SET b.dateTo = current_date, b.status='RELEASED' WHERE b.userId = :userId")
  void removeUser(@Param("userId") UUID userId);

  @Query("SElECT b FROM BlackList b WHERE b.dateTo is null AND b.userId = :userId")
  Optional<BlackList> findByUserId(@Param("userId") UUID userId);
}
