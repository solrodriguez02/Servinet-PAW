package ar.edu.itba.paw.webapp.controller;

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
import java.util.*;


@Controller
public class HelloWorldController {

    private UserService us;
    private BusinessService bs;
    private ServiceService service;
    private AppointmentService appointment;
    private final ManageServiceService manageServiceService;

    private final SecurityService securityService;
    private final PasswordRecoveryCodeService passwordRecoveryCodeService;
    private ImageService is;

    List<Categories> categories = new ArrayList<>();
    List<PricingTypes> pricingTypes = new ArrayList<>();
    List<Neighbourhoods> neighbourhoods = new ArrayList<>();

    @Autowired
    public HelloWorldController( @Qualifier("userServiceImpl") final UserService us,@Qualifier("imageServiceImpl") final ImageService is, @Qualifier("serviceServiceImpl") final ServiceService service,
    @Qualifier("appointmentServiceImpl") final AppointmentService appointment, @Qualifier("BusinessServiceImpl") final BusinessService bs, @Qualifier("manageServiceServiceImpl") final ManageServiceService manageServiceService, @Qualifier("passwordRecoveryCodeServiceImpl") final PasswordRecoveryCodeService passwordRecoveryCodeService, @Qualifier("securityServiceImpl") final SecurityService securityService){
        this.us = us;
        this.service = service;
        this.appointment = appointment;
        this.manageServiceService = manageServiceService;
        this.passwordRecoveryCodeService = passwordRecoveryCodeService;
        this.securityService = securityService;

        this.is=is;
        this.bs = bs;
        categories.addAll(Arrays.asList(Categories.values()));
        pricingTypes.addAll(Arrays.asList(PricingTypes.values()));
        neighbourhoods.addAll(Arrays.asList(Neighbourhoods.values()));
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
            return invalidOperation("tokeninvalido");
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

    @RequestMapping(method = RequestMethod.GET, path = "/servicios")
    public ModelAndView services(
            @RequestParam(name = "categoria", required = false) String category,
            @RequestParam(name = "ubicacion", required = false) String location,
            @RequestParam(name = "pagina", required = false) Integer page
    ) {
        final ModelAndView mav = new ModelAndView("services");
        if(page == null) page=0;
        List<Service> serviceList = service.services(page, category, location);
        mav.addObject("services", serviceList);
        mav.addObject("page", page);
        mav.addObject("isServicesEmpty", serviceList.isEmpty());
        mav.addObject("category", category);
        mav.addObject("neighbourhoods", neighbourhoods);
        mav.addObject("location", location);
        mav.addObject("resultsAmount", service.getServiceCount(category, location));
        mav.addObject("pageCount", service.getPageCount(category, location));
        return mav;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/publicar-servicio/{businessId:\\d+}")
    public ModelAndView postForm(@PathVariable("businessId") final long businessId){
        final ModelAndView mav = new ModelAndView("postService");
        mav.addObject("pricingTypes",pricingTypes);
        mav.addObject("neighbours",neighbourhoods);
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

    @RequestMapping(method = RequestMethod.GET, path = "/contratar-servicio/{serviceId:\\d+}")
    public ModelAndView hireService(@PathVariable("serviceId") final long serviceId, @ModelAttribute("appointmentForm") final AppointmentForm form) {

        final ModelAndView mav = new ModelAndView("postAppointment");
        try {
            Service service = this.service.findById(serviceId).orElseThrow(ServiceNotFoundException::new);
            mav.addObject("service",service);
        } catch (ServiceNotFoundException ex) {
            return new ModelAndView("redirect:/operacion-invalida/?argumento=servicionoexiste");
        }

        return mav;
    }
/*
    @RequestMapping(method = RequestMethod.POST, path = "/nuevo-turno/{serviceId:\\d+}")
    public ModelAndView appointment(@PathVariable("serviceId") final long serviceId,
                                    @RequestParam(value = "nombre") final String name,
                                    @RequestParam(value = "apellido") final String surname,
                                    @RequestParam(value = "lugar") final String location,
                                    @RequestParam(value = "email") final String email,
                                    @RequestParam(value = "telefono") final String telephone,
                                    @RequestParam(value = "fecha") final String date
    ) {
        User newuser = us.findByEmail(email).orElse(null);
        if (newuser == null){
            newuser = us.create("default",name,"default",surname,email,telephone);
            String newUsername = String.format("%s%s%d",name.replaceAll("\\s", ""),surname.replaceAll("\\s", ""),newuser.getUserId());
            us.changeUsername(newuser.getUserId(),newUsername);
        }
        Service service = this.service.findById(serviceId).orElseThrow(ServiceNotFoundException::new);
        //falta manejo de errores de ingreso del formulario (se lanzarían excepciones a nivel sql)

        LocalDateTime startDate = LocalDateTime.parse(date);
        Appointment app = appointment.create(serviceId, newuser.getUserId(),startDate, startDate.plusMinutes(service.getDuration()), location );


        return new ModelAndView("redirect:/turno/"+app.getId());
    }
*/
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
    public ModelAndView service(@PathVariable("serviceId") final long serviceId) {
        final ModelAndView mav = new ModelAndView("service");
        Service serv;
        try {
            serv = service.findById(serviceId).orElseThrow(ServiceNotFoundException::new);
        } catch (ServiceNotFoundException e) {
            return new ModelAndView("redirect:/operacion-invalida/?argumento=servicionoexiste");
        }
        mav.addObject("service",serv);
        return mav;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/operacion-invalida")
    public ModelAndView invalidOperation(@RequestParam(value = "argumento") final String argument) {
        final ModelAndView mav = new ModelAndView("invalidOperation");
        mav.addObject("argument",argument);
        return mav;
    }


    @RequestMapping(method = RequestMethod.GET, path = "/{userId}/micuenta")
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
