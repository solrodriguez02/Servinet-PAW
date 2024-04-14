package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.Appointment;
import ar.edu.itba.paw.model.Service;
import ar.edu.itba.paw.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentService {

    Optional<Appointment> findById(long id);

    Optional<List<Appointment>> getAllUpcomingServiceAppointments(long serviceid);

    Appointment create(Service service, User user, String date, String location);

    void denyAppointment(Appointment appointment, Service service);

    void cancelAppointment(Appointment appointment, Service service);

    void confirmAppointment(Appointment appointment, Service service);
}
