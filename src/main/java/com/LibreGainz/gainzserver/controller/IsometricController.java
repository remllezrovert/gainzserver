package com.LibreGainz.gainzserver.controller;

import com.LibreGainz.gainzserver.repo.*;
import com.LibreGainz.gainzserver.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//import org.apache.catalina.core.ApplicationContext;

import org.springframework.context.ApplicationContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.*;





@RestController  
public class IsometricController {
@Autowired
    private final WorkoutRepo workoutRepo;
    private final IsometricRepo isometricRepo;
     

    public IsometricController(
        WorkoutRepo workoutRepo,
        IsometricRepo isometricRepo
        ) {
        this.workoutRepo = workoutRepo;
        this.isometricRepo = isometricRepo;
    }

 

    @GetMapping("/isometric")
    public List<Isometric> getisometric(){

        List<Isometric> wList= new ArrayList<>();
        wList.addAll(isometricRepo.findAll());
        return wList;
    }

    @GetMapping("/{userId}/isometric")
    public List<Isometric> getUserIsometric(@PathVariable Integer userId){
        int limit = 10;
        List<Isometric> wList= new ArrayList<>();
        wList.addAll(isometricRepo.findAll(userId, limit));
        return wList;
    }





}

