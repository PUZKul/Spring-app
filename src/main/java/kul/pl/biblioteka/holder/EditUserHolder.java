package kul.pl.biblioteka.holder;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EditUserHolder {
    private String email;
    private String newPassword;
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private String oldPassword;
}
