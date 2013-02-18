package org.antbear.tododont.web.beans.security.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Simple password complexity constraint.
 * <p/>
 * http://stackoverflow.com/questions/3721843/here-is-a-regular-expression-for-password-complexity-can-someone-show-me-how-to
 * <p/>
 * (?=^.{10,}$)(?=.*[a-z])(?=.*[A-Z])(?=.*[\W_])(?=^.*[^\s].*$).*$
 * ^          ^          ^          ^            ^
 * |          |          |          |            L--does not contain a whitespace
 * |          |          |          L--at least one non word character(a-zA-Z0-9_) or _ or 0-9
 * |          |          L--at least one upper case letter
 * |          L--at least one lowercase Letter
 * L--Number of charaters
 */
@NotNull
@Size(min = 10, max = 64, message = "{org.antbear.security.password.size}")
@Pattern.List({
        @Pattern(regexp = "(?=.*[a-z]).*$",      message = "{org.antbear.security.password.lowerCase}"),
        @Pattern(regexp = "(?=.*[A-Z]).*$",      message = "{org.antbear.security.password.upperCase}"),
        @Pattern(regexp = "(?=.*[\\W_]).*$",     message = "{org.antbear.security.password.nonWord}"),
        @Pattern(regexp = "(?=^.*[^\\s].*$).*$", message = "{org.antbear.security.password.whitespace}")
})
@Constraint(validatedBy = {})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
public @interface Password {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
