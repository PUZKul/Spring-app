package kul.pl.biblioteka.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class LibraryUser {
    private final UUID id;
    private final String username;
    private final String email;
    private final int points;

    public LibraryUser(@JsonProperty("id") UUID id,
                       @JsonProperty("username") String username,
                       @JsonProperty("email") String email, int points) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.points = points;
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
}
