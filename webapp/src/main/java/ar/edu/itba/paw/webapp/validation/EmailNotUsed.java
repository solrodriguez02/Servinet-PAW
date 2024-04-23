package ar.edu.itba.paw.webapp.validation;



import org.hibernate.validator.internal.constraintvalidators.AbstractEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
    @Documented
    @Constraint(validatedBy = {EmailNotUsedValidator.class})
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)

public @interface EmailNotUsed {
   String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\\\.[A-Za-z0-9-]+)*(\\\\.[A-Za-z]{2,})$";

   String message() default "{ar.edu.itba.paw.webapp.validation.EmailNotUsedValidator.message}";

   String regex() default EMAIL_REGEX;

   Class<?>[] groups() default {};

   Class<? extends Payload>[] payload() default {};

   @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
   @Retention(RUNTIME)
   @Documented
   @interface List {
    EmailNotUsed[] emails();
   }
}
