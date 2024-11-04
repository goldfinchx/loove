package net.loove.auth.form;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import lombok.Data;
import net.loove.auth.validation.confirmation.PasswordConfirmation;
import net.loove.auth.validation.password.Password;

@Data
@PasswordConfirmation
public class RegistrationForm {

    @Email
    private String email;

    @Password
    private String password;

    private String passwordConfirm;

    @AssertTrue
    private boolean ageConfirmation;

    @AssertTrue
    private boolean termsConfirmation;

}
