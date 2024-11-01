package net.loove.auth.validation.confirmation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PasswordConfirmationValidator.class)
public @interface PasswordConfirmation {

    String message() default "Passwords do not match!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}