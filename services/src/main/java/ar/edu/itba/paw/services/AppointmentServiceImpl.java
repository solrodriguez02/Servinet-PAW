package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.exceptions.AppointmentAlreadyConfirmed;
import ar.edu.itba.paw.model.exceptions.AppointmentNonExistentException;
import ar.edu.itba.paw.model.exceptions.ServiceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service("appointmentServiceImpl")
public class AppointmentServiceImpl implements AppointmentService{

    private final AppointmentDao appointmentDao;
    private final EmailService emailService;
    private final ServiceDao serviceDao;
    private final BusinessDao businessDao;
    private final UserService userService;
    @Autowired
    public AppointmentServiceImpl(final AppointmentDao appointmentDao, final EmailService emailService,
                                    final BusinessDao businessDao, ServiceDao serviceDao, final UserService userService) {
        this.appointmentDao = appointmentDao;
        this.emailService = emailService;
        this.businessDao = businessDao;
        this.serviceDao = serviceDao;
        this.userService = userService;
    }

    @Override
    public Optional<Appointment> findById(long id) {
        return appointmentDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Appointment> getAllUpcomingServiceAppointments(long serviceid) {
        return appointmentDao.getAllUpcomingServiceAppointments(serviceid);
    }
    @Transactional(readOnly = true)
    @Override
    public List<Appointment> getAllUpcomingServicesAppointments(Collection<Long> serviceIds, boolean confirmed) {
        return appointmentDao.getAllUpcomingServicesAppointments(serviceIds,confirmed);
    }
    @Transactional(readOnly = true)
    @Override
    public List<AppointmentInfo> getAllUpcomingUserAppointments(long userid, boolean confirmed) {
        return appointmentDao.getAllUpcomingUserAppointments(userid, confirmed);
    }
    @Transactional
    @Override
    public Appointment create(long serviceid, String name, String surname, String email, String location, String telephone, String date) {
        Service service = serviceDao.findById(serviceid).orElseThrow(ServiceNotFoundException::new);
        User newuser = userService.findByEmail(email).orElse(null);
        LocalDateTime startDate = LocalDateTime.parse(date);
        Appointment appointment = appointmentDao.create(service.getId(), newuser.getUserId(), startDate, startDate.plusMinutes(service.getDuration()), location);
        Business business = businessDao.findById(service.getBusinessid()).get();
        try {
            //* si ya tiene el Service => ya lo paso x param
            emailService.requestAppointment(appointment, service, business, newuser);

        } catch (MessagingException e) {
            System.err.println(e.getMessage());
        }
        return appointment;
    }
    @Transactional
    @Override
    public long confirmAppointment(long appointmentid) {
        Optional<Appointment> optionalAppointment = findById(appointmentid);
        if (!optionalAppointment.isPresent())
            throw new AppointmentNonExistentException();
        Appointment appointment = optionalAppointment.get();
        final Service service = serviceDao.findById(appointment.getServiceid()).get();
        final User client = userService.findById( appointment.getUserid()).get();

        if (appointment.getConfirmed())
            throw new AppointmentAlreadyConfirmed();

        Business business = businessDao.findById( service.getBusinessid()).get();
        appointmentDao.confirmAppointment(appointment.getId());
        try {
            emailService.confirmedAppointment(appointment, service, business, client);

        } catch (MessagingException e) {
            System.err.println(e.getMessage());
        }
        return service.getId();
    }
    @Transactional
    @Override
    public long denyAppointment(long appointmentid) {
        Optional<Appointment> optionalAppointment = findById(appointmentid);
        if (!optionalAppointment.isPresent())
            throw new AppointmentNonExistentException();
        Appointment appointment = optionalAppointment.get();
        final Service service = serviceDao.findById(appointment.getServiceid()).get();
        final User client = userService.findById( appointment.getUserid()).get();

        if (appointment.getConfirmed())
            throw new AppointmentAlreadyConfirmed();

        Business business = businessDao.findById( service.getBusinessid()).get();
        appointmentDao.cancelAppointment(appointment.getId());
        try {
            emailService.deniedAppointment(appointment, service, business, client,false);
        } catch (MessagingException e) {
            System.err.println(e.getMessage());
        }

        return service.getId();
    }
    @Transactional
    @Override
    public long cancelAppointment(long appointmentid) {
        Optional<Appointment> optionalAppointment = findById(appointmentid);
        if (!optionalAppointment.isPresent())
            throw new AppointmentNonExistentException();
        Appointment appointment = optionalAppointment.get();
        final Service service = serviceDao.findById(appointment.getServiceid()).get();
        final User client = userService.findById( appointment.getUserid()).get();

        Business business = businessDao.findById( service.getBusinessid()).get();
        appointmentDao.cancelAppointment(appointment.getId());
        try {
            emailService.cancelledAppointment(appointment, service,business, client,false);

        } catch (MessagingException e) {
            System.err.println(e.getMessage());
        }

        return service.getId();
    }
}
