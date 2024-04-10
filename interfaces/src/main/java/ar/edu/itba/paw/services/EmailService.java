package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.Appointment;

import javax.mail.MessagingException;

import ar.edu.itba.paw.model.Service;
import ar.edu.itba.paw.model.User;

import java.util.List;

public interface EmailService {

    // ! tuve q agregar dependencia javax.mail en pom
    public void requestAppointment(Appointment appointment, User client) throws MessagingException ;

    public void confirmedAppointment(Appointment appointment) throws MessagingException;

    public void cancelledAppointment(Appointment appointment) throws MessagingException;

    public void deniedAppointment(Appointment appointment) throws MessagingException;

    public void deletedService(Service service, List<Appointment> appointmentList) throws MessagingException;

    void createdService(Service service) throws MessagingException;
}
