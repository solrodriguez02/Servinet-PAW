package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Appointment;
import ar.edu.itba.paw.model.Categories;
import ar.edu.itba.paw.model.Neighbourhoods;
import ar.edu.itba.paw.model.PricingTypes;
import ar.edu.itba.paw.model.Service;
import ar.edu.itba.paw.model.exceptions.ServiceNotFoundException;
import ar.edu.itba.paw.model.exceptions.UserNotFoundException;
import ar.edu.itba.paw.services.*;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.services.AppointmentService;
import ar.edu.itba.paw.services.BusinessService;
import ar.edu.itba.paw.services.ServiceService;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.services.*;
import ar.edu.itba.paw.webapp.auth.ServinetAuthUserDetails;
import ar.edu.itba.paw.webapp.form.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;


@Controller
@Qualifier("HelloWorldController")
public class HelloWorldController {

    private UserService us;
    private BusinessService bs;
    private ServiceService ss;
    private AppointmentService appointment;
    private final SecurityService securityService;
    private final PasswordRecoveryCodeService passwordRecoveryCodeService;
    private RatingService rating;
    private QuestionService question;
    private ImageService is;
    private static final String TBDPricing = PricingTypes.TBD.getValue();

    List<Categories> categories = new ArrayList<>();
    List<PricingTypes> pricingTypes = new ArrayList<>();
    List<Neighbourhoods> neighbourhoods = new ArrayList<>();
    List<Ratings> ratings = new ArrayList<>();


