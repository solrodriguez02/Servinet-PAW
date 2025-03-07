package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.PricingTypes;
import ar.edu.itba.paw.services.*;
import ar.edu.itba.paw.services.ServiceService;
import ar.edu.itba.paw.webapp.auth.ServinetAuthControl;
import ar.edu.itba.paw.webapp.form.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.*;


@Controller
@Qualifier("ServinetController")
public class ServinetController {

    private UserService us;
    private ServiceService ss;
    private final ServinetAuthControl authControl;
    private final PasswordRecoveryCodeService passwordRecoveryCodeService;
    private static final String TBDPricing = PricingTypes.TBD.getValue();

    private Logger LOGGER = LoggerFactory.getLogger(ServinetController.class);

    @Autowired
    public ServinetController(
            @Qualifier("userServiceImpl") final UserService us,
            @Qualifier("serviceServiceImpl") final ServiceService ss,
            @Qualifier("passwordRecoveryCodeServiceImpl") final PasswordRecoveryCodeService passwordRecoveryCodeService,
            @Qualifier("servinetAuthControl") final ServinetAuthControl authControl
    ){
        this.us = us;
        this.ss = ss;
        this.passwordRecoveryCodeService = passwordRecoveryCodeService;
        this.authControl= authControl;
    }

    @RequestMapping(path="/login")
    public ModelAndView login() {

        return new ModelAndView("login");
    }

    @RequestMapping(path="/registrarse", method=RequestMethod.GET)
    public ModelAndView registerUser(@ModelAttribute("registerUserForm") RegisterUserForm form) {
        final ModelAndView mav = new ModelAndView("postPersonal");

        return mav;
    }

    @RequestMapping(path="/olvide-mi-clave", method = RequestMethod.GET)
    public ModelAndView forgotPasswordRequest(@ModelAttribute("requestPasswordRecoveryForm") RequestPasswordRecoveryForm form) {
        return new ModelAndView("forgotPassword");
    }

    @RequestMapping(path="/olvide-mi-clave", method = RequestMethod.POST)
    public ModelAndView requestPasswordRecovery(@Valid @ModelAttribute("requestPasswordRecoveryForm") RequestPasswordRecoveryForm form, final BindingResult errors) {
        if (errors.hasErrors()){
            return forgotPasswordRequest(form);
        }
       passwordRecoveryCodeService.sendCode(form.getEmail());

        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(path="/restablecer-clave/{token}", method = RequestMethod.GET)
    public ModelAndView resetPasswordRequest(@PathVariable(value = "token") final String token, @ModelAttribute("PasswordResetForm") PasswordResetForm form){
        if (!passwordRecoveryCodeService.validateCode(UUID.fromString(token))){
            //TODO: pantalla de error
            return new ModelAndView("redirect:/login");
        }
        ModelAndView mv = new ModelAndView("resetPassword");
        mv.addObject("token", token);
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST , path = "/restablecer-clave/{token}")
    public ModelAndView resetPassword(@PathVariable(value = "token")final String token, @Valid @ModelAttribute("PasswordResetForm") PasswordResetForm form, final BindingResult errors){
        if (errors.hasErrors()){
            return resetPasswordRequest(token, form);
        }
        passwordRecoveryCodeService.changePassword(UUID.fromString(token), form.getPassword());
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(method = RequestMethod.GET, path = "/")
    public ModelAndView home() {
        final ModelAndView mav = new ModelAndView("home");

        mav.addObject("TBDPricing", TBDPricing);
        mav.addObject("recommendedServices", ss.getRecommendedServices());
        return mav;
    }


    @RequestMapping(method = RequestMethod.POST, path = "/registrarse")
    public ModelAndView post(@ModelAttribute @Valid final RegisterUserForm form, final BindingResult errors
    ) {
        if (errors.hasErrors()) {
            return registerUser(form);
        }
        us.create(form.getUsername(),form.getName(),form.getSurname(),form.getPassword(),form.getEmail(),form.getTelephone());
        return new ModelAndView("redirect:/perfil");
    }

}
