package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PasswordResetForm {
    @NotNull(message = "Debe ingresar su nueva contraseña")
    @NotEmpty(message = "Debe ingresar su nueva contraseña")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres.")
    private String password;
    @NotNull(message = "Debe ingresar su nueva contraseña")
    @NotEmpty(message = "Debe ingresar su nueva contraseña")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres.")
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
