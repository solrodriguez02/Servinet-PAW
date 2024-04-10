package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.Business;

import java.util.Optional;

public interface BusinessDao {
     Optional<Business> findById(long id);

     Optional<Business> findByBusinessName(String businessName);

     void changeBusinessEmail(long businessId, String value);

     void deleteBusiness(long businessid);

     Business createBusiness(String businessName, long userId, String telephone, String email, String location);
}
