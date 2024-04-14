package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.*;

import java.util.List;
import java.util.Optional;

public interface ServiceService {
    Optional <List<Service>> getAllServices();
    Optional<Service> findById(long id);
    Service create(long businessid, String name, String description, Boolean homeservice, Neighbourhoods neighbourhood, String location, Categories category, int minimalduration, PricingTypes pricing, String price, Boolean additionalCharges,long imageId);
    Service edit(long serviceid, String field, String newvalue);
    void delete(long serviceid);

    Appointment createAppointment(long serviceid, String name, String surname, String email, String location, String telephone, String date);

    long confirmAppointment(long appointmentid);

    long cancelAppointment(long appointmentid);

    long denyAppointment(long appointmentid);

    List<Service> services(int page, String category, String location);
    int getServiceCount(String category, String location);
    int getPageCount(String category, String location);
}
