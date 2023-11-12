package com.sergeb.luxgatewayjava.configs;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.OAuth2ResourceServerSpec;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final RsaKeyProperties rsaKeys;

    public SecurityConfig(RsaKeyProperties rsaKeys) {
        this.rsaKeys = rsaKeys;
    }

    // Create a user
    // TODO - Production - refactor the magic strings
    @Bean
    public MapReactiveUserDetailsService userDetailsRepository() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user1")
                .password("password")
                .roles("READ")
                .build();
        return new MapReactiveUserDetailsService(user);
    }

    // Our custom security configs to meet the project REQ
    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())           // Disable cross-Site Request Forgery
                .authorizeExchange(exchanges -> exchanges
                    .anyExchange().authenticated()      // User must be authenticated for any REQ in this app
                )                                       // Enable Jwt-encoded bearer token support
                .oauth2ResourceServer(OAuth2ResourceServerSpec::jwt)
                .httpBasic(Customizer.withDefaults());  // Basic Auth by default
        return http.build();
    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder (){
        return NimbusReactiveJwtDecoder.withPublicKey(rsaKeys.publicKey()).build();
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(rsaKeys.publicKey()).privateKey(rsaKeys.privateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }
}
