package com.Blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.config.Customizer;


import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class config   {

     @Bean
     public PasswordEncoder passwordEncoder()
     {
        return new BCryptPasswordEncoder();
     }
    
     @Bean
     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
        .csrf(csrf->
        csrf.disable())
        .headers(header->
        header.frameOptions(frame ->
        frame.disable()))
        .httpBasic(Customizer.withDefaults())
        .authorizeHttpRequests(authRequest ->
        authRequest
        .requestMatchers(new AntPathRequestMatcher("/doc/swagger-ui/**","GET")).permitAll()
        .requestMatchers(new AntPathRequestMatcher("/swagger-resources/**","GET")).permitAll()
        .requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**","GET")).permitAll()
        .anyRequest().permitAll()
        )
        .build();
        
     }

     
        //Pruebas
    //   @Bean
    // public UserDetailsService userDetailsService() {
    //     UserDetails user = User
    //         .withUsername("user")
    //         .password(passwordEncoder().encode("password"))
    //         .roles("USER")
    //         .build();

    //     UserDetails admin = User
    //         .withUsername("admin")
    //         .password(passwordEncoder().encode("adminPassword"))
    //         .roles("ADMINISTRADOR")
    //         .build();

    //     return new InMemoryUserDetailsManager(user, admin);
    // }
    //------------------------------------------//
}
