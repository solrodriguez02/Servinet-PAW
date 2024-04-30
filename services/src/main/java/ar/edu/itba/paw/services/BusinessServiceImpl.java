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
    public Optional<List<Business>> findByAdminId(long adminId){
        return businessDao.findByAdminId(adminId);
    }
        @Override
    public void changeBusinessEmail(long businessId, String value){
        businessDao.changeBusinessEmail(businessId,value);
    }

    @Override
    public void deleteBusiness(long businessid){

        final Business business = findById(businessid).get();
        Optional<List<Service>> servicesList = serviceService.getAllBusinessServices(businessid);
        if ( servicesList.isPresent())
            for ( Service service : servicesList.get() )
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

    /*
    @Override
    public Service createService(long businessId, String name, String description, Boolean homeservice, Neighbourhoods[] neighbourhood, String location, Categories category, int minimalduration, PricingTypes pricing, String price, Boolean additionalCharges, long imageId) {

        return serviceService.create(business, name,description, homeservice, neighbourhood,location,category,minimalduration,pricing,price,additionalCharges,imageId);
    }
     */

    @Override
    public void deleteService(long serviceId) {
        Optional<Service> optionalService = serviceService.findById(serviceId);
        if ( !optionalService.isPresent() )
            return;
        final Service service = optionalService.get();
        final Business business = findById( service.getBusinessid() ).get();
        serviceService.delete(service, business);
    }
}
