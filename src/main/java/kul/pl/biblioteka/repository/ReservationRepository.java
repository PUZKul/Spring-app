package kul.pl.biblioteka.repository;

import kul.pl.biblioteka.model.BookReservation;
import kul.pl.biblioteka.model.LibraryBook;
import kul.pl.biblioteka.utils.ReservationState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;

public interface ReservationRepository extends JpaRepository<BookReservation, Long> {

  @Query(value = "SELECT * from reservations where user_id = ?", nativeQuery = true)
  Page<BookReservation> findAllByUserId(UUID userId, Pageable pageable);

  @Transactional
  @Modifying
  @Query("UPDATE BookReservation r SET r.state = :state WHERE r.id = :id")
  void changeStatus(@Param("id") long reservationId, @Param("state") ReservationState state);

  @Query("SELECT r FROM BookReservation r WHERE r.state = :state")
  Page<BookReservation> findAllByState(@Param("state") ReservationState state, Pageable pageable);

  @Query("SELECT r FROM BookReservation r WHERE r.state = :state")
  List<BookReservation> findAllByState(@Param("state") ReservationState state);

  @Query("SELECT r FROM BookReservation r WHERE r.state = :state and r.userId = :userId")
  Page<BookReservation> findByStateAndUserId(@Param("state") ReservationState state, @Param("userId") UUID userId, Pageable pageable);

}
