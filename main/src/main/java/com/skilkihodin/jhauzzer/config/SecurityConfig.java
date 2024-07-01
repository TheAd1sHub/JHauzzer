package com.skilkihodin.jhauzzer.config;

import com.skilkihodin.jhauzzer.security.Crc32HashGenerator;
import com.skilkihodin.jhauzzer.security.HashGenerator;
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
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.function.Consumer;

import static org.springframework.security.config.Customizer.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AccountsService accountsService;

    @Autowired
    public SecurityConfig(AccountsService accountsService) {
        this.accountsService = accountsService;
    }


    @Bean
    public UserDetailsService userDetailsService() {
        return accountsService;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers( "/", "/index.html", "api/v1/accounts/register"
                            //new AntPathRequestMatcher("/"),
                            //new AntPathRequestMatcher("/index.html"),
                            //new AntPathRequestMatcher("api/v1/accounts/register")
                    ).permitAll()
                    //.requestMatchers(
                    //        new AntPathRequestMatcher("api/v1/accounts/get/*")
                    //).authenticated()
                    //.requestMatchers(
                    //        new AntPathRequestMatcher("api/v1/products/get-all"),
                    //        new AntPathRequestMatcher("api/v1/products/get-like"),
                    //        new AntPathRequestMatcher("api/v1/products/get/*"),
                    //        new AntPathRequestMatcher("api/v1/products/buy/*")
                    //).hasRole("USER")
                    //.requestMatchers(
                    //        new AntPathRequestMatcher("api/v1/warehouses/register"),
                    //        new AntPathRequestMatcher("api/v1/warehouses/update-info"),
                    //        new AntPathRequestMatcher("api/v1/warehouses/find/*"),
                    //        new AntPathRequestMatcher("api/v1/products/get-like"),
                    //        new AntPathRequestMatcher("api/v1/products/register"),
                    //        new AntPathRequestMatcher("api/v1/products/remove"),
                    //        new AntPathRequestMatcher("api/v1/products/replenish/*")
                    //).hasRole("WAREHOUSE_ADMIN")
                    .anyRequest().hasRole("ADMIN")
            ).formLogin(AbstractAuthenticationFilterConfigurer::permitAll)

            .authenticationManager(new ProviderManager(authenticationProvider()))
            .build();
    }

    //@Bean
    //public PasswordEncoder passwordEncoder() {
    //    return new BCryptPasswordEncoder();
    //}

    @Bean
    public AuthenticationProvider authenticationProvider() {
        var provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(new PasswordEncoder() {
            private final HashGenerator hashGenerator = new Crc32HashGenerator();

            @Override
            public String encode(CharSequence rawPassword) {
                return hashGenerator.getHash(rawPassword.toString());
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encode(rawPassword).equals(encodedPassword);
            }
        });

        return provider;
    }
}
