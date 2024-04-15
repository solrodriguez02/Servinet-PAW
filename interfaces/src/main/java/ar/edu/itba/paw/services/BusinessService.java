package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.*;

import java.util.Optional;

public interface BusinessService {

    Optional<Business> findById(long id);

    Optional<Business> findByBusinessName(String businessName);

    void deleteBusiness(long businessid);

    void changeBusinessEmail(long businessId, String value);

    Business createBusiness(String businessName, long userId, String telephone, String email, String location);

    Service createService(long businessId, String name, String description, Boolean homeservice, Neighbourhoods neighbourhood, String location, Categories category, int minimalduration, PricingTypes pricing, String price, Boolean additionalCharges, long imageId);

    void deleteService(long serviceId);
}
