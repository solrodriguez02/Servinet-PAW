

package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.model.Rating;
import ar.edu.itba.paw.persistance.config.TestConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import javax.sql.DataSource;


@Sql("classpath:sql/schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class RatingsDaoJdbcTest {
    private static final String COMMENT = "This is a comment";
    private static final int RATING5 = 5;
    private static final int RATING3 = 3;
    private static final int RATING1 = 1;
    private static final long USERID = 1;
    private static final long USERID2=2;
    private static final long USERID3=3;
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
        jdbcTemplate.execute("INSERT INTO users (userid, username, password, name, surname, email, telephone) VALUES (1, 'username', 'password', 'name', 'surname', 'email', 'telephone')");
        jdbcTemplate.execute("INSERT INTO users (userid, username, password, name, surname, email, telephone) VALUES (2, 'username2', 'password2', 'name2', 'surname2', 'email2', 'telephone2')");
        jdbcTemplate.execute("INSERT INTO users (userid, username, password, name, surname, email, telephone) VALUES (3, 'username3', 'password3', 'name2', 'surname3', 'email3', 'telephone3')");

        jdbcTemplate.execute("INSERT INTO business(businessid, userid, businessname, businessTelephone, businessEmail, businessLocation) VALUES (1, 1, 'businessname', 'businessTelephone', 'businessEmail', 'businessLocation')");
        jdbcTemplate.execute("INSERT INTO services (id, businessid, servicename, servicedescription, homeservice, location, category, minimalduration, pricingtype, price, additionalcharges, imageId) VALUES (1, 1, 'serviceName', 'serviceDescription', true, 'serviceLocation', 'Belleza', 30, 'Total', '1000', false, null);");
    }

    @Test
    public void testCreate() {
        Rating rating = ratingDao.create(SERVICEID, USERID, RATING1, COMMENT);

        Assert.assertNotNull(rating);
        Assert.assertEquals(SERVICEID, rating.getServiceid());
        Assert.assertEquals(USERID, rating.getUserid());
        Assert.assertEquals(COMMENT, rating.getComment());
        Assert.assertEquals(RATING1, rating.getRating());
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "ratings"));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testInvalidCreate() {
        ratingDao.create(SERVICEID, USERID,6, COMMENT);
    }

    @Test
    public void testFindById() {
        jdbcTemplate.execute(String.format("insert into ratings (ratingid, serviceid, userid, rating, comment) values (1, %d, %d, %d, '%s')", SERVICEID, USERID, RATING5, COMMENT));
        Rating ratingFound = ratingDao.findById(1).get();

        Assert.assertNotNull(ratingFound);
        Assert.assertEquals(SERVICEID, ratingFound.getServiceid());
        Assert.assertEquals(USERID, ratingFound.getUserid());
        Assert.assertEquals(COMMENT, ratingFound.getComment());
        Assert.assertEquals(RATING5, ratingFound.getRating());
    }

    @Test
    public void testRatingCount() {
        jdbcTemplate.execute(String.format("insert into ratings (ratingid, serviceid, userid, rating, comment) values (1, %d, %d, %d, '%s')", SERVICEID, USERID, RATING5, COMMENT));

        Assert.assertEquals(1, ratingDao.getRatingsCount(SERVICEID));
    }
    @Test
    public void testRatingAvg(){
        jdbcTemplate.execute(String.format("insert into ratings (ratingid, serviceid, userid, rating, comment) values (1, %d, %d, %d, '%s')", SERVICEID, USERID, RATING5, COMMENT));
        jdbcTemplate.execute(String.format("insert into ratings (ratingid, serviceid, userid, rating, comment) values (2, %d, %d, %d, '%s')", SERVICEID, USERID2, RATING1, COMMENT));
        jdbcTemplate.execute(String.format("insert into ratings (ratingid, serviceid, userid, rating, comment) values (3, %d, %d, %d, '%s')", SERVICEID, USERID3, RATING3, COMMENT));
        double avg= (double) (RATING5 + RATING1 + RATING3) /3;
        Assert.assertEquals(avg, ratingDao.getRatingsAvg(SERVICEID), 0.0001);
    }
    @Test
    public void testEmptyRatingAvg(){

        Assert.assertEquals(0.0, ratingDao.getRatingsAvg(SERVICEID), 0.0001);
    }


}

