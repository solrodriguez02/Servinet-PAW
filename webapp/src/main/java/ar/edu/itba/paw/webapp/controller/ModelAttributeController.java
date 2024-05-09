package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Categories;
import ar.edu.itba.paw.model.Neighbourhoods;
import ar.edu.itba.paw.model.PricingTypes;
import ar.edu.itba.paw.model.Ratings;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class ModelAttributeController {

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
