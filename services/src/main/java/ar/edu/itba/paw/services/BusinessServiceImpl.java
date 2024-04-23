package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.Business;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("BusinessServiceImpl")
public class BusinessServiceImpl implements BusinessService{

    private final BusinessDao businessDao;
    private final UserService userService;


    @Autowired
    public BusinessServiceImpl(final BusinessDao businessDao, final UserService userService){
        this.businessDao = businessDao;
        this.userService = userService;
    }

    @Override
    public Optional<Business> findById(long id) {
        return businessDao.findById(id);
    }
    @Override
    public Optional<Business> findByBusinessName(String businessName) {
        return businessDao.findByBusinessName(businessName);
    }
        @Override
    public void changeBusinessEmail(long businessId, String value){
        businessDao.changeBusinessEmail(businessId,value);
    }

    @Override
    public void deleteBusiness(long businessid){
        businessDao.deleteBusiness(businessid);
    }
    @Override
    public Business createBusiness(String businessName, long userId, String telephone, String email, String location){
        Business business = businessDao.createBusiness(businessName,userId,telephone,email,location);
        userService.changeUserType(userId);
        return business;
    }
    @Override
    public Boolean isBusinessOwner(long businessId, long userId){
        Business business = businessDao.findById(businessId).orElse(null);
        if(business == null){
            return false;
        }
        return business.getUserId() == userId;
    }
}
