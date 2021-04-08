package kul.pl.biblioteka.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    @DisplayName("NameValidator test")
    @ParameterizedTest(name = "\"{0}\" should be {1}")
    @CsvSource({
            "Michał, false",
            "Marek, true",
            "Ola123, true",
            "ola_321, true",
            "mini-man, false",
            "a, false",
            "mareknlkojknszymondfilksol, false",
            "j()ack, false",
            "_mirek, false",
            "0mirek, false",
            "S7@e&s, false",
            "' ', false"
    })
    public void username_shouldValidateGivenUserNames(String username, boolean expected){
        //when
        boolean result = Validator.username(username);

        //then
        assertEquals(result, expected);
    }

    @DisplayName("EmailValidator test")
    @ParameterizedTest(name = "\"{0}\" should be {1}")
    @CsvSource({
            "michal@onet, false",
            "marekonet.pl, false",
            "marek@onet.pl, true",
            "marek@onet.com, true",
            "marek@gmail.com, true",
            "marek.m@vp.pl, true",
            "mini-man@wp.pl, true",
            "a, false",
            "mareknlkojknszymondfilksol, false",
            "j()ack@.gmail.com, false",
            "mirek@gmailcom, false",
            "mirek@.com, false",
            "' ', false"
    })
    public void email_shouldValidateGivenEmails(String email, boolean expected){
        //when
        boolean result = Validator.email(email);

        //then
        assertEquals(result, expected);
    }


    @DisplayName("PasswordValidator test")
    @ParameterizedTest(name = "\"{0}\" should be {1}")
    @CsvSource({
            "ola1, false",
            "a, false",
            "adammarek, false",
            "adam12345, false",
            "12345678, false",
            "adam123!, true",
            "mini&mini2, true",
            "alexander, false",
            "123#mareknlkojknszymondfilksol, false",
            "jack@gmail.com, false",
            "SQ7YNy?PM.7QAv/zWU7Z, true",
            "FVC#8X9BTZEk7$xt, true",
            "' ', false"
    })
    public void password_shouldValidateGivenPasswords(String password, boolean expected){
        //when
        boolean result = Validator.password(password);

        //then
        assertEquals(result, expected);
    }

}