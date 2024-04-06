package ar.edu.itba.paw.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

@Service
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendMail( final String recipientName, final String recipientEmail, final Locale locale) throws MessagingException {

        // Prepare the evaluation context
        final Context ctx = new Context(locale);
        ctx.setVariable("name", recipientName);
        //ctx.setVariable("subscriptionDate", new Date ());
        // ctx.setVariable("imageResourceName", imageResourceName); // so that we can reference it from HTML

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message =
                new MimeMessageHelper(mimeMessage, true, "UTF-8"); // true = multipart
        message.setSubject("Example HTML email with inline image");
        message.setFrom("servinet@example.com");
        message.setTo(recipientEmail);

        // Create the HTML body using Thymeleaf
        final String htmlContent = this.templateEngine.process("mail.html", ctx);
        message.setText(htmlContent, true); // true = isHtml

        // Add the inline image, referenced from the HTML code as "cid:${imageResourceName}"
                /*
        final InputStreamSource imageSource = new ByteArrayResource(imageBytes);
        message.addInline(imageResourceName, imageSource, imageContentType);
        */
        // Send mail
        this.mailSender.send(mimeMessage);
    }
}
