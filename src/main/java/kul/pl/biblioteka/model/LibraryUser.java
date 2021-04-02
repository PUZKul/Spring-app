package kul.pl.biblioteka.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class LibraryUser {
    private UUID id;
    private String username;
    private String email;
    private int points;
    private int warnings;
    private boolean banned;
    private String role;

    public LibraryUser() {
    }

    public LibraryUser(@JsonProperty("id") UUID id,
                       @JsonProperty("username") String username,
                       @JsonProperty("email") String email) {
        this.id = id;
        this.username = username;
        this.email = email;
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
        return banned;
    }

    public String getRole() {
        return role;
    }
}
