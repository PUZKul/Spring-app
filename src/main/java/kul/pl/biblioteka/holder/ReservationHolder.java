package kul.pl.biblioteka.holder;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class ReservationHolder {
  private long id;
  private UUID userId;
  private long bookId;
  private long bookCopyId;
  private String title;
  private String imageUrl;
  private Date dateReservation;
  private Date dateBorrow;
}
