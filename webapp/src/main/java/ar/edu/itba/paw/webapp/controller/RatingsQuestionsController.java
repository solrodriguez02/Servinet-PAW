package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.services.*;
import ar.edu.itba.paw.webapp.form.QuestionForm;
import ar.edu.itba.paw.webapp.form.ReviewsForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;

@Controller
public class RatingsQuestionsController {
    private RatingService rating;
    private QuestionService question;
    private HelloWorldController helloWorldController;

    @Autowired
    public RatingsQuestionsController(
            @Qualifier("HelloWorldController") HelloWorldController helloWorldController,
            @Qualifier("QuestionServiceImpl") final QuestionService question,
            @Qualifier("RatingServiceImpl") final RatingServiceImpl rating
    ){
        this.helloWorldController = helloWorldController;
        this.rating = rating;
        this.question = question;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/preguntar/{serviceId:\\d+}")
    public ModelAndView addQuestion(
            @Valid @ModelAttribute("questionForm") QuestionForm form, final BindingResult errors,
            @PathVariable("serviceId") final long serviceId
    ){
        if(errors.hasErrors()) {
            return helloWorldController.service(serviceId, form, null, "qst", 0, 0);
        }
        question.create(serviceId, form.getUserId(), form.getQuestion());
        return new ModelAndView("redirect:/servicio/" + serviceId + "/?opcion=qst");
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
            @Valid @ModelAttribute("reviewForm") ReviewsForm form, final BindingResult errors,
            @PathVariable("serviceId") final long serviceId
    ){
        if(errors.hasErrors()) {
            return helloWorldController.service(serviceId, null, form, "rw", 0, 0);
        }
        rating.create(serviceId, form.getQuestionUserId(), form.getRating(), form.getComment());
        return new ModelAndView("redirect:/servicio/" + serviceId + "/?opcion=rw");
    }

}
