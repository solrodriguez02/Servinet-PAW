package ar.edu.itba.paw.services;


import ar.edu.itba.paw.model.Categories;
import ar.edu.itba.paw.model.Neighbourhoods;
import ar.edu.itba.paw.model.PricingTypes;

import javax.mail.MessagingException;

public interface ManageServiceService {

    void deleteService(long serviceid);

    void createService(long businessid, String title, String description, boolean homeservice, Neighbourhoods neighbourhood, String location, Categories category, int minimalduration, PricingTypes pricingtype, String price, boolean additionalCharges);
}
