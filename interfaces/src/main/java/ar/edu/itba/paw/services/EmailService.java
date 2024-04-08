package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.Appointment;

import javax.mail.MessagingException;
import org.thymeleaf.context.Context;

public interface EmailService {

    // ! tuve q agregar dependencia javax.mail en pom
    public void requestAppointment(Appointment appointment, String userMail) throws MessagingException ;

    // saque Locale
    public void sendMail(final String recipientEmail, final String subject, final String template, final Context context) throws MessagingException;
}
