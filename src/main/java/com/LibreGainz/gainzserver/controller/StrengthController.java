package com.LibreGainz.gainzserver.controller;
import org.springframework.web.bind.annotation.RestController;

import com.LibreGainz.gainzserver.repo.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers.IntegerDeserializer;
import com.LibreGainz.gainzserver.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.sql.Date;


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
public class StrengthController {
@Autowired
    private final WorkoutRepo workoutRepo;
    private final StrengthRepo strengthRepo;

    public StrengthController(
        WorkoutRepo workoutRepo,
        StrengthRepo strengthRepo
        ) {
        this.workoutRepo = workoutRepo;
        this.strengthRepo = strengthRepo;
    }

 

    @GetMapping("/strength")
    public List<Strength> getStrengthAll(){
        List<Strength> wList= new ArrayList<>();
        wList.addAll(strengthRepo.findAll());
        return wList;
    }

 @GetMapping("/strength/{workoutId}")
    public List<Strength> getStrength(@PathVariable Long workoutId){
        List<Strength> wList= new ArrayList<>();
        wList.addAll(strengthRepo.find(workoutId));
        return wList;
    }












    @GetMapping("/{userId}/strength")
    public List<Strength> getUserStrength(@PathVariable Integer userId){
        int limit = 10;
        List<Strength> wList= new ArrayList<>();
        wList.addAll(strengthRepo.findAll(userId, limit));
        return wList;
    }



    @GetMapping("/{userId}/strength/date/template")
    public List<Strength> getUserStrength(
        @PathVariable Integer userId, 
        @RequestParam Integer templateId,
        @RequestParam Date startDate,
        @RequestParam Date endDate,
        @RequestParam Integer limit
    )
    {
        List<Strength> wList= new ArrayList<>();
        wList.addAll(strengthRepo.findAll(userId, templateId, startDate, endDate, limit));
        return wList;
    }


@GetMapping("/{userId}/strength/date")
    public List<Strength> getUserStrength(
        @PathVariable Integer userId, 
        @RequestParam Date startDate,
        @RequestParam Date endDate,
        @RequestParam Integer limit
    )
    {
        List<Strength> wList= new ArrayList<>();
        wList.addAll(strengthRepo.findAll(userId, startDate, endDate, limit));
        return wList;
    }














    @PostMapping("/{userId}/strength")
    public void postUserStrength(@RequestBody String entity, @PathVariable Integer userId) {
         try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Strength> list = objectMapper.readValue(entity, objectMapper.getTypeFactory().constructCollectionType(List.class, Strength.class));
            list.forEach((strength) -> strengthRepo.save(strength));
           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @PatchMapping("/{userId}/strength")
    public boolean patchUserStrength(@RequestBody String entity, @PathVariable Integer userId){
    try {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Strength> list = objectMapper.readValue(entity, objectMapper.getTypeFactory().constructCollectionType(List.class, Strength.class));
        list.forEach((strength) -> strengthRepo.update(userId, strength));
        return true;
        
        } catch (Exception e) {
        e.printStackTrace();
        return false;
        }

    }



    @PatchMapping("/strength/{workoutId}")
    public boolean patchStrength(@RequestBody String entity, @PathVariable Long workoutId){
    try {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Strength> list = objectMapper.readValue(entity, objectMapper.getTypeFactory().constructCollectionType(List.class, Strength.class));
        list.forEach((strength) -> {
            int userId = strength.getUserId();
            strengthRepo.update(userId, strength);
        });
        return true;
        
        } catch (Exception e) {
        e.printStackTrace();
        return false;
        }
    }





}




