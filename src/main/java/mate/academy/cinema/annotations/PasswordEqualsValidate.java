package mate.academy.cinema.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.Payload;

import mate.academy.cinema.security.validations.PasswordEqualsValidater;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordEqualsValidater.class)
public @interface PasswordEqualsValidate {
    String message() default "Passwords are not equal";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
