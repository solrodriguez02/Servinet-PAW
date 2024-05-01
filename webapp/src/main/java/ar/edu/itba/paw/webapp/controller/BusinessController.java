package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.exceptions.AppointmentNonExistentException;
import ar.edu.itba.paw.model.exceptions.BusinessNotFoundException;
import ar.edu.itba.paw.model.exceptions.ForbiddenOperation;
import ar.edu.itba.paw.model.exceptions.UserNotFoundException;
import ar.edu.itba.paw.services.*;
import ar.edu.itba.paw.webapp.auth.ServinetAuthUserDetails;
import ar.edu.itba.paw.webapp.form.BusinessForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

@Controller
public class BusinessController {

    private final BusinessService businessService;
    private final ServiceService serviceService;
    private final AppointmentService appointmentService;
    private final UserService userService;
    private final SecurityService securityService;

    List<Neighbourhoods> neighbourhoods = new ArrayList<>();

    @Autowired
    public BusinessController(@Qualifier("BusinessServiceImpl") final BusinessService businessService,  @Qualifier("serviceServiceImpl") final ServiceService serviceService,
                              @Qualifier("appointmentServiceImpl") final AppointmentService appointmentService,
                              @Qualifier("userServiceImpl") final UserService userService,
                              @Qualifier("securityServiceImpl") final SecurityService securityService) {
        this.businessService = businessService;
        this.serviceService = serviceService;
        this.appointmentService = appointmentService;
        this.userService = userService;
        this.securityService = securityService;
        neighbourhoods.addAll(Arrays.asList(Neighbourhoods.values()));
    }


    @RequestMapping(method = RequestMethod.GET, path="/registrar-negocio")
    public ModelAndView registerBusiness(@ModelAttribute("BusinessForm") final BusinessForm form) {
        final ModelAndView mav = new ModelAndView("postBusiness");
        mav.addObject("neighbours",neighbourhoods);
        return mav;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/registrar-negocio")
    public ModelAndView postBusiness(@ModelAttribute("BusinessForm") @Valid final BusinessForm form, final BindingResult errors
    ) {
        if (errors.hasErrors()) {
            return registerBusiness(form);
        }
        ServinetAuthUserDetails userDetails = (ServinetAuthUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByEmail(userDetails.getUsername()).orElse(null);
        if (user == null){
            return new ModelAndView("redirect:/login");
        }
        businessService.createBusiness(form.getBusinessName(),user.getUserId(), form.getBusinessEmail(),form.getBusinessTelephone(),form.getBusinessLocation());
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(method = RequestMethod.DELETE , path = "/borrar-negocio/{businessId:\\d+}")
    public ModelAndView deleteBusiness(@PathVariable("businessId") final long businessId){
        validateUserIsOwner(businessId);
        businessService.deleteBusiness(businessId);
        return new ModelAndView("redirect:/operacion-invalida/?argumento=negocionoexiste");
    }

    //todo: autenticar q es el dueño del service
    private void validateUserIsOwner(long businessId) {
        Business business = businessService.findById(businessId).orElseThrow(BusinessNotFoundException::new);
        validateUserIsOwner(business);
    }
    private void validateUserIsOwner(Business business) {
        User user = securityService.getCurrentUser().get();
        if ( user.getUserId() != business.getUserId())
            throw new ForbiddenOperation();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/negocio/{businessId:\\d+}/turnos/")
    public ModelAndView businessesAppointments(@PathVariable("businessId") final long businessId, @RequestParam(name = "confirmados") final boolean confirmed) {

        Business business = businessService.findById(businessId).orElseThrow(BusinessNotFoundException::new);
        validateUserIsOwner(business);
        Optional<List<BasicService>> services = serviceService.getAllBusinessBasicServices(businessId);
        List<Appointment> appointmentList = new ArrayList<>();

        Map<Long, BasicService> serviceMap = new HashMap<>();
        if ( services.isPresent()){
            services.get().forEach(service -> serviceMap.put(service.getId(), service) );
            appointmentList = appointmentService.getAllUpcomingServicesAppointments( serviceMap.keySet(), confirmed).orElse(new ArrayList<>());
        }

        final ModelAndView mav = new ModelAndView("businessAppointments");
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
        validateUserIsOwner(businessId);

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
        List<BasicService> serviceList = serviceService.getAllBusinessBasicServices(businessId).orElse(new ArrayList<>());

        mav.addObject("business",business);
        mav.addObject("serviceList", serviceList);
        return mav;
    }


}
