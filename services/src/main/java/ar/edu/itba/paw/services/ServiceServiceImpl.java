package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.Categories;
import ar.edu.itba.paw.model.Neighbourhoods;
import ar.edu.itba.paw.model.PricingTypes;
import ar.edu.itba.paw.model.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<Service> services(int page, String category, String location,String query) {
            return serviceDao.getServicesFilteredBy(page, category, location,query);
    }

    @Override
    public int getServiceCount(String category, String location,String searchQuery) {
        return serviceDao.getServiceCount(category, location,searchQuery);
    }

    @Override
    public int getPageCount(String category, String location,String searchQuery) {
        int serviceCount = getServiceCount(category, location,searchQuery);
        int pageCount = serviceCount / 10;
        if(serviceCount % 10 != 0) pageCount++;
        return pageCount;
    }

}
