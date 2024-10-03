package com.libregainz.server.config;

import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
@Configuration
@EnableWebSecurity
public class SecurityConfig {



    @Value("${security.jwt.secret-key}")
    private String jwtKey;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    public SecurityConfig(
        JwtAuthenticationFilter jwtAuthenticationFilter,
        AuthenticationProvider authenticationProvider
    ){
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @SuppressWarnings("removal")
    @Bean
    public SecurityFilterChain jwtSecurityFilterChain(HttpSecurity http)throws Exception
    {
        http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(authorize -> {
            try {
                authorize
                    .requestMatchers("/auth/**").permitAll()
                    .requestMatchers(HttpMethod.OPTIONS).permitAll()
                    .anyRequest()
                    .anonymous();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
            )
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        //.addFilterBefore(jwtAuthenticationFilter, AnonymousAuthenticationFilter.class)
        //.anonymous();
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("https://remllez.com","http://localhost:8081","*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH","OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

/*
    @Bean
    SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception{
        return http
        .securityMatcher("/api/**")
        .authorizeHttpRequests( auth ->{
            auth.anyRequest().authenticated();
        }) 
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .httpBasic(withDefaults()) //replace this later with Oauth2 resource server and json web token
        .build();
    }


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/").permitAll();
            auth.requestMatchers("/error").permitAll();
            auth.anyRequest().authenticated();
        })
        .csrf(AbstractHttpConfigurer::disable)
        .httpBasic(Customizer.withDefaults())
        .oauth2Login(withDefaults())
        .formLogin(withDefaults()) 
        .build();
    }

    */




}
