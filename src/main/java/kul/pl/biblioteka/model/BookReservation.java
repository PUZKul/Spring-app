package kul.pl.biblioteka.model;

import lombok.Data;

import kul.pl.biblioteka.utils.ReservationState;

import java.util.Date;
import java.util.UUID;
import javax.persistence.*;

@Entity
@Table(name = "reservations")
@Data
public class BookReservation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @ManyToOne
  @JoinColumn(name = "book_copy_id")
  private BookCopy bookCopy;
  @Column(name = "user_id")
  private UUID userId;
  @Enumerated(EnumType.STRING)
  @Column
  private ReservationState state;
  @Column(name = "date_reservation")
  private Date dateReservation;
  @Column(name = "date_borrow")
  private Date dateBorrow;
}
