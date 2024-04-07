package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.Appointment;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.Locale;

public class EmailServiceImplTest {


    private static LocalDateTime STARTDATE = LocalDateTime.now();
    private static final String EMAIL = "andresdominguez555@gmail.com";
    private static final String SUBJECT = "Confirmation";

    // Los Mocks hacen el constructor automaticamente (no necesito hacer un Before setup)
    @InjectMocks
    private EmailServiceImpl emailService;
    @Test
    public void testCreate() throws MessagingException {
            emailService.sendMail( EMAIL, SUBJECT, "",  new Locale("sp"));
    }
}
