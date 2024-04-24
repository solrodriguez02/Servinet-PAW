package ar.edu.itba.paw.webapp.validation;

import ar.edu.itba.paw.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class EmailRegisteredValidator implements ConstraintValidator<EmailRegistered, String> {
    @Autowired
    private UserService userService;
    private Pattern pattern;

    @Override
    public void initialize(EmailRegistered constraintAnnotation) {
        this.pattern = Pattern.compile(constraintAnnotation.regex());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && !value.isEmpty() && pattern.matcher(value).matches() && userService.findByEmail(value).isPresent();
    }
}
