package kul.pl.biblioteka.repository;

import kul.pl.biblioteka.model.BookReservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ReservationRepository extends CrudRepository<BookReservation, Long> {

  @Query(value = "SELECT * from reservations where user_id = ?", nativeQuery = true)
  Page<BookReservation> findAllByUserId(UUID userId, Pageable pageable);
}
