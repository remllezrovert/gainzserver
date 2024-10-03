package com.libregainz.server.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Home, home";
    }

    @GetMapping("/secured")
    public String secured() {
        return "Hello, Secured!";
    }

    @GetMapping("/jwt")
    public String jwt(Authentication authentication){
       return "Hello, " + authentication.getName() ;
    }
    
    
}
