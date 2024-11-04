package net.loove.auth.form;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class LoginForm {

    @Email
    private String email;
    private String password;

    private boolean rememberMe;

}
