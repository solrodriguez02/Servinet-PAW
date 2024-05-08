package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.Question;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service("QuestionServiceImpl")
public class QuestionServiceImpl implements  QuestionService {

    private final QuestionDao questionDao;
    private final EmailService emailService;
    private final UserService userService;

    @Autowired
    public QuestionServiceImpl(final QuestionDao questionDao, final EmailService emailService,
                               final UserService userService) {
        this.questionDao = questionDao;
        this.emailService = emailService;
        this.userService = userService;
    }

    @Override
    public List<Question> getAllQuestions(long serviceid, int page) {
        List<Question> questions;
        questions = questionDao.getAllQuestions(serviceid, page);
        return questions;
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
        Question question = questionDao.findById(id).get();
        User user = userService.findById(question.getUserid()).get();
        // todo
        /*
        try {
            emailService.answeredQuestion(user, response);

        } catch (MessagingException e ){
            System.err.println(e.getMessage());
        }
         */
    }

    @Override
    public int getQuestionsCount(long serviceid) {
        return questionDao.getQuestionsCount(serviceid);
    }

    @Override
    public Map<Question, String> getQuestionsToRespond(long userid) {
        Map<Question, String> questions;
        if(questionDao.getQuestionsToRespond(userid).isPresent()) {
            questions = questionDao.getQuestionsToRespond(userid).get();
            if(questions.isEmpty()) questions = null;
        } else questions = null;
        return questions;
    }

}
