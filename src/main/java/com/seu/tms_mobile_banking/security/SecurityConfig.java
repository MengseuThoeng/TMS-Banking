package com.seu.tms_mobile_banking.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import static org.springframework.security.oauth2.core.authorization.OAuth2AuthorizationManagers.hasScope;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    //    @Bean
//    InMemoryUserDetailsManager inMemoryUserDetailsManager(){
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        UserDetails userAdmin= User.builder()
//                .username("admin")
//                .password(passwordEncoder.encode("admin"))//{noop}
//                .roles("USER","ADMIN")
//                .build();
//        UserDetails userEditor= User.builder()
//                .username("seu")
//                .password(passwordEncoder.encode("seu"))
//                .roles("USER","EDITOR")
//                .build();
//        UserDetails userRead= User.builder()
//                .username("luffy")
//                .password(passwordEncoder.encode("luffy"))
//                .roles("USER","READ")
//                .build();
//        manager.createUser(userAdmin);
//        manager.createUser(userEditor);
//        manager.createUser(userRead);
//        return manager;
//    }
    @Bean
    JwtAuthenticationProvider jwtAuthenticationProvider(@Qualifier("refreshJwtDecoder") JwtDecoder refreshJwtDecoder) {
        return new JwtAuthenticationProvider(refreshJwtDecoder);
    }
    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/users/**").hasAuthority("SCOPE_user:write")
                        .requestMatchers(HttpMethod.GET, "/api/v1/users/**").hasAuthority("SCOPE_user:read")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/users/**").hasAuthority("SCOPE_user:write")

                        .requestMatchers(HttpMethod.POST, "/api/v1/accounts").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/accounts/**").hasAuthority("SCOPE_account:write")
                        .requestMatchers(HttpMethod.GET, "/api/v1/accounts/**").hasAuthority("SCOPE_account:read")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/accounts/**").hasAuthority("SCOPE_account:write")

                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable) // disable csrf
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); //Stateless
        httpSecurity.oauth2ResourceServer(
                jwt -> jwt.jwt(Customizer.withDefaults())
        );
        return httpSecurity.build();
    }

    @Primary
    @Bean
    //JWT ACCESS TOKEN
    KeyPair keyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException();
        }
    }
    @Primary
    @Bean
    RSAKey rsaKey(KeyPair keyPair){
        return new RSAKey.Builder(
                (RSAPublicKey) keyPair.getPublic())
                .privateKey(keyPair.getPrivate())
                .keyID((UUID.randomUUID().toString()))
                .build();
    }
    @Primary
    @Bean
    JWKSource<SecurityContext> jwkSource(RSAKey rsaKey){
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }
    @Primary
    @Bean
    //Create Token
    JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource){
        return new NimbusJwtEncoder(jwkSource);
    }

    @Primary
    @Bean
    JwtDecoder jwtDecoder(RSAKey rsaKey) throws JOSEException {
        return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
    }

    @Bean(name = "refreshKeyPair")//JWT REFRESH TOKEN
    KeyPair refreshKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException();
        }
    }
    @Bean(name = "refreshRsaKey")
    RSAKey refreshRsaKey(@Qualifier("refreshKeyPair") KeyPair keyPair){
        return new RSAKey.Builder(
                (RSAPublicKey) keyPair.getPublic())
                .privateKey(keyPair.getPrivate())
                .keyID((UUID.randomUUID().toString()))
                .build();
    }
    @Bean(name="refreshJwkSource")
    JWKSource<SecurityContext> refreshJwkSource(@Qualifier("refreshRsaKey") RSAKey rsaKey){
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }
    @Bean(name="refreshJwtEncoder")
        //Create Token
    JwtEncoder refreshJwtEncoder(@Qualifier("refreshJwkSource") JWKSource<SecurityContext> jwkSource){
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean(name = "refreshJwtDecoder")
    JwtDecoder refreshJwtDecoder(@Qualifier("refreshRsaKey") RSAKey rsaKey) throws JOSEException {
        return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
    }



}

