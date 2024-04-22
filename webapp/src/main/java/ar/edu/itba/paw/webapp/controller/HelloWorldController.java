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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Controller
@Qualifier("HelloWorldController")
public class HelloWorldController {

    private UserService us;
    private BusinessService bs;
    private ServiceService service;
    private AppointmentService appointment;
    private RatingService rating;
    private QuestionService question;
    private ImageService is;

    List<Categories> categories = new ArrayList<>();
    List<PricingTypes> pricingTypes = new ArrayList<>();
    List<Neighbourhoods> neighbourhoods = new ArrayList<>();
    List<Ratings> ratings = new ArrayList<>();

    @Autowired
    public HelloWorldController(
            @Qualifier("userServiceImpl") final UserService us,
            @Qualifier("imageServiceImpl") final ImageService is,
            @Qualifier("serviceServiceImpl") final ServiceService service,
            @Qualifier("appointmentServiceImpl") final AppointmentService appointment,
            @Qualifier("BusinessServiceImpl") final BusinessService bs,
            @Qualifier("QuestionServiceImpl") final QuestionService question,
            @Qualifier("RatingServiceImpl") final RatingServiceImpl rating
    ){
        this.us = us;
        this.service = service;
        this.appointment = appointment;
        this.is=is;
        this.bs = bs;
        this.rating = rating;
        this.question = question;
        categories.addAll(Arrays.asList(Categories.values()));
        pricingTypes.addAll(Arrays.asList(PricingTypes.values()));
        neighbourhoods.addAll(Arrays.asList(Neighbourhoods.values()));
        ratings.addAll(Arrays.asList(Ratings.values()));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/")
    public ModelAndView home() {
        final ModelAndView mav = new ModelAndView("home");
        mav.addObject("categories", categories);
        mav.addObject("neighbourhoods", neighbourhoods);
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
        List<Service> serviceList = service.services(page, category, neighbourhoodFilters, ratingFilters, query);
        mav.addObject("services", serviceList);
        mav.addObject("page", page);
        mav.addObject("isServicesEmpty", serviceList.isEmpty());
        mav.addObject("category", category);
        mav.addObject("neighbourhoods", neighbourhoods);
        mav.addObject("location", neighbourhoodFilters);
        mav.addObject("resultsAmount", service.getServiceCount(category, neighbourhoodFilters,query));
        mav.addObject("pageCount", service.getPageCount(category, neighbourhoodFilters,query));
        mav.addObject("ratings", ratings);
        return mav;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/publicar-servicio/{businessId:\\d+}")
    public ModelAndView postForm(@PathVariable("businessId") final long businessId){
        final ModelAndView mav = new ModelAndView("post");
        mav.addObject("pricingTypes",pricingTypes);
        mav.addObject("neighbours",neighbourhoods);
        return mav;
    }


    @RequestMapping(method = RequestMethod.GET, path = "/registrar-datos-personales")
    public ModelAndView personalForm(
    ) {
        return new ModelAndView("postPersonal");
    }

    @RequestMapping(method = RequestMethod.POST, path = "/crear-perfiles")
    public ModelAndView post(
            @RequestParam(value = "nombre") final String name,
            @RequestParam(value = "apellido") final String surname,
            @RequestParam(value = "email") final String email,
            @RequestParam(value = "telefono") final String telephone,
            @RequestParam(value = "nombre-negocio") final String businessName,
            @RequestParam(value = "email-negocio") final String businessEmail,
            @RequestParam(value = "telefono-negocio") final String businessTelephone,
            @RequestParam(value = "ubicacion-negocio") final String businessLocation
    ) {
        User newuser = us.findByEmail(email).orElse(null);
        if (newuser == null){
            newuser = us.create("default",name,"default",surname,email,telephone);
            String newUsername = String.format("%s%s%d",name.replaceAll("\\s", ""),surname.replaceAll("\\s", ""),newuser.getUserId());
            us.changeUsername(newuser.getUserId(),newUsername);
        }else {
            if (!newuser.getName().equals(name) || !newuser.getSurname().equals(surname)){
                return new ModelAndView("redirect:/registrar-datos-personales");
            }
        }
        Business newBusiness = bs.findByBusinessName(businessName).orElse(null);
        if (newBusiness == null) {
            String finalBusinessName = businessName.equals("") ? String.format("Servinet de %s %s", name, surname) : businessName;
            String finalBusinessEmail = businessEmail.equals("") ? email : businessEmail;
            String finalBusinessTelephone = businessTelephone.equals("") ? telephone : businessTelephone;
            newBusiness = bs.createBusiness(finalBusinessName, newuser.getUserId(), finalBusinessTelephone, finalBusinessEmail, businessLocation);
        }else {
            if (newBusiness.getUserId() != newuser.getUserId()){
                return new ModelAndView("redirect:/registrar-datos-personales");
            }
        }
        return new ModelAndView("redirect:/publicar-servicio/" + newBusiness.getBusinessid());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/contratar-servicio/{serviceId:\\d+}")
    public ModelAndView hireService(@PathVariable("serviceId") final long serviceId){

        final ModelAndView mav = new ModelAndView("postAppointment");
        try {
            Service service = this.service.findById(serviceId).orElseThrow(ServiceNotFoundException::new);
            mav.addObject("service",service);
        } catch (ServiceNotFoundException ex) {
            return new ModelAndView("redirect:/operacion-invalida/?argumento=servicionoexiste");
        }

        return mav;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/turno/{serviceId:\\d+}/{appointmentId:\\d+}")
    public ModelAndView appointment(
            @PathVariable("appointmentId") final long appointmentId,
            @PathVariable("serviceId") final long serviceId) {

        Optional<Appointment> optionalAppointment = appointment.findById(appointmentId);
        if(!optionalAppointment.isPresent()) {
            if(service.findById(serviceId).isPresent() ) {
                return new ModelAndView("redirect:/sinturno/" + serviceId + "/?argumento=cancelado");
            }
            else {
                return new ModelAndView("redirect:/sinturno/" + serviceId + "/?argumento=noexiste");
            }
        }

        Appointment app = appointment.findById(appointmentId).get();
        User user = us.findById(app.getUserid()).get();
        Service service = this.service.findById(app.getServiceid()).orElseThrow(ServiceNotFoundException::new);
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
        serv = service.findById(serviceId).orElseThrow(ServiceNotFoundException::new);
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
    return mav;
}


    @RequestMapping(method = RequestMethod.GET, path = "/{userId}/micuenta")
    public ModelAndView profile() {
        final ModelAndView mav = new ModelAndView("profile");
        mav.addObject("username", us);
        return mav;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/misservicios")
    public ModelAndView userServices(@ModelAttribute("responseForm") final ResponseForm responseForm) {
        final ModelAndView mav = new ModelAndView("userServices");
        // USER ID HARDCODEADO
        mav.addObject("pendingQst", question.getQuestionsToRespond(1));
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
