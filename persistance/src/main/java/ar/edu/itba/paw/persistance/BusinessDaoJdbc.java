package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.model.Business;
import ar.edu.itba.paw.services.BusinessDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class BusinessDaoJdbc implements BusinessDao {
    private static final RowMapper<Business> ROW_MAPPER = (rs, rowNum) -> new Business(rs.getLong("businessid"),
            rs.getString("businessname"), rs.getLong("userid"), rs.getString("businessTelephone"), rs.getString("businessEmail"),
            rs.getString("businessLocation"));
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public BusinessDaoJdbc(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        simpleJdbcInsert = new SimpleJdbcInsert(ds).usingGeneratedKeyColumns("businessid")
                .withTableName("business");
    }

    @Override
    public Optional<Business> findById(long id) {
        final List<Business> list = jdbcTemplate.query("SELECT * from business WHERE businessid = ?", new Object[] {id}, ROW_MAPPER);
        return list.stream().findFirst();
    }

    @Override
    public Optional<Business> findByBusinessName(String businessName) {
        final List<Business> list = jdbcTemplate.query("SELECT * from business WHERE businessname= ?", new Object[] {businessName}, ROW_MAPPER);
        return list.stream().findFirst();
    }

    @Override
    public Optional<List<Business>> findByAdminId(long adminId){
        final List<Business> list = jdbcTemplate.query("SELECT * from business WHERE userid= ?", new Object[] {adminId}, ROW_MAPPER);
        return Optional.of(list);
    }

    @Override
    public void deleteBusiness(long businessid) {
        jdbcTemplate.update("delete from business where businessid = ?", businessid);
    }
    private void changeField(final String field, long businessId, String value) {
        jdbcTemplate.update(String.format("update business set %s = ? where businessid = ?", field), value, businessId);
    }

    @Override
    public void changeBusinessEmail(long businessId, String value) {
        changeField("email", businessId, value);
    }

    @Override
    public Business createBusiness(String businessName, long userId, String telephone, String email, String location) {
        Map<String,Object> businessData = new HashMap<>();
        businessData.put("businessname", businessName);
        businessData.put("userid", userId);
        businessData.put("telephone", telephone);
        businessData.put("email", email);
        businessData.put("location", location);
        final Number generatedId = simpleJdbcInsert.executeAndReturnKey(businessData);
        return new Business(generatedId.longValue(), businessName, userId, telephone, email, location);
    }

}
