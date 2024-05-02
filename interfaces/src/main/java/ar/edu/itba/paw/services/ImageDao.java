package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.ImageModel;

import java.util.Optional;

public interface ImageDao {
    ImageModel addImage(byte[] imageBytes);
    Optional<ImageModel> getImageById(long serviceId);

}
