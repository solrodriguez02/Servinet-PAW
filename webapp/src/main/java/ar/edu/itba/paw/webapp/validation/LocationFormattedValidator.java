package ar.edu.itba.paw.webapp.validation;

import ar.edu.itba.paw.model.Neighbourhoods;
import ar.edu.itba.paw.webapp.form.ServiceForm;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class LocationFormattedValidator implements ConstraintValidator<LocationFormatted, ServiceForm> {
//    private Neighbourhoods[] neighbourhoods;
 //   private boolean hasHomeService;
    @Override
    public void initialize(LocationFormatted constraintAnnotation) {

    }
    @Override
    public boolean isValid(ServiceForm form, ConstraintValidatorContext context) {
        Neighbourhoods[] neighbourhoods = form.getNeighbourhood();
        boolean homeserv=form.getHomeserv();//Neighbourhoods[] neighbourhoods1 = (Neighbourhoods[]) new BeanWrapperImpl(value).getPropertyValue(neighbourhoods);
        return neighbourhoods!=null && ((homeserv && neighbourhoods.length > 0) || (!homeserv && neighbourhoods.length==1 ) );//Boolean hasHomeService1 = (Boolean) new BeanWrapperImpl(value).getPropertyValue(hasHomeService);

    }

}
