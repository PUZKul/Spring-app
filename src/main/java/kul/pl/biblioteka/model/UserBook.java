package kul.pl.biblioteka.model;

import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "borrow")
@Getter
public class UserBook {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @Column(name = "user_id")
  private UUID userId;

  @ManyToOne
  @JoinColumn(name = "book_copy_id")
  private BookCopy bookCopy;

  private Date dateIssued;
  private Date dateReturn;
  @Column(name = "reservation_id")
  private Long reservationId;
  private Date expectedTime;

  public UserBook(
      @JsonProperty("userId") UUID userId,
      @JsonProperty("bookCopyId") BookCopy bookCopy,
      @JsonProperty("borrowDate") Date dateIssued,
      @JsonProperty("returnedDate") Date dateReturn,
      @JsonProperty("reservationId") Long reservationId,
      @JsonProperty("expectedTime") Date expectedTime) {
    this.userId = userId;
    this.bookCopy = bookCopy;
    this.dateIssued = dateIssued;
    this.dateReturn = dateReturn;
    this.reservationId = reservationId;
    this.expectedTime = expectedTime;
  }

  public UserBook() {

  }
}
