package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Appointment;
import ar.edu.itba.paw.model.Categories;
import ar.edu.itba.paw.model.Neighbourhoods;
import ar.edu.itba.paw.model.PricingTypes;
import ar.edu.itba.paw.model.Service;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.services.AppointmentService;
import ar.edu.itba.paw.services.ManageServiceService;
import ar.edu.itba.paw.services.ServiceService;
import ar.edu.itba.paw.webapp.exception.ServiceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ar.edu.itba.paw.services.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class HelloWorldController {

    private UserService us;
    private ServiceService service;
    private AppointmentService appointment;
    private final ManageServiceService manageServiceService;

    List<Categories> categories = new ArrayList<>();
    List<PricingTypes> pricingTypes = new ArrayList<>();
    List<Neighbourhoods> neighbourhoods = new ArrayList<>();

    @Autowired
    public HelloWorldController(@Qualifier("userServiceImpl") final UserService us, @Qualifier("serviceServiceImpl") final ServiceService service,
                                @Qualifier("appointmentServiceImpl") final AppointmentService appointment, @Qualifier("manageServiceServiceImpl") final ManageServiceService manageServiceService) {
        this.us = us;
        this.service = service;
        this.appointment = appointment;
        this.manageServiceService = manageServiceService;
        categories.addAll(Arrays.asList(Categories.values()));
        pricingTypes.addAll(Arrays.asList(PricingTypes.values()));
        neighbourhoods.addAll(Arrays.asList(Neighbourhoods.values()));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/")
    public ModelAndView home() {
        final ModelAndView mav = new ModelAndView("home");
        mav.addObject("categories", categories);
        mav.addObject("neighbourhoods", neighbourhoods);
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
        Boolean isMoreServices = service.isMoreServices(page, category, location);
        Boolean isServicesEmpty = serviceList.isEmpty();
        mav.addObject("services", serviceList);
        mav.addObject("isMoreServices", isMoreServices);
        mav.addObject("page", page);
        mav.addObject("isServicesEmpty", isServicesEmpty);
        mav.addObject("category", category);
        mav.addObject("neighbourhoods", neighbourhoods);
        mav.addObject("location", location);
        return mav;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/publicar")
    public ModelAndView postForm() {
        final ModelAndView mav = new ModelAndView("post");
        mav.addObject("user","home page");
        mav.addObject("categories",categories);
        mav.addObject("pricingTypes",pricingTypes);
        mav.addObject("neighbours",neighbourhoods);
        return mav;
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
        manageServiceService.createService(1,title,description,homeserv,neighbourhood,location,category,minimalduration,pricingtype,price,additionalCharges);
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(method = RequestMethod.POST, path = "/{serviceid}/deleteservicio")
    public ModelAndView deleteService(@PathVariable(value = "serviceid") final long serviceid){
        service.delete(serviceid);
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(method = RequestMethod.POST, path = "/datospersonales")
    public ModelAndView dataForm(
            @RequestParam(value = "titulo") final String title,
            @RequestParam(value = "descripcion") final String description,
            @RequestParam(value = "ubicacion") final String location,
            @RequestParam(value = "categoria") final String category
    ) {
        final ModelAndView mav = new ModelAndView("postPersonal");
        mav.addObject("title", title);
        mav.addObject("description", description);
        mav.addObject("location", location);
        mav.addObject("category", category);
        return mav;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/crear")
    public ModelAndView post(
            @RequestParam(value = "titulo") final String title,
            @RequestParam(value = "descripcion") final String description,
            @RequestParam(value = "ubicacion") final String location,
            @RequestParam(value = "categoria") final String category,
            @RequestParam(value = "nombre") final String name,
            @RequestParam(value = "apellido") final String apellido,
            @RequestParam(value = "email") final String email
    ) {
        return new ModelAndView("redirect:/misservicios");
    }

    @RequestMapping(method = RequestMethod.GET, path = "/contratar-servicio/{serviceId:\\d+}")
    public ModelAndView hireService(@PathVariable("serviceId") final long serviceId){

        final ModelAndView mav = new ModelAndView("postAppointment");
        Service service = this.service.findById(serviceId).orElseThrow(ServiceNotFoundException::new);
        mav.addObject("service",service);

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
    @RequestMapping(method = RequestMethod.GET, path = "/turno/{appointmentId:\\d+}")
    public ModelAndView appointment(@PathVariable("appointmentId") final long appointmentId) {
        Appointment app = appointment.findById(appointmentId).get();
        User user = us.findById(app.getUserid()).get();
        Service service = this.service.findById(app.getServiceid()).orElseThrow(ServiceNotFoundException::new);
        final ModelAndView mav = new ModelAndView("appointment");
        mav.addObject("appointment", app);
        mav.addObject("user", user);
        mav.addObject("service", service);
        mav.addObject("new", true);
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
    @RequestMapping(method = RequestMethod.GET, path = "/{serviceId:\\d+}")
    public ModelAndView service(@PathVariable("serviceId") final long serviceId) {
        final ModelAndView mav = new ModelAndView("service");
        mav.addObject("service",service.findById(serviceId).orElseThrow(ServiceNotFoundException::new));
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
