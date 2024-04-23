package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Business;
import ar.edu.itba.paw.model.Service;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exceptions.BusinessNotFoundException;
import ar.edu.itba.paw.model.exceptions.UserNotFoundException;
import ar.edu.itba.paw.services.AppointmentService;
import ar.edu.itba.paw.services.BusinessService;
import ar.edu.itba.paw.services.ServiceService;
import ar.edu.itba.paw.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private final BusinessService businessService;
    private final ServiceService serviceService;
    private final UserService userService;

    //todo: autenticar q es el dueño del service

    @Autowired
    public UserController (@Qualifier("BusinessServiceImpl") final BusinessService businessService, @Qualifier("serviceServiceImpl") final ServiceService serviceService,
                              @Qualifier("userServiceImpl") final UserService userService) {
        this.businessService = businessService;
        this.serviceService = serviceService;
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{userId:\\d+}/perfil")
    public ModelAndView profile(@PathVariable("userId") final long userId) {
        final ModelAndView mav = new ModelAndView("profile");

        User user = userService.findById(userId).orElseThrow(UserNotFoundException::new);
        List<Business> businessList = new ArrayList<>();
        if ( user.isProvider() )
            businessList = businessService.findByAdminId(userId).orElse(new ArrayList<>());

        mav.addObject("businessList", businessList);
        mav.addObject("user",user);
        return mav;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{userId:\\d+}/negocios")
    public ModelAndView business(@PathVariable("userId") final long userId) {
        final ModelAndView mav = new ModelAndView("userBusiness");

        User user = userService.findById(userId).orElseThrow(UserNotFoundException::new);
        List<Business> businessList = new ArrayList<>();
        if ( user.isProvider() )
            businessList = businessService.findByAdminId(userId).orElse(new ArrayList<>());
        else
            return new ModelAndView("redirect:/crear-negocio");

        mav.addObject("user",user);
        mav.addObject("businessList", businessList);
        return mav;
    }



}
