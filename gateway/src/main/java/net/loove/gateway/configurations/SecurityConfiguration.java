package net.loove.gateway.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity security) throws Exception {
        return security
            .authorizeExchange(exchanges -> exchanges
                .pathMatchers("/eureka/**", "/css/**", "/js/**", "/images/**", "/api/**", "/logout", "/registration/**", "/registration", "/login/**", "/login", "/oauth2/**").permitAll()
                .anyExchange().authenticated()
            )
            .oauth2ResourceServer(customizer -> customizer.jwt(Customizer.withDefaults()))
            .build();
    }


}
