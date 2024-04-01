package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.services.ServiceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ServiceDaoJdbc implements ServiceDao {
    private static final RowMapper<Service> ROW_MAPPER = (rs, rowNum) -> new Service(rs.getLong("id"),
            rs.getLong("businessid"), rs.getString("servicename"),
            rs.getString("servicedescription"), rs.getBoolean("homeservice"),
            rs.getString("location"), Neighbourhoods.findByValue(rs.getString("location")) ,
            Categories.findByValue(rs.getString("category")), rs.getInt("minimalduration"),
            PricingTypes.findByValue(rs.getString("pricingtype")), rs.getString("price"),
            rs.getBoolean("additionalcharges"));

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

   @Autowired
   public ServiceDaoJdbc(final DataSource ds){
       jdbcTemplate = new JdbcTemplate(ds);
       simpleJdbcInsert = new SimpleJdbcInsert(ds).withTableName("services").usingGeneratedKeyColumns("id");
   }
    @Override
    public Optional<Service> findById(long id) {
        final List<Service> list = jdbcTemplate.query("SELECT * from services WHERE id = ?", new Object[] {id}, ROW_MAPPER);
        return list.stream().findFirst();
    }

    @Override
    public Service create(long businessid, String name, String description, Boolean homeservice, String location, Categories category, int minimalduration, PricingTypes pricing, String price, Boolean additionalCharges){
        final Map<String, Object> userData = new HashMap<>();
        userData.put("businessid", businessid);
        userData.put("servicename", name);
        userData.put("servicedescription", description);
        userData.put("homeservice", homeservice);
        userData.put("location", location);
        userData.put("category", category.getValue());
        userData.put("pricingtype", pricing.getValue());
        userData.put("minimalduration", minimalduration);
        userData.put("price", price);
        userData.put("additionalcharges", additionalCharges);
        final Number generatedId = simpleJdbcInsert.executeAndReturnKey(userData);
        return new Service(generatedId.longValue(), businessid, name, description, homeservice, location, Neighbourhoods.findByValue(location), category, minimalduration, pricing, price, additionalCharges);
    }

    @Override
    public Service edit(long serviceid, String field, String newvalue) {
           jdbcTemplate.update(String.format("update services set  %s  = ? where id= ? ", field), newvalue, serviceid);
           return findById(serviceid).get();
    }

    //TODO: consultar si es mejor manejar dataAccessExceptions sin salir de persistence (lo catcheo y devuelvo un boolean)
    // o se propaga la excepcion hasta que en el controller se maneje como en el ejemplo de userNotFoundExcaption
    @Override
    public void delete(long serviceid) {
            jdbcTemplate.update("delete from services where id= ? ", serviceid);
    }
}
