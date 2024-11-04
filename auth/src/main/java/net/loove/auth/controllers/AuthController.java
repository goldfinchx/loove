package net.loove.auth.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.loove.auth.form.LoginForm;
import net.loove.auth.form.RegistrationForm;
import net.loove.auth.model.AuthUser;
import net.loove.auth.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(AuthService authService, PasswordEncoder passwordEncoder) {
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
    }



    @PostMapping("/login")
    public ResponseEntity handleLogin(@Validated @RequestBody LoginForm form) {
        final AuthUser foundUser = this.authService.loadUserByUsername(form.getEmail());
        if (foundUser == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        final boolean matches = this.passwordEncoder.matches(form.getPassword(), foundUser.getPassword());
        if (!matches) {
            return ResponseEntity.badRequest().body("Bad credentials");
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/registration")
    public ResponseEntity handleRegister(@Validated @RequestBody RegistrationForm form, HttpServletRequest request) {
        if (this.authService.userExists(form.getEmail())) {
            return ResponseEntity.badRequest().body("User with this email already exists");
        }

        final AuthUser user = this.authService.createUser(form);
        this.authService.authenticateUser(user, form.getPassword(), request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request, HttpServletResponse response) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            final SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
            logoutHandler.logout(request, response, auth);
        }

        return ResponseEntity.ok().build();
    }

}
