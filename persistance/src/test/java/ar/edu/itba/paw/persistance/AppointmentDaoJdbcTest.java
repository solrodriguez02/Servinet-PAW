package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.model.Appointment;
import ar.edu.itba.paw.persistance.config.TestConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.time.LocalDateTime;

@Sql("classpath:sql/appointments.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class AppointmentDaoJdbcTest {

    @Autowired
    private AppointmentDaoJdbc appointmentDao;

    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;
    private LocalDateTime STARTDATE = LocalDateTime.now();
    private LocalDateTime ENDDATE = STARTDATE.plusHours(2);
    private final long SERVICEID = 1;
    private final long USERID = 1;

    @Before
    public void setup() {
        this.jdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "appointments");
        jdbcTemplate.execute("insert into services values(default,'peluqueria');");
        jdbcTemplate.execute("insert into users values (default,'agus');");
    }

    @Test
    public void testCreate() {
        // 1. Precondiciones (una sola)

        // 2. Ejecuta la class under test (una sola)
        Appointment appointment = appointmentDao.create(SERVICEID, USERID, STARTDATE, ENDDATE);

        // 3. Postcondiciones - assertions (todas las que sean necesarias)
        Assert.assertNotNull(appointment);
        Assert.assertFalse(appointment.getConfirmed());
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "appointments"));
    }

    @Test
    public void testCreate2() {
        // 1. Precondiciones (una sola)

        // 2. Ejecuta la class under test (una sola)
        Appointment a1 = appointmentDao.create(SERVICEID, USERID, STARTDATE, ENDDATE);
        Appointment a2 = appointmentDao.create(SERVICEID, USERID, STARTDATE.plusHours(1), ENDDATE);

        // 3. Postcondiciones - assertions (todas las que sean necesarias)
        Assert.assertEquals(2, JdbcTestUtils.countRowsInTable(jdbcTemplate, "appointments"));
        Assert.assertTrue( a1.getId() < a2.getId());
    }

    @Test
    public void testFindById(){
        appointmentDao.create(SERVICEID, USERID, STARTDATE, ENDDATE);

        Appointment appointment = appointmentDao.findById(1).get();

        Assert.assertEquals(SERVICEID,appointment.getServiceid());
        Assert.assertTrue(ENDDATE.isAfter(appointment.getStartDate()));
    }

    @Test
    public void testConfirmAppointment(){
        Appointment toConfirmAppointment = appointmentDao.create(SERVICEID, USERID, STARTDATE, ENDDATE);
        Appointment pendingAppointment = appointmentDao.create(SERVICEID, USERID, STARTDATE.plusHours(1), ENDDATE);

        appointmentDao.confirmAppointment(toConfirmAppointment.getId());

        Appointment confirmedAppointment = appointmentDao.findById(toConfirmAppointment.getId()).get();
        Assert.assertTrue(confirmedAppointment.getConfirmed());
        Assert.assertFalse(pendingAppointment.getConfirmed());
    }

    @Test
    public void testCancelAppointment(){
        Appointment appointment = appointmentDao.create(SERVICEID, USERID, STARTDATE, ENDDATE);
        Appointment anotherAppointment = appointmentDao.create(SERVICEID, USERID, STARTDATE.plusHours(1), ENDDATE);

        appointmentDao.cancelAppointment(appointment.getId());
        Assert.assertFalse( appointmentDao.findById(appointment.getId()).isPresent());
        Assert.assertTrue( appointmentDao.findById(anotherAppointment.getId()).isPresent());
    }
}
