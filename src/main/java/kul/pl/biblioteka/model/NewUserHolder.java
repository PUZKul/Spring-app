package kul.pl.biblioteka.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NewUserHolder {
    private final String nick;
    private final String email;
    private final String password;

    public NewUserHolder(@JsonProperty("nick") String nick,
                         @JsonProperty("email") String email,
                         @JsonProperty("password") String password) {
        this.nick = nick;
        this.email = email;
        this.password = password;
    }

    public String getNick() {
        return nick;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
