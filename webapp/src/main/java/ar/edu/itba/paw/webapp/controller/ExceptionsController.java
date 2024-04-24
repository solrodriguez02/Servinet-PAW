package ar.edu.itba.paw.webapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ar.edu.itba.paw.model.exceptions.InvalidOperationException;
@ControllerAdvice
public class ExceptionsController {

    @ExceptionHandler(InvalidOperationException.class)
    public ModelAndView invalidOperation(InvalidOperationException ex){
        return new ModelAndView("redirect:/operacion-invalida/?argumento="+ ex.getArgument());
    }

    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    public ModelAndView badRequest(){
        return new ModelAndView("/errors/pageNotFound");
    }
}
