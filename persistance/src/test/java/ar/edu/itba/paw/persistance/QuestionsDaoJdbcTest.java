/*

package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.model.Question;
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
import java.time.LocalDate;

@Sql("classpath:sql/schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class QuestionsDaoJdbcTest {
    private static final String QUESTION = "This is a question";
    private static final String RESPONSE = "This is a response";
    private static final long USERID = 1;
    private static final long SERVICEID = 1;
    private static final int QUESTIONS_TO_RESPOND = 1;
    private static final int QUESTIONS_COUNT = 1;


    @Autowired
    private QuestionDaoJdbc questionDao;

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
        Question qst = questionDao.create(SERVICEID, USERID, QUESTION);

        Assert.assertNotNull(qst);
        Assert.assertEquals(SERVICEID, qst.getServiceid());
        Assert.assertEquals(USERID, qst.getUserid());
        Assert.assertEquals(QUESTION, qst.getQuestion());
        Assert.assertEquals(LocalDate.now(), qst.getDate());
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "questions"));
    }

    @Test
    public void testRespond() {
        Question qst = questionDao.create(SERVICEID, USERID, QUESTION);
        questionDao.addResponse(qst.getId(), RESPONSE);

        Assert.assertNotNull(qst.getResponse());
        Assert.assertEquals(RESPONSE, qst.getResponse());
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "questions"));
    }

    @Test
    public void testQuestionsToRespond() {
        questionDao.create(SERVICEID, USERID, QUESTION);

        Assert.assertEquals(QUESTIONS_TO_RESPOND, questionDao.getQuestionsToRespond(USERID).stream().count());
    }

    @Test
    public void testQuestionCount() {
        questionDao.create(SERVICEID, USERID, QUESTION);

        Assert.assertEquals(QUESTIONS_COUNT, questionDao.getQuestionsCount(SERVICEID));
    }

}

 */
