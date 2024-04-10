package ar.edu.itba.paw.services;


import ar.edu.itba.paw.model.ImageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("imageServiceImpl")
public class ImageServiceImpl implements ImageService{

    private final ImageDao imageDao;
    @Autowired
    public ImageServiceImpl(final ImageDao imageDao){
        this.imageDao = imageDao;
    }
    @Override
    public Optional<ImageModel> getImageById(long id) {
        return imageDao.getImageById(id);
    }

    @Override
    public ImageModel addImage( byte[] imageBytes) {
        return imageDao.addImage( imageBytes);
    }

}
