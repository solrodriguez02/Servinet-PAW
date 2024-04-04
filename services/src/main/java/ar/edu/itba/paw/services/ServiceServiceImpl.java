package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.Categories;
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
    public Service create(long businessid, String name, String description,Boolean homeservice, String location, Categories category, int minimalduration, PricingTypes pricing, String price, Boolean additionalCharges, String imageurl) {
        return serviceDao.create(businessid, name, description, homeservice, location, category,minimalduration ,pricing, price, additionalCharges, imageurl);
    }

    @Override
    public Service edit(long serviceid, String field, String newvalue) {
        return serviceDao.edit(serviceid, field, newvalue);
    }

    @Override
    public void delete(long serviceid) {
        serviceDao.delete(serviceid);
    }

    @Override
    public List<Service> services(int page, String category) {
        if(category != null) {
            return serviceDao.getServicesByCategory(page, category);
        } else {
            return serviceDao.getServices(page);
        }
    }

    @Override
    public Boolean isMoreServices(int page, String category) {
        if(category != null) {
            return serviceDao.isMoreServicesInCategory(page, category);
        } else {
            return serviceDao.isMoreServices(page);
        }
    }

}
