package net.loove.auth.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import net.loove.auth.AuthUsersRepository;
import net.loove.auth.form.RegistrationForm;
import net.loove.auth.model.AuthUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsManager {

    private final AuthUsersRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;

    @Override
    public void createUser(UserDetails user) {
        this.repository.save((AuthUser) user);
    }

    @Override
    public void updateUser(UserDetails user) {
        this.repository.save((AuthUser) user);
    }

    @Override
    public void deleteUser(String username) {
        this.repository.deleteByUsername(username);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        final AuthUser user = this.repository.findByPassword(oldPassword)
            .orElseThrow(() -> new UsernameNotFoundException("Invalid password"));

        user.setPassword(this.passwordEncoder.encode(newPassword));
        this.repository.save(user);

    }

    @Override
    public boolean userExists(String username) {
        return this.repository.findByEmail(username).isPresent();
    }

    @Override
    public AuthUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.repository
            .findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public void authenticateUser(AuthUser user, String password, HttpServletRequest request) {
        final UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, password);
        final WebAuthenticationDetails details = new WebAuthenticationDetails(request);
        authToken.setDetails(details);

        final Authentication authentication = this.authManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final HttpSession httpSession = request.getSession(true);
        httpSession.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
    }

    public AuthUser createUser(RegistrationForm form) {
        final String encodedPassword = this.passwordEncoder.encode(form.getPassword());
        final AuthUser user = new AuthUser(form.getEmail(), encodedPassword);
        // todo generate jwt token
        return this.repository.save(user);
    }
}
