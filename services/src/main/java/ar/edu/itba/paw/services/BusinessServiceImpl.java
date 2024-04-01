package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.Business;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("BusinessServiceImpl")
public class BusinessServiceImpl implements BusinessService{

    private final BusinessDao businessDao;


    @Autowired
    public BusinessServiceImpl(final BusinessDao businessDao){
        this.businessDao = businessDao;
    }

    @Override
    public Optional<Business> findById(long id) {
        return businessDao.findById(id);
    }
    @Override
    public void changeBusinessEmail(long businessId, String value){
        businessDao.changeBusinessEmail(businessId,value);
    }
    @Override
    public Business createBusiness(String businessName, long userId, String telephone, String email, String location){
        return businessDao.createBusiness(businessName,userId,telephone,email,location);
    }
}
