package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PasswordResetForm {
    @NotNull
    @NotEmpty
    @Size(min = 8, max=255)
    private String password;
    @NotNull
    @NotEmpty
    @Size(min = 8, max=255)
    private String passwordConfirmation;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }
}
