package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service("BusinessServiceImpl")
public class BusinessServiceImpl implements BusinessService{

    private final BusinessDao businessDao;
    private final ServiceService serviceService;
    private final UserService userService;


    @Autowired
    public BusinessServiceImpl(final BusinessDao businessDao, final ServiceService serviceService, final UserService userService){
        this.businessDao = businessDao;
        this.serviceService = serviceService;
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
    public List<Business> findByAdminId(long adminId){
        return businessDao.findByAdminId(adminId);
    }

    @Override
    public void changeBusinessEmail(long businessId, String value){
        businessDao.changeBusinessEmail(businessId,value);
    }

    @Override
    public void deleteBusiness(long businessid){

        final Business business = findById(businessid).get();
        List<Service> servicesList = serviceService.getAllBusinessServices(businessid);
            for ( Service service : servicesList)
                serviceService.delete(service, business );
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
