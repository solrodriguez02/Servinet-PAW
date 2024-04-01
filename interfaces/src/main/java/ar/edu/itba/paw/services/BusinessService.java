package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.Business;

import java.util.Optional;

public interface BusinessService {
    Optional<Business> findById(long id);

    void changeBusinessEmail(long businessId, String value);

    Business createBusiness(String businessName, long userId, String telephone, String email, String location);
}
