 package ar.edu.itba.paw.persistance;


 import ar.edu.itba.paw.model.Categories;
 import ar.edu.itba.paw.model.Neighbourhoods;
 import ar.edu.itba.paw.model.PricingTypes;
 import ar.edu.itba.paw.model.Service;
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
 import java.util.List;

 @Sql("classpath:sql/schema.sql")
 @RunWith(SpringJUnit4ClassRunner.class)
 @ContextConfiguration(classes = TestConfig.class)
 public class ServiceDaoJdbcTest {
     private static final String NAME = "name";
     private static final long SERVICEID=1;
     private static final long BUSINESSID = 1;
     private static final String DESCRIPTION = "description";
     private static final Boolean HOMESERVICE = true;
     private static final String LOCATION = "calle 123";
     private static final Neighbourhoods[] NEIGHBOURHOODS = {Neighbourhoods.PALERMO};
     private static final Categories CATEGORY = Categories.BELLEZA;
     private static final int DURATION = 30;
     private static final String PRICE = "ARS 1000";
     private static final Boolean ADDITIONALCHARGES = false;
     private static final PricingTypes PRICING = PricingTypes.PER_TOTAL;

    @Autowired
    private ServiceDaoJdbc serviceDao;
    @Autowired
    private DataSource ds;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setup(){
        this.jdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "services" );
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "users" );
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "business" );
        jdbcTemplate.execute("INSERT INTO users (userid, username, password, name, surname, email, telephone) VALUES (1, 'username', 'password', 'name', 'surname', 'email', 'telephone')");
        jdbcTemplate.execute("INSERT INTO business(businessid, userid, businessname, businessTelephone, businessEmail, businessLocation) VALUES (1, 1, 'businessname', 'businessTelephone', 'businessEmail', 'businessLocation')");
    }

    @Test
     public void testCreate() {
        Service service = serviceDao.create(BUSINESSID, NAME, DESCRIPTION, HOMESERVICE, LOCATION, NEIGHBOURHOODS,CATEGORY, DURATION, PRICING, PRICE, ADDITIONALCHARGES,0);

        Assert.assertNotNull(service);
        Assert.assertEquals(BUSINESSID, service.getBusinessid());
        Assert.assertEquals(NAME, service.getName());
        Assert.assertEquals(DESCRIPTION, service.getDescription());
        Assert.assertEquals(HOMESERVICE, service.getHomeService());
        Assert.assertEquals(LOCATION, service.getLocation());
        Assert.assertEquals(CATEGORY, service.getCategory());
        Assert.assertEquals(DURATION, service.getDuration());
        Assert.assertEquals(PRICING, service.getPricing());
        Assert.assertEquals(PRICE, service.getPrice());
        Assert.assertEquals(ADDITIONALCHARGES, service.getAdditionalCharges());
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "services"));
    }

    @Test
     public void testFindById() {
        jdbcTemplate.execute(String.format("INSERT INTO services (id, businessid, servicename, servicedescription, homeservice, location, category, minimalduration, pricingtype, price, additionalcharges, imageId) VALUES (%d, %d, '%s', '%s', true, '%s', 'Belleza', 30, 'Total', '%s', false, null);", SERVICEID,BUSINESSID, NAME, DESCRIPTION, LOCATION,PRICE));
        jdbcTemplate.execute(String.format("INSERT INTO nbservices (serviceid, neighbourhood) VALUES (%d, '%s');", SERVICEID, NEIGHBOURHOODS[0].getValue()));
        Service service = serviceDao.findById(SERVICEID).get();

        Assert.assertNotNull(service);
        Assert.assertEquals(SERVICEID,service.getId());
        Assert.assertEquals(BUSINESSID,service.getBusinessid());
        Assert.assertEquals(NAME,service.getName());
        Assert.assertEquals(DESCRIPTION,service.getDescription());
        Assert.assertEquals(HOMESERVICE,service.getHomeService());
        Assert.assertEquals(LOCATION,service.getLocation());
        Assert.assertEquals(CATEGORY.getValue(),service.getCategory().getValue());
        Assert.assertEquals(DURATION,service.getDuration());
        Assert.assertEquals(PRICING,service.getPricing());
        Assert.assertEquals(PRICE,service.getPrice());
        Assert.assertEquals(ADDITIONALCHARGES,service.getAdditionalCharges());
    }

   @Test
    public void testDelete() {
       Service service = serviceDao.create(BUSINESSID, NAME, DESCRIPTION, HOMESERVICE, LOCATION, NEIGHBOURHOODS,CATEGORY, DURATION, PRICING, PRICE, ADDITIONALCHARGES,0);
        serviceDao.delete(service.getId());

        Assert.assertEquals(0, JdbcTestUtils.countRowsInTable(jdbcTemplate, "services"));
    }
 }
