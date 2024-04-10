// package ar.edu.itba.paw.persistance;
//
//
// import ar.edu.itba.paw.model.Business;
// import ar.edu.itba.paw.persistance.config.TestConfig;
// import org.junit.Assert;
// import org.junit.Before;
// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.jdbc.core.JdbcTemplate;
// import org.springframework.test.context.ContextConfiguration;
// import org.springframework.test.context.jdbc.Sql;
// import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
// import org.springframework.test.jdbc.JdbcTestUtils;
// import javax.sql.DataSource;
//
// @Sql("classpath:sql/schema.sql")
// @RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(classes = TestConfig.class)
// public class BusinessDaoJdbcTest {
//
//     private static final long ID =1;
//     private static final String BUSINESS_NAME = "Business";
//     private static final long USER_ID = 1;
//     private static final String TELEPHONE = "123456789";
//     private static final String EMAIL = "mail@mail.com";
//     private static final String LOCATION = null;
//
//     @Autowired
//     private BusinessDaoJdbc businessDaoJdbc;
//     @Autowired
//     private DataSource ds;
//     private JdbcTemplate jdbcTemplate;
//
//     @Before
//     public void setup() {
//         this.jdbcTemplate = new JdbcTemplate(ds);
//         JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
//     }
//
//     @Test
//     public void testCreateBusiness() {
//         // 1. Precondiciones
//         jdbcTemplate.execute("INSERT INTO users (userid, username, password, name, surname, email, telephone) VALUES (1, 'username', 'password', 'name', 'surname', 'email', 'telephone')");
//
//         // 2. Ejecuta la class under test (una sola)
//         Business business= businessDaoJdbc.createBusiness(BUSINESS_NAME, 1, TELEPHONE, EMAIL, LOCATION);
//
//         // 3. Postcondiciones - assertions (todas las que sean necesarias)
//         Assert.assertNotNull(business);
//         Assert.assertEquals(BUSINESS_NAME, business.getBusinessName());
//         Assert.assertEquals(1,JdbcTestUtils.countRowsInTable(jdbcTemplate, "business"));
//     }
//
//     @Test
//     public void testDeleteBusiness(){
//         // 1. Precondiciones
//         jdbcTemplate.execute("INSERT INTO users (userid, username, password, name, surname, email, telephone) VALUES (1, 'username', 'password', 'name', 'surname', 'email', 'telephone')");
//         jdbcTemplate.execute(String.format("INSERT INTO business (businessid, businessname, userid, businessTelephone, businessEmail, businessLocation) VALUES (1,'%s', 1, '%s','%s','%s')",BUSINESS_NAME, TELEPHONE, EMAIL, LOCATION));
//
//         businessDaoJdbc.deleteBusiness(ID);
//
//         Assert.assertEquals(0,JdbcTestUtils.countRowsInTable(jdbcTemplate, "business"));
//
//     }
//
// }
//