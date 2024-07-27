package com.libregainz.server.controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.libregainz.server.model.*;
import com.libregainz.server.repo.*;
//import org.apache.catalina.core.ApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers.IntegerDeserializer;




import org.springframework.context.ApplicationContext;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
public class ExerciseController {
private final ExerciseRepo exerciseRepo;
    public ExerciseController(
        ExerciseRepo exerciseRepo
    ) {
        this.exerciseRepo = exerciseRepo;
    }

 

    @GetMapping("/exercise")
    public List<Exercise> getExerciseAll(){
        List<Exercise> wList= new ArrayList<>();
        wList.addAll(exerciseRepo.findAll());
        return wList;
    }

 @GetMapping("/exercise/{exerciseId}")
    public List<Exercise> getExercise(@PathVariable Long exerciseId){
        List<Exercise> wList= new ArrayList<>();
        wList.addAll(exerciseRepo.find(exerciseId));
        return wList;
    }












    @GetMapping("/{clientId}/exercise")
    public List<Exercise> getUserexercise(@PathVariable Integer clientId){
        int limit = 10;
        List<Exercise> wList= new ArrayList<>();
        wList.addAll(exerciseRepo.findAll(clientId, limit));
        return wList;
    }



    @GetMapping("/{clientId}/exercise/date/template")
    public List<Exercise> getUserexercise(
        @PathVariable Integer clientId, 
        @RequestParam Integer templateId,
        @RequestParam Date startDate,
        @RequestParam Date endDate,
        @RequestParam Integer limit
    )
    {
        List<Exercise> wList= new ArrayList<>();
        wList.addAll(exerciseRepo.findAll(clientId, templateId, startDate, endDate, limit));
        return wList;
    }


@GetMapping("/{clientId}/exercise/date")
    public List<Exercise> getUserexercise(
        @PathVariable Integer clientId, 
        @RequestParam Date startDate,
        @RequestParam Date endDate,
        @RequestParam Integer limit
    )
    {
        List<Exercise> wList= new ArrayList<>();
        wList.addAll(exerciseRepo.findAll(clientId, startDate, endDate, limit));
        return wList;
    }














    @PostMapping("/{clientId}/exercise")
    public void postUserExercise(@RequestBody String entity, @PathVariable Integer clientId) {
         try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Exercise> list = objectMapper.readValue(entity, objectMapper.getTypeFactory().constructCollectionType(List.class, Exercise.class));
            list.forEach((exercise) -> exerciseRepo.save(exercise));
           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @PatchMapping("/{clientId}/exercise")
    public boolean patchUserExercise(@RequestBody String entity, @PathVariable Integer clientId){
    try {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Exercise> list = objectMapper.readValue(entity, objectMapper.getTypeFactory().constructCollectionType(List.class, Exercise.class));
        list.forEach((exercise) -> exerciseRepo.update(clientId, exercise));
        return true;
        
        } catch (Exception e) {
        e.printStackTrace();
        return false;
        }

    }



    @PatchMapping("/exercise/{exerciseId}")
    public boolean patchExercise(@RequestBody String entity, @PathVariable Long exerciseId){
    try {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Exercise> list = objectMapper.readValue(entity, objectMapper.getTypeFactory().constructCollectionType(List.class, Exercise.class));
        list.forEach((exercise) -> {
            int clientId = exercise.getClientId();
            exerciseRepo.update(clientId, exercise);
        });
        return true;
        
        } catch (Exception e) {
        e.printStackTrace();
        return false;
        }
    }


    
}
