package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.model.ImageModel;
import ar.edu.itba.paw.services.ImageService;
import ar.edu.itba.paw.webapp.exception.ImageNotFoundException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Optional;

@Controller
public class ImageController {
    private final ImageService is;

    @Value("classpath:defaultImg.png")
    private Resource defaultImage;
    @Autowired
    public ImageController(@Qualifier("imageServiceImpl") ImageService is) {
        this.is = is;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/images/{imageId:\\d+}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] images(@PathVariable("imageId") final int imageId) throws IOException {
        if(imageId==0){
            return IOUtils.toByteArray(defaultImage.getInputStream());
        }
        ImageModel img = is.getImageById(imageId).orElseThrow(ImageNotFoundException::new);
        return img.getImageBytes();//TODO: preguntar si esto es un checkeo de logica de negocio, ende, violando la capa de restriccion
    }

}
