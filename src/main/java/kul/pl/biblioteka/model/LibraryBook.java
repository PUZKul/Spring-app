package kul.pl.biblioteka.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "books")
public class LibraryBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;
    @Column(name = "author")
    private String authors;
    @Column
    private double rating;
    @Column
    private double popularity;
    @Column
    private int pages;
    @Column
    private Date year;
    @Column
    private String publisher;
    @Column
    private String imageUrl;

    public LibraryBook() {
    }

    public LibraryBook(@JsonProperty("id") int id,
                       @JsonProperty("title") String title,
                       @JsonProperty("authors") String authors,
                       @JsonProperty("rating") double rating,
                       @JsonProperty("popularity") double popularity,
                       @JsonProperty("pages") int pages,
                       @JsonProperty("year") Date year,
                       @JsonProperty("publisher") String publisher,
                       @JsonProperty("imageUrl") String imageUrl)
    {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.rating = rating;
        this.popularity = popularity;
        this.pages = pages;
        this.year = year;
        this.publisher = publisher;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        return authors;
    }

    public double getRating() {
        return rating;
    }

    public double getPopularity() {
        return popularity;
    }

    public int getPages() {
        return pages;
    }

    public Date getYear() {
        return year;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getImageUrl() {
        return imageUrl;
    }

}
