package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.Question;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface QuestionDao {
    Optional<List<Question>> getAllQuestions(long serviceid, int page);
    Optional<Question> findById(long id);
    Question create(long serviceid, long userid, String question);
    void addResponse(long id, String response);
    int getQuestionsCount(long serviceid);
    Optional<Map<Question, String>> getQuestionsToRespond(long userid);
}
