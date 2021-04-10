package kul.pl.biblioteka.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NewUserHolder {
    private final String username;

    private final String email;

    private final String password;

    public NewUserHolder(@JsonProperty("username")  String username,
                         @JsonProperty("email") String email,
                         @JsonProperty("password") String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
