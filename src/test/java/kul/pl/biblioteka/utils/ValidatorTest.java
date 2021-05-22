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

  @DisplayName("NameValidator test")
  @ParameterizedTest(name = "\"{0}\" should be {1}")
  @CsvSource({
      "ola1, false",
      "a, false",
      "adammarek, true",
      "adam12345, false",
      "Jan-Maria, true",
      "Bożena, true",
  })
  public void name_shouldValidateGivenNames(String name, boolean expected){
    //when
    boolean result = Validator.name(name);
    //then
    assertEquals(result, expected);
  }

  @DisplayName("AddressValidator test")
  @ParameterizedTest(name = "\"{0}\" should be {1}")
  @CsvSource({
      "090 as0d0(0+, false",
      "Lublin 12-400, true",
      "ul. Konstantynow 12, true",
  })
  public void shouldValidateGivenAddress(String address, boolean expected){
    //when
    boolean result = Validator.address(address);
    //then
    assertEquals(result, expected);
  }

  @DisplayName("PhoneValidator test")
  @ParameterizedTest(name = "\"{0}\" should be {1}")
  @CsvSource({
      "123456789, true",
      "+49123456789, true",
      "90-2312231231, false",
  })
  public void shouldValidateGivenPhone(String phone, boolean expected){
    //when
    boolean result = Validator.phone(phone);
    //then
    assertEquals(result, expected);
  }

}