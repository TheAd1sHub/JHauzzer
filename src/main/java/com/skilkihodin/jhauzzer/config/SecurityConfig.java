package com.skilkihodin.jhauzzer.config;

import com.skilkihodin.jhauzzer.service.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.function.Consumer;

import static org.springframework.security.config.Customizer.*;

//@Configuration
//@EnableWebSecurity
public class SecurityConfig {

    private ApplicationContext container;

    private PasswordEncoder cachedPasswordEncoder;

    @Autowired
    private AccountsService accountsService;


    @Bean
    public UserDetailsService userDetailsService() {
        return accountsService;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("Security filter chain received");

        return http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                //.requestMatchers("/", "/api/v1/accounts/new").permitAll()
                .requestMatchers("/*").permitAll()
                //.anyRequest().permitAll()//.authenticated()
            ).formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
            .authenticationManager(new ProviderManager(authenticationProvider()))
            .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        if (cachedPasswordEncoder == null) {
            cachedPasswordEncoder = new BCryptPasswordEncoder();
        }

        return cachedPasswordEncoder;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        var provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }
}
