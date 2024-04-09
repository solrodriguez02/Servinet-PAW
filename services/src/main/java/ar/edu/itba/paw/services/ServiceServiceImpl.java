package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.Categories;
import ar.edu.itba.paw.model.Neighbourhoods;
import ar.edu.itba.paw.model.PricingTypes;
import ar.edu.itba.paw.model.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service("serviceServiceImpl")

public class ServiceServiceImpl implements ServiceService {
    private final ServiceDao serviceDao;

    @Autowired
    public ServiceServiceImpl(final ServiceDao serviceDao) {
        this.serviceDao = serviceDao;
    }

    @Override
    public Optional<Service> findById(long id) {
        return serviceDao.findById(id);
    }

    @Override
    public Service create(long businessid, String name, String description, Boolean homeservice, Neighbourhoods neighbourhood, String location, Categories category, int minimalduration, PricingTypes pricing, String price, Boolean additionalCharges,long imageId){
        return serviceDao.create(businessid, name, description, homeservice, String.format("%s;%s",neighbourhood.getValue(),location), category,minimalduration ,pricing, price, additionalCharges,imageId);
    }

    @Override
    public Service edit(long serviceid, String field, String newvalue) {
        return serviceDao.edit(serviceid, field, newvalue);
    }

    @Override
    public Optional <List<Service>> getAllServices() {
        return serviceDao.getAllServices();
    }

    @Override
    public void delete(long serviceid) {
        serviceDao.delete(serviceid);
    }

    @Override
    public List<Service> services(int page, String category, String location) {
        if(category != null || location != null) {
            return serviceDao.getServicesFilteredBy(page, category, location);
        } else {
            return serviceDao.getServices(page);
        }
    }

    @Override
    public Boolean isMoreServices(int page, String category, String location) {
        if(category != null || location != null) {
            return serviceDao.isMoreServicesFiltered(page, category, location);
        } else {
            return serviceDao.isMoreServices(page);
        }
    }

}
