package kul.pl.biblioteka.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;
@Entity
@Table(name = "borrow")
@SecondaryTable(name = "books", pkJoinColumns = {@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")})
public class  UserHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "user_id")
    private UUID userId;

    @JoinColumn(name = "title", table = "books")
    private String title;
    @Column(name = "book_id")
    private long bookId;
    @Column(name = "book_copy_id")
    private long bookCopyId;
    @Column
    private Date dateIssued;
    @Column
    private Date dateReturn;

    public UserHistory(@JsonProperty("userId") UUID userId,
                       @JsonProperty("title") String title,
                       @JsonProperty("bookId") long bookId,
                       @JsonProperty("bookCopyId") long bookCopyId,
                       @JsonProperty("borrowDate") Date dateIssued,
                       @JsonProperty("returnedDate") Date dateReturn) {
        this.userId = userId;
        this.title = title;
        this.bookId = bookId;
        this.bookCopyId = bookCopyId;
        this.dateIssued = dateIssued;
        this.dateReturn = dateReturn;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public long getBookId() {
        return bookId;
    }

    public long getBookCopyId() {
        return bookCopyId;
    }

    public Date getDateIssued() {
        return dateIssued;
    }

    public Date getDateReturn() {
        return dateReturn;
    }

}
