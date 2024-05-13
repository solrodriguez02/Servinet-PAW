package ar.edu.itba.paw.webapp.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class FutureDateValidator implements ConstraintValidator<FutureDate,String> {

    @Override
    public boolean isValid(String dateTime, ConstraintValidatorContext context) {
        return dateTime !=null && LocalDateTime.parse(dateTime).isAfter(LocalDateTime.now());
    }
}
