package kul.pl.biblioteka.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;
@Entity
@Table(name = "users")
public class LibraryUser {
    @Id
    private UUID id;
    @Column(name = "nick")
    private String username;
    @Column
    private String password;
    @Column
    private String email;
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

    public LibraryUser(@JsonProperty("username") String username,
                       @JsonProperty("email") String email,
                       @JsonProperty("password") String password) {
        this.username = username;
        this.email = email;
        this.password = password;
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
