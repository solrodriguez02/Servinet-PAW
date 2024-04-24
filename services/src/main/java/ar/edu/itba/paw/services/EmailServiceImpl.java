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

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Async // * funciona pues no invoque a un metodo dentro de la clase
    @Override
    public void requestAppointment(Appointment appointment, Service service, Business business, User client) throws MessagingException {
        // no llama a prepareAndSendMails() pues no necesita user (en sprint1)

        final Context ctx = getContext(appointment,service,false, client, business);

        sendMailToBusiness(EmailTypes.REQUEST, business.getEmail(), ctx);
        sendMailToClient(EmailTypes.WAITING,client.getEmail(),ctx);
    }

    @Async
    @Override
    public void confirmedAppointment(Appointment appointment, Service service, Business business, User client) throws MessagingException {
        prepareAndSendAppointmentMails(appointment, EmailTypes.ACCEPTED, service, business, client, false);
    }
    public void recoverPassword(User user, PasswordRecoveryCode code) throws MessagingException {
        final Context ctx = new Context(LOCALE);
        ctx.setVariable("user", user);
        ctx.setVariable("token", code.getCode());
        sendMail(user.getEmail(), String.format("%s- Cuenta de Servinet de @%s", EmailTypes.PASSWORD_RECOVER.getSubject(""),user.getUsername()) ,ctx, EmailTypes.PASSWORD_RECOVER.getRecoverPasswordTemplate());
    }
    @Async
    @Override
    public void confirmNewPassword(User user) throws MessagingException {
        final Context ctx = new Context(LOCALE);
        ctx.setVariable("user", user);
        sendMail(user.getEmail(), String.format("Nueva contraseña definida con éxito para @%s - Servinet", user.getUsername()) ,ctx, EmailTypes.PASSWORD_RECOVER.getConfirmNewPasswordTemplate());
    }

    @Async
    @Override
    public void cancelledAppointment(Appointment appointment, Service service, Business business, User client, Boolean isServiceDeleted) throws MessagingException {
        prepareAndSendAppointmentMails(appointment,EmailTypes.CANCELLED, service, business, client, isServiceDeleted);
    }

    @Async
    @Override
    public void deniedAppointment(Appointment appointment, Service service, Business business, User client,Boolean isServiceDeleted) throws MessagingException {
        prepareAndSendAppointmentMails(appointment,EmailTypes.DENIED, service, business, client, isServiceDeleted);
    }

    private void prepareAndSendAppointmentMails(Appointment appointment, EmailTypes emailType,  Service service, Business business, User client, Boolean isServiceDeleted) throws MessagingException {
        final Context ctx = getContext(appointment,service,isServiceDeleted, client, business);

        if (!isServiceDeleted)
            sendMailToBusiness(emailType, business.getEmail(), ctx);
        sendMailToClient(emailType,client.getEmail(),ctx);
    }

    private Context getContext(Appointment appointment, Service service, Boolean isServiceDeleted, User client, Business business){
        final Context ctx = new Context(LOCALE);
        ctx.setVariable("client", client);
        ctx.setVariable("business", business);
        ctx.setVariable("serviceName",service.getName());
        ctx.setVariable("serviceId",service.getId());
        ctx.setVariable("appointmentId",appointment.getId());
        ctx.setVariable("startdate",appointment.getStartDate());
        ctx.setVariable("isServiceDeleted", isServiceDeleted);
        return ctx;
    }

    @Async
    @Override
    public void createdService(Service service, Business business) throws MessagingException {
        Context ctx = new Context(LOCALE);
        ctx.setVariable("serviceId",service.getId());
        ctx.setVariable("serviceName",service.getName());
        // TODO : paso businessname?
        sendMailToBusiness(EmailTypes.CREATED_SERVICE, business.getEmail(), ctx);
    }

    @Override
    @Async
    public void deletedService(Service service, Business business ) throws MessagingException {

        Context ctx = new Context(LOCALE);
        ctx.setVariable("serviceId",service.getId());
        ctx.setVariable("serviceName",service.getName());
        sendMailToBusiness(EmailTypes.DELETED_SERVICE, business.getEmail(),ctx);
    }

    public void sendMailToClient( EmailTypes emailType, String userMail, Context ctx) throws MessagingException {
        ctx.setVariable("isClient",true);
        ctx.setVariable("type", emailType.getType());
        sendMail(userMail, emailType.getSubject((Long) ctx.getVariable("appointmentId"), (String) ctx.getVariable("serviceName")),ctx, emailType.getAppointmentTemplate());
    }

    private void sendMailToBusiness( EmailTypes emailType, String businessMail, Context ctx) throws MessagingException {

        ctx.setVariable("isClient",false);
        ctx.setVariable("type", emailType.getType());
        // Preparo subject
        String serviceName = (String) ctx.getVariable("serviceName");
        String subject;
        if (emailType.isAboutAppointment())
            subject =  emailType.getSubject((Long) ctx.getVariable("appointmentId"), serviceName );
        else
            subject = emailType.getSubject(serviceName);
        sendMail(businessMail, subject,ctx, emailType.getTemplate());
    }

    private void sendMail( final String recipientEmail, String subject, final Context ctx, String template) throws MessagingException {

        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        message.setSubject(subject);
        message.setTo(recipientEmail);

        ctx.setVariable("url", APP_URL);

        final String htmlContent = this.templateEngine.process(template, ctx );
        message.setText(htmlContent, true); // true = isHtml

        try {
            this.mailSender.send(mimeMessage);
        }
        catch (MailException ex) {
            System.out.println(ex.getMessage());
        }

    }
}
