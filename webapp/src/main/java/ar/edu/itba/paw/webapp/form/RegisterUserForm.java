package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.validation.EmailNotUsed;
import ar.edu.itba.paw.webapp.validation.FieldsValueMatch;
import ar.edu.itba.paw.webapp.validation.UsernameNotUsed;

import javax.validation.constraints.*;
@FieldsValueMatch(field = "password", fieldMatch = "passwordConfirmation", message = "Las contraseñas no coinciden")
public class RegisterUserForm {
   @NotEmpty(message = "Debe ingresar su nombre")
   @NotNull(message = "Debe ingresar su nombre")
   private String name;
   @NotNull(message = "Debe ingresar su apellido")
   @NotEmpty(message = "Debe ingresar su apellido")
   private String surname;
   @NotNull(message = "Debe ingresar un email")
   @NotEmpty(message = "Debe ingresar un email")
   @Email(message = "El email no es válido.")
   @EmailNotUsed(message = "El email ingresado ya está en uso. Use otro email o si es el propietario de la cuenta inicie sesión")
   private String email;
   @NotNull(message = "Debe ingresar un nombre de usuario")
   @NotEmpty(message = "Debe ingresar un nombre de usuario ")
   @UsernameNotUsed(message = "El nombre de usuario ingresado ya está en uso. Use otro nombre de usuario o si es el propietario de la cuenta inicie sesión")
   private String username;
   @NotNull(message = "Debe ingresar una contraseña")
   @NotEmpty(message = "Debe ingresar alguna contraseña de al menos 8 caracteres")
   @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres.")
   private String password;
   @NotNull(message = "Debe ingresar la confirmación de la contraseña")
   @NotEmpty(message = "Debe ingresar la confirmación de la contraseña")
   @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres.")
   private String passwordConfirmation;
   @NotNull(message = "Debe ingresar un número de teléfono")
   @NotEmpty(message = "Debe ingresar un número de teléfono")
   private String telephone;

   public String getUsername(){
       return username;
   }

   public String getName() {
       return name;
   }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public String getSurname() {
        return surname;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

}
