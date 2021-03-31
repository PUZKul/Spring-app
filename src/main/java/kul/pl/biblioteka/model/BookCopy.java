package kul.pl.biblioteka.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "book_copies")
public class BookCopy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private LibraryBook libraryBook;

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

    public long getId() {
        return id;
    }

    public LibraryBook getLibraryBook() {
        return libraryBook;
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
}
