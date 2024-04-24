package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.exceptions.UserNotFoundException;
import ar.edu.itba.paw.services.*;
import ar.edu.itba.paw.webapp.form.ResponseForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller()
public class UserController {
    private final BusinessService businessService;
    private final ServiceService serviceService;
    private final UserService userService;
    private final QuestionService questionService;
    private final AppointmentService appointmentService;
    //todo: autenticar q es el dueño del service

    @Autowired
    public UserController (@Qualifier("BusinessServiceImpl") final BusinessService businessService, @Qualifier("serviceServiceImpl") final ServiceService serviceService,
                              @Qualifier("userServiceImpl") final UserService userService,
                           @Qualifier("QuestionServiceImpl") final QuestionService questionService,
                           @Qualifier("appointmentServiceImpl") final AppointmentService appointmentService) {
        this.businessService = businessService;
        this.serviceService = serviceService;
        this.userService = userService;
        this.questionService = questionService;
        this.appointmentService = appointmentService;

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
        List<Business> businessList;
        if ( user.isProvider() )
            businessList = businessService.findByAdminId(userId).orElse(new ArrayList<>());
        else
            return new ModelAndView("redirect:/crear-negocio");

        mav.addObject("user",user);
        mav.addObject("businessList", businessList);
        return mav;
    }

    // ! USERID HARDCODEADO
    @RequestMapping(method = RequestMethod.GET, path = "/{userId:\\d+}/negocios/consultas")
    public ModelAndView userServices(@ModelAttribute("responseForm") final ResponseForm responseForm) {
        final ModelAndView mav = new ModelAndView("userQuestions");
        // USER ID HARDCODEADO
        mav.addObject("pendingQst", questionService.getQuestionsToRespond(2));
        return mav;
    }

    // TODO
    @RequestMapping(method = RequestMethod.GET, path = "/{userId:\\d+}/turnos/")
    public ModelAndView userAppointments(@PathVariable("userId") final long userId, @RequestParam(name = "confirmados") final boolean confirmed) {

        final ModelAndView mav = new ModelAndView("userAppointments");
        //TODO: validar user

        List<AppointmentInfo> appointmentList = appointmentService.getAllUpcomingUserAppointments(userId,confirmed).orElse(new ArrayList<>());

        mav.addObject("appointmentList", appointmentList);
        mav.addObject("confirmed",confirmed);
        return mav;
    }


}
