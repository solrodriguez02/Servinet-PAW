package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.services.*;
import ar.edu.itba.paw.webapp.exception.ServiceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Controller
public class EmailController {
    private final EmailService emailService;
    private final ServiceService serviceService;
    private final UserService userService;
    private final AppointmentService appointmentService;
    private final ManageServiceService manageServiceService;
    @Autowired
    public EmailController(
            @Qualifier("emailServiceImpl") final EmailService emailService, @Qualifier("userServiceImpl") final UserService userService,
            @Qualifier("serviceServiceImpl") final ServiceService serviceService, @Qualifier("appointmentServiceImpl") final AppointmentService appointmentService,
            @Qualifier("manageServiceServiceImpl") final ManageServiceService manageServiceService) {
        this.emailService = emailService;
        this.serviceService = serviceService;
        this.userService = userService;
        this.appointmentService = appointmentService;
        this.manageServiceService = manageServiceService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/nuevo-turno/{serviceId:\\d+}")
    public ModelAndView appointment(@PathVariable("serviceId") final long serviceId,
                                    @RequestParam(value = "nombre") final String name,
                                    @RequestParam(value = "apellido") final String surname,
                                    @RequestParam(value = "lugar") final String location,
                                    @RequestParam(value = "email") final String email,
                                    @RequestParam(value = "telefono") final String telephone,
                                    @RequestParam(value = "fecha") final String date
    ) {
        User newuser = userService.findByEmail(email).orElse(null);
        if (newuser == null){
            newuser = userService.create("default",name,"default",surname,email,telephone);
            String newUsername = String.format("%s%s%d",name.replaceAll("\\s", ""),surname.replaceAll("\\s", ""),newuser.getUserId());
            userService.changeUsername(newuser.getUserId(),newUsername);
        }
        Service service = this.serviceService.findById(serviceId).orElseThrow(ServiceNotFoundException::new);
        //falta manejo de errores de ingreso del formulario (se lanzarían excepciones a nivel sql)

        LocalDateTime startDate = LocalDateTime.parse(date);
        Appointment createdAppointment = appointmentService.create(serviceId, newuser.getUserId(),startDate, startDate.plusMinutes(service.getDuration()), location );

        try {
            //* si ya tiene el Service => ya lo paso x param
            emailService.requestAppointment(createdAppointment, newuser.getEmail());

        } catch (MessagingException e ){
            System.err.println(e.getMessage());
        }

        return new ModelAndView("redirect:/turno/"+createdAppointment.getId());
    }

    @RequestMapping(method = RequestMethod.POST , path = "/rechazar-turno/{appointmentId:\\d+}")
    public ModelAndView denyAppointment(@PathVariable("appointmentId") final long appointmentId) {
        Appointment appointment = appointmentService.findById(appointmentId).orElseThrow(NoSuchElementException::new);
        appointmentService.cancelAppointment(appointmentId);
        try {
            emailService.deniedAppointment(appointment);

        } catch (MessagingException e ){
            System.err.println(e.getMessage());
        }

        return new ModelAndView("redirect:/turno/"+appointmentId);
    }

    @RequestMapping(method = RequestMethod.POST , path = "/aceptar-turno/{appointmentId:\\d+}")
    public ModelAndView confirmAppointment(@PathVariable("appointmentId") final long appointmentId) {
        Appointment appointment = appointmentService.findById(appointmentId).orElseThrow(NoSuchElementException::new);
        appointmentService.confirmAppointment(appointmentId);
        try {
            emailService.confirmedAppointment(appointment);

        } catch (MessagingException e ){
            System.err.println(e.getMessage());
        }

        return new ModelAndView("redirect:/turno/"+appointmentId);
    }

    @RequestMapping(method = RequestMethod.POST , path = "/cancelar-turno/{appointmentId:\\d+}")
    public ModelAndView cancelAppointment(@PathVariable("appointmentId") final long appointmentId) {

        Appointment appointment = appointmentService.findById(appointmentId).orElseThrow(NoSuchElementException::new);
        appointmentService.cancelAppointment(appointmentId);
        try {
            emailService.cancelledAppointment(appointment);

        } catch (MessagingException e ){
            System.err.println(e.getMessage());
        }

        return new ModelAndView("redirect:/turno/"+appointmentId);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/crearservicio")
    public ModelAndView createService(@RequestParam(value = "titulo") final String title,
                                      @RequestParam(value="descripcion") final String description,
                                      @RequestParam(value="homeserv",required = false, defaultValue = "false") final boolean homeserv,
                                      @RequestParam(value="ubicacion",required = false, defaultValue = "") final String location,
                                      @RequestParam(value="categoria") final Categories category,
                                      @RequestParam(value="neighbourhood") final Neighbourhoods neighbourhood,
                                      @RequestParam(value="pricingtype") final PricingTypes pricingtype,
                                      @RequestParam(value="precio") final String price,
                                      @RequestParam(value="minimalduration",defaultValue = "0") final int minimalduration,
                                      @RequestParam(value="additionalCharges",defaultValue = "false") final boolean additionalCharges){
        final long serviceId = manageServiceService.createService(1,title,description,homeserv,neighbourhood,location,category,minimalduration,pricingtype,price,additionalCharges);
        return new ModelAndView("redirect:/"+serviceId);
    }

    @RequestMapping(method = RequestMethod.POST , path = "/borrar-servicio/{serviceId:\\d+}")
    public ModelAndView deleteService(@PathVariable("serviceId") final long serviceId){
        manageServiceService.deleteService(serviceId);
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(method = RequestMethod.GET, path = "/trucho")
    public ModelAndView trucho(){
        final long serviceId = manageServiceService.createService(1,"title","description",true,Neighbourhoods.ALMAGRO,"location",Categories.BELLEZA,4,PricingTypes.PER_TOTAL,"40",true);
        return new ModelAndView("redirect:/"+serviceId);
    }

}

