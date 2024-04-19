package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.Categories;
import ar.edu.itba.paw.model.Neighbourhoods;
import ar.edu.itba.paw.model.PricingTypes;
import ar.edu.itba.paw.model.Service;

import java.util.List;
import java.util.Optional;

public interface ServiceService {
    Optional <List<Service>> getAllServices();
    Optional<Service> findById(long id);
    Service create(long businessid, String name, String description, Boolean homeservice, Neighbourhoods neighbourhood, String location, Categories category, int minimalduration, PricingTypes pricing, String price, Boolean additionalCharges,long imageId);
    Service edit(long serviceid, String field, String newvalue);
    void delete(long serviceid);;
    List<Service> services(int page,String category,String location,String query);
    int getServiceCount(String category, String location,String searchQuery);
    int getServiceCount(String category, String location);
    int getPageCount(String category, String location);
}
