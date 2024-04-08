package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.Appointment;
import ar.edu.itba.paw.model.Business;
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

import ar.edu.itba.paw.model.Service;

@org.springframework.stereotype.Service()
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    //temp
    private final Locale LOCALE = Locale.forLanguageTag("es-419"); // Locale.of("es");
    private final String APP_URL = "http://localhost:8080/webapp_war_exploded/";

    private final static Set<String> moreTemplatesSet = new HashSet<>();
    private final static String FRAGMENTS_TEMPLATE = "mail/fragments.html";
    private final ServiceService serviceService;
    private final BusinessService businessService;

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender, TemplateEngine templateEngine,final ServiceService serviceService,  final BusinessService businessService) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.serviceService =serviceService;
        this.businessService = businessService;
        moreTemplatesSet.add(FRAGMENTS_TEMPLATE);
    }

    @Async // * funciona pues no invoque a un metodo dentro de la clase
    @Override
    public void requestAppointment(Appointment appointment, String userMail) throws MessagingException {

        Service service = serviceService.findById(appointment.getServiceid()).orElseThrow(NoSuchElementException::new);

        final Context ctx = new Context(LOCALE);
        ctx.setVariable("subject", "WAITING");
        ctx.setVariable("serviceName",service.getName());
        ctx.setVariable("serviceId",service.getId());
        ctx.setVariable("url", APP_URL);
        ctx.setVariable("startdate",appointment.getStartDate());

        // para el profesional
        requestConfirmationFromServiceProvider(appointment, service);

        // para el user
        ctx.setVariable("subject", "REQUEST");
        sendMail(userMail,"Turno solicitado a la espera de confirmación","html/mail.html",ctx);
    }

    @Async
    public void confirmedAppointment(Appointment appointment){
        //para el prof

    }

    private void requestConfirmationFromServiceProvider(Appointment appointment, Service service) throws MessagingException {

        // service duration y ubi
        Business business = businessService.findById( service.getBusinessid() ).orElseThrow(NoSuchElementException::new);

        String template = "html/mail.html";
/*
        final Context ctx = new Context(locale);
        ctx.setVariable("subject", subject);
        ctx.setVariable("service",1);   //!LE PUEDO PASAR SERVICE Y HAGO .id o lo q necesite
*/

        //   sendMail(business.getEmail(), "Waiting confirmation",template,null);
    }

    // ! CONSTRUIR OTRO THREAD

    public void sendMail( final String recipientEmail, final String subject, final String template, final Context ctx) throws MessagingException {

        // Prepare the evaluation context
        /* no paso locale
        final Context ctx = new Context(locale);
        ctx.setVariable("subject", subject);
        ctx.setVariable("service",1);   //!LE PUEDO PASAR SERVICE Y HAGO .id o lo q necesite
        */
        //ctx.setVariable("subscriptionDate", new Date ());

        // ctx.setVariable("imageResourceName", imageResourceName); // so that we can reference it from HTML

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message =
                new MimeMessageHelper(mimeMessage, true, "UTF-8"); //! true = multipart
        message.setSubject(subject);
        message.setFrom("servinet@gmail.com");
        message.setTo(recipientEmail);

        // Create the HTML body using Thymeleaf
        //final String htmlContent = this.templateEngine.process(template, moreTemplatesSet, ctx );
        final String htmlContent = this.templateEngine.process(template, ctx );
        message.setText(htmlContent, true); // true = isHtml


        // Add the inline image, referenced from the HTML code as "cid:${imageResourceName}"
                /*
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
