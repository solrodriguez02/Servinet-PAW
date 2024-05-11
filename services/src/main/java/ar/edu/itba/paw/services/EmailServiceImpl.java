package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.*;

@org.springframework.stereotype.Service()
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final MessageSource messageSource;
    private final static String SERVINET_EMAIL = "servinet.servinet.servinet@gmail.com";
    //temp
    private final Locale LOCALE = LocaleContextHolder.getLocale();  //Locale.getDefault(); //Locale.of("en"); //Locale.forLanguageTag("es-419"); // Locale.of("es");
    private final String APP_URL = "http://localhost:8080/webapp_war/"; //! CAMBIAR EN DEPLOY

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender, TemplateEngine templateEngine, MessageSource messageSource) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.messageSource = messageSource;
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
        sendAppointmentMails(appointment, EmailTypes.ACCEPTED, service, business, client, false);
    }
    public void recoverPassword(User user, PasswordRecoveryCode code) throws MessagingException {
        final Context ctx = new Context(LOCALE);
        ctx.setVariable("user", user);
        ctx.setVariable("token", code.getCode());
        sendMail(user.getEmail(), getSubject(EmailTypes.PASSWORD_RECOVER,user.getUsername()) ,ctx, EmailTypes.PASSWORD_RECOVER.getTemplate());
    }
    @Async
    @Override
    public void confirmNewPassword(User user) throws MessagingException {
        final Context ctx = new Context(LOCALE);
        ctx.setVariable("user", user);
        sendMail(user.getEmail(), getSubject(EmailTypes.CONFIRM_NEW_PASSWORD,user.getUsername()) ,ctx, EmailTypes.CONFIRM_NEW_PASSWORD.getTemplate());
    }

    @Async
    @Override
    public void cancelledAppointment(Appointment appointment, Service service, Business business, User client, boolean isServiceDeleted) throws MessagingException {
        sendAppointmentMails(appointment,EmailTypes.CANCELLED, service, business, client, isServiceDeleted);
    }

    @Async
    @Override
    public void deniedAppointment(Appointment appointment, Service service, Business business, User client,boolean isServiceDeleted) throws MessagingException {
        sendAppointmentMails(appointment,EmailTypes.DENIED, service, business, client, isServiceDeleted);
    }

    private void sendAppointmentMails(Appointment appointment, EmailTypes emailType,  Service service, Business business, User client, boolean isServiceDeleted) throws MessagingException {
        final Context ctx = getContext(appointment,service,isServiceDeleted, client, business);

        if (!isServiceDeleted)
            sendMailToBusiness(emailType, business.getEmail(), ctx);
        sendMailToClient(emailType,client.getEmail(),ctx);
    }

    private Context getContext(Appointment appointment, Service service, boolean isServiceDeleted, User client, Business business){
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

    @Override
    @Async
    public void createdBusiness(Business business) throws MessagingException {
        Context ctx = new Context(LOCALE);
        ctx.setVariable("businessId",business.getBusinessid());
        ctx.setVariable("businessName",business.getName());
        sendMail(business.getEmail(), getSubject(EmailTypes.CREATED_BUSINESS, business.getBusinessName()), ctx, EmailTypes.CREATED_BUSINESS.getTemplate());
    }

    @Override
    @Async
    public void deletedBusiness(Business business) throws MessagingException {
        Context ctx = new Context(LOCALE);
        ctx.setVariable("businessId",business.getBusinessid());
        ctx.setVariable("businessName",business.getName());
        sendMail(business.getEmail(),getSubject(EmailTypes.DELETED_BUSINESS, business.getBusinessName()), ctx, EmailTypes.DELETED_BUSINESS.getTemplate());
    }

    private String getSubject(EmailTypes emailType, String name){
        return messageSource.getMessage(emailType.getSubject(), new Object[]{name} ,LOCALE ) ;
    }

    private String getSubjectWithId(EmailTypes emailType, Long id, String name){
        return messageSource.getMessage(emailType.getSubject(), new Object[]{id, name} ,LOCALE ) ;
    }

    @Async
    @Override
    public void askedQuestion(BasicService service, String businessEmail, User client, String question) throws MessagingException{
        Context ctx = new Context(LOCALE);
        ctx.setVariable("serviceId",service.getId());
        ctx.setVariable("serviceName", service.getName());
        ctx.setVariable("client", client);
        ctx.setVariable("question", question);
        sendMailToClient(EmailTypes.ASKED_QUESTION, client.getEmail(), ctx );
        sendMailToBusiness(EmailTypes.ASKED_QUESTION, businessEmail, ctx );
    }

    @Async
    @Override
    public void answeredQuestion(BasicService service, User client, String question, String response) throws MessagingException{
        Context ctx = new Context(LOCALE);
        ctx.setVariable("serviceId",service.getId());
        ctx.setVariable("serviceName", service.getName());
        ctx.setVariable("client", client);
        ctx.setVariable("question", question);
        ctx.setVariable("response", response);
        sendMailToClient(EmailTypes.ANSWERED_QUESTION, client.getEmail(), ctx );
    }

    public void sendMailToClient( EmailTypes emailType, String userMail, Context ctx) throws MessagingException {
        prepareAndSendMail(emailType, userMail, true, ctx);
    }

    private void sendMailToBusiness( EmailTypes emailType, String businessMail, Context ctx) throws MessagingException {
        prepareAndSendMail(emailType,businessMail,false,ctx);
    }

    private void prepareAndSendMail(EmailTypes emailType, String businessMail, boolean isClient, Context ctx) throws MessagingException {
        ctx.setVariable("isClient",isClient);
        ctx.setVariable("type", emailType.getType());
        // Preparo subject
        String serviceName = (String) ctx.getVariable("serviceName");
        String subject;
        if (emailType.isAboutAppointment())
            subject = getSubjectWithId(emailType,(Long ) ctx.getVariable("appointmentId"), serviceName );
        else
            subject = getSubject(emailType, serviceName);
        sendMail(businessMail, subject,ctx, emailType.getTemplate());
    }

    private void sendMail( final String recipientEmail, String subject, final Context ctx, String template) throws MessagingException {

        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        message.setSubject(subject);
        message.setFrom(SERVINET_EMAIL);
        message.setTo(recipientEmail);

        ctx.setVariable("url", APP_URL);

        final String htmlContent = this.templateEngine.process(template, ctx );
        message.setText(htmlContent, true);

        try {
            this.mailSender.send(mimeMessage);
        }
        catch (MailException ex) {
            System.out.println(ex.getMessage());
        }

    }
}
