package com.LibreGainz.gainzserver.controller;

import org.springframework.web.bind.annotation.RestController;

import com.LibreGainz.gainzserver.repo.*;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.*;





@RestController  
public class WorkoutController{

@Autowired
    private final WorkoutRepo workoutRepo;
     
    private final ApplicationContext applicatonContext;

    public WorkoutController(
        WorkoutRepo workoutRepo,
        ApplicationContext applicationContext) {
        this.workoutRepo = workoutRepo;
        this.applicatonContext = applicationContext;
    }

   
    @GetMapping("/workout")
    public List<Object> getAllWorkouts() {
        List<Object> allWorkouts = new ArrayList<>();
        allWorkouts.addAll(workoutRepo.findAll());
        return allWorkouts;
    }




    @GetMapping("/workout/{workoutId}")
    public List<Workout> getWorkout(@PathVariable Long workoutId)
    {
        List<Workout> wList= new ArrayList<>();
        wList.addAll(workoutRepo.find(workoutId));
        return wList;
    }


     @DeleteMapping("/workout/{id}")
    public boolean deleteWorkout(@PathVariable Integer id){
        return workoutRepo.delete(id);
    }

      
 
    @DeleteMapping("/{userId}/workout/{id}")
    public boolean deleteUserWorkout(@PathVariable Integer userId, @PathVariable Integer id){
        return workoutRepo.delete(userId, id);
    }

            
    @PatchMapping("/workout/{workoutId}")
    public boolean patchWorkout(@RequestBody String entity, @PathVariable Long workoutId){
    try {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Workout> list = objectMapper.readValue(entity, objectMapper.getTypeFactory().constructCollectionType(List.class, Workout.class));
        list.forEach((workout) -> {
            int userId = workout.getUserId();
            workoutRepo.update(userId, workout);
        });
        return true;
        
        } catch (Exception e) {
        e.printStackTrace();
        return false;
        }
    }


            

@RequestMapping(value = "/helloworld", method=RequestMethod.GET)
public String helloWorld() {
    return "HelloWorld";
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
