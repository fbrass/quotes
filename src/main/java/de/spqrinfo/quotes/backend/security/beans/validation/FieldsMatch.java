package de.spqrinfo.quotes.backend.security.beans.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FieldsMatchValidator.class)
@Documented
public @interface FieldsMatch {

    String message() default "{de.spqrinfo.security.fieldsMatch}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String firstProperty();

    String secondProperty();
}
