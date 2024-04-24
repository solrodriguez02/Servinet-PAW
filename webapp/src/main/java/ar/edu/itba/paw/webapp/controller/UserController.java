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

@Controller
public class UserController {
    private final BusinessService businessService;
    private final ServiceService serviceService;
    private final UserService userService;
    private final QuestionService questionService;
    private final SecurityService securityService;
    private final AppointmentService appointmentService;

    @Autowired
    public UserController (@Qualifier("BusinessServiceImpl") final BusinessService businessService, @Qualifier("serviceServiceImpl") final ServiceService serviceService,
                            @Qualifier("userServiceImpl") final UserService userService,
                           @Qualifier("QuestionServiceImpl") final QuestionService questionService,
                              @Qualifier("securityServiceImpl") final SecurityService securityService,
                               @Qualifier("appointmentServiceImpl") final AppointmentService appointmentService){

        this.businessService = businessService;
        this.serviceService = serviceService;
        this.userService = userService;
        this.questionService = questionService;
        this.securityService = securityService;
        this.appointmentService = appointmentService;
    }

//    @RequestMapping(method = RequestMethod.GET, path = "/{userId:\\d+}/perfil")
    @RequestMapping(method = RequestMethod.GET, path = "/perfil")
    public ModelAndView profile() {
        final ModelAndView mav = new ModelAndView("profile");
        //TODO:agregar regla para casos anonymous
        long userid = securityService.getCurrentUser().get().getUserId();

        User user = userService.findById(userid).orElseThrow(UserNotFoundException::new);
        List<Business> businessList = new ArrayList<>();
        if ( user.isProvider() )
            businessList = businessService.findByAdminId(userid).orElse(new ArrayList<>());

        mav.addObject("businessList", businessList);
        mav.addObject("user", user);
        return mav;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/negocios")
    public ModelAndView business() {
        final ModelAndView mav = new ModelAndView("userBusiness");
        long userid = securityService.getCurrentUser().get().getUserId();
        User currentUser = userService.findById(userid).orElseThrow(UserNotFoundException::new);

        List<Business> businessList;
        if ( currentUser.isProvider() )
            businessList = businessService.findByAdminId(currentUser.getUserId()).orElse(new ArrayList<>());
        else
            return new ModelAndView("redirect:/registrar-negocio");

        mav.addObject("user",currentUser);
        mav.addObject("businessList", businessList);
        return mav;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/negocios/consultas")
    public ModelAndView userServices(@ModelAttribute("responseForm") final ResponseForm responseForm) {
        final ModelAndView mav = new ModelAndView("userQuestions");
        long userid = securityService.getCurrentUser().get().getUserId();

        mav.addObject("pendingQst", questionService.getQuestionsToRespond(userid));
        return mav;
    }


    @RequestMapping(method = RequestMethod.GET, path = "/turnos")
    public ModelAndView userAppointments( @RequestParam(name = "confirmados") final boolean confirmed) {

        final ModelAndView mav = new ModelAndView("userAppointments");
        //TODO: validar user

        long userid = securityService.getCurrentUser().get().getUserId();

        List<AppointmentInfo> appointmentList = appointmentService.getAllUpcomingUserAppointments(userid,confirmed).orElse(new ArrayList<>());

        mav.addObject("appointmentList", appointmentList);
        mav.addObject("confirmed",confirmed);
        mav.addObject("userId", userid);
        return mav;
    }


}
