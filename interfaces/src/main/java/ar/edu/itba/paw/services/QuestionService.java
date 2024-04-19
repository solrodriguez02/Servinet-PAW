package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionService {
    List<Question> getAllQuestions(long serviceid);
    Optional<Question> findById(long id);
    Question create(long serviceid, long userid, String question);
    void addResponse(long id, String response);
}
