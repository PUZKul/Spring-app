package kul.pl.biblioteka.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;


@Entity
@Table(name = "users")
@JsonFilter("userFilter")
public class LibraryUser {
    @Id
    private UUID id;
    @Column(name = "nick")
    private String username;

    @JsonIgnore
    @Column
    private String password;
    @Column
    private String email;
    @Column(name = "max_books")
    private int maxBooks;
    @Column
    private int points;
    @Column
    private int warnings;
    @Column(name = "is_banned")
    private boolean isBanned;
    @Column(name = "is_enabled")
    private boolean isEnabled;
    @Column
    private String role;
    @Column
    private String comment;


    public LibraryUser() {
    }

    public LibraryUser(UUID id, String username, String password, String email, int maxBooks, int points, int warnings, boolean isBanned, boolean isEnabled, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.maxBooks = maxBooks;
        this.points = points;
        this.warnings = warnings;
        this.isBanned = isBanned;
        this.isEnabled = isEnabled;
        this.role = role;
    }

    public int getPoints() {
        return points;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public int getWarnings() {
        return warnings;
    }

    public int getMaxBooks() {
        return maxBooks;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public String getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public String getComment() {
        return comment;
    }

}
