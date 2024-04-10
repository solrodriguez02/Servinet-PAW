package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Appointment;
import ar.edu.itba.paw.model.Service;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.services.AppointmentService;
import ar.edu.itba.paw.services.EmailService;
import ar.edu.itba.paw.services.ServiceService;
import ar.edu.itba.paw.services.UserService;
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

    @Autowired
    public EmailController(
            @Qualifier("emailServiceImpl") final EmailService emailService, @Qualifier("userServiceImpl") final UserService userService,
            @Qualifier("serviceServiceImpl") final ServiceService serviceService,@Qualifier("appointmentServiceImpl") final AppointmentService appointmentService) {
        this.emailService = emailService;
        this.serviceService = serviceService;
        this.userService = userService;
        this.appointmentService = appointmentService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/post/{serviceId:\\d+}")
    public ModelAndView createAppointment(@PathVariable("serviceId") final long serviceId ) {
        String userEmail = "andresdominguez5555@gmail.com";
        // ! temp {
        int index = userEmail.indexOf('@');
        if (index == 0) throw new RuntimeException("not an email");
        String username = userEmail.substring(0, index)+ LocalDateTime.now();
        // ! }

        //User user = userService.create(username,"juan","","juan",userEmail,"");
        Service serv = serviceService.findById(serviceId).orElseThrow(ServiceNotFoundException::new);
        LocalDateTime startDate = LocalDateTime.now(); //! temp
        Appointment createdAppointment = appointmentService.create(serviceId,1, startDate, startDate.plusMinutes(serv.getDuration()), "bahamas" );

        try {
            //* si ya tiene el Service => ya lo paso x param
            emailService.requestAppointment(createdAppointment, userEmail);

        } catch (MessagingException e ){
            System.err.println(e.getMessage());
        }
        return new ModelAndView("redirect:/"+serviceId);
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
        }else {
            if(!newuser.getName().equals(name) || !newuser.getSurname().equals(surname) || !newuser.getTelephone().equals(telephone)){
                //TODO: manejar error de usuario ya existente para que el usuario sepa por que se lo redirige nuevamente
                return new ModelAndView("redirect:/contratar-servicio/"+serviceId);
            }
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

        return new ModelAndView("redirect:/turno/"+ serviceId + "/" + createdAppointment.getId());
    }

    @RequestMapping(method = RequestMethod.POST , path = "/rechazar-turno/{appointmentId:\\d+}")
    public ModelAndView denyAppointment(@PathVariable("appointmentId") final long appointmentId) {
        Appointment appointment = appointmentService.findById(appointmentId).orElseThrow(NoSuchElementException::new);
        long serviceId = appointment.getServiceid();
        appointmentService.cancelAppointment(appointmentId);
        try {
            emailService.deniedAppointment(appointment);

        } catch (MessagingException e ){
            System.err.println(e.getMessage());
        }

        return new ModelAndView("redirect:/sinturno/" + serviceId + "/?argumento=rechazado");
    }

    @RequestMapping(method = RequestMethod.POST , path = "/aceptar-turno/{appointmentId:\\d+}")
    public ModelAndView confirmAppointment(@PathVariable("appointmentId") final long appointmentId) {
        Appointment appointment = appointmentService.findById(appointmentId).orElseThrow(NoSuchElementException::new);
        long serviceId = appointment.getServiceid();
        appointmentService.confirmAppointment(appointmentId);
        try {
            emailService.confirmedAppointment(appointment);

        } catch (MessagingException e ){
            System.err.println(e.getMessage());
        }

        return new ModelAndView("redirect:/turno/"+serviceId+"/"+appointmentId);
    }

    @RequestMapping(method = RequestMethod.POST , path = "/cancelar-turno/{appointmentId:\\d+}")
    public ModelAndView cancelAppointment(@PathVariable("appointmentId") final long appointmentId) {
        Appointment appointment = appointmentService.findById(appointmentId).orElseThrow(NoSuchElementException::new);
        long serviceId = appointment.getServiceid();
        appointmentService.cancelAppointment(appointmentId);
        try {
            emailService.cancelledAppointment(appointment);

        } catch (MessagingException e ){
            System.err.println(e.getMessage());
        }

        return new ModelAndView("redirect:/sinturno/" + serviceId + "/?argumento=cancelado");
    }
}

