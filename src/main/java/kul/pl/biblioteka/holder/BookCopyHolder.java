package kul.pl.biblioteka.holder;

import lombok.Data;

import java.util.Date;

@Data
public class BookCopyHolder {
    private long id;
    private long bookId;
    private boolean borrow;
    private boolean access;
    private String code;
    private Date approximateDate;

    public BookCopyHolder(long id, long bookId, boolean borrow, boolean access, String code, Date approximateDate) {
        this.id = id;
        this.bookId = bookId;
        this.borrow = borrow;
        this.access = access;
        this.code = code;
        this.approximateDate = approximateDate;
    }
}
