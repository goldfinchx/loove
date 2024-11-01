package net.loove.auth.services;

import lombok.RequiredArgsConstructor;
import net.loove.auth.AuthUsersRepository;
import net.loove.auth.model.AuthUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsManager {

    private final AuthUsersRepository repository;
    private final PasswordEncoder passwordEncoder;

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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.repository
            .findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
