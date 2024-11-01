package net.loove.auth.controllers;

import net.loove.auth.form.LoginForm;
import net.loove.auth.form.RegistrationForm;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthViewController {

    @GetMapping("/login")
    public String login(Model model) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }

        final LoginForm form = new LoginForm();
        model.addAttribute("form", form);
        return "login";
    }

    @GetMapping("/registration")
    public String register(Model model) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }

        final RegistrationForm form = new RegistrationForm();
        model.addAttribute("form", form);
        return "registration";
    }

}
