package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Appointment;
import ar.edu.itba.paw.model.Business;
import ar.edu.itba.paw.model.Service;
import ar.edu.itba.paw.model.exceptions.AppointmentNonExistentException;
import ar.edu.itba.paw.model.exceptions.BusinessNotFoundException;
import ar.edu.itba.paw.services.AppointmentService;
import ar.edu.itba.paw.services.BusinessService;
import ar.edu.itba.paw.services.ServiceService;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    //todo: autenticar q es el dueño del service

    @Autowired
    public BusinessController(@Qualifier("BusinessServiceImpl") final BusinessService businessService,  @Qualifier("serviceServiceImpl") final ServiceService serviceService,
                              @Qualifier("appointmentServiceImpl") final AppointmentService appointmentService) {
        this.businessService = businessService;
        this.serviceService = serviceService;
        this.appointmentService = appointmentService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/misnegocios")
    public ModelAndView userBusinesses() {
        final ModelAndView mav = new ModelAndView("userBusinesses");
        return mav;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/negocio/{businessId:\\d+}")
    public ModelAndView businesses(@PathVariable("businessId") final long businessId) {

        final ModelAndView mav = new ModelAndView("business");
        Business business = businessService.findById(businessId).orElseThrow(BusinessNotFoundException::new);
        Optional<List<Service>> services = serviceService.getAllBusinessServices(businessId);
        Optional<List<Appointment>> appointmentsRequested = Optional.of(new ArrayList<>());

        Map<Long,Service> serviceMap = new HashMap<>();
        if ( services.isPresent()){
            services.get().forEach(service -> serviceMap.put(service.getId(), service) );
            appointmentsRequested = appointmentService.getAllUpcomingServicesAppointments( serviceMap.keySet(), false);
        }
        mav.addObject("business",business);
        mav.addObject("serviceMap", serviceMap );
        mav.addObject("requestedAppointments", appointmentsRequested.orElse(new ArrayList<>()));

        return mav;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/negocio/{businessId:\\d+}/pag")
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


    @RequestMapping(method = RequestMethod.POST, path = "negocio/{businessId:\\d+}/solicitud-turno/{appoinmentId:\\d+}")
    public void acceptedOrDeniedAppointment(@PathVariable(value = "businessId") final long businessId,
                                            @PathVariable(value = "appoinmentId") final long appoinmentId,
                                            @RequestParam(value = "accepted") final boolean accepted,
                                            HttpServletResponse response) throws IOException{
        // TODO: businessId por si es necesario para auth

        if ( accepted )
            try {
                appointmentService.confirmAppointment(appoinmentId);
            } catch (AppointmentNonExistentException e) {
                //TODO: http response 422: Unprocessable Entity
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                response.getWriter().println("EL turno ya no existe");
            }
        else
            appointmentService.denyAppointment(appoinmentId);

    }


}
