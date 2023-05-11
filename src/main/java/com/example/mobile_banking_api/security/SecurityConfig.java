package com.example.mobile_banking_api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //Define in-memory user
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        InMemoryUserDetailsManager userDetailsManager=new InMemoryUserDetailsManager();
        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}0809")
                .roles("ADMIN")
                .build();
        UserDetails goldUser = User.builder()
                .username("goldUser")
                .password("{noop}0809")
                .roles("ACCOUNT")
                .build();
        UserDetails user = User.builder()
                .username("user")
                .password("{noop}0809")
                .roles("USER")
                .build();
        userDetailsManager.createUser(admin);
        userDetailsManager.createUser(goldUser);
        userDetailsManager.createUser(user);
        return userDetailsManager;

    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(token->token.disable());

        //Security mechanism
        http.httpBasic();

        //Authorize URL mapping
        http.authorizeHttpRequests(request->{
            request.requestMatchers("/api/v1/users/**")
                    .hasRole("ADMIN");
            request.requestMatchers("/api/v1/account-type/**","/api/v1/files/**")
                    .hasAnyRole("ACCOUNT","USER");
            request.anyRequest().permitAll();
                });

        //Make security http STATELESS
        http.sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }

}
