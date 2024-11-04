package net.loove.auth.configurations;

import net.loove.auth.services.AuthService;
import net.loove.auth.services.OAuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationProvider;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AuthConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security, OAuth2UserService oAuthService, OAuth2AuthorizationRequestResolver oauthResolver) throws Exception {
        security
            .authorizeHttpRequests(authorizeRequests -> {
                authorizeRequests
                    .requestMatchers("/css/**", "/js/**", "/images/**", "/api/**", "/logout", "/registration/**", "/registration", "/login/**", "/login", "/oauth2/**").permitAll()
                    .anyRequest().authenticated();
            })
            .formLogin(configurer -> {
                configurer.loginPage("/login");
                configurer.usernameParameter("email");
                configurer.passwordParameter("password");
                configurer.defaultSuccessUrl("/home", true);
            })
            .oauth2Login(configurer -> {
                configurer.loginPage("/login");
                configurer.userInfoEndpoint(customizer -> customizer.userService(oAuthService));
                configurer.authorizationEndpoint(customizer -> customizer.authorizationRequestResolver(oauthResolver));
            })
            .logout(configurer -> {
                configurer.logoutUrl("/logout");
                configurer.logoutSuccessUrl("/login");
            });

        return security.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthService authService, OAuthService oAuthService, PasswordEncoder passwordEncoder) {
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(authService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        final OAuth2AccessTokenResponseClient accessTokenResponseClient = new DefaultAuthorizationCodeTokenResponseClient();
        final OAuth2LoginAuthenticationProvider oAuthLoginProvider = new OAuth2LoginAuthenticationProvider(accessTokenResponseClient, oAuthService);
        return new ProviderManager(authenticationProvider, oAuthLoginProvider);
    }

}
