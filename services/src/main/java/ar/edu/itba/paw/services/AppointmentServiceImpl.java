package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.Appointment;

import java.time.LocalDateTime;
import java.util.Optional;

public class AppointmentServiceImpl implements AppointmentService{
    @Override
    public Optional<Appointment> findById(long id) {
        return Optional.empty();
    }

    @Override
    public Appointment create(long serviceid, long userid, LocalDateTime startDate, LocalDateTime endDate, Boolean confirmed) {
        return null;
    }

    @Override
    public void confirmAppointment(long appointmentid) {

    }

    @Override
    public void cancelAppointment(long appointmentid) {

    }
}
