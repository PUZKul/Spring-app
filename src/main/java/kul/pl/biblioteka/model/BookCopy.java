package kul.pl.biblioteka.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "book_copies")
public class BookCopy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "book_id")
    private long bookId;
    @Column
    private boolean borrowed;
    @Column
    private String code;
    @Column
    private boolean access;
    @Column
    private int damageLvl;
    @Column
    private String comment;

    @Formula("(select b.date_issued from borrow b where b.book_copy_id = id and b.date_return is null)")
    private Date dateIssued;

    public long getId() {
        return id;
    }

    public long getBookId() {
        return bookId;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public String getCode() {
        return code;
    }

    public boolean isAccess() {
        return access;
    }

    public int getDamageLvl() {
        return damageLvl;
    }

    public String getComment() {
        return comment;
    }

    public Date getDateIssued() {
        return dateIssued;
    }
}
