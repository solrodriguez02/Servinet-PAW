package ar.edu.itba.paw.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GlobalController {
    @RequestMapping(method = RequestMethod.GET, path = "/operacion-invalida")
    public ModelAndView invalidOperation(@RequestParam(value = "argumento") final String argument) {
        final ModelAndView mav = new ModelAndView("invalidOperation");
        mav.addObject("argument",argument);
        return mav;
    }
}

