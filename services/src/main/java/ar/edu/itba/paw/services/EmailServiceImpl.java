package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender, TemplateEngine templateEngine, MessageSource messageSource) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.messageSource = messageSource;
    }

    @Async // * funciona pues no invoque a un metodo dentro de la clase
    @Override
    public void requestAppointment(Appointment appointment, Service service, Business business, User client) {
        // no llama a prepareAndSendMails() pues no necesita user (en sprint1)

        final Context ctx = getContext(appointment,service,false, client, business);

        LOGGER.info("Preparing request mail for business owner.");
        try {
            sendMailToBusiness(EmailTypes.REQUEST, business.getEmail(), ctx);
        }catch(MessagingException e){
            LOGGER.warn("Error while preparing request notification email for business owner: {}", e.getMessage());
        }
        LOGGER.info("Preparing request mail for client.");
        try {
            sendMailToClient(EmailTypes.WAITING, client.getEmail(), ctx);
        }catch(MessagingException e){
            LOGGER.warn("Error while preparing request notification email for client: {}", e.getMessage());
        }
    }

    @Async
    @Override
    public void confirmedAppointment(Appointment appointment, Service service, Business business, User client) {
        sendAppointmentMails(appointment, EmailTypes.ACCEPTED, service, business, client, false);
    }
    public void recoverPassword(User user, PasswordRecoveryCode code) {
        final Context ctx = new Context(LOCALE);
        ctx.setVariable("user", user);
        ctx.setVariable("token", code.getCode());
        LOGGER.info("Preparing password recovery mail for user.");
        try {
            sendMail(user.getEmail(), getSubject(EmailTypes.PASSWORD_RECOVER,user.getUsername()) ,ctx, EmailTypes.PASSWORD_RECOVER.getTemplate());
        }catch(MessagingException e){
            LOGGER.warn("Error while preparing password recovery email: {}", e.getMessage());
        }
    }
    @Async
    @Override
    public void confirmNewPassword(User user) {
        final Context ctx = new Context(LOCALE);
        ctx.setVariable("user", user);
        LOGGER.info("Preparing new password confirmation mail for user.");
        try {
            sendMail(user.getEmail(), getSubject(EmailTypes.CONFIRM_NEW_PASSWORD,user.getUsername()) ,ctx, EmailTypes.CONFIRM_NEW_PASSWORD.getTemplate());
        }catch(MessagingException e){
            LOGGER.warn("Error while preparing new password confirmation email: {}", e.getMessage());
        }
    }

    @Async
    @Override
    public void cancelledAppointment(Appointment appointment, Service service, Business business, User client, boolean isServiceDeleted) {
        sendAppointmentMails(appointment,EmailTypes.CANCELLED, service, business, client, isServiceDeleted);
    }

    @Async
    @Override
    public void deniedAppointment(Appointment appointment, Service service, Business business, User client,boolean isServiceDeleted) {
        sendAppointmentMails(appointment,EmailTypes.DENIED, service, business, client, isServiceDeleted);
    }

    private void sendAppointmentMails(Appointment appointment, EmailTypes emailType,  Service service, Business business, User client, boolean isServiceDeleted) {
        final Context ctx = getContext(appointment,service,isServiceDeleted, client, business);

        if (!isServiceDeleted) {
            LOGGER.info("Preparing {} mail for business owner.", emailType.getType());
            try {
                sendMailToBusiness(emailType, business.getEmail(), ctx);
            }catch(MessagingException e){
                LOGGER.warn("Error while preparing {} notification email: {}", emailType.getType(), e.getMessage());
            }
        }
        try {
            sendMailToClient(emailType, client.getEmail(), ctx);
            LOGGER.info("{} mail for client sent successfully.", emailType.getType());
        }catch (MessagingException e){
            LOGGER.warn("Error while preparing {} notification email: {}", emailType.getType(), e.getMessage());
        }
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
    public void createdService(Service service, Business business) {
        Context ctx = new Context(LOCALE);
        ctx.setVariable("serviceId",service.getId());
        ctx.setVariable("serviceName",service.getName());
        LOGGER.info("Preparing service creation notification mail for business owner");
        try {
            sendMailToBusiness(EmailTypes.CREATED_SERVICE, business.getEmail(), ctx);
        }catch(MessagingException e){
            LOGGER.warn("Error while preparing service creation notification email: {}", e.getMessage());
        }
    }

    @Override
    @Async
    public void deletedService(Service service, Business business ) {

        Context ctx = new Context(LOCALE);
        ctx.setVariable("serviceId",service.getId());
        ctx.setVariable("serviceName",service.getName());
        LOGGER.info("Preparing service deletion notification mail for business owner");
        try {
            sendMailToBusiness(EmailTypes.DELETED_SERVICE, business.getEmail(), ctx);
        }catch(MessagingException e){
            LOGGER.warn("Error while preparing service deletion notification email: {}", e.getMessage());
        }
    }

    @Override
    @Async
    public void createdBusiness(Business business) {
        Context ctx = new Context(LOCALE);
        ctx.setVariable("businessId",business.getBusinessid());
        ctx.setVariable("businessName",business.getName());
        LOGGER.info("Preparing business creation notification mail for business owner");
        try {
            sendMail(business.getEmail(), getSubject(EmailTypes.CREATED_BUSINESS, business.getBusinessName()), ctx, EmailTypes.CREATED_BUSINESS.getTemplate());
        }catch(MessagingException e){
            LOGGER.warn("Error while preparing business creation notification email: {}", e.getMessage());
        }
    }

    @Override
    @Async
    public void deletedBusiness(Business business) {
        Context ctx = new Context(LOCALE);
        ctx.setVariable("businessId",business.getBusinessid());
        ctx.setVariable("businessName",business.getName());
        LOGGER.info("Preparing business deletion notification mail for business owner");
        try {
            sendMail(business.getEmail(),getSubject(EmailTypes.DELETED_BUSINESS, business.getBusinessName()), ctx, EmailTypes.DELETED_BUSINESS.getTemplate());
        }catch(MessagingException e){
            LOGGER.warn("Error while preparing business deletion notification email: {}", e.getMessage());
        }
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
            LOGGER.info("Preparing to send mail");
            this.mailSender.send(mimeMessage);
            LOGGER.info("Mail sent successfully.");
        }
        catch (MailException ex) {
            LOGGER.warn("Error sending email: {}", ex.getMessage());
        }

    }
}
