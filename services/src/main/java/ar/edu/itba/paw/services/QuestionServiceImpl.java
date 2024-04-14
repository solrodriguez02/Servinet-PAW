package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("QuestionServiceImpl")
public class QuestionServiceImpl implements  QuestionService {

    private final QuestionDao questionDao;

    @Autowired
    public QuestionServiceImpl(final QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public Optional<List<Question>> getAllQuestions(long serviceid) {
        return questionDao.getAllQuestions(serviceid);
    }

    @Override
    public Optional<Question> findById(long id) {
        return questionDao.findById(id);
    }

    @Override
    public Question create(long serviceid, long userid, String question) {
        return questionDao.create(serviceid, userid, question);
    }

    @Override
    public void addResponse(long id, String response) {
        questionDao.addResponse(id, response);
    }

}
