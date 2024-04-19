package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.model.Question;
import ar.edu.itba.paw.services.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.util.*;

@Repository
public class QuestionDaoJdbc implements QuestionDao {

    private static final RowMapper<Question> ROW_MAPPER = (rs, rowNum) -> new Question(rs.getInt("questionid") ,
            rs.getInt("serviceid"),rs.getInt("userid"), rs.getString("question"),
            rs.getString("response"), rs.getDate("date").toLocalDate());

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public QuestionDaoJdbc(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        simpleJdbcInsert = new SimpleJdbcInsert(ds).usingGeneratedKeyColumns("questionid")
                .withTableName("questions");
    }

    @Override
    public Optional<List<Question>> getAllQuestions(long serviceid) {
        final List<Question> list = jdbcTemplate.query("SELECT * from questions WHERE serviceid = ?", new Object[] {serviceid}, ROW_MAPPER);
        return Optional.of(list);
    }

    @Override
    public Optional<Question> findById(long id) {
        final List<Question> list = jdbcTemplate.query("SELECT * from questions WHERE questionid = ?", new Object[] {id}, ROW_MAPPER);
        return list.stream().findFirst();
    }

    @Override
    public Question create(long serviceid, long userid, String question) {
        final Map<String, Object> questionData = new HashMap<>();
        questionData.put("serviceid", serviceid);
        questionData.put("userid", userid);
        questionData.put("question", question);
        questionData.put("response", null);
        questionData.put("date", new Date());
        final Number generatedId = simpleJdbcInsert.executeAndReturnKey(questionData);
        return new Question(generatedId.longValue(), serviceid, userid, question, null, null);
    }

    @Override
    public void addResponse(long id, String response) {
        jdbcTemplate.update("UPDATE questions SET response = ? WHERE questionid = ?", response, id);
    }

}
