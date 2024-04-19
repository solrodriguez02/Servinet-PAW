package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;

@Controller
public class RatingsQuestionsController {
    private RatingService rating;
    private QuestionService question;

    @Autowired
    public RatingsQuestionsController(
            @Qualifier("QuestionServiceImpl") final QuestionService question,
            @Qualifier("RatingServiceImpl") final RatingServiceImpl rating
    ){
        this.rating = rating;
        this.question = question;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/preguntar/{serviceId:\\d+}")
    public ModelAndView addQuestion(
            @RequestParam(value = "usuario") final long userid,
            @RequestParam(value = "pregunta") final String qst,
            @PathVariable("serviceId") final long serviceId
    ){
        question.create(serviceId, userid, qst);
        return new ModelAndView("redirect:/servicio/" + serviceId);
    }


    @RequestMapping(method = RequestMethod.POST, path = "/responder/{questionId:\\d+}")
    public ModelAndView addResponse(
            @RequestParam(value = "respuesta") final String response,
            @PathVariable("questionId") final long questionId
    ){
        question.addResponse(questionId, response);
        return new ModelAndView("redirect:/");
    }


    @RequestMapping(method = RequestMethod.POST, path = "/opinar/{serviceId:\\d+}")
    public ModelAndView addReview(
            @RequestParam(value = "usuario") final long userid,
            @RequestParam(value = "estrellas") final int rate,
            @RequestParam(value = "comentario") final String comment,
            @PathVariable("serviceId") final long serviceId
    ){
        rating.create(serviceId, userid, rate, comment);
        return new ModelAndView("redirect:/servicio/" + serviceId);
    }

}
