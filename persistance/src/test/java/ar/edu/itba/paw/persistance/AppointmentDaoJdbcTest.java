package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.model.Appointment;
import ar.edu.itba.paw.persistance.config.TestConfig;
import org.junit.After;
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
import java.time.chrono.ChronoLocalDateTime;

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

    @After
    public void testFindById(){
        Appointment appointment = appointmentDao.findById(1).get();

        Assert.assertEquals(SERVICEID,appointment.getServiceid());
        Assert.assertTrue(ENDDATE.isAfter(appointment.getStartDate()));
    }
}
