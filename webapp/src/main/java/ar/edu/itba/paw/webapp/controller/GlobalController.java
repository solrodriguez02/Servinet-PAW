package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Categories;
import ar.edu.itba.paw.model.Neighbourhoods;
import ar.edu.itba.paw.model.PricingTypes;
import ar.edu.itba.paw.model.Ratings;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalController {
    @RequestMapping(method = RequestMethod.GET, path = "/operacion-invalida")
    public ModelAndView invalidOperation(@RequestParam(value = "argumento") final String argument) {
        final ModelAndView mav = new ModelAndView("invalidOperation");
        mav.addObject("argument",argument);
        return mav;
    }

    @ModelAttribute("categories")
    public Categories[] categories() {
        return Categories.values();
    }
    @ModelAttribute("neighbourhoods")
    public Neighbourhoods[] neighbourhoods() {
        return Neighbourhoods.values();
    }

    @ModelAttribute("pricingTypes")
    public PricingTypes[] pricingTypes() {
        return PricingTypes.values();
    }
    @ModelAttribute("ratings")
    public Ratings[] ratings() {
        return Ratings.values();
    }

}

