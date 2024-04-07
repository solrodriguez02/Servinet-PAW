package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.Appointment;

import javax.mail.MessagingException;
import java.util.Locale;

public interface EmailService {

    // ! tuve q agregar dependencia javax.mail en pom
    public void requestAppointment(Appointment appointment, String userMail) throws MessagingException ;

    public void sendMail( final String recipientEmail, final String subject, final String content, final Locale locale) throws MessagingException;
}
