package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Business;
import ar.edu.itba.paw.model.Service;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exceptions.BusinessNotFoundException;
import ar.edu.itba.paw.model.exceptions.UserNotFoundException;
import ar.edu.itba.paw.services.*;
import ar.edu.itba.paw.webapp.form.ResponseForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    private final QuestionService questionService;
    private final SecurityService securityService;

    //todo: autenticar q es el dueño del service

    @Autowired
    public UserController (@Qualifier("BusinessServiceImpl") final BusinessService businessService, @Qualifier("serviceServiceImpl") final ServiceService serviceService,
                              @Qualifier("userServiceImpl") final UserService userService, @Qualifier("QuestionServiceImpl") final QuestionService questionService,
                              @Qualifier("securityServiceImpl") final SecurityService securityService){
        this.businessService = businessService;
        this.serviceService = serviceService;
        this.userService = userService;
        this.questionService = questionService;
        this.securityService = securityService;

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
            return new ModelAndView("redirect:/crear-negocio");

        mav.addObject("user",currentUser);
        mav.addObject("businessList", businessList);
        return mav;
    }

    // ! USERID HARDCODEADO
    @RequestMapping(method = RequestMethod.GET, path = "/negocios/consultas")
    public ModelAndView userServices(@ModelAttribute("responseForm") final ResponseForm responseForm) {
        final ModelAndView mav = new ModelAndView("userQuestions");
        long userid = securityService.getCurrentUser().get().getUserId();

        mav.addObject("pendingQst", questionService.getQuestionsToRespond(userid));
        return mav;
    }


}
