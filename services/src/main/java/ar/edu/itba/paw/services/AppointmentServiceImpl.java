package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.Appointment;
import ar.edu.itba.paw.model.Business;
import ar.edu.itba.paw.model.Service;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.services.exception.AppointmentAlreadyConfirmed;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service("appointmentServiceImpl")
public class AppointmentServiceImpl implements AppointmentService{

    private final AppointmentDao appointmentDao;
    private final EmailService emailService;
    private final BusinessService businessService;
    @Autowired
    public AppointmentServiceImpl(final AppointmentDao appointmentDao, final EmailService emailService,
                                    final BusinessService businessService) {
        this.appointmentDao = appointmentDao;
        this.emailService = emailService;
        this.businessService = businessService;
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
    public Appointment create(Service service, User user, String date, String location) {
        // PARAMS deberian ser validados aca
        // ejem: startData < EndDate o desde el front?
                /*

        if ( LocalDateTime.now().isAfter(startDate) || (!service.hasDuration && startDate > endDate) )
                excep
        */
        LocalDateTime startDate = LocalDateTime.parse(date);
        Appointment appointment = appointmentDao.create(service.getId(), user.getUserId(), startDate, startDate.plusMinutes(service.getDuration()), location);
        Business business = businessService.findById( service.getBusinessid()).get();
        try {
            //* si ya tiene el Service => ya lo paso x param
            emailService.requestAppointment(appointment, service,business,user);

        } catch (MessagingException e ){
            System.err.println(e.getMessage());
        }
        return appointment;
    }

    @Override
    public void confirmAppointment(Appointment appointment, Service service, User client) {
        if (appointment.getConfirmed())
            throw new AppointmentAlreadyConfirmed();

        Business business = businessService.findById( service.getBusinessid()).get();
        appointmentDao.confirmAppointment(appointment.getId());
        try {
            emailService.confirmedAppointment(appointment, service, business, client);

        } catch (MessagingException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void denyAppointment(Appointment appointment, Service service, User client) {
        if (appointment.getConfirmed())
            throw new AppointmentAlreadyConfirmed();

        Business business = businessService.findById( service.getBusinessid()).get();
        appointmentDao.cancelAppointment(appointment.getId());
        try {
            emailService.deniedAppointment(appointment, service, business, client);
        } catch (MessagingException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void cancelAppointment(Appointment appointment, Service service, User client) {
        Business business = businessService.findById( service.getBusinessid()).get();
        appointmentDao.cancelAppointment(appointment.getId());
        try {
            emailService.cancelledAppointment(appointment, service,business, client);

        } catch (MessagingException e) {
            System.err.println(e.getMessage());
        }
    }
}
