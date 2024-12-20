package com.libregainz.server.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.libregainz.server.dto.LoginClientDto;
import com.libregainz.server.dto.RegisterClientDto;
import com.libregainz.server.dto.VerifyClientDto;
import com.libregainz.server.model.Client;
import com.libregainz.server.response.LoginResponse;
import com.libregainz.server.service.AuthenticationService;
import com.libregainz.server.service.JwtService;

@RestController

@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000") //new stuff allows for calling API with gainzserver running remote on localhost
public class AuthController {

        @PostMapping("/token")
        public String token(Authentication authentication){
            return "";
        }


          private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }
    @PostMapping("/signup")
    public ResponseEntity<Client> register(@RequestBody RegisterClientDto registerClientDto) {
        Client registeredClient = authenticationService.signup(registerClientDto);
        return ResponseEntity.ok(registeredClient);
    }

    //@PostMapping("/login")
    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")

    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginClientDto loginClientDto){
        Client authenticatedClient = authenticationService.authenticate(loginClientDto);
        String jwtToken = jwtService.generateToken(authenticatedClient);
        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());
        System.out.println(loginResponse.toString());
        //return ResponseEntity.ok(loginResponse);
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(loginResponse);
    }



    @PostMapping("/verify")
    public ResponseEntity<?> verifyClient(@RequestBody VerifyClientDto verifyClientDto) {
        try {
            authenticationService.verifyClient(verifyClientDto);
            return ResponseEntity.ok("Account verified successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/resend")
    public ResponseEntity<?> resendVerificationCode(@RequestParam String email) {
        try {
            authenticationService.resendVerificationCode(email);
            return ResponseEntity.ok("Verification code sent");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
