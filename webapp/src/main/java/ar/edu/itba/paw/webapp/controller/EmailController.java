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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
public class EmailController {

    private static final String APPOINTMENT_ALREADY_CONFIRMED = "turnoyaconfirmado";
    private static final String APPOINTMENT_NON_EXISTENT = "turnonoexiste";
    private static final String SERVICET_NON_EXISTENT = "servicionoexiste";

    private final ServiceService serviceService;
    private final ImageService is;
    private final BusinessService businessService;
    private final AppointmentService appointmentService;

    @Autowired
    public EmailController(
    @Qualifier("imageServiceImpl") final ImageService is, @Qualifier("serviceServiceImpl") final ServiceService serviceService,
    @Qualifier("BusinessServiceImpl") final BusinessService businessService, @Qualifier("appointmentServiceImpl") final AppointmentService appointmentService) {
        this.serviceService = serviceService;
        this.is = is;
        this.businessService = businessService;
        this.appointmentService = appointmentService;
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
        /*
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
            emailService.requestAppointment(createdAppointment, newuser);

        } catch (MessagingException e ){
            System.err.println(e.getMessage());
        }
        */
        Appointment createdAppointment = appointmentService.create(serviceId,name,surname,email,location,telephone,date);
        return new ModelAndView("redirect:/turno/"+ serviceId + "/" + createdAppointment.getId());
    }

    private ModelAndView invalidOperation(String argumento){
        return new ModelAndView("redirect:/operacion-invalida/?argumento="+argumento);
    }

    @RequestMapping(method = RequestMethod.POST , path = "/rechazar-turno/{appointmentId:\\d+}")
    public ModelAndView denyAppointment(@PathVariable("appointmentId") final long appointmentId) {
        /*
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
        */
        final long serviceId = appointmentService.denyAppointment(appointmentId);
        return new ModelAndView("redirect:/sinturno/" + serviceId + "/?argumento=cancelado");
    }

    @RequestMapping(method = RequestMethod.POST , path = "/aceptar-turno/{appointmentId:\\d+}")
    public ModelAndView confirmAppointment(@PathVariable("appointmentId") final long appointmentId) {
        /*
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
        */
        final long serviceId = appointmentService.confirmAppointment(appointmentId);
        return new ModelAndView("redirect:/turno/"+serviceId+"/"+appointmentId);
    }

    @RequestMapping(method = RequestMethod.POST , path = "/cancelar-turno/{appointmentId:\\d+}")
    public ModelAndView cancelAppointment(@PathVariable("appointmentId") final long appointmentId) {
        /*
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

         */
        final long serviceId = appointmentService.cancelAppointment(appointmentId);
        return new ModelAndView("redirect:/sinturno/" + serviceId + "/?argumento=cancelado");
    }

    @RequestMapping(method = RequestMethod.POST, path = "/crear-servicio/{businessId:\\d+}")
    public ModelAndView createService(@PathVariable("businessId") final long businessId,
    @RequestParam(value = "titulo") final String title,
    @RequestParam(value="descripcion") final String description,
    @RequestParam(value="homeserv",required = false, defaultValue = "false") final boolean homeserv,
    @RequestParam(value="ubicacion",required = false, defaultValue = "") final String location,
    @RequestParam(value="categoria") final Categories category,
    @RequestParam(value="imageInput") final MultipartFile image,
    @RequestParam(value="neighbourhood") final Neighbourhoods neighbourhood,
    @RequestParam(value="pricingtype") final PricingTypes pricingtype,
    @RequestParam(value="precio") final String price,
    @RequestParam(value="minimalduration",defaultValue = "0") final int minimalduration,
    @RequestParam(value="additionalCharges",defaultValue = "false") final boolean additionalCharges) throws
    IOException {

        final long imageId = image.isEmpty()? 0 : is.addImage(image.getBytes()).getImageId();
        final Service service = businessService.createService(businessId,title,description,homeserv,neighbourhood,location,category,minimalduration,pricingtype,price,additionalCharges, imageId);
        return new ModelAndView("redirect:/servicio/"+service.getId());
    }

        @RequestMapping(method = RequestMethod.POST , path = "/borrar-servicio/{serviceId:\\d+}")
    public ModelAndView deleteService(@PathVariable("serviceId") final long serviceId){

        businessService.deleteService(serviceId);
        return invalidOperation(SERVICET_NON_EXISTENT);
    }

    /*
    ! TESTING {
    @RequestMapping(method = RequestMethod.GET, path = "/trucho")
    public ModelAndView trucho(){
        final long serviceId = manageServiceService.createService(1,"title","description",true,Neighbourhoods.ALMAGRO,"location",Categories.BELLEZA,4,PricingTypes.PER_TOTAL,"40",true);
        return new ModelAndView("redirect:/"+serviceId);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/borrar/{serviceId:\\d+}")
    public ModelAndView trucho2(@PathVariable("serviceId") final long serviceId){
        manageServiceService.deleteService(serviceId);
        return new ModelAndView("redirect:/borrar-servicio/"+ serviceId);
    }
    ! } */
}

