package net.loove.auth.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import net.loove.auth.parameters.OAuthContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/oauth")
public class OAuthController {

    @PostMapping("/login/google")
    public void loginWithGoogle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final OAuthContext context = OAuthContext.SIGNIN;
        request.getSession().setAttribute("context", context);
        response.sendRedirect("/oauth2/authorization/google?context=" + context);
    }

    @PostMapping("/registration/google")
    public void signupWithGoogle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final OAuthContext context = OAuthContext.SIGNUP;
        request.getSession().setAttribute("context", context);
        response.sendRedirect("/oauth2/authorization/google?context=" + context);
    }

}
