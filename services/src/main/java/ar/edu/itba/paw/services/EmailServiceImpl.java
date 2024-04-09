package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.*;

@org.springframework.stereotype.Service()
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    //temp
    private final Locale LOCALE = Locale.forLanguageTag("es-419"); // Locale.of("es");
    private final String APP_URL = "http://localhost:8080/webapp_war/"; //! CAMBIAR EN DEPLOY

    private final static String TEMPLATE = "html/mail.html";
    private final ServiceService serviceService;
    private final BusinessService businessService;
    private final UserService userService;

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender, TemplateEngine templateEngine,final ServiceService serviceService, final BusinessService businessService, final UserService userService) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.serviceService =serviceService;
        this.businessService = businessService;
        this.userService =  userService;
    }

    @Async // * funciona pues no invoque a un metodo dentro de la clase
    @Override
    public void requestAppointment(Appointment appointment, String clientMail) throws MessagingException {

        Service service = serviceService.findById(appointment.getServiceid()).orElseThrow(NoSuchElementException::new);

        final Context ctx = getContext(appointment,service);

        sendMailToBusiness(EmailTypes.REQUEST, service, ctx);
        sendMailToClient(EmailTypes.WAITING,clientMail,ctx);
    }

    @Async
    @Override
    public void confirmedAppointment(Appointment appointment) throws MessagingException {
        String clientMail = userService.findById(appointment.getUserid()).orElseThrow(NoSuchElementException::new).getEmail();
        Service service = serviceService.findById(appointment.getServiceid()).orElseThrow(NoSuchElementException::new);

        final Context ctx = getContext(appointment,service);

        sendMailToBusiness(EmailTypes.ACCEPTED, service, ctx);
        sendMailToClient(EmailTypes.ACCEPTED,clientMail,ctx);
    }


    @Async
    @Override
    public void cancelledAppointment(Appointment appointment) throws MessagingException {
        String clientMail = userService.findById(appointment.getUserid()).orElseThrow(NoSuchElementException::new).getEmail();
        Service service = serviceService.findById(appointment.getServiceid()).orElseThrow(NoSuchElementException::new);

        final Context ctx = getContext(appointment,service);

        sendMailToBusiness(EmailTypes.CANCELLED, service, ctx);
        sendMailToClient(EmailTypes.CANCELLED,clientMail,ctx);
    }

    @Async
    @Override
    public void deniedAppointment(Appointment appointment) throws MessagingException {
        String clientMail = userService.findById(appointment.getUserid()).orElseThrow(NoSuchElementException::new).getEmail();
        Service service = serviceService.findById(appointment.getServiceid()).orElseThrow(NoSuchElementException::new);

        final Context ctx = getContext(appointment,service);

        sendMailToBusiness(EmailTypes.DENIED, service, ctx);
        sendMailToClient(EmailTypes.DENIED,clientMail,ctx);
    }

    private Context getContext(Appointment appointment, Service service){
        final Context ctx = new Context(LOCALE);
        ctx.setVariable("serviceName",service.getName());
        ctx.setVariable("serviceId",service.getId());
        ctx.setVariable("appointmentId",appointment.getId());
        ctx.setVariable("url", APP_URL);
        ctx.setVariable("startdate",appointment.getStartDate());
        return ctx;
    }

    public void sendMailToClient( EmailTypes emailType, String userMail, Context ctx) throws MessagingException {
        ctx.setVariable("isClient",true);
        ctx.setVariable("type", emailType.getType());
        ctx.setVariable("subject", emailType.getSubject());
        sendMail(userMail, emailType.getSubject(),ctx);
    }

    private void sendMailToBusiness( EmailTypes emailType, Service service, Context ctx) throws MessagingException {

        // service duration y ubi
        Business business = businessService.findById( service.getBusinessid() ).orElseThrow(NoSuchElementException::new);
        ctx.setVariable("isClient",false);
        ctx.setVariable("type", emailType.getType());
        ctx.setVariable("subject", emailType.getSubject());
        sendMail(business.getEmail(), emailType.getSubject(),ctx);
    }

    private void sendMail( final String recipientEmail, final String subject, final Context ctx) throws MessagingException {

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message =
                new MimeMessageHelper(mimeMessage, true, "UTF-8"); //! true = multipart
        message.setSubject(subject);
        message.setFrom("servinet@gmail.com");
        message.setTo(recipientEmail);

        // Create the HTML body using Thymeleaf
        //final String htmlContent = this.templateEngine.process(template, moreTemplatesSet, ctx );
        final String htmlContent = this.templateEngine.process(TEMPLATE, ctx );
        message.setText(htmlContent, true); // true = isHtml


        /* Add the inline image, referenced from the HTML code as "cid:${imageResourceName}"
        ctx.setVariable("imageResourceName", imageResourceName); // so that we can reference it from HTML
        final InputStreamSource imageSource = new ByteArrayResource(imageBytes);
        message.addInline(imageResourceName, imageSource, imageContentType);
        */
        // Send mail
        try {
            this.mailSender.send(mimeMessage);
        }
        catch (MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());
        }

    }
}
