package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Appointment;
import ar.edu.itba.paw.model.Business;
import ar.edu.itba.paw.model.Service;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exceptions.AppointmentNonExistentException;
import ar.edu.itba.paw.model.exceptions.BusinessNotFoundException;
import ar.edu.itba.paw.model.exceptions.UserNotFoundException;
import ar.edu.itba.paw.services.AppointmentService;
import ar.edu.itba.paw.services.BusinessService;
import ar.edu.itba.paw.services.ServiceService;
import ar.edu.itba.paw.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class BusinessController {

    private final BusinessService businessService;
    private final ServiceService serviceService;
    private final AppointmentService appointmentService;
    private final UserService userService;

    //todo: autenticar q es el dueño del service

    @Autowired
    public BusinessController(@Qualifier("BusinessServiceImpl") final BusinessService businessService,  @Qualifier("serviceServiceImpl") final ServiceService serviceService,
                              @Qualifier("appointmentServiceImpl") final AppointmentService appointmentService,
                              @Qualifier("userServiceImpl") final UserService userService) {
        this.businessService = businessService;
        this.serviceService = serviceService;
        this.appointmentService = appointmentService;
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/negocio/{businessId:\\d+}/turnos/")
    public ModelAndView businessesAppointments(@PathVariable("businessId") final long businessId, @RequestParam(name = "confirmados") final boolean confirmed) {

        final ModelAndView mav = new ModelAndView("businessAppointments");
        Business business = businessService.findById(businessId).orElseThrow(BusinessNotFoundException::new);
        Optional<List<Service>> services = serviceService.getAllBusinessServices(businessId);
        List<Appointment> appointmentList = new ArrayList<>();

        Map<Long,Service> serviceMap = new HashMap<>();
        if ( services.isPresent()){
            services.get().forEach(service -> serviceMap.put(service.getId(), service) );
            appointmentList = appointmentService.getAllUpcomingServicesAppointments( serviceMap.keySet(), confirmed).orElse(new ArrayList<>());
        }

        Map<Long, User> userMap = new HashMap<>();
        if (confirmed){
            for (Appointment a : appointmentList){
                if (!userMap.containsKey(a.getUserid()))
                    userMap.put(a.getUserid(),userService.findById(a.getUserid()).get());
            }
            mav.addObject("userMap", userMap );
        }

        mav.addObject("business",business);
        mav.addObject("serviceMap", serviceMap );
        mav.addObject("appointmentList", appointmentList);
        mav.addObject("confirmed",confirmed);
        return mav;
    }

    // TODO
    /*
    @RequestMapping(method = RequestMethod.GET, path = "/negocio/{businessId:\\d+}/turnos/")
    public List<Appointment> getMoreAppointments(@PathVariable("businessId") final long businessId,
                                                   @RequestParam(name = "confirmed") final boolean confirmed ) {
        Optional<List<Service>> services = serviceService.getAllBusinessServices(businessId);
        Optional<List<Appointment>> appointmentsRequested = Optional.of(new ArrayList<>());
        List<Long> serviceIds = new ArrayList<>();
        if ( services.isPresent()){
            services.get().forEach(service -> serviceIds.add(service.getId()));
            appointmentsRequested = appointmentService.getAllUpcomingServicesAppointments(serviceIds, confirmed);
        }
        return appointmentsRequested.orElse(new ArrayList<>());
    }
    */

    @RequestMapping(method = RequestMethod.POST, path = "negocio/{businessId:\\d+}/solicitud-turno/{appoinmentId:\\d+}")
    public void acceptOrDenyAppointment(@PathVariable(value = "businessId") final long businessId,
                                            @PathVariable(value = "appoinmentId") final long appoinmentId,
                                            @RequestParam(value = "accepted") final boolean accepted,
                                            HttpServletResponse response) throws IOException{
        // TODO: businessId por si es necesario para auth

        if ( accepted )
            try {
                appointmentService.confirmAppointment(appoinmentId);
            } catch (AppointmentNonExistentException e) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                response.getWriter().println("El turno ya no existe");
            }
        else
            appointmentService.denyAppointment(appoinmentId);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/negocio/{businessId:\\d+}")
    public ModelAndView businesses(@PathVariable("businessId") final long businessId) {
        final ModelAndView mav = new ModelAndView("business");
        Business business = businessService.findById(businessId).orElseThrow(BusinessNotFoundException::new);
        List<Service> serviceList = serviceService.getAllBusinessServices(businessId).orElse(new ArrayList<>());

        mav.addObject("business",business);
        mav.addObject("serviceList", serviceList);
        return mav;
    }


}
