package kul.pl.biblioteka.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


public class PasswordConfig {

    public static PasswordEncoder encoder(){
        return new BCryptPasswordEncoder(10);
    }
}
