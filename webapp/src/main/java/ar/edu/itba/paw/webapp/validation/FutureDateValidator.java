package ar.edu.itba.paw.webapp.validation;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class FutureDateValidator implements ConstraintValidator<FutureDate,String> {


    @Override
    public boolean isValid(String dateTime, ConstraintValidatorContext context) {
        if (dateTime ==null || dateTime.isEmpty())
            return false;
        try {
            return LocalDateTime.parse(dateTime).isAfter(LocalDateTime.now());
        }
        catch (DateTimeParseException e) {
            return false;
        }
    }
}
