package com.seu.tms_mobile_banking.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{
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
    DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.POST, "/api/v1/users/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/users/**").hasAnyRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable) // disable csrf
                .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); //Stateless
        return httpSecurity.build();
    }
}
