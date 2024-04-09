package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.Appointment;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentDao {

    Optional<Appointment> findById(long id);

    Optional<List<Appointment>> getAllUpcomingServiceAppointments(long serviceid);

    // siempre confirmed = false
    Appointment create(long serviceid, long userid, LocalDateTime startDate, LocalDateTime endDate, String location);

    void confirmAppointment(long appointmentid);

    void cancelAppointment(long appointmentid);
}
