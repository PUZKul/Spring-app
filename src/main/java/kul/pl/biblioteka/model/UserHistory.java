package kul.pl.biblioteka.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.UUID;

public class  UserHistory {
    private final UUID userId;
    private final String title;
    private final long bookId;
    private final long bookCopyId;
    private final Date borrowDate;
    private final Date returnedDate;

    public UserHistory(@JsonProperty("userId") UUID userId,
                       @JsonProperty("title") String title,
                       @JsonProperty("bookId") long bookId,
                       @JsonProperty("bookCopyId") long bookCopyId,
                       @JsonProperty("borrowDate") Date borrowDate,
                       @JsonProperty("returnedDate") Date returnedDate) {
        this.userId = userId;
        this.title = title;
        this.bookId = bookId;
        this.bookCopyId = bookCopyId;
        this.borrowDate = borrowDate;
        this.returnedDate = returnedDate;
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

    public Date getBorrowDate() {
        return borrowDate;
    }

    public Date getReturnedDate() {
        return returnedDate;
    }

}
