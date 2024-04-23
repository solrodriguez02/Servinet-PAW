package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.validation.EmailRegistered;

import javax.validation.constraints.NotNull;

public class RequestPasswordRecoveryForm {
   @NotNull(message="Debe ingresar un email para restablecer su contraseña")
   @EmailRegistered(message="El email ingresado no está registrado en el sistema")
   String email;
   public String getEmail() {
         return email;
   }
   public void setEmail(String email) {
         this.email = email;
   }
}
