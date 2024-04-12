package com.LibreGainz.gainzserver.controller;

import org.springframework.web.bind.annotation.RestController;

import com.LibreGainz.gainzserver.repo.*;
import com.LibreGainz.gainzserver.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
public class WorkoutController{

@Autowired
    private final WorkoutRepo workoutRepo;
    private final StrengthRepo strengthRepo;
    private final IsometricRepo isometricRepo;
    private final CardioRepo cardioRepo;
     
    private final ApplicationContext applicatonContext;

    public WorkoutController(
        WorkoutRepo workoutRepo,
        StrengthRepo strengthRepo,
        IsometricRepo isometricRepo,
        CardioRepo cardioRepo,
        ApplicationContext applicationContext) {
        this.workoutRepo = workoutRepo;
        this.strengthRepo = strengthRepo;
        this.isometricRepo= isometricRepo;
        this.cardioRepo = cardioRepo;
        this.applicatonContext = applicationContext;
    }

   
    @GetMapping("/workout")
    public List<Object> getAllWorkouts() {
        List<Object> allWorkouts = new ArrayList<>();
        allWorkouts.addAll(workoutRepo.findAll());
        return allWorkouts;
    }

           
 



            
//     @GetMapping("/isometric")
//    public List<Isometric> getIsometric(){
//
//        List<Isometric> wList= new ArrayList<>();
//        wList.addAll(isometricRepo.findAll());
//        return wList;
//    }
//   
//  @GetMapping("/{userId}/isometric")
//    public List<Isometric> getUserIsometric(@PathVariable Integer userId){
//        int limit = 10;
//        List<Isometric> wList= new ArrayList<>();
//        wList.addAll(isometricRepo.findAll(userId, limit));
//        return wList;
//    }
//       
//
//
//
//    @GetMapping("/cardio")
//    public List<Cardio> getCardio(){
//
//        List<Cardio> wList= new ArrayList<>();
//        wList.addAll(cardioRepo.findAll());
//        return wList;
//    }
//            
//   @GetMapping("/{userId}/cardio")
//    public List<Cardio> getUserCardio(@PathVariable Integer userId){
//        int limit = 10;
//        List<Cardio> wList= new ArrayList<>();
//        wList.addAll(cardioRepo.findAll(userId, limit));
//        return wList;
//    }
//            
            
            

@RequestMapping(value = "/helloworld", method=RequestMethod.GET)
public String helloWorld() {
    return "HelloWorld";
}






@PostMapping()
public String postMethodName(@RequestBody String entity) {
    return entity;
}

@DeleteMapping
public String deleteMethodName(@RequestBody String entity) {
    return entity;
}


@PutMapping()
public String putMethodName(@PathVariable String id, @RequestBody String entity) {

    return entity;
}



}
