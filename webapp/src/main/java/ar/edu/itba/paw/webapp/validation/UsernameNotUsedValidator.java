package ar.edu.itba.paw.webapp.validation;

import ar.edu.itba.paw.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameNotUsedValidator implements ConstraintValidator<UsernameNotUsed, String> {
    @Autowired
    private UserService userService;
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && !value.isEmpty() && !userService.findByUsername(value).isPresent();
    }
}
