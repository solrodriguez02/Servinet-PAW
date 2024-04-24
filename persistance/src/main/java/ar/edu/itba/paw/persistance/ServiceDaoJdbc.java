package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.services.ServiceDao;
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
import java.util.Arrays;

@Repository
public class ServiceDaoJdbc implements ServiceDao {
    private static final RowMapper<Service> ROW_MAPPER = (rs, rowNum) -> new Service(rs.getLong("id"),
            rs.getLong("businessid"), rs.getString("servicename"),
            rs.getString("servicedescription"), rs.getBoolean("homeservice"),
            rs.getString("location"),(String[])rs.getArray("neighbourhoods").getArray(),
            Categories.findByValue(rs.getString("category")), rs.getInt("minimalduration"),
            PricingTypes.findByValue(rs.getString("pricingtype")), rs.getString("price"),
            rs.getBoolean("additionalcharges"),rs.getLong("imageId"));

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

   @Autowired
   public ServiceDaoJdbc(final DataSource ds){
       jdbcTemplate = new JdbcTemplate(ds);
       simpleJdbcInsert = new SimpleJdbcInsert(ds).withTableName("services").usingGeneratedKeyColumns("id");
   }
    @Override
    public Optional<Service> findById(long id) {
        final List<Service> list = jdbcTemplate.query("SELECT * from (select s.* ,array_agg(nb.neighbourhood) as neighbourhoods from services s inner join nbservices nb on s.id=nb.serviceid group by s.id,s.businessid) sq  WHERE id = ?", new Object[] {id}, ROW_MAPPER);
        return list.stream().findFirst();
    }

    @Override
    public Service create(long businessid, String name, String description, Boolean homeservice, String location,Neighbourhoods[] neighbourhoods, Categories category, int minimalduration, PricingTypes pricing, String price, Boolean additionalCharges,long imageId){
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
        userData.put("imageid", imageId != 0 ? imageId : null);
        final Number generatedId = simpleJdbcInsert.executeAndReturnKey(userData);
        if(!homeservice) { //TODO: implementar esto bien con spring security
            jdbcTemplate.update("insert into nbservices (serviceid,neighbourhood) values (?,?)", generatedId, neighbourhoods[0].getValue());
        }
        else {
            for (Neighbourhoods n : neighbourhoods) {
                jdbcTemplate.update("insert into nbservices (serviceid,neighbourhood) values (?,?)", generatedId, n.getValue());
            }
        }
        return new Service(generatedId.longValue(), businessid, name, description, homeservice, location, Arrays.stream(neighbourhoods).map(Enum::name).toArray(String[]::new), category, minimalduration, pricing, price, additionalCharges, imageId);
    }

    @Override
    public Optional<List<Service>> getAllServices(){
        final List<Service> list = jdbcTemplate.query("select s.* ,array_agg(nb.neighbourhood) as neighbourhoods from services s inner join nbservices nb on s.id=nb.serviceid group by s.id ;", ROW_MAPPER);
        return Optional.of(list);

    }

    @Override
    public Optional<List<Service>> getAllBusinessServices(long businessId){
        final List<Service> list = jdbcTemplate.query("select s.*,array_agg(nb.neighbourhood) as neighbourhoods from services s inner join nbservices nb on s.id=nb.serviceid WHERE businessid = ?  group by id" , new Object[] {businessId}, ROW_MAPPER);
        return Optional.of(list);
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

    @Override
    public List<Service> getServices(int page) {
        final List<Service> list = jdbcTemplate.query("select s.*,array_agg(nb.neighbourhood) as neighbourhoods from services s inner join nbservices nb on s.id=nb.serviceid group by s.id ORDER BY id ASC OFFSET ? LIMIT 10", new Object[] {page*10}, ROW_MAPPER);
        return list;
    }

    @Override
    public List<Service> getServicesFilteredBy(int page, String category, String[] location, int rating, String searchQuery) {
    FilterArgument filterArgument = new FilterArgument().addCategory(category).addLocation(location).addSearch(searchQuery).addPage(page).addRating(rating);
    String sqlQuery= "select * from " + filterArgument.formSqlSentence("(select s.* ,array_agg(nb.neighbourhood) as neighbourhoods from services s inner join nbservices nb on s.id=nb.serviceid group by s.id) ");
    Object[] values = filterArgument.getValues().toArray();
    return jdbcTemplate.query(sqlQuery.toString(), values, ROW_MAPPER);
   }

    @Override
    public int getServiceCount(String category, String[] location, int rating, String searchQuery) {
        FilterArgument filterArgument = new FilterArgument().addCategory(category).addLocation(location).addSearch(searchQuery).addRating(rating);
        Object[] values = filterArgument.getValues().toArray();
        if(values.length==0){
            return jdbcTemplate.queryForObject("SELECT count(id) from services", Integer.class);

        }
        String sqlQuery=  filterArgument.formSqlSentence("SELECT count(id) from (select s.* ,array_agg(nb.neighbourhood) as neighbourhoods from services s inner join nbservices nb on s.id=nb.serviceid group by s.id) ");
        return jdbcTemplate.queryForObject(sqlQuery, Integer.class,values);
    }

    @Override
    public List<Service> getRecommendedServices() {
        final List<Service> list = jdbcTemplate.query("select s.*,array_agg(nb.neighbourhood) as neighbourhoods from services s inner join nbservices nb on s.id=nb.serviceid group by s.id ORDER BY id DESC LIMIT 10", ROW_MAPPER);
        return list;
    }


}
