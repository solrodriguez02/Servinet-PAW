package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.services.*;
import ar.edu.itba.paw.webapp.exception.ServiceNotFoundException;
import ar.edu.itba.paw.webapp.form.AppointmentForm;
import ar.edu.itba.paw.webapp.form.ServiceForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class EmailController {

    private static final String APPOINTMENT_ALREADY_CONFIRMED = "turnoyaconfirmado";
    private static final String APPOINTMENT_NON_EXISTENT = "turnonoexiste";
    private static final String SERVICET_NON_EXISTENT = "servicionoexiste";

    private final EmailService emailService;
    private final ServiceService serviceService;
    private final UserService userService;
    private final AppointmentService appointmentService;
    private final ManageServiceService manageServiceService;
    private final HelloWorldController helloWorldController;
    private final SecurityService securityService;
    private final ImageService is;
    @Autowired
    public EmailController(
            @Qualifier("emailServiceImpl") final EmailService emailService, @Qualifier("userServiceImpl") final UserService userService,@Qualifier("imageServiceImpl") final ImageService is,
            @Qualifier("serviceServiceImpl") final ServiceService serviceService, @Qualifier("appointmentServiceImpl") final AppointmentService appointmentService,
            @Qualifier("manageServiceServiceImpl") final ManageServiceService manageServiceService,
            @Qualifier("securityServiceImpl") final SecurityService securityService,
            HelloWorldController helloWorldController){
        this.emailService = emailService;
        this.serviceService = serviceService;
        this.userService = userService;
        this.appointmentService = appointmentService;
        this.manageServiceService = manageServiceService;
        this.securityService = securityService;
        this.is = is;
        this.helloWorldController = helloWorldController;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/contratar-servicio/{serviceId:\\d+}")
    public ModelAndView appointment(@PathVariable("serviceId") final long serviceId, @Valid @ModelAttribute("appointmentForm") AppointmentForm form, BindingResult errors) {
        // User newuser = userService.findByEmail(securityemail).orElse(null);
        // if (newuser == null){
        //     newuser = userService.create("default",name,"default",surname,email,telephone);
        //     String newUsername = String.format("%s%s%d",name.replaceAll("\\s", ""),surname.replaceAll("\\s", ""),newuser.getUserId());
        //     userService.changeUsername(newuser.getUserId(),newUsername);
        // }else {
        //     if(!newuser.getName().equals(name) || !newuser.getSurname().equals(surname) || !newuser.getTelephone().equals(telephone)){
        //         //TODO: manejar error de usuario ya existente para que el usuario sepa por que se lo redirige nuevamente
        //         return new ModelAndView("redirect:/contratar-servicio/"+serviceId);
        //     }
        // }
        Service service = this.serviceService.findById(serviceId).orElseThrow(ServiceNotFoundException::new);
        //falta manejo de errores de ingreso del formulario (se lanzarían excepciones a nivel sql)

        Appointment createdAppointment = appointmentService.create(serviceId, securityService.getCurrentUser().get().getUserId(),form.getDate(), form.getDate().plusMinutes(service.getDuration()), form.getLocation() );

        try {
            //* si ya tiene el Service => ya lo paso x param
            emailService.requestAppointment(createdAppointment, securityService.getCurrentUser().get());
        } catch (MessagingException e ){
            System.err.println(e.getMessage());
        }

        return new ModelAndView("redirect:/turno/"+ serviceId + "/" + createdAppointment.getId());
    }

    @RequestMapping(method = RequestMethod.POST , path = "/rechazar-turno/{appointmentId:\\d+}")
    public ModelAndView denyAppointment(@PathVariable("appointmentId") final long appointmentId) {
        Optional<Appointment> optionalAppointment = appointmentService.findById(appointmentId);
        if (!optionalAppointment.isPresent())
            return invalidOperation(APPOINTMENT_NON_EXISTENT);
        Appointment appointment = optionalAppointment.get();
        long serviceId = appointment.getServiceid();
        if (!appointment.getConfirmed()) {
            appointmentService.cancelAppointment(appointmentId);
            try {
                emailService.deniedAppointment(appointment);

            } catch (MessagingException e) {
                System.err.println(e.getMessage());
            }
            return new ModelAndView("redirect:/sinturno/" + serviceId + "/?argumento=rechazado");
        }

        return invalidOperation(APPOINTMENT_ALREADY_CONFIRMED);
    }

    private ModelAndView invalidOperation(String argumento){
        return new ModelAndView("redirect:/operacion-invalida/?argumento="+argumento);
    };

    @RequestMapping(method = RequestMethod.POST , path = "/aceptar-turno/{appointmentId:\\d+}")
    public ModelAndView confirmAppointment(@PathVariable("appointmentId") final long appointmentId) {
        Optional<Appointment> optionalAppointment = appointmentService.findById(appointmentId);
        if (!optionalAppointment.isPresent())
            return invalidOperation(APPOINTMENT_NON_EXISTENT);
        Appointment appointment = optionalAppointment.get();
        long serviceId = appointment.getServiceid();
        if (!appointment.getConfirmed()) {
            appointmentService.confirmAppointment(appointmentId);
            try {
                emailService.confirmedAppointment(appointment);

            } catch (MessagingException e) {
                System.err.println(e.getMessage());
            }
            return new ModelAndView("redirect:/turno/"+serviceId+"/"+appointmentId);
        }
        return invalidOperation(APPOINTMENT_ALREADY_CONFIRMED);
    }

    @RequestMapping(method = RequestMethod.POST , path = "/cancelar-turno/{appointmentId:\\d+}")
    public ModelAndView cancelAppointment(@PathVariable("appointmentId") final long appointmentId) {
        Optional<Appointment> optionalAppointment = appointmentService.findById(appointmentId);
        if (!optionalAppointment.isPresent())
            return invalidOperation(APPOINTMENT_NON_EXISTENT);

        Appointment appointment = optionalAppointment.get();
        long serviceId = appointment.getServiceid();
        appointmentService.cancelAppointment(appointmentId);
        try {
            emailService.cancelledAppointment(appointment);

        } catch (MessagingException e) {
            System.err.println(e.getMessage());
        }
        return new ModelAndView("redirect:/sinturno/" + serviceId + "/?argumento=cancelado");
    }

    @RequestMapping(method = RequestMethod.POST, path = "/crear-servicio/{businessId:\\d+}")
    public ModelAndView createService(@PathVariable("businessId") final long businessId, @Valid @ModelAttribute("serviceForm") final ServiceForm form, BindingResult errors) throws IOException {

        if (errors.hasErrors()) {
            return helloWorldController.registerService(businessId, form);
        }

        long imageId = form.getImage().isEmpty()? 0 : is.addImage(form.getImage().getBytes()).getImageId();
        //final long serviceId = manageServiceService.createService(businessId,title,description,homeserv,neighbourhood,location,category,minimalduration,pricingtype,price,additionalCharges,imageId);
        Service service = serviceService.create(businessId,form.getTitle(),form.getDescription(),form.getHomeserv(),form.getNeighbourhood(),form.getLocation(),form.getCategory(),form.getMinimalduration(),form.getPricingtype(),form.getPrice(),form.getAdditionalCharges(), imageId);
        try {
            emailService.createdService(service);
        } catch (MessagingException e) {
            //usar LOGGING
            throw new RuntimeException(e);
        }
        return new ModelAndView("redirect:/servicio/"+service.getId());
    }

        @RequestMapping(method = RequestMethod.POST , path = "/borrar-servicio/{serviceId:\\d+}")
    public ModelAndView deleteService(@PathVariable("serviceId") final long serviceId){
            Optional<Service> service = serviceService.findById(serviceId);
            if ( !service.isPresent() )
                return invalidOperation(SERVICET_NON_EXISTENT);
            Optional<List<Appointment>> appointmentList = appointmentService.getAllUpcomingServiceAppointments(service.get().getId());
            appointmentList.ifPresent(appointments -> {
                try {
                    emailService.deletedService(service.get(), appointments);
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            });

            serviceService.delete(serviceId);
        return invalidOperation(SERVICET_NON_EXISTENT);
    }

}

