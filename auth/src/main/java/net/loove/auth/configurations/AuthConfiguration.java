package net.loove.auth.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AuthConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
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

}
