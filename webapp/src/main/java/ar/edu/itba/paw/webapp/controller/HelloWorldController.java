package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Categories;
import ar.edu.itba.paw.model.Service;
import ar.edu.itba.paw.services.ServiceService;
import ar.edu.itba.paw.webapp.exception.ServiceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ar.edu.itba.paw.services.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class HelloWorldController {

    private UserService us;
    private ServiceService service;

    List<Categories> categories = new ArrayList<>();

    @Autowired
    public HelloWorldController(@Qualifier("userServiceImpl") final UserService us, @Qualifier("serviceServiceImpl") final ServiceService service) {
        this.us = us;
        this.service = service;
        categories.addAll(Arrays.asList(Categories.values()));
    }


    @RequestMapping(method = RequestMethod.GET, path = "/")
    public ModelAndView home(@RequestParam(name = "categoria", required = false) String category) {
        final ModelAndView mav = new ModelAndView("home");
        List<Service> serviceList = new ArrayList<>();
        if (category != null && !category.isEmpty()) {
            // Se debería crear un metodo que devuelva los servicios de la categoria recibida por parametro
            // IMPORTANTE para front: usar try catch de la excepción para mostrar la información en la vista creando un service hardcodeado que se agrega a la lista
            // o bien popular la db local con los ids que se busquen
            serviceList.add(service.findById(10).orElseThrow(ServiceNotFoundException::new));
        } else {
            for (int i = 1; i < 5; i++) {
                serviceList.add(service.findById(i).orElseThrow(ServiceNotFoundException::new));
            }
        }
        mav.addObject("services", serviceList);
        mav.addObject("categories", categories);
        return mav;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/publicar")
    public ModelAndView postForm() {
        final ModelAndView mav = new ModelAndView("post");
        mav.addObject("user","home page");
        return mav;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/datospersonales")
    public ModelAndView dataForm(
            @RequestParam(value = "titulo") final String title,
            @RequestParam(value = "descripcion") final String description,
            @RequestParam(value = "ubicacion") final String location,
            @RequestParam(value = "categoria") final String category
    ) {
        final ModelAndView mav = new ModelAndView("postPersonal");
        mav.addObject("title", title);
        mav.addObject("description", description);
        mav.addObject("location", location);
        mav.addObject("category", category);
        return mav;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/crear")
    public ModelAndView post(
            @RequestParam(value = "titulo") final String title,
            @RequestParam(value = "descripcion") final String description,
            @RequestParam(value = "ubicacion") final String location,
            @RequestParam(value = "categoria") final String category,
            @RequestParam(value = "nombre") final String name,
            @RequestParam(value = "apellido") final String apellido,
            @RequestParam(value = "email") final String email
    ) {
        // Aca se debería crear el nuevo servicio
        return new ModelAndView("redirect:/misservicios");
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{serviceId:\\d+}")
    public ModelAndView service(@PathVariable("serviceId") final long serviceId) {
        final ModelAndView mav = new ModelAndView("service");
        mav.addObject("service",service.findById(serviceId).orElseThrow(ServiceNotFoundException::new));
        return mav;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/micuenta")
    public ModelAndView profile() {
        final ModelAndView mav = new ModelAndView("profile");
        mav.addObject("username", us);
        return mav;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/misservicios")
    public ModelAndView userServices() {
        final ModelAndView mav = new ModelAndView("userServices");
        return mav;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/misturnos")
    public ModelAndView userAppointments() {
        final ModelAndView mav = new ModelAndView("userAppointments");
        return mav;
    }

    /*
    @RequestMapping(method = RequestMethod.GET, path = "/")
    public ModelAndView registerForm() {
        final ModelAndView mav = new ModelAndView("helloworld/registerForm");
        return mav;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/create")
    public ModelAndView register(@RequestParam(value = "username") final String username) {
        final User user = us.create(username);
        return new ModelAndView("redirect:/" + user.getUserId());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{userId:\\d+}")
    public ModelAndView userProfile(@PathVariable("userId") final long userId) {
        final ModelAndView mav = new ModelAndView("helloworld/hello");
        mav.addObject("user",us.findById(userId).orElseThrow(UserNotFoundException::new));
        return mav;
    }


    @RequestMapping(method = RequestMethod.GET, path = "/{nonnumeric:[a-z]+}")
    public ModelAndView userProfile() {
        final ModelAndView mav = new ModelAndView("helloworld/hello");
        mav.addObject("user",us.findById(-1).orElseThrow(UserNotFoundException::new));
        return mav;
    }

     */


}
