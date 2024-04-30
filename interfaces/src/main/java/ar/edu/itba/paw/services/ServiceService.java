package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ServiceService {
    Optional <List<Service>> getAllServices();
    Optional<Service> findById(long id);
    Optional<BasicService> findBasicServiceById(long id);
    Service create(long businessId, String name, String description, Boolean homeservice, Neighbourhoods[] neighbourhood, String location, Categories category, int minimalduration, PricingTypes pricing, String price, Boolean additionalCharges, MultipartFile image) throws IOException;
    Service edit(long serviceid, String field, String newvalue);
    void delete(Service service, Business business);
    void delete(long serviceId);
    List<Service> services(int page,String category,String[] location, String rating, String query);
    int getServiceCount(String category, String[] location, String rating, String searchQuery);
    int getPageCount(String category, String[] location, String rating, String searchQuery);
    List<Service> getRecommendedServices();
    Optional<List<BasicService>> getAllBusinessBasicServices(long businessId);
    Optional<List<Service>> getAllBusinessServices(long businessId);
}
