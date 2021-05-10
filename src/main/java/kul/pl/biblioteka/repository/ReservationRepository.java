package kul.pl.biblioteka.repository;

import kul.pl.biblioteka.model.BookReservation;
import kul.pl.biblioteka.utils.ReservationState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;
import javax.transaction.Transactional;

public interface ReservationRepository extends CrudRepository<BookReservation, Long> {

  @Query(value = "SELECT * from reservations where user_id = ?", nativeQuery = true)
  Page<BookReservation> findAllByUserId(UUID userId, Pageable pageable);

  @Transactional
  @Modifying
  @Query("UPDATE BookReservation r SET r.state = :state WHERE r.id = :id")
  void cancel(@Param("id") long reservationId, @Param("state") ReservationState state);
}