    @Autowired
    public HelloWorldController(
            @Qualifier("userServiceImpl") final UserService us,
            @Qualifier("imageServiceImpl") final ImageService is,
            @Qualifier("serviceServiceImpl") final ServiceService ss,
            @Qualifier("appointmentServiceImpl") final AppointmentService appointment,
            @Qualifier("BusinessServiceImpl") final BusinessService bs,
            @Qualifier("QuestionServiceImpl") final QuestionService question,
            @Qualifier("RatingServiceImpl") final RatingService rating,
            @Qualifier("passwordRecoveryCodeServiceImpl") final PasswordRecoveryCodeService passwordRecoveryCodeService,
            @Qualifier("securityServiceImpl") final SecurityService securityService
    ){
        this.us = us;
        this.ss = ss;
        this.appointment = appointment;
        this.passwordRecoveryCodeService = passwordRecoveryCodeService;
        this.securityService = securityService;
        this.is=is;
        this.bs = bs;
        this.rating = rating;
        this.question = question;
        categories.addAll(Arrays.asList(Categories.values()));
        pricingTypes.addAll(Arrays.asList(PricingTypes.values()));
        neighbourhoods.addAll(Arrays.asList(Neighbourhoods.values()));
        ratings.addAll(Arrays.asList(Ratings.values()));
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
        try{
            passwordRecoveryCodeService.sendCode(form.getEmail());
        }catch(MessagingException e){
            //usar LOGGING
            System.err.println(e.getMessage());
        }

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
        try {
            passwordRecoveryCodeService.changePassword(UUID.fromString(token), form.getPassword());
        } catch (MessagingException e) {
            System.err.println(e.getMessage());
        }
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(method = RequestMethod.GET, path = "/")
    public ModelAndView home() {
        final ModelAndView mav = new ModelAndView("home");
        mav.addObject("categories", categories);
        mav.addObject("neighbourhoods", neighbourhoods);
        mav.addObject("TBDPricing", TBDPricing);
        mav.addObject("recommendedServices", ss.getRecommendedServices());
        return mav;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/servicios")
    public ModelAndView services(
            @RequestParam(name = "categoria", required = false) String category,
            @RequestParam(name = "ubicacion", required = false) String[] neighbourhoodFilters,
            @RequestParam(name = "calificacion", required = false) String ratingFilters,
            @RequestParam(name = "pagina", required = false) Integer page,
            @RequestParam(name="query",required=false) String query
    ) {
        if(page == null) page = 0;
        final ModelAndView mav = new ModelAndView("services");

        List<Service> serviceList = ss.services(page, category, neighbourhoodFilters, ratingFilters, query);
        mav.addObject("services", serviceList);
        mav.addObject("page", page);
        mav.addObject("isServicesEmpty", serviceList.isEmpty());
        mav.addObject("category", category);
        mav.addObject("neighbourhoods", neighbourhoods);
        mav.addObject("location", neighbourhoodFilters);
        mav.addObject("resultsAmount", ss.getServiceCount(category, neighbourhoodFilters, ratingFilters, query));
        mav.addObject("pageCount", ss.getPageCount(category, neighbourhoodFilters, ratingFilters, query));
        mav.addObject("ratings", ratings);
        return mav;
    }

    // @RequestMapping(method = RequestMethod.GET, path = "/publicar-servicio/{businessId:\\d+}")
    // public ModelAndView postForm(@PathVariable("businessId") final long businessId){
    //     final ModelAndView mav = new ModelAndView("postService");
    //     mav.addObject("pricingTypes",pricingTypes);
    //     mav.addObject("neighbours",neighbourhoods);
    //     return mav;
    // }

    @RequestMapping(method = RequestMethod.GET, path="/publicar")
    public ModelAndView publish() {
        final ModelAndView mav = new ModelAndView("publish");
        User currentUser = securityService.getCurrentUser().get();
        if(!currentUser.isProvider()) {
            return new ModelAndView("redirect:/registrar-negocio");
        }
        return mav;
    }

    @RequestMapping(method = RequestMethod.GET, path="/registrar-negocio")
    public ModelAndView registerBusiness(@ModelAttribute("BusinessForm") final BusinessForm form) {
        final ModelAndView mav = new ModelAndView("postBusiness");
        mav.addObject("neighbours",neighbourhoods);
        return mav;
    }
    /*
    @RequestMapping(method = RequestMethod.POST, path = "/{serviceid}/eliminar-servicio")
    public ModelAndView deleteService(@PathVariable(value = "serviceid") final long serviceid){
        service.delete(serviceid);
        return new ModelAndView("redirect:/");
    }
    */

    @RequestMapping(method = RequestMethod.POST, path = "/registrar-negocio")
    public ModelAndView postBusiness(@ModelAttribute("BusinessForm") @Valid final BusinessForm form, final BindingResult errors
    ) {
        if (errors.hasErrors()) {
            return registerBusiness(form);
        }
        ServinetAuthUserDetails userDetails = (ServinetAuthUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = us.findByEmail(userDetails.getUsername()).orElse(null);
        if (user == null){
            return new ModelAndView("redirect:/login");
        }
        bs.createBusiness(form.getBusinessName(),user.getUserId(), form.getBusinessEmail(),form.getBusinessTelephone(),form.getBusinessLocation());
        return new ModelAndView("redirect:/");
    }

//@RequestParam(value = "nombre-negocio") final String businessName,
//@RequestParam(value = "email-negocio") final String businessEmail,
//@RequestParam(value = "telefono-negocio") final String businessTelephone,
//@RequestParam(value = "ubicacion-negocio") final String businessLocation
        // Business newBusiness = bs.findByBusinessName(businessName).orElse(null);
        // if (newBusiness == null) {
        //     String finalBusinessName = businessName.equals("") ? String.format("Servinet de %s %s", name, surname) : businessName;
        //     String finalBusinessEmail = businessEmail.equals("") ? email : businessEmail;
        //     String finalBusinessTelephone = businessTelephone.equals("") ? telephone : businessTelephone;
        //     newBusiness = bs.createBusiness(finalBusinessName, newuser.getUserId(), finalBusinessTelephone, finalBusinessEmail, businessLocation);
        // }else {
        //     if (newBusiness.getUserId() != newuser.getUserId()){
        //         return new ModelAndView("redirect:/registrar-datos-personales");
        //     }
        // }
    @RequestMapping(method = RequestMethod.POST, path = "/registrarse")
    public ModelAndView post(@ModelAttribute @Valid final RegisterUserForm form, final BindingResult errors
    ) {
        if (errors.hasErrors()) {
            return registerUser(form);
        }
        us.create(form.getUsername(),form.getName(),form.getPassword(),form.getSurname(),form.getEmail(),form.getTelephone());
        return new ModelAndView("redirect:/login");
    }


}
