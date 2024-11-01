package net.loove.auth.services;

import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import net.loove.auth.AuthUsersRepository;
import net.loove.auth.exceptions.GoogleUserAlreadyRegistered;
import net.loove.auth.exceptions.GoogleUserNotRegistered;
import net.loove.auth.model.AuthUser;
import net.loove.auth.parameters.OAuthContext;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;

@Service
@RequiredArgsConstructor
public class OAuthService extends DefaultOAuth2UserService {

    private final AuthUsersRepository repository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        final OAuth2User loadedUser = super.loadUser(userRequest);
        final HttpSession session = (HttpSession) RequestContextHolder.currentRequestAttributes().getSessionMutex();
        final Optional<AuthUser> foundUser = this.repository.findByEmail(loadedUser.getAttribute("email"));
        final OAuthContext context = (OAuthContext) session.getAttribute("context");

        return switch (context) {
            case SIGNUP -> {
                if (foundUser.isPresent()) {
                    throw new GoogleUserAlreadyRegistered();
                }

                final AuthUser newUser = new AuthUser(loadedUser);
                yield this.repository.save(newUser);
            }
            case SIGNIN -> {
                if (foundUser.isEmpty()) {
                    throw new GoogleUserNotRegistered();
                }

                yield foundUser.get();
            }
        };
    }
}
