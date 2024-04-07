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
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import java.time.LocalDateTime;

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

        User user = userService.create(username,"juan","","juan",userEmail,"");
        Service serv = serviceService.findById(serviceId).orElseThrow(ServiceNotFoundException::new);
        LocalDateTime startDate = LocalDateTime.now(); //! temp
        Appointment createdAppointment = appointmentService.create(serviceId,user.getUserId(), startDate, startDate.plusMinutes(serv.getDuration()) );

        try {
            emailService.requestAppointment(createdAppointment, userEmail);

        } catch (MessagingException e ){
            System.err.println(e.getMessage());
        }


        return new ModelAndView("redirect:/"+serviceId);
    }


}

