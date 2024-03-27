package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.Service;

public interface ServiceDao {
    Service findById(long id);

}
