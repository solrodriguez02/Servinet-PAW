package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.Appointment;

import javax.mail.MessagingException;

import ar.edu.itba.paw.model.Service;
import ar.edu.itba.paw.model.User;

import java.util.List;

public interface EmailService {

    void requestAppointment(Appointment appointment, Service service, User client) throws MessagingException;

    void confirmedAppointment(Appointment appointment, Service service) throws MessagingException;

    void cancelledAppointment(Appointment appointment, Service service) throws MessagingException;

    void deletedService(Service service, List<Appointment> appointmentList) throws MessagingException;

    void deniedAppointment(Appointment appointment, Service service) throws MessagingException;

    void createdService(Service service) throws MessagingException;
}
