package com.LibreGainz.gainzserver.controller;
import org.springframework.web.bind.annotation.RestController;

import com.LibreGainz.gainzserver.model.User;
import com.LibreGainz.gainzserver.repo.UserRepo;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController

@RequestMapping()

public class UserController {
    private UserRepo userRepo;
    public UserController(UserRepo userRepo){
        this.userRepo = userRepo;
    }

 @DeleteMapping("/user/{userId}/")
    public boolean deleteUser(@PathVariable Integer userId){
        return userRepo.delete(userId);
    }


//newUser post request returns userId of a brand new user
@PostMapping("/user")
public int postNew(@RequestBody String entity){
    try {
    User user = new ObjectMapper().readValue(entity, User.class);
    userRepo.save(user);
    return user.getId();
    }catch(JsonProcessingException jpe){
        System.out.println("Json Parse Exception in UserController");
        return -1;
    }





}







}
