package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.Appointment;

import javax.mail.MessagingException;

import ar.edu.itba.paw.model.Business;
import ar.edu.itba.paw.model.PasswordRecoveryCode;
import ar.edu.itba.paw.model.Service;
import ar.edu.itba.paw.model.User;

public interface EmailService {

    void requestAppointment(Appointment appointment, Service service, Business business, User client) throws MessagingException;
    public void recoverPassword(User user, PasswordRecoveryCode passwordRecoveryCode) throws MessagingException;

    public void confirmNewPassword(User user) throws MessagingException;
    // ! tuve q agregar dependencia javax.mail en pom

    void confirmedAppointment(Appointment appointment, Service service, Business business, User client) throws MessagingException;

    void cancelledAppointment(Appointment appointment, Service service, Business business, User client, Boolean isServiceDeleted) throws MessagingException;

    void deniedAppointment(Appointment appointment, Service service, Business business, User client,Boolean isServiceDeleted) throws MessagingException;

    void deletedService(Service service, Business business) throws MessagingException;

    void createdService(Service service, Business business) throws MessagingException;
}
