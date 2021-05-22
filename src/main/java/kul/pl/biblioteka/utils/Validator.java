package kul.pl.biblioteka.utils;

import lombok.NonNull;

public class Validator {
    public static boolean username(String name){
        if(name.length() < 3 || name.length() > 24) return false;
        return name.matches("^[a-zA-Z]\\w*$");
    }

    @NonNull
    public static boolean email(String email) {
        if(email == null) return false;
        return email.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");
    }

    public static boolean password(String password) {
        return password.matches("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@!#$%^&-+=().]).{8,25}$");
    }

    public static boolean name(String name) {
        return name.matches("^[\\p{L}'][ \\p{L}'-]*[\\p{L}]$");
    }

    public static boolean address(String address) {
      return address.matches("^[#.0-9\\p{L}\\s\\/,-]+$");
    }

    public static boolean phone(String phone) {
      return phone.matches("^[+]?[0-9]{9,11}$");
    }
}
