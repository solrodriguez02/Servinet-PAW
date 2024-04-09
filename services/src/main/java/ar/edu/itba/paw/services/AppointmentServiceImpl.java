package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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
    public Optional<List<Appointment>> getAllUpcomingServiceAppointments(long serviceid) {
        return appointmentDao.getAllUpcomingServiceAppointments(serviceid);
    }

    @Override
    public Appointment create(long serviceid, long userid, LocalDateTime startDate, LocalDateTime endDate, String location) {
        // PARAMS deberian ser validados aca?
        // ejem: startData < EndDate o desde el front?
                /*
        Service service = serviceService.findById(serviceid).orElseThrow(NoSuchElementException::new);
        if ( LocalDateTime.now().isAfter(startDate) || (!service.hasDuration && startDate > endDate) )
                excep
        */

        return appointmentDao.create(serviceid,userid,startDate,endDate, location);
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
