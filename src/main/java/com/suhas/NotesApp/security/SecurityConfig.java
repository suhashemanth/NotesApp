package com.suhas.NotesApp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtFilter jwtFilter;
    @Autowired
    private ApplicationContext context;

    @Bean
    public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
       return httpSecurity.
        csrf(AbstractHttpConfigurer::disable).
        authorizeHttpRequests(req-> req.
                requestMatchers("/api/v1/user/register").
                hasAuthority("ADMIN").
                requestMatchers("/api/v1/user/login").
                permitAll().
                anyRequest().authenticated()).
               httpBasic(Customizer.withDefaults()).
               sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).
               addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class).
        build();
    }


    //When server recieves user details, it is still a unauthenticated object
    //In order to convert unauthenticated to authenticated
    @Bean
    public AuthenticationProvider getAuthenticated(BCryptPasswordEncoder encoder)
    {

       DaoAuthenticationProvider provider= new DaoAuthenticationProvider();
       provider.setPasswordEncoder(encoder);
       provider.setUserDetailsService(context.getBean(CustomUserDetailsService.class));
       return provider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
    //AuthenticationManager talks to AuthenticationProvider

    @Bean
    public AuthenticationManager authenticate(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }



}
