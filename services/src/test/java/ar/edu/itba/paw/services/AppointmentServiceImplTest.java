package ar.edu.itba.paw.services;
/*
import ar.edu.itba.paw.model.Appointment;
import ar.edu.itba.paw.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;

@RunWith(MockitoJUnitRunner.class)
public class AppointmentServiceImplTest {


    private static LocalDateTime STARTDATE = LocalDateTime.now();
    private static LocalDateTime ENDDATE = STARTDATE.plusHours(2);
    private static final long SERVICEID = 1;
    private static final long USERID = 1;
    private static final long APPOINTMENTID = 1;

    // Los Mocks hacen el constructor automaticamente (no necesito hacer un Before setup)
    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    @Mock
    private AppointmentDao appointmentDao;
    @Test
    public void testCreate() {
        // 1. Precondiciones

        Mockito.when(appointmentDao.create(Mockito.eq(SERVICEID),Mockito.eq(USERID),
                Mockito.eq(STARTDATE),Mockito.eq(ENDDATE), Mockito.eq(""))).thenReturn(new Appointment(APPOINTMENTID,SERVICEID,USERID,STARTDATE,ENDDATE,"f",false));


        // 2. Ejecuta la class under test (una sola)
        Appointment appointment = appointmentService.create(SERVICEID,USERID,STARTDATE,ENDDATE,"");

        // 3. Postcondiciones - assertions (todas las que sean necesarias)
        Assert.assertNotNull(appointment);
        Assert.assertEquals(APPOINTMENTID, appointment.getId());
        Assert.assertEquals(ENDDATE, appointment.getEndDate());


    }
}
*/
