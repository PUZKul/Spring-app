package kul.pl.biblioteka.holder;

public class EditUserHolder {
    private String email;
    private String newPassword;
    private String oldPassword;

    public EditUserHolder(String email, String newPassword, String oldPassword) {
        this.email = email;
        this.newPassword = newPassword;
        this.oldPassword = oldPassword;
    }

    public String getEmail() {
        return email;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
