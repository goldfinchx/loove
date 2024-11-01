package net.loove.auth.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AuthUser implements UserDetails, OidcUser {

    @Id
    @GeneratedValue
    private Long id;

    private String email;
    private String password;

    private String token;
    private Instant expiresAt = Instant.MAX;
    private Instant issuedAt = Instant.now();

    private String attributes;
    private String claims;

    public AuthUser(OAuth2User user) {
        this.email = user.getAttribute("email");
        this.attributes = user.getAttributes().toString();
        this.claims = user.getAttributes().toString();
        this.token = user.getAttribute("id_token");
    }

    @Override
    public String getName() {
        return this.email;
    }

    @Override
    public Map<String, Object> getAttributes() {
        final ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(this.attributes, Map.class);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public Map<String, Object> getClaims() {
        final ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(this.claims, Map.class);
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return OidcUserInfo.builder()
            .claims(currentClaims -> currentClaims.putAll(this.getClaims()))
            .email(this.email)
            .build();
    }

    @Override
    public OidcIdToken getIdToken() {
        return new OidcIdToken(this.token, this.issuedAt, this.expiresAt, this.getClaims());
    }
}
