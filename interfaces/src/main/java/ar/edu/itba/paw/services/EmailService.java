package ar.edu.itba.paw.services;

import javax.mail.MessagingException;
import java.util.Locale;

public interface EmailService {

    // ! tuve q agregar dependencia javax.mail en pom
    public void sendMail(final String recipientName, final String recipientEmail, final Locale locale) throws MessagingException;
}
