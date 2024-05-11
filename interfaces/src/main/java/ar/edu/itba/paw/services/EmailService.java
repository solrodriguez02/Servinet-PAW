package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.*;

import javax.mail.MessagingException;

import org.springframework.scheduling.annotation.Async;

public interface EmailService {

    void requestAppointment(Appointment appointment, Service service, Business business, User client) throws MessagingException;
    void recoverPassword(User user, PasswordRecoveryCode passwordRecoveryCode) throws MessagingException;

    void confirmNewPassword(User user) throws MessagingException;

    void confirmedAppointment(Appointment appointment, Service service, Business business, User client) throws MessagingException;

    void cancelledAppointment(Appointment appointment, Service service, Business business, User client, boolean isServiceDeleted) throws MessagingException;

    void deniedAppointment(Appointment appointment, Service service, Business business, User client, boolean isServiceDeleted) throws MessagingException;

    void deletedService(Service service, Business business) throws MessagingException;

    void createdService(Service service, Business business) throws MessagingException;

    void createdBusiness(Business business) throws MessagingException;

    void deletedBusiness(Business business) throws MessagingException;

    void askedQuestion(BasicService service, String businessEmail, User client, String question)  throws MessagingException;
    void answeredQuestion(BasicService service, User client, String question, String response) throws MessagingException;

}
