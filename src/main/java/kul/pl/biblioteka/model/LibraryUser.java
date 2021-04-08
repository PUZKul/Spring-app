package kul.pl.biblioteka.model;

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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }
}
