/*

package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.model.Rating;
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
import java.sql.SQLException;
import java.util.Optional;

@Sql("classpath:sql/schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class RatingsDaoJdbcTest {
    private static final String COMMENT = "This is a comment";
    private static final int RATING = 5;
    private static final long USERID = 1;
    private static final long SERVICEID = 1;


    @Autowired
    private RatingDaoJdbc ratingDao;

    @Autowired
    private DataSource ds;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setup(){
        this.jdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "services" );
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "users" );
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "business" );
        jdbcTemplate.execute("INSERT INTO users (userid, username, password, name, surname, email, telephone) VALUES (USERID, 'username', 'password', 'name', 'surname', 'email', 'telephone')");
        jdbcTemplate.execute("INSERT INTO business(businessid, userid, businessname, businessTelephone, businessEmail, businessLocation) VALUES (1, 1, 'businessname', 'businessTelephone', 'businessEmail', 'businessLocation')");
        jdbcTemplate.execute("INSERT INTO services (id, businessid, servicename, servicedescription, homeservice, location, category, minimalduration, pricingtype, price, additionalcharges, imageId, neighbourhood) VALUES (1, 1, 'serviceName', 'serviceDescription', true, 'serviceLocation', 'Belleza', 30, 'Total', '1000', false, 123, 'Palermo');\n");
    }

    @Test
    public void testCreate() {
        Rating rating = ratingDao.create(SERVICEID, USERID, RATING, COMMENT);

        Assert.assertNotNull(rating);
        Assert.assertEquals(SERVICEID, rating.getServiceid());
        Assert.assertEquals(USERID, rating.getUserid());
        Assert.assertEquals(COMMENT, rating.getComment());
        Assert.assertEquals(RATING, rating.getRating());
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "ratings"));
    }

    @Test
    public void testInvalidCreate() {
        try {
            ratingDao.create(SERVICEID, USERID,6, COMMENT);
            Assert.fail("An exception was expected due to invalid rating value");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof SQLException);
        }
    }

    @Test
    public void testFindById() {
        Rating rating = ratingDao.create(SERVICEID, USERID, RATING, COMMENT);
        Rating ratingFound = ratingDao.findById(rating.getId()).get();

        Assert.assertNotNull(ratingFound);
        Assert.assertEquals(SERVICEID, ratingFound.getServiceid());
        Assert.assertEquals(USERID, ratingFound.getUserid());
        Assert.assertEquals(COMMENT, ratingFound.getComment());
        Assert.assertEquals(RATING, ratingFound.getRating());
    }

    @Test
    public void testRatingCount() {
        ratingDao.create(SERVICEID, USERID, RATING, COMMENT);

        Assert.assertEquals(1, ratingDao.getRatingsCount(SERVICEID));
    }

}
*/
