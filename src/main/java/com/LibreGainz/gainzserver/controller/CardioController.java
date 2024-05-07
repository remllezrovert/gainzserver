package com.LibreGainz.gainzserver.controller;

import com.LibreGainz.gainzserver.repo.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.LibreGainz.gainzserver.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.sql.Date;


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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.*;





@RestController  
public class CardioController {
@Autowired
    private final WorkoutRepo workoutRepo;
    private final CardioRepo cardioRepo;
     

    public CardioController(
        WorkoutRepo workoutRepo,
        CardioRepo cardioRepo
        ) {
        this.workoutRepo = workoutRepo;
        this.cardioRepo = cardioRepo;
    }

 

    @GetMapping("/cardio")
    public List<Cardio> getCardioAll(){

        List<Cardio> wList= new ArrayList<>();
        wList.addAll(cardioRepo.findAll());
        return wList;
    }

    @GetMapping("/cardio/{workoutId}")
    public List<Cardio> getCardio(@PathVariable Long workoutId){
        List<Cardio> wList= new ArrayList<>();
        wList.addAll(cardioRepo.find(workoutId));
        return wList;
    }



    @GetMapping("/{userId}/cardio")
    public List<Cardio> getUserCardio(@PathVariable Integer userId){
        int limit = 10;
        List<Cardio> wList= new ArrayList<>();
        wList.addAll(cardioRepo.findAll(userId, limit));
        return wList;
    }


    @GetMapping("/{userId}/cardio/date/template")
    public List<Cardio> getUserCardio(
        @PathVariable Integer userId, 
        @RequestParam Integer templateId,
        @RequestParam Date startDate,
        @RequestParam Date endDate,
        @RequestParam Integer limit
    )
    {
        List<Cardio> wList= new ArrayList<>();
        wList.addAll(cardioRepo.findAll(userId, templateId, startDate, endDate, limit));
        return wList;
    }


@GetMapping("/{userId}/cardio/date")
    public List<Cardio> getUserCardio(
        @PathVariable Integer userId, 
        @RequestParam Date startDate,
        @RequestParam Date endDate,
        @RequestParam Integer limit
    )
    {
        List<Cardio> wList= new ArrayList<>();
        wList.addAll(cardioRepo.findAll(userId, startDate, endDate, limit));
        return wList;
    }





     @PostMapping("/{userId}/cardio")
    public void postUserCardio(@RequestBody String entity, @PathVariable Integer userId) {
         try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Cardio> list = objectMapper.readValue(entity, objectMapper.getTypeFactory().constructCollectionType(List.class, Cardio.class));
            list.forEach((cardio) -> cardioRepo.save(cardio));
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(entity + "\n" + userId);

}


    @PatchMapping("/{userId}/cardio")
    public boolean patchUserCardio(@RequestBody String entity, @PathVariable Integer userId){
    try {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Cardio> list = objectMapper.readValue(entity, objectMapper.getTypeFactory().constructCollectionType(List.class, Cardio.class));
        list.forEach((cardio) -> cardioRepo.update(userId, cardio));
        return true;
        
        } catch (Exception e) {
        e.printStackTrace();
        return false;
        }
    }


    @PatchMapping("/cardio/{workoutId}")
    public boolean patchCardio(@RequestBody String entity, @PathVariable Long workoutId){
    try {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Cardio> list = objectMapper.readValue(entity, objectMapper.getTypeFactory().constructCollectionType(List.class, Cardio.class));
        list.forEach((cardio) -> {
            int userId = cardio.getUserId();
            cardioRepo.update(userId, cardio);
        });
        return true;
        
        } catch (Exception e) {
        e.printStackTrace();
        return false;
        }
    }







}

