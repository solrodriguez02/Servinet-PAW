package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service("appointmentServiceImpl")
public class AppointmentServiceImpl implements AppointmentService{

    private final AppointmentDao appointmentDao;

    @Autowired
    public AppointmentServiceImpl(final AppointmentDao appointmentDao) {
        this.appointmentDao = appointmentDao;
    }

    @Override
    public Optional<Appointment> findById(long id) {
        return appointmentDao.findById(id);
    }

    @Override
    public Appointment create(long serviceid, long userid, LocalDateTime startDate, LocalDateTime endDate) {
        // PARAMS deberian ser validados aca?
        // ejem: startData < EndDate o desde el front?
        return appointmentDao.create(serviceid,userid,startDate,endDate);
    }

    @Override
    public void confirmAppointment(long appointmentid) {
        appointmentDao.confirmAppointment(appointmentid);
    }

    @Override
    public void cancelAppointment(long appointmentid) {
        appointmentDao.cancelAppointment(appointmentid);
    }
}
