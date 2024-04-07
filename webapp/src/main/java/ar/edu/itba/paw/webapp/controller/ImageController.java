package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.model.ImageModel;
import ar.edu.itba.paw.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ImageController {
    private final ImageService is;

    @Autowired
    public ImageController(@Qualifier("imageServiceImpl") ImageService is) {
        this.is = is;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/images/{imageId:\\d+}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] images(@PathVariable("imageId") final int imageId) {
        ImageModel img = is.getImageById(imageId).orElseThrow(IllegalArgumentException::new);
        return img.getImageBytes();
    }

}
