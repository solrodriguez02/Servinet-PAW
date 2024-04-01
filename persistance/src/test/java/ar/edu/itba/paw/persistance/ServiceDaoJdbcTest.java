package ar.edu.itba.paw.persistance;


import ar.edu.itba.paw.model.Categories;
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

@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ServiceDaoJdbcTest {
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
       jdbcTemplate.execute("INSERT INTO business(id, userid, businessname, businessTelephone, businessEmail, businessLocation) VALUES (1, 1, 'businessname', 'businessTelephone', 'businessEmail', 'businessLocation')");
   }

   @Test
    public void testCreate() {
       Service service = serviceDao.create(BUSINESSID, NAME, DESCRIPTION, HOMESERVICE, LOCATION, CATEGORY, DURATION, PRICING, PRICE, ADDITIONALCHARGES);

       Assert.assertNotNull(service);
       Assert.assertEquals(BUSINESSID, service.getBusinessid());
       Assert.assertEquals(NAME, service.getName());
       Assert.assertEquals(DESCRIPTION, service.getDescription());
       Assert.assertEquals(HOMESERVICE, service.getHomeService());
       Assert.assertEquals(LOCATION, service.getLocation());
       Assert.assertEquals(CATEGORY, Categories.findByValue(service.getCategory()));
       Assert.assertEquals(DURATION, service.getDuration());
       Assert.assertEquals(PRICING, PricingTypes.findByValue(service.getPricing()));
       Assert.assertEquals(PRICE, service.getPrice());
       Assert.assertEquals(ADDITIONALCHARGES, service.getAdditionalCharges());
       Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "services"));
   }

   @Test
    public void testFindById() {
       Service service = serviceDao.create(BUSINESSID, NAME, DESCRIPTION, HOMESERVICE, LOCATION, CATEGORY, DURATION, PRICING, PRICE, ADDITIONALCHARGES);
       Service service2 = serviceDao.findById(service.getId()).get();

       Assert.assertNotNull(service2);
       Assert.assertEquals(service.getId(), service2.getId());
       Assert.assertEquals(service.getBusinessid(), service2.getBusinessid());
       Assert.assertEquals(service.getName(), service2.getName());
       Assert.assertEquals(service.getDescription(), service2.getDescription());
       Assert.assertEquals(service.getHomeService(), service2.getHomeService());
       Assert.assertEquals(service.getLocation(), service2.getLocation());
       Assert.assertEquals(service.getCategory(), service2.getCategory());
       Assert.assertEquals(service.getDuration(), service2.getDuration());
       Assert.assertEquals(service.getPricing(), service2.getPricing());
       Assert.assertEquals(service.getPrice(), service2.getPrice());
       Assert.assertEquals(service.getAdditionalCharges(), service2.getAdditionalCharges());
   }

   @Test
    public void testEdit() {
       Service service = serviceDao.create(BUSINESSID, NAME, DESCRIPTION, HOMESERVICE, LOCATION, CATEGORY, DURATION, PRICING, PRICE, ADDITIONALCHARGES);
       Service service2 = serviceDao.edit(service.getId(), "servicename", "newname");

       Assert.assertNotNull(service);
       Assert.assertNotNull(service2);
       Assert.assertEquals(service.getId(), service2.getId());
       Assert.assertEquals(service.getBusinessid(), service2.getBusinessid());
       Assert.assertEquals("newname", service2.getName());
       Assert.assertEquals(service.getDescription(), service2.getDescription());
       Assert.assertEquals(service.getHomeService(), service2.getHomeService());
       Assert.assertEquals(service.getLocation(), service2.getLocation());
       Assert.assertEquals(service.getCategory(), service2.getCategory());
       Assert.assertEquals(service.getDuration(), service2.getDuration());
       Assert.assertEquals(service.getPricing(), service2.getPricing());
       Assert.assertEquals(service.getPrice(), service2.getPrice());
       Assert.assertEquals(service.getAdditionalCharges(), service2.getAdditionalCharges());
   }
  @Test
   public void testDelete() {
      Service service = serviceDao.create(BUSINESSID, NAME, DESCRIPTION, HOMESERVICE, LOCATION, CATEGORY, DURATION, PRICING, PRICE, ADDITIONALCHARGES);
       serviceDao.delete(service.getId());

       Assert.assertEquals(0, JdbcTestUtils.countRowsInTable(jdbcTemplate, "services"));
   }
}
