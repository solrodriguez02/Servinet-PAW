package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.services.exception.AppointmentNonExistentException;
import ar.edu.itba.paw.services.exception.EmailAlreadyUsedException;
import ar.edu.itba.paw.services.exception.ServiceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service("serviceServiceImpl")

public class ServiceServiceImpl implements ServiceService {
    private final ServiceDao serviceDao;
    private final EmailService emailService;
    private final AppointmentService appointmentService;
    private final UserService userService;

    @Autowired
    public ServiceServiceImpl(final ServiceDao serviceDao, final EmailService emailService,
                              final AppointmentService appointmentService, final UserService userService) {
        this.serviceDao = serviceDao;
        this.emailService = emailService;
        this.appointmentService = appointmentService;
        this.userService = userService;
    }

    @Override
    public Optional<Service> findById(long id) {
        return serviceDao.findById(id);
    }

    @Override
    public Service create(Business business, String name, String description, Boolean homeservice, Neighbourhoods neighbourhood, String location, Categories category, int minimalduration, PricingTypes pricing, String price, Boolean additionalCharges,long imageId){
        Service service = serviceDao.create(business.getBusinessid(), name, description, homeservice, String.format("%s;%s",neighbourhood.getValue(),location), category,minimalduration ,pricing, price, additionalCharges,imageId);
        try {
            emailService.createdService(service, business);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return service;
    }

    @Override
    public Service edit(long serviceid, String field, String newvalue) {
        return serviceDao.edit(serviceid, field, newvalue);
    }

    @Override
    public Optional <List<Service>> getAllServices() {
        return serviceDao.getAllServices();
    }

    @Override
    public void delete(Service service, Business business) {

        Optional<List<Appointment>> appointmentList = appointmentService.getAllUpcomingServiceAppointments(service.getId());
         if ( appointmentList.isPresent() ){
             for ( Appointment appointment : appointmentList.get()){
                 User client = userService.findById( appointment.getUserid()).get();

                 try {
                     if (appointment.getConfirmed())
                         emailService.cancelledAppointment(appointment,service,business,client);
                     else
                         emailService.deniedAppointment(appointment,service,business,client);
                 } catch (MessagingException e){
                     throw new RuntimeException(e);
                 }
             }
         }
        serviceDao.delete(service.getId());
        try {
            emailService.deletedService(service,business);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Appointment createAppointment(long serviceid, String name, String surname, String email, String location, String telephone, String date){
        Service service = findById(serviceid).orElseThrow(ServiceNotFoundException::new);
        // ! TEMP {
        User newuser = userService.findByEmail(email).orElse(null);
        if (newuser == null){
            newuser = userService.create("default",name,"default",surname,email,telephone);
            String newUsername = String.format("%s%s%d",name.replaceAll("\\s", ""),surname.replaceAll("\\s", ""),newuser.getUserId());
            userService.changeUsername(newuser.getUserId(),newUsername);
        }else {
            if(!newuser.getName().equals(name) || !newuser.getSurname().equals(surname) || !newuser.getTelephone().equals(telephone)){
                //TODO: manejar error de usuario ya existente para que el usuario sepa por que se lo redirige nuevamente
                throw new EmailAlreadyUsedException(email);
            }
        }
        // ! }
        return appointmentService.create(service, newuser , date, location );
    }

    @Override
    public long confirmAppointment(long appointmentid){
        Optional<Appointment> optionalAppointment = appointmentService.findById(appointmentid);
        if (!optionalAppointment.isPresent())
            throw new AppointmentNonExistentException();
        Appointment appointment = optionalAppointment.get();
        final Service service = findById(appointment.getServiceid()).get();
        final User client = userService.findById( appointment.getUserid()).get();

        appointmentService.confirmAppointment(appointment, service, client);
        return service.getId();
    }

    @Override
    public long denyAppointment(long appointmentid){
        Optional<Appointment> optionalAppointment = appointmentService.findById(appointmentid);
        if (!optionalAppointment.isPresent())
            throw new AppointmentNonExistentException();
        Appointment appointment = optionalAppointment.get();
        final Service service = findById(appointment.getServiceid()).get();
        final User client = userService.findById( appointment.getUserid()).get();

        appointmentService.denyAppointment(appointment, service, client );
        return service.getId();
    }

    @Override
    public long cancelAppointment(long appointmentid){
        Optional<Appointment> optionalAppointment = appointmentService.findById(appointmentid);
        if (!optionalAppointment.isPresent())
            throw new AppointmentNonExistentException();
        Appointment appointment = optionalAppointment.get();
        final Service service = findById(appointment.getServiceid()).get();
        final User client = userService.findById( appointment.getUserid()).get();

        appointmentService.cancelAppointment(appointment, service, client);
        return service.getId();
    }


        @Override
    public List<Service> services(int page, String category, String location) {
        if(category != null || location != null) {
            return serviceDao.getServicesFilteredBy(page, category, location);
        } else {
            return serviceDao.getServices(page);
        }
    }

    @Override
    public int getServiceCount(String category, String location) {
        return serviceDao.getServiceCount(category, location);
    }

    @Override
    public int getPageCount(String category, String location) {
        int serviceCount = getServiceCount(category, location);
        int pageCount = serviceCount / 10;
        if(serviceCount % 10 != 0) pageCount++;
        return pageCount;
    }

    @Override
    public Optional getAllBusinessServices(long businessid){
        return serviceDao.getAllBusinessServices(businessid);
    }
}
