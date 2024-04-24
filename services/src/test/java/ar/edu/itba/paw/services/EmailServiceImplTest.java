package ar.edu.itba.paw.services;
/*
import ar.edu.itba.paw.model.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;

import javax.mail.MessagingException;
import java.time.LocalDateTime;


@RunWith(MockitoJUnitRunner.class)
public class EmailServiceImplTest {


    private static LocalDateTime STARTDATE = LocalDateTime.now();
    private static LocalDateTime ENDDATE = STARTDATE.plusHours(2);
    private static final String EMAIL = "andresdominguez555@gmail.com";
    private static final String PROVIDER_EMAIL = "peluqueria@gmail.com";
    private static final String SUBJECT = "Confirmation";
    private static final long SERVICEID = 1;
    private static final long USERID = 1;
    private static final long APPOINTMENTID = 1;

    private static final long ID =1;
    private static final String BUSINESS_NAME = "Business";
    private static final long PROVIDERID = 1000;
    private static final String TELEPHONE = "123456789";

    private static final String NAME = "name";
    private static final long BUSINESSID = 1;
    private static final String DESCRIPTION = "description";
    private static final Boolean HOMESERVICE = true;
    private static final String LOCATION = "Palermo";
    private static final Categories CATEGORY = Categories.BELLEZA;
    private static final int DURATION = 30;
    private static final String PRICE = "ARS 1000";
    private static final Boolean ADDITIONALCHARGES = false;
    private static final PricingTypes PRICING = PricingTypes.PER_TOTAL;

    // Los Mocks hacen el constructor automaticamente (no necesito hacer un Before setup)
    @InjectMocks
    private EmailServiceImpl emailService;

    @Mock
    private static AppointmentService appointmentService;

    @Mock
    private static ServiceService serviceService;

    @Mock
    private static BusinessService businessService;


    @Mock
    private static UserService userService;
    @Test
    public void testCreate() throws MessagingException {

        Mockito.when(userService.create(Mockito.eq("lucas"),Mockito.eq("lucas"),Mockito.eq("aaa"),
            Mockito.eq("aa"), Mockito.eq(EMAIL), Mockito.eq(TELEPHONE))).thenReturn(new User(USERID,"","","","",EMAIL,TELEPHONE,false));

        Mockito.when(businessService.createBusiness(Mockito.eq(BUSINESS_NAME),
                Mockito.eq(PROVIDERID),Mockito.eq(TELEPHONE),Mockito.eq(PROVIDER_EMAIL),Mockito.eq(LOCATION))).thenReturn(new Business(1,BUSINESS_NAME,USERID,TELEPHONE,EMAIL,LOCATION));

        Mockito.when(serviceService.create(Mockito.eq(BUSINESSID),
                Mockito.eq(NAME),Mockito.eq(DESCRIPTION),Mockito.eq(HOMESERVICE),Mockito.eq(LOCATION),Mockito.eq(CATEGORY),Mockito.eq(DURATION),
                        Mockito.eq(PRICING),Mockito.eq(PRICE),Mockito.eq(ADDITIONALCHARGES)))
                .thenReturn(new Service(ID,BUSINESSID,NAME,DESCRIPTION,HOMESERVICE,LOCATION,Neighbourhoods.ALMAGRO,CATEGORY,DURATION,PRICING,PRICE,ADDITIONALCHARGES));

        Mockito.when(appointmentService.create(Mockito.eq(SERVICEID),Mockito.eq(USERID),
                Mockito.eq(STARTDATE),Mockito.eq(ENDDATE))).thenReturn(new Appointment(APPOINTMENTID,SERVICEID,USERID,STARTDATE,ENDDATE,false));

        Appointment appointment = appointmentService.create(1,1,STARTDATE,ENDDATE);

        emailService.requestAppointment(appointment,EMAIL);
    }

}
*/
