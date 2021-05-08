package kul.pl.biblioteka.holder;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class UserBookHolder {
  private long id;
  private UUID userId;
  private long bookId;
  private long bookCopyId;
  private String title;
  private String imageUrl;
  private Date dateIssued;
  private Date dateReturn;
}
