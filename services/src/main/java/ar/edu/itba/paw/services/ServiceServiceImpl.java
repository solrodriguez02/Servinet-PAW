package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.exceptions.BusinessNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service("serviceServiceImpl")

public class ServiceServiceImpl implements ServiceService {
    private final ServiceDao serviceDao;
    private final EmailService emailService;
    private final AppointmentService appointmentService;
    private final UserService userService;
    private final BusinessDao businessDao;
    private final ImageService imageService;

    @Autowired
    public ServiceServiceImpl(final ServiceDao serviceDao, final EmailService emailService,
                              final AppointmentService appointmentService, final UserService userService,
                              final ImageService imageService, final BusinessDao businessDao) {
        this.serviceDao = serviceDao;
        this.emailService = emailService;
        this.appointmentService = appointmentService;
        this.userService = userService;
        this.businessDao = businessDao;
        this.imageService = imageService;
    }

    @Override
    public Optional<Service> findById(long id) {
        return serviceDao.findById(id);
    }

    @Override
    public Optional<BasicService> findBasicServiceById(long id) {
        return serviceDao.findBasicServiceById(id);
    }


    public Service create(long businessId, String name, String description, Boolean homeservice,
                          Neighbourhoods[] neighbourhood, String location, Categories category, int minimalduration,
                          PricingTypes pricing, String price, Boolean additionalCharges, MultipartFile image) throws IOException {
        Business business = businessDao.findById( businessId).orElseThrow(BusinessNotFoundException::new);

        long imageId = image.isEmpty()? 0 : imageService.addImage(image.getBytes()).getImageId();

        Service service = serviceDao.create(business.getBusinessid(), name, description, homeservice,location,neighbourhood, category,minimalduration ,pricing, price, additionalCharges,imageId);
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
    public List<Service> getAllServices() {
        return serviceDao.getAllServices();
    }

    @Override
    public void delete(long serviceId) {

        Optional<Service> optionalService = findById(serviceId);
        if (!optionalService.isPresent())
            return;
        final Service service = optionalService.get();
        final Business business = businessDao.findById(service.getBusinessid()).get();

        delete(service,business);
    }

        @Override
    public void delete(Service service, Business business) {

        List<Appointment> appointmentList = appointmentService.getAllUpcomingServiceAppointments(service.getId());
             for ( Appointment appointment : appointmentList){
                 User client = userService.findById( appointment.getUserid()).get();

                 try {
                     if (appointment.getConfirmed())
                         emailService.cancelledAppointment(appointment,service,business,client,true);
                     else
                         emailService.deniedAppointment(appointment,service,business,client,true);
                 } catch (MessagingException e){
                     throw new RuntimeException(e);
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
    public List<Service> services(int page, String category, String[] location, String rating, String query) {
        int ratingNum = Ratings.getMinValueByName(rating);
        return serviceDao.getServicesFilteredBy(page, category, location, ratingNum, query);
    }

    @Override
    public int getServiceCount(String category, String[] location, String rating, String searchQuery) {
        int ratingNum = Ratings.getMinValueByName(rating);
        return serviceDao.getServiceCount(category, location, ratingNum, searchQuery);
    }

    @Override
    public int getPageCount(String category, String[] location, String rating, String searchQuery) {
        int serviceCount = getServiceCount(category, location, rating, searchQuery);
        int pageCount = serviceCount / 10;
        if(serviceCount % 10 != 0) pageCount++;
        return pageCount;
    }

    @Override
    public List<BasicService> getAllBusinessBasicServices(long businessId) {
        return serviceDao.getAllBusinessBasicServices(businessId);
    }

    @Override
    public List<Service> getAllBusinessServices(long businessid){
        return serviceDao.getAllBusinessServices(businessid);
    }

    @Override
    public List<Service> getRecommendedServices() {
        return serviceDao.getRecommendedServices();
    }
}
