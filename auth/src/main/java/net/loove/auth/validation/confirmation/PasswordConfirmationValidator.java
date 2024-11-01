package net.loove.auth.validation.confirmation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import net.loove.auth.form.RegistrationForm;

public class PasswordConfirmationValidator implements ConstraintValidator<PasswordConfirmation, RegistrationForm> {

    @Override
    public boolean isValid(RegistrationForm value, ConstraintValidatorContext context) {
        final boolean matches = value.getPassword().equals(value.getPasswordConfirm());
        if (!matches) {
            context.buildConstraintViolationWithTemplate("Passwords do not match!")
                .addPropertyNode("passwordConfirm")
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        }
        return matches;
    }
}
