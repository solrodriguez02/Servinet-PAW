package ar.edu.itba.paw.webapp.validation;

import ar.edu.itba.paw.model.Neighbourhoods;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {LocationFormattedValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface LocationFormatted {

    String message() default "Si ingresó una entrega a domicilio, debe seleccionar al menos un barrio. Si tiene un domicilio particular aclare SOLO en que barrio se encuentra";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        LocationFormatted[] locations();
    }

}
