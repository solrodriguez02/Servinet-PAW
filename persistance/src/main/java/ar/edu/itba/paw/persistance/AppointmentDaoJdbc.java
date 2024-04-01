package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.model.Appointment;
import ar.edu.itba.paw.services.AppointmentDao;

import java.time.LocalDateTime;
import java.util.Optional;

public class AppointmentDaoJdbc implements AppointmentDao {
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
