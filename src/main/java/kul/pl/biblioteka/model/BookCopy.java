package kul.pl.biblioteka.model;

import lombok.Getter;
import lombok.Value;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
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

}
