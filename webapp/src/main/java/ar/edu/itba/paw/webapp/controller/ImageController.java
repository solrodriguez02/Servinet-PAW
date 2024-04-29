package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.model.ImageModel;
import ar.edu.itba.paw.services.ImageService;
import ar.edu.itba.paw.webapp.exception.ImageNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Optional;


@Controller
public class ImageController {
    private final ImageService is;
    @Value("resources/defaultImg.png")
    private Resource defaultImage;
    @Autowired
    public ImageController(@Qualifier("imageServiceImpl") ImageService is) {
        this.is = is;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/images/{imageId:\\d+}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] images(@PathVariable("imageId") final int imageId) {
        final Optional<ImageModel> image = is.getImageById(imageId);
        if(image.isEmpty()){
            try{
                return StreamUtils.copyToByteArray(defaultImage.getInputStream());
            } catch (IOException e) {
                throw new ImageNotFoundException();//TODO:imprimir en log
            }
        }
        return image.get().getImageBytes();
    }
}
