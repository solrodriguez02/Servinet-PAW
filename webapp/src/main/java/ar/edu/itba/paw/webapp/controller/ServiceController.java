package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.services.AppointmentService;
import ar.edu.itba.paw.services.ServiceService;
import ar.edu.itba.paw.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Controller
public class ServiceController {
    //    private final EmailService emailService;
    private final ServiceService serviceService;
    private final UserService userService;
    private final AppointmentService appointmentService;

    @Autowired
    public ServiceController(
            @Qualifier("userServiceImpl") final UserService userService,
            @Qualifier("serviceServiceImpl") final ServiceService serviceService, @Qualifier("appointmentServiceImpl") final AppointmentService appointmentService) {
//        this.emailService = emailService;
        this.serviceService = serviceService;
        this.userService = userService;
        this.appointmentService = appointmentService;
    }
}