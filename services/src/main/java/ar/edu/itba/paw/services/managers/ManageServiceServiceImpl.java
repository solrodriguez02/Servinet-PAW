package ar.edu.itba.paw.services.managers;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.services.AppointmentService;
import ar.edu.itba.paw.services.EmailService;
import ar.edu.itba.paw.services.ManageServiceService;
import ar.edu.itba.paw.services.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Optional;


@org.springframework.stereotype.Service
public class ManageServiceServiceImpl implements ManageServiceService {
    private final EmailService emailService;
    private final AppointmentService appointmentService;
    private final ServiceService serviceService;

    @Autowired
    public ManageServiceServiceImpl(final EmailService emailService, final AppointmentService appointmentService, final ServiceService serviceService) {
        this.emailService = emailService;
        this.appointmentService = appointmentService;
        this.serviceService = serviceService;
    }

    @Override
    public long createService(long businessid, String title, String description, boolean homeservice, Neighbourhoods neighbourhood, String location, Categories category, int minimalduration, PricingTypes pricingtype, String price, boolean additionalCharges, long imageId){
        Service service = serviceService.create(businessid,title,description,homeservice,neighbourhood,location,category,minimalduration,pricingtype,price,additionalCharges, imageId);
        try {
            emailService.createdService(service);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return service.getId();
    }

    @Override
    public void deleteService(long serviceid) {
        Optional<Service> service = serviceService.findById(serviceid);
        if ( !service.isPresent() )
            return;
        Optional<List<Appointment>> appointmentList = appointmentService.getAllUpcomingServiceAppointments(service.get().getId());
        appointmentList.ifPresent(appointments -> {
            try {
                emailService.deletedService(service.get(), appointments);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        });

        serviceService.delete(serviceid);
    }

}
