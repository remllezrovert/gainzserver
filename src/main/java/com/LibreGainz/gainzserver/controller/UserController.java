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

import java.sql.SQLException;
import java.util.List;

import org.postgresql.util.PSQLException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController

@RequestMapping()
//TODO: Add patch request to modify user
public class UserController {
    private UserRepo userRepo;
    public UserController(UserRepo userRepo){
        this.userRepo = userRepo;
    }

 @DeleteMapping("/user/{userId}")
    public boolean deleteUser(@PathVariable Integer userId){
        return userRepo.delete(userId);
    }
@GetMapping("/user/{userId}")
    public User getUser(@PathVariable Integer userId){
    List<User> list = userRepo.find(userId);
    return (list.size() > 0) ? list.get(0) : null;
    }


@GetMapping("/user")
    public User getUser(@RequestParam String name){
    try {
        User u = userRepo.find(name).get(0);
        return u;
    } catch(Exception e){
        return null;
    }
    }



//newUser post request returns userId of a brand new user

@PostMapping("/user")
public int postNew(@RequestBody String entity){
    try {
    User user = new ObjectMapper().readValue(entity, User.class);
    try{
    userRepo.save(user);
    } catch (DuplicateKeyException e) {
        user = userRepo.find(user.getName()).get(0);
        return user.getId();
    }
    user = userRepo.find(user.getName()).get(0);
    return user.getId();

    }catch(JsonProcessingException e){
        System.out.println("Exception in UserController");
        e.printStackTrace();
        return -1;
    }
}







}
