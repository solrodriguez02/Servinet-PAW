package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.exceptions.AppointmentNonExistentException;
import ar.edu.itba.paw.model.exceptions.ForbiddenOperation;
import ar.edu.itba.paw.model.exceptions.ServiceNotFoundException;
import ar.edu.itba.paw.services.*;
import ar.edu.itba.paw.webapp.form.AppointmentForm;
import ar.edu.itba.paw.webapp.form.ServiceForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

@Controller
public class AppointmentController {

    private final ServiceService serviceService;
    private final AppointmentService appointmentService;
    private final SecurityService securityService;
    private final ImageService is;
    private final BusinessService businessService;

    @Autowired
    public AppointmentController(
        @Qualifier("imageServiceImpl") final ImageService is, @Qualifier("serviceServiceImpl") final ServiceService serviceService,
        @Qualifier("BusinessServiceImpl") final BusinessService businessService, @Qualifier("appointmentServiceImpl") final AppointmentService appointmentService,
        @Qualifier("securityServiceImpl") final SecurityService securityService){
        this.serviceService = serviceService;
        this.is = is;   // TODO: debe ir en ServiceService, en create() paso MultipartFile como param
        this.businessService = businessService;
        this.appointmentService = appointmentService;
        this.securityService = securityService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/contratar-servicio/{serviceId:\\d+}")
    public ModelAndView appointment(@PathVariable("serviceId") final long serviceId, @Valid @ModelAttribute("appointmentForm") AppointmentForm form, BindingResult errors) {
        //todo: manejo de errores de ingreso del formulario (se lanzarían excepciones a nivel sql)
        User user = securityService.getCurrentUser().get();

        Appointment createdAppointment = appointmentService.create(serviceId,user.getName(),user.getSurname(),user.getEmail(),form.getLocation(),user.getEmail(), form.getDate().toString());
        return new ModelAndView("redirect:/turno/"+ serviceId + "/" + createdAppointment.getId());
    }

    private void validateUser(long appointmentId) {
        User user = securityService.getCurrentUser().get();
        Appointment appointment = appointmentService.findById(appointmentId).orElseThrow(AppointmentNonExistentException::new);
        // todo business find admin
        if ( user.getUserId() != appointment.getUserid() || !businessService.isBusinessOwner(user.getUserId(), appointment.getUserid()) )
            throw new ForbiddenOperation();
    }

    @RequestMapping(method = RequestMethod.POST , path = "/rechazar-turno/{appointmentId:\\d+}")
    public ModelAndView denyAppointment(@PathVariable("appointmentId") final long appointmentId) {
        validateUser(appointmentId);
        final long serviceId = appointmentService.denyAppointment(appointmentId);
        return new ModelAndView("redirect:/sinturno/" + serviceId + "/?argumento=cancelado");
    }

    @RequestMapping(method = RequestMethod.POST , path = "/aceptar-turno/{appointmentId:\\d+}")
    public ModelAndView confirmAppointment(@PathVariable("appointmentId") final long appointmentId) {
        validateUser(appointmentId);
        final long serviceId = appointmentService.confirmAppointment(appointmentId);
        return new ModelAndView("redirect:/turno/"+serviceId+"/"+appointmentId);
    }

    @RequestMapping(method = RequestMethod.POST , path = "/cancelar-turno/{appointmentId:\\d+}")
    public ModelAndView cancelAppointmentFromMail(@PathVariable("appointmentId") final long appointmentId) {
        validateUser(appointmentId);
        final long serviceId = appointmentService.cancelAppointment(appointmentId);
        return new ModelAndView("redirect:/sinturno/" + serviceId + "/?argumento=cancelado");
    }

    @RequestMapping(method = RequestMethod.DELETE , path = "/cancelar-turno/{appointmentId:\\d+}")
    public void cancelAppointment(@PathVariable("appointmentId") final long appointmentId,
                                  HttpServletResponse response) throws IOException{
        validateUser(appointmentId);
        try {
            appointmentService.cancelAppointment(appointmentId);
        } catch (Exception e) {
            if ( e.getClass() != AppointmentNonExistentException.class ) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }
        }
        response.setStatus(HttpServletResponse.SC_OK);

    }

    @RequestMapping(method = RequestMethod.GET, path = "/turno/{serviceId:\\d+}/{appointmentId:\\d+}")
    public ModelAndView getAppointment(
            @PathVariable("appointmentId") final long appointmentId,
            @PathVariable("serviceId") final long serviceId) {

        Optional<Appointment> optionalAppointment = appointmentService.findById(appointmentId);
        if(!optionalAppointment.isPresent()) {
            if(serviceService.findById(serviceId).isPresent() ) {
                return new ModelAndView("redirect:/sinturno/" + serviceId + "/?argumento=cancelado");
            }
            else {
                return new ModelAndView("redirect:/sinturno/" + serviceId + "/?argumento=noexiste");
            }
        }

        Appointment app = appointmentService.findById(appointmentId).get();
        User user = securityService.getCurrentUser().get();
        if ( app.getUserid() != user.getUserId() )
            throw new ForbiddenOperation();

        Service service = serviceService.findById(app.getServiceid()).orElseThrow(ServiceNotFoundException::new);
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

}

