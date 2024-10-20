package com.libregainz.server.config;
import java.util.ArrayList;
import java.util.List;
import com.libregainz.server.model.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.libregainz.server.repo.ClientRepo;

@Configuration
public class ApplicationConfiguration {
 /**
     * Constructor for ApplicationConfiguration.
     *
     * @param clientRepo the repository for accessing client data from the database.
     */
    private final ClientRepo clientRepo;
    public ApplicationConfiguration(ClientRepo clientRepo){
        /**
     * Defines a UserDetailsService bean to retrieve user details from the client repository.
     * This service is used by Spring Security during authentication to load a user's details by their email.
     *
     * @return a UserDetailsService that fetches a client by their email.
     */
        this.clientRepo = clientRepo;
    }


    @Bean
    UserDetailsService userDetailsService(){
        /**
     * Defines a BCryptPasswordEncoder bean that provides password encryption and decryption using the BCrypt hashing algorithm.
     * This encoder is used by the authentication provider to securely store and verify passwords.
     *
     * @return an instance of BCryptPasswordEncoder.
     */
        return username -> clientRepo.findByEmail(username);
        }
    @Bean
    BCryptPasswordEncoder passwordEncoder(){
        /**
     * Defines an AuthenticationManager bean that manages the overall authentication process.
     * This bean is obtained from Spring's AuthenticationConfiguration and is used to authenticate user credentials.
     *
     * @param config the authentication configuration provided by Spring Security.
     * @return an instance of AuthenticationManager.
     * @throws Exception if there is an error obtaining the AuthenticationManager.
     */
        return new BCryptPasswordEncoder();
    } 
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }
    /**
     * Defines an AuthenticationProvider bean that is responsible for authenticating users.
     * This provider uses the DaoAuthenticationProvider, which checks user credentials using the UserDetailsService
     * and validates passwords using the BCryptPasswordEncoder.
     *
     * @return an instance of AuthenticationProvider (specifically DaoAuthenticationProvider).
     */
    @Bean
    AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
