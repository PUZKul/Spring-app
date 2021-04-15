package kul.pl.biblioteka.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;
@Entity
@Table(name = "borrow")
//@SecondaryTable(name = "books", pkJoinColumns = {@PrimaryKeyJoinColumn(name = "bookId", referencedColumnName = "bookCopy"),@PrimaryKeyJoinColumn(name="id", referencedColumnName = "bookId")})
public class  UserHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "user_id")
    private UUID userId;

//    @JoinColumn(name = "title", table = "books")
//    private String title;

    @ManyToOne
    @JoinColumn(name = "book_copy_id")
    private BookCopy bookCopy;

    @Column
    private Date dateIssued;
    @Column
    private Date dateReturn;

    public UserHistory(@JsonProperty("userId") UUID userId,

                       @JsonProperty("bookCopyId") BookCopy bookCopy,
                       @JsonProperty("borrowDate") Date dateIssued,
                       @JsonProperty("returnedDate") Date dateReturn) {
        this.userId = userId;

        this.bookCopy = bookCopy;
        this.dateIssued = dateIssued;
        this.dateReturn = dateReturn;
    }

    public UUID getUserId() {
        return userId;
    }



    public BookCopy getBookCopy() {
        return bookCopy;
    }

    public Date getDateIssued() {
        return dateIssued;
    }

    public Date getDateReturn() {
        return dateReturn;
    }

}
