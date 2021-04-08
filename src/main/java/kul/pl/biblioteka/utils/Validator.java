package kul.pl.biblioteka.utils;

public class Validator {
    public static boolean username(String name){
        if(name.length() < 3 || name.length() > 24) return false;
        return name.matches("^[a-zA-Z]\\w*$");
    }

    public static boolean email(String email) {
        if(email == null) return false;
        return email.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");
    }

    public static boolean password(String password) {
        return password.matches("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@!#$%^&-+=().]).{8,25}$");
    }
}
