package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.*;

import java.util.List;
import java.util.Optional;

public interface ServiceDao {
    Optional<List<Service>> getAllServices();
    Optional<Service> findById(long id);
    Optional<BasicService> findBasicServiceById(long id);

    Service create(long businessid, String name, String description, Boolean homeservice, String location, Neighbourhoods[] neighbourhoods, Categories category, int minimalduration, PricingTypes pricing, String price, Boolean additionalCharges, long imageId);
    Service edit(long serviceid, String field, String newvalue);

    Optional<List<BasicService>> getAllBusinessBasicServices(long businessId);
    Optional<List<Service>> getAllBusinessServices(long businessId);

    void delete(long serviceid);
    List<Service> getServices(int page);
    List<Service> getServicesFilteredBy(int page, String category, String[] neighbourhoods, int rating, String query);
    int getServiceCount(String category, String[] neighbourhoods, int rating, String query);
    List<Service> getRecommendedServices();

}
