package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.model.User;
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

// Uso una base de datos provista por un tercero

@Sql("classpath:sql/schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class UserDaoJdbcTest {
    private static final int USERID = 1;
    private static final String USERNAME = "username";
    private static final String PASSWORD = "mepassword";
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String EMAIL = "email";
    private static final String TELEPHONE = "telephone";


    @Autowired
    private UserDaoJdbc userDao;

    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setup() {
        this.jdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
    }

    @Test
    public void testCreate() {
        // 1. Precondiciones (una sola)

        // 2. Ejecuta la class under test (una sola)
        User user = userDao.create(USERNAME, NAME, SURNAME,PASSWORD, EMAIL, TELEPHONE, false);

        // 3. Postcondiciones - assertions (todas las que sean necesarias)
        Assert.assertNotNull(user);
        Assert.assertEquals(USERNAME, user.getUsername());
        Assert.assertEquals(NAME, user.getName());
        Assert.assertEquals(SURNAME, user.getSurname());
        Assert.assertEquals(EMAIL, user.getEmail());
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));
    }

    @Test
    public void testChangeUsername() {
        // 1. Precondiciones
        jdbcTemplate.execute(String.format("INSERT INTO users (userid,username, password, name, surname, email, telephone, isprovider) VALUES (%d,'%s','%s','%s','%s','%s','%s',false)",USERID,USERNAME, PASSWORD, NAME, SURNAME, EMAIL, TELEPHONE));

        // 2. Ejecuta la class under test (una sola)
        userDao.changeUsername(USERID,"newUsername");

        // 3. Postcondiciones - assertions (todas las que sean necesarias)
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "users", "username = 'newUsername'"));
    }
}
