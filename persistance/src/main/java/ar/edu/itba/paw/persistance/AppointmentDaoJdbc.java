package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.model.Appointment;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.services.AppointmentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class AppointmentDaoJdbc implements AppointmentDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private static final RowMapper<Appointment> ROW_MAPPER = (rs, rowNum) -> new Appointment(rs.getInt("appointmentid"), rs.getInt("serviceid"), rs.getInt("userid"),
            rs.getTimestamp("startDate").toLocalDateTime(), rs.getTimestamp("endDate").toLocalDateTime(), rs.getBoolean("confirmed"));

    @Autowired
    public AppointmentDaoJdbc(final DataSource ds){
        jdbcTemplate = new JdbcTemplate(ds);
        simpleJdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName("appointments").usingGeneratedKeyColumns("appointmentid");
    }

    @Override
    public Optional<Appointment> findById(long id) {
        final List<Appointment> list = jdbcTemplate.query("SELECT * from appointments WHERE appointmentid = ?", new Object[] {id}, ROW_MAPPER);
        return list.stream().findFirst();
    }

    @Override
    public Appointment create(long serviceid, long userid, LocalDateTime startDate, LocalDateTime endDate) {
        final Map<String, Object> appointmentData = new HashMap<>();
        appointmentData.put("serviceid", serviceid);
        appointmentData.put("userid", userid);
        appointmentData.put("startdate", Timestamp.valueOf(startDate));
        appointmentData.put("enddate", Timestamp.valueOf(endDate) );
        //appointmentData.put("confirmed", false); pues default es false

        final Number generatedId = simpleJdbcInsert.executeAndReturnKey(appointmentData);
        return new Appointment(generatedId.longValue(), serviceid, userid, startDate, endDate, false);
    }


    @Override
    public void confirmAppointment(long appointmentid) {

    }

    @Override
    public void cancelAppointment(long appointmentid) {

    }
}
