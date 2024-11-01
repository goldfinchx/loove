package net.loove.auth.validation.password;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;

public class PasswordConstraintsValidator implements ConstraintValidator<Password, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        final PasswordValidator passwordValidator = new PasswordValidator(
            new LengthRule(10, 64),
            new CharacterRule(EnglishCharacterData.UpperCase, 1),
            new CharacterRule(EnglishCharacterData.LowerCase, 1),
            new CharacterRule(EnglishCharacterData.Digit, 1)

        );

        final RuleResult result = passwordValidator.validate(new PasswordData(value));
        if (result.isValid()) {
            return true;
        }

        context.buildConstraintViolationWithTemplate(passwordValidator.getMessages(result).stream().findFirst().get())
            .addConstraintViolation()
            .disableDefaultConstraintViolation();

        return false;

    }

}
