package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.validation.EmailRegistered;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RequestPasswordRecoveryForm {
   @NotNull
   @EmailRegistered
   @Size(max=255)
   String email;

   public String getEmail() {
         return email;
   }
   public void setEmail(String email) {
         this.email = email;
   }
}
