package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Appointment;
import ar.edu.itba.paw.model.Categories;
import ar.edu.itba.paw.model.Neighbourhoods;
import ar.edu.itba.paw.model.PricingTypes;
import ar.edu.itba.paw.model.Service;
import ar.edu.itba.paw.services.*;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.services.AppointmentService;
import ar.edu.itba.paw.services.BusinessService;
import ar.edu.itba.paw.services.ServiceService;
import ar.edu.itba.paw.webapp.exception.ServiceNotFoundException;
import ar.edu.itba.paw.webapp.form.QuestionForm;
import ar.edu.itba.paw.webapp.form.ResponseForm;
import ar.edu.itba.paw.webapp.form.ReviewsForm;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.services.*;
import ar.edu.itba.paw.webapp.auth.ServinetAuthUserDetails;
import ar.edu.itba.paw.webapp.exception.ServiceNotFoundException;
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

    @RequestMapping(method = RequestMethod.GET, path="/crear-servicio/{businessId:\\d+}")
    public ModelAndView registerService(@PathVariable("businessId")long businessId, @ModelAttribute("serviceForm") final ServiceForm form) {
        if(!bs.isBusinessOwner(businessId, securityService.getCurrentUser().get().getUserId())){
            return new ModelAndView("redirect:/operacion-invalida/?argumento=negocionoexiste");
        }
        final ModelAndView mav = new ModelAndView("postService");
        mav.addObject("pricingTypes",pricingTypes);
        mav.addObject("neighbours",neighbourhoods);
        return mav;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/crear-servicio/{businessId:\\d+}")
    public ModelAndView createService(@PathVariable("businessId") final long businessId, @Valid @ModelAttribute("serviceForm") final ServiceForm form, BindingResult errors) throws IOException {

        if (errors.hasErrors()) {
            return registerService(businessId, form);
        }

        long imageId = form.getImage().isEmpty()? 0 : is.addImage(form.getImage().getBytes()).getImageId();
        //final long serviceId = manageServiceService.createService(businessId,title,description,homeserv,neighbourhood,location,category,minimalduration,pricingtype,price,additionalCharges,imageId);
        Business business = bs.findById(businessId).orElseThrow(ServiceNotFoundException::new);
        Service newService = ss.create(business,form.getTitle(),form.getDescription(),form.getHomeserv(),form.getNeighbourhood(),form.getLocation(),form.getCategory(),form.getMinimalduration(),form.getPricingtype(),form.getPrice(),form.getAdditionalCharges(), imageId);
        return new ModelAndView("redirect:/servicio/"+newService.getId());
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

//    @RequestMapping(method = RequestMethod.GET, path = "/contratar-servicio/{serviceId:\\d+}")
//    public ModelAndView hireService(@PathVariable("serviceId") final long serviceId, @ModelAttribute("appointmentForm") final AppointmentForm form) {
//
//        final ModelAndView mav = new ModelAndView("postAppointment");
//        try {
//            Service service = ss.findById(serviceId).orElseThrow(ServiceNotFoundException::new);
//            mav.addObject("service",service);
//        } catch (ServiceNotFoundException ex) {
//            return new ModelAndView("redirect:/operacion-invalida/?argumento=servicionoexiste");
//        }
//
//        return mav;
//    }
    @RequestMapping(method = RequestMethod.GET, path = "/turno/{serviceId:\\d+}/{appointmentId:\\d+}")
    public ModelAndView appointment(
            @PathVariable("appointmentId") final long appointmentId,
            @PathVariable("serviceId") final long serviceId) {

        Optional<Appointment> optionalAppointment = appointment.findById(appointmentId);
        if(!optionalAppointment.isPresent()) {
            if(ss.findById(serviceId).isPresent() ) {
                return new ModelAndView("redirect:/sinturno/" + serviceId + "/?argumento=cancelado");
            }
            else {
                return new ModelAndView("redirect:/sinturno/" + serviceId + "/?argumento=noexiste");
            }
        }

        Appointment app = appointment.findById(appointmentId).get();
        User user = us.findById(app.getUserid()).get();
        Service service = ss.findById(app.getServiceid()).orElseThrow(ServiceNotFoundException::new);
        final ModelAndView mav = new ModelAndView("appointment");
        mav.addObject("appointment", app);
        mav.addObject("user", user);
        mav.addObject("service", service);
        mav.addObject("new", true);
        mav.addObject("confirmed", app.getConfirmed());
        return mav;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/sinturno/{serviceId:\\d+}")
    public ModelAndView noneAppointment(
            @PathVariable("serviceId") final long serviceId,
            @RequestParam(name = "argumento") String argument
    ){
        final ModelAndView mav = new ModelAndView("noneAppointment");
        mav.addObject("argument", argument);
        mav.addObject("serviceId", serviceId);
        return mav;
    }

/*
    @RequestMapping(method = RequestMethod.POST , path = "/cancelar-turno/{appointmentId:\\d+}")
    public ModelAndView cancelAppointment(@PathVariable("appointmentId") final long appointmentId) {
        Appointment app = appointment.findById(appointmentId).get();
        appointment.cancelAppointment(appointmentId);
        return new ModelAndView("redirect:/");
    }
*/
@RequestMapping(method = RequestMethod.GET, path = "/servicio/{serviceId:\\d+}")
public ModelAndView service(
        @PathVariable("serviceId") final long serviceId,
        @ModelAttribute("questionForm") final QuestionForm questionForm,
        @ModelAttribute("reviewForm") final ReviewsForm reviewForm,
        @RequestParam(value = "opcion", required = false) final String option,
        @RequestParam(value = "qstPag", required = false) Integer questionPage,
        @RequestParam(value = "rwPag", required = false) Integer reviewPage
) {
    final ModelAndView mav = new ModelAndView("service");
    Service serv;
    if(questionPage == null) questionPage = 0;
    if(reviewPage == null) reviewPage = 0;
    try {
        serv = ss.findById(serviceId).orElseThrow(ServiceNotFoundException::new);
    } catch (ServiceNotFoundException e) {
        return new ModelAndView("redirect:/operacion-invalida/?argumento=servicionoexiste");
    }
    mav.addObject("option", option);
    mav.addObject("avgRating", rating.getRatingsAvg(serviceId));
    mav.addObject("service",serv);
    mav.addObject("questions", question.getAllQuestions(serviceId, questionPage));
    mav.addObject("reviews", rating.getAllRatings(serviceId, reviewPage));
    mav.addObject("questionsCount", question.getQuestionsCount(serviceId));
    mav.addObject("reviewsCount", rating.getRatingsCount(serviceId));
    mav.addObject("questionPage", questionPage);
    mav.addObject("reviewPage", reviewPage);
    mav.addObject("TBDPricing", TBDPricing);
    mav.addObject("hasAlreadyRated", rating.hasAlreadyRated(1, serviceId));
    return mav;
}


}
