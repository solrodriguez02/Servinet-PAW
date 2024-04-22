package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.exceptions.AppointmentNonExistentException;
import ar.edu.itba.paw.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class EmailController {

    private final ServiceService serviceService;
    private final ImageService is;
    private final BusinessService businessService;
    private final AppointmentService appointmentService;

    @Autowired
    public EmailController(
    @Qualifier("imageServiceImpl") final ImageService is, @Qualifier("serviceServiceImpl") final ServiceService serviceService,
    @Qualifier("BusinessServiceImpl") final BusinessService businessService, @Qualifier("appointmentServiceImpl") final AppointmentService appointmentService) {
        this.serviceService = serviceService;
        this.is = is;   // TODO: debe ir en ServiceService, en create() paso MultipartFile como param
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
        Appointment createdAppointment = appointmentService.create(serviceId,name,surname,email,location,telephone,date);
        return new ModelAndView("redirect:/turno/"+ serviceId + "/" + createdAppointment.getId());
    }



    @RequestMapping(method = RequestMethod.POST , path = "/rechazar-turno/{appointmentId:\\d+}")
    public ModelAndView denyAppointment(@PathVariable("appointmentId") final long appointmentId) {
        final long serviceId = appointmentService.denyAppointment(appointmentId);
        return new ModelAndView("redirect:/sinturno/" + serviceId + "/?argumento=cancelado");
    }

    @RequestMapping(method = RequestMethod.POST , path = "/aceptar-turno/{appointmentId:\\d+}")
    public ModelAndView confirmAppointment(@PathVariable("appointmentId") final long appointmentId) {
        final long serviceId = appointmentService.confirmAppointment(appointmentId);
        return new ModelAndView("redirect:/turno/"+serviceId+"/"+appointmentId);
    }

    @RequestMapping(method = RequestMethod.POST , path = "/cancelar-turno/{appointmentId:\\d+}")
    public ModelAndView cancelAppointmentFromMail(@PathVariable("appointmentId") final long appointmentId) {
        final long serviceId = appointmentService.cancelAppointment(appointmentId);
        return new ModelAndView("redirect:/sinturno/" + serviceId + "/?argumento=cancelado");
    }

    @RequestMapping(method = RequestMethod.DELETE , path = "/cancelar-turno/{appointmentId:\\d+}")
    public void cancelAppointment(@PathVariable("appointmentId") final long appointmentId,
                                  HttpServletResponse response) throws IOException{
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


    // todo: serviceService.createAppointment => appointmentService.create()
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

    // todo: serviceService.createAppointment => appointmentService.create()
    @RequestMapping(method = RequestMethod.POST , path = "/borrar-servicio/{serviceId:\\d+}")
    public ModelAndView deleteService(@PathVariable("serviceId") final long serviceId){

        businessService.deleteService(serviceId);
        return new ModelAndView("redirect:/operacion-invalida/?argumento=servicionoexiste");
    }

}

