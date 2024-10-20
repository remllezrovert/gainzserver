package com.libregainz.server.config;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.libregainz.server.service.JwtService;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    /**
     * Constructor for JwtAuthenticationFilter.
     *
     * @param jwtService The service responsible for handling JWT operations like extraction and validation.
     * @param userDetailsService The service used to load user-specific data for authentication purposes.
     * @param handlerExceptionResolver The exception resolver to handle any exceptions thrown during filtering.
     */
    public JwtAuthenticationFilter(
        JwtService jwtService,
        UserDetailsService userDetailsService,
        @Qualifier("handlerExceptionResolver") HandlerExceptionResolver handlerExceptionResolver
    ){
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    /**
     * Filters incoming HTTP requests to validate JWT tokens.
     * If a valid token is found in the request's `Authorization` header, the corresponding user is authenticated
     * and their details are loaded into the SecurityContext.
     *
     * @param request The incoming HTTP request to filter.
     * @param response The HTTP response to be sent.
     * @param filterChain The chain of filters that this request goes through.
     * @throws ServletException if an error occurs during request handling.
     * @throws IOException if an I/O error occurs during request processing.
     */
    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain
    ) throws ServletException, java.io.IOException{

        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || ! authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

    try {
        final String jwt = authHeader.substring(7);
        final String userEmail = jwtService.extractUsername(jwt);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (userEmail != null && authentication == null){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            if (jwtService.isTokenValid(jwt,userDetails)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request,response);
    } catch (Exception exception){
        handlerExceptionResolver.resolveException(request, response, null, exception);
    }
    }
}
