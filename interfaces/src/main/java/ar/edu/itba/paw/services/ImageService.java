package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.ImageModel;

import java.util.Optional;

public interface ImageService {
    Optional<ImageModel> getImageById(long id);
    ImageModel addImage(byte[] imageBytes);
}
