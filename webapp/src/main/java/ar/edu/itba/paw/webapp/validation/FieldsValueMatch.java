package ar.edu.itba.paw.webapp.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FieldsValueMatchValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldsValueMatch {

        String message() default "{ar.edu.itba.paw.webapp.validation.FieldsValueMatch.message}";

        String field();

        String fieldMatch();

        Class<?>[] groups() default { };

        Class<? extends Payload>[] payload() default { };

        @Target({ ElementType.TYPE })
        @Retention(RetentionPolicy.RUNTIME)
        @interface List {
                FieldsValueMatch[] value();
        }
}