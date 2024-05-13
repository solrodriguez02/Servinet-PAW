package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


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

    private final Logger LOGGER = LoggerFactory.getLogger(AppointmentServiceImpl.class);
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
        User newuser = userService.findByEmail(email).orElseThrow(UserNotFoundException::new);
        LocalDateTime startDate = LocalDateTime.parse(date);
        Appointment appointment = appointmentDao.create(service.getId(), newuser.getUserId(), startDate, startDate.plusMinutes(service.getDuration()), location);
        Business business = businessDao.findById(service.getBusinessid()).orElseThrow(BusinessNotFoundException::new);

        emailService.requestAppointment(appointment, service, business, newuser, getBusinessLocale(business.getUserId()));
        LOGGER.info("Appointment request email sent successfully.");
        return appointment;
    }
    @Transactional
    @Override
    public long confirmAppointment(long appointmentid) {

        Appointment appointment = findById(appointmentid).orElseThrow(AppointmentNonExistentException::new);
        final Service service = serviceDao.findById(appointment.getServiceid()).orElseThrow(ServiceNotFoundException::new);
        final User client = userService.findById( appointment.getUserid()).orElseThrow(UserNotFoundException::new);

        if (appointment.getConfirmed())
            throw new AppointmentAlreadyConfirmed();

        Business business = businessDao.findById( service.getBusinessid()).orElseThrow(BusinessNotFoundException::new);
        appointmentDao.confirmAppointment(appointment.getId());
        emailService.confirmedAppointment(appointment, service, business, client, getBusinessLocale(business.getUserId()) );
        LOGGER.info("Appointment confirmation email sent successfully.");
        return service.getId();
    }
    @Transactional
    @Override
    public long denyAppointment(long appointmentid) {
        Appointment appointment = findById(appointmentid).orElseThrow(AppointmentNonExistentException::new);
        final Service service = serviceDao.findById(appointment.getServiceid()).orElseThrow(ServiceNotFoundException::new);
        final User client = userService.findById( appointment.getUserid()).orElseThrow(UserNotFoundException::new);

        if (appointment.getConfirmed())
            throw new AppointmentAlreadyConfirmed();

        Business business = businessDao.findById( service.getBusinessid()).orElseThrow(BusinessNotFoundException::new);
        appointmentDao.cancelAppointment(appointment.getId());
        emailService.deniedAppointment(appointment, service, business, client,false, getBusinessLocale(business.getUserId()));
        LOGGER.info("Denied appointment email sent successfully.");

        return service.getId();
    }
    @Transactional
    @Override
    public long cancelAppointment(long appointmentid) {
        Appointment appointment = findById(appointmentid).orElseThrow(AppointmentNonExistentException::new);
        final Service service = serviceDao.findById(appointment.getServiceid()).orElseThrow(ServiceNotFoundException::new);
        final User client = userService.findById( appointment.getUserid()).orElseThrow(UserNotFoundException::new);

        Business business = businessDao.findById( service.getBusinessid()).orElseThrow(BusinessNotFoundException::new);
        appointmentDao.cancelAppointment(appointment.getId());
        emailService.cancelledAppointment(appointment, service,business, client,false, getBusinessLocale(business.getUserId()));
        LOGGER.info("Cancel Appointment email sent successfully.");

        return service.getId();
    }

    private String getBusinessLocale(long adminId){
        return userService.getUserLocale(adminId);
    }
}
