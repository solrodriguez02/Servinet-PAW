package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.BasicService;
import ar.edu.itba.paw.model.Business;
import ar.edu.itba.paw.model.Question;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exceptions.BusinessNotFoundException;
import ar.edu.itba.paw.model.exceptions.QuestionNotFoundException;
import ar.edu.itba.paw.model.exceptions.ServiceNotFoundException;
import ar.edu.itba.paw.model.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service("QuestionServiceImpl")
public class QuestionServiceImpl implements  QuestionService {

    private final QuestionDao questionDao;
    private final EmailService emailService;
    private final UserService userService;
    private final ServiceService serviceService;
    private final BusinessService businessService;
    private final Logger LOGGER = LoggerFactory.getLogger(QuestionServiceImpl.class);

    @Autowired
    public QuestionServiceImpl(final QuestionDao questionDao, final EmailService emailService,
                               final UserService userService, final ServiceService serviceService,
                               final BusinessService businessService) {
        this.questionDao = questionDao;
        this.emailService = emailService;
        this.userService = userService;
        this.serviceService = serviceService;
        this.businessService = businessService;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Question> getAllQuestions(long serviceid, int page) {
        List<Question> questions;
        questions = questionDao.getAllQuestions(serviceid, page);
        return questions;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Question> findById(long id) {
        return questionDao.findById(id);
    }

    @Transactional
    @Override
    public Question create(long serviceid, long userid, String questionString) {
        Question question = questionDao.create(serviceid, userid, questionString);
        BasicService service = serviceService.findBasicServiceById(serviceid).orElseThrow(ServiceNotFoundException::new);
        Business business = businessService.findById(service.getBusinessid()).orElseThrow(BusinessNotFoundException::new);
        User user = userService.findById(userid).orElseThrow(UserNotFoundException::new);
        try {
            emailService.askedQuestion( service, business.getEmail(), user, questionString, userService.getUserLocale(business.getUserId()));
        } catch (MessagingException e ){
            LOGGER.warn("Error sending email with question: " + e.getMessage());
        }
        return question;
    }

    @Transactional
    @Override
    public void addResponse(long id, String response) {
        questionDao.addResponse(id, response);
        Question question = questionDao.findById(id).orElseThrow(QuestionNotFoundException::new);
        BasicService service = serviceService.findBasicServiceById(question.getServiceid()).orElseThrow(ServiceNotFoundException::new);
        User user = userService.findById(question.getUserid()).orElseThrow(UserNotFoundException::new);

        try {
            emailService.answeredQuestion(service, user, question.getQuestion(), response );

        } catch (MessagingException e ){
            LOGGER.warn("Error sending email with response: " + e.getMessage());
        }

    }

    @Transactional(readOnly = true)
    @Override
    public int getQuestionsCount(long serviceid) {
        return questionDao.getQuestionsCount(serviceid);
    }

    @Transactional(readOnly = true)
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
