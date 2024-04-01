package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.Appointment;
import java.time.LocalDateTime;
import java.util.Optional;

public interface AppointmentDao {

    Optional<Appointment> findById(long id);

    // siempre confirmed = false
    Appointment create(long serviceid, long userid, LocalDateTime startDate, LocalDateTime endDate);

    void confirmAppointment(long appointmentid);

    void cancelAppointment(long appointmentid);
}
