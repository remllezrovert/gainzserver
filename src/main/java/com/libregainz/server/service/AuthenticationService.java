package com.libregainz.server.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.libregainz.server.dto.LoginClientDto;
import com.libregainz.server.dto.RegisterClientDto;
import com.libregainz.server.dto.VerifyClientDto;
import com.libregainz.server.model.Client;
import com.libregainz.server.repo.ClientRepo;

import jakarta.mail.MessagingException;

@Service
public class AuthenticationService {

    private final ClientRepo clientRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    public AuthenticationService(
        ClientRepo clientRepo,
        PasswordEncoder passwordEncoder,
        AuthenticationManager authenticationManager,
        EmailService emailService
    ){
        this.clientRepo = clientRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.emailService = emailService;
    }

    public Client signup(RegisterClientDto input){
        Client client = new Client();
        client.setUsername(input.getUsername());
        client.setEmail(input.getEmail());
        client.setPassword(passwordEncoder.encode(input.getPassword()));
        client.setVerificationCode((generateVerificationCode()));
        client.setVerificationExpire(LocalDateTime.now().plusMinutes(15));
        client.setEnabled(false);
        sendVerificationEmail(client);
        clientRepo.save(client);
        return client;
    }

    public Client authenticate(LoginClientDto input){
        Client client;
        try{
        client = clientRepo.findByEmail(input.getEmail());
        } catch (Exception e){
        throw new RuntimeException("Client not found");
        }
        if (!client.isEnabled()){
            throw new RuntimeException("account not yet verified. Please verify account");
        }
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                input.getEmail(),
                input.getPassword()
                )
        );
        return client;
    }

    public void verifyClient(VerifyClientDto input){
        Optional<Client> optionalClient = Optional.ofNullable(clientRepo.findByEmail(input.getEmail()));
        if (optionalClient.isPresent()){
            Client client = optionalClient.get();
            if(client.getVerificationExpire().isBefore(LocalDateTime.now())){
                throw new RuntimeException("Verification code has expired");
            }
            if (client.getVerificationCode().equals(input.getVerificationCode())){
                client.setEnabled(true);
                client.setVerificationCode(null);
                client.setVerificationExpire(null);
                clientRepo.save(client);

            } else {
               throw new RuntimeException("Invalid verification code");
            }
        } else {
            throw new RuntimeException("Client not found");
        }
    }


        public void resendVerificationCode(String email) {
        Optional<Client> optionalClient = Optional.ofNullable(clientRepo.findByEmail(email));
        if (optionalClient.isPresent()) {
            Client user = optionalClient.get();
            if (user.isEnabled()) {
                throw new RuntimeException("Account is already verified");
            }
            user.setVerificationCode(generateVerificationCode());
            user.setVerificationExpire(LocalDateTime.now().plusHours(1));
            sendVerificationEmail(user);
            clientRepo.save(user);
        } else {
            throw new RuntimeException("Client not found");
        }
    }

    public void sendVerificationEmail(Client client){
        String subject = "Account verifiaton";
        String verificationCode = client.getVerificationCode();
        String htmlMessage = "<html>"
        + "<body style=\"font-family: Arial, sans-serif;\">"
        + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
        + "<h2 style=\"color: #333;\">Welcome to our app!</h2>"
        + "<p style=\"font-size: 16px;\">Please enter the verification code below to continue:</p>"
        + "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
        + "<h3 style=\"color: #333;\">Verification Code:</h3>"
        + "<p style=\"font-size: 18px; font-weight: bold; color: #007bff;\">" + verificationCode + "</p>"
        + "</div>"
        + "</div>"
        + "</body>"
        + "</html>";

        try {
            emailService.sendVerificationEmail(client.getEmail(), subject, htmlMessage);
        } catch (MessagingException e) {
            // Handle email sending exception
            e.printStackTrace();
        }
    }

      private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }

}



