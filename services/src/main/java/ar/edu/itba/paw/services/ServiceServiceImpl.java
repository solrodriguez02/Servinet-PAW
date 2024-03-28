package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.Service;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service("serviceServiceImpl")

public class ServiceServiceImpl implements ServiceService {
    private final ServiceDao serviceDao;

    @Autowired
    public ServiceServiceImpl(final ServiceDao serviceDao) {
        this.serviceDao = serviceDao;
    }

    @Override
    public Service findById(long id) {
        return serviceDao.findById(id);
    }
}
