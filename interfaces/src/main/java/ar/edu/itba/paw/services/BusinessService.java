package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.*;

import java.util.List;
import java.util.Optional;

public interface BusinessService {

    Optional<Business> findById(long id);

    Optional<Business> findByBusinessName(String businessName);

    List<Business> findByAdminId(long adminId);

    Optional<String> getBusinessEmail(long businessid);

    void deleteBusiness(long businessid);
    void changeBusinessTelephone(long businessId, String value);
    void changeBusinessLocation(long businessId,String value);
    void changeBusinessEmail(long businessId, String value);

    Business createBusiness(String businessName, long userId, String telephone, String email, String location);

}
