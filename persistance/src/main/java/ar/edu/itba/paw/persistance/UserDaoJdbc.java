package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.services.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.util.*;

@Repository
public class UserDaoJdbc implements UserDao {

    private static final RowMapper<User> ROW_MAPPER = (rs, rowNum) -> new User(rs.getInt("userid") ,
            rs.getString("username"), rs.getString("name"), rs.getString("surname"), rs.getString("email"),
            rs.getString("telephone"), rs.getBoolean("isprovider"));

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public UserDaoJdbc(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        simpleJdbcInsert = new SimpleJdbcInsert(ds).usingGeneratedKeyColumns("userid")
                .withTableName("users");
    }

    @Override
    public Optional<User> findById(long id) {
        // nunca se debe concatenar parametros en una query
        // "SELECT * from users WHERE userID " + id esta mal
        final List<User> list = jdbcTemplate.query("SELECT * from users WHERE userid = ?", new Object[] {id}, ROW_MAPPER);
        return list.stream().findFirst();
    }

    @Override
    public User create(final String username,final String name, final String surname, final String email, final String telephone, final Boolean isProvider) {
        final Map<String, Object> userData = new HashMap<>();
        userData.put("username", username);
        final Number generatedId = simpleJdbcInsert.executeAndReturnKey(userData);
        return new User(generatedId.longValue(), username , name, surname, email, telephone, isProvider);
    }



    /*
    @Override
    public User create(final String username) {
        return new User(1, "username");
    }


    @Override
    public Optional<User> findById(long id) {
        // nunca se debe concatenar parametros en una query
        // "SELECT * from users WHERE userID " + id esta mal
        User u = new User(1, "sol");
        return Optional.of(u);
    }

     */

}
