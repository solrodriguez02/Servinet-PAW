package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.Appointment;
import ar.edu.itba.paw.model.Business;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

import java.util.NoSuchElementException;
import ar.edu.itba.paw.model.Service;

@org.springframework.stereotype.Service()
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Autowired
    private ServiceService serviceService;
    @Autowired
    private UserService userService;
    @Autowired
    private BusinessService businessService;

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void requestAppointment(Appointment appointment, String userMail) throws MessagingException {

        Service service = serviceService.findById(appointment.getServiceid()).orElseThrow(NoSuchElementException::new);

        // para el profesional
        requestConfirmationFromServiceProvider(appointment, service);

        // para el user
        sendMail(userMail,"Waiting confirmation","html/mail.html",null);
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
    @Async
    public void sendMail( final String recipientEmail, final String subject, final String content, final Locale locale) throws MessagingException {

        // Prepare the evaluation context
        final Context ctx = new Context(locale);
        ctx.setVariable("subject", subject);
        ctx.setVariable("service",1);   //!LE PUEDO PASAR SERVICE Y HAGO .id o lo q necesite
        //ctx.setVariable("subscriptionDate", new Date ());
        // ctx.setVariable("imageResourceName", imageResourceName); // so that we can reference it from HTML

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message =
                new MimeMessageHelper(mimeMessage, true, "UTF-8"); // true = multipart
        message.setSubject(subject);
        message.setFrom("servinet@gmail.com");
        message.setTo(recipientEmail);

        // Create the HTML body using Thymeleaf
        final String htmlContent = this.templateEngine.process(content, ctx);
        message.setText(htmlContent, true); // true = isHtml
    //message.setText("hola",true);
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
