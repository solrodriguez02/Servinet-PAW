package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class AppointmentServiceImplTest {

    private static final String BUSINESSNAME="busname";
    private static final String USERNAME="username";
    private static final String PASSWORD="password";
    private static final LocalDateTime STARTDATE = LocalDateTime.now();
    private static final LocalDateTime ENDDATE = STARTDATE.plusHours(2);
    private static final String LOCATION = "calle 123";
    private static final long SERVICEID = 1;
    private static final String NAME= "name";
    private static final String SURNAME= "surname";
    private static final String TELEPHONE= "123456789";
    private static final String EMAIL= "email";
    private static final String DESCRIPTION = "description";
    private static final Boolean HOMESERVICE = true;
    private static final int DURATION = 30;
    private static final String PRICE = "ARS 1000";
    private static final Boolean ADDITIONALCHARGES = false;
    private static final PricingTypes PRICING = PricingTypes.PER_TOTAL;
    private static final long BUSINESSID = 1;
    private static final Neighbourhoods[] NEIGHBOURHOODS = {Neighbourhoods.PALERMO};
    private static final Categories CATEGORY = Categories.BELLEZA;
    private static final long USERID = 1;
    private static final long APPOINTMENTID = 1;

    // Los Mocks hacen el constructor automaticamente (no necesito hacer un Before setup)
    @InjectMocks
    private AppointmentServiceImpl appointmentService;
    @Mock
    private BusinessDao businessDao;
    @Mock
    private UserService userService;
    @Mock
    private AppointmentDao appointmentDao;
    @Mock
    private ServiceDao serviceDao;
    @Test
    public void testCreate() {
        // 1. Precondiciones

        Mockito.when(appointmentDao.create(Mockito.eq(SERVICEID),Mockito.eq(USERID),
                Mockito.eq(STARTDATE),Mockito.eq(ENDDATE), Mockito.eq(LOCATION))).thenReturn(new Appointment(APPOINTMENTID,SERVICEID,USERID,STARTDATE,ENDDATE,"f",false));

        Mockito.when(userService.findByEmail(Mockito.eq(EMAIL))).thenReturn(Optional.of(new User(USERID,USERNAME,PASSWORD,NAME,SURNAME,EMAIL,TELEPHONE,false)));

       Mockito.when(businessDao.findById(Mockito.eq(BUSINESSID))).thenReturn(Optional.of(new Business(BUSINESSID,BUSINESSNAME,USERID,TELEPHONE,EMAIL,LOCATION)));

        Mockito.when(serviceDao.findById(Mockito.eq(SERVICEID))).thenReturn(Optional.of(new Service(SERVICEID,BUSINESSID,NAME,DESCRIPTION,HOMESERVICE,LOCATION, Arrays.stream(NEIGHBOURHOODS).map(Enum::name).toArray(String[]::new),CATEGORY,DURATION,PRICING,PRICE,ADDITIONALCHARGES,0)));
        // 2. Ejecuta la class under test (una sola)
        Appointment appointment = appointmentService.create(SERVICEID,NAME,SURNAME,EMAIL,LOCATION,TELEPHONE,STARTDATE.toString());

        // 3. Postcondiciones - assertions (todas las que sean necesarias)
        Assert.assertNotNull(appointment);
        Assert.assertEquals(APPOINTMENTID, appointment.getId());
        Assert.assertEquals(ENDDATE, appointment.getEndDate());


    }
}

