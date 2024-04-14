package com.LibreGainz.gainzserver.controller;


import java.util.*;
import com.LibreGainz.gainzserver.repo.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.LibreGainz.gainzserver.model.*;
//import org.apache.catalina.core.ApplicationContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.*;





@RestController  
public class TemplateController {
@Autowired
    private final WorkoutRepo workoutRepo;
    private final TemplateRepo templateRepo;
     

    public TemplateController(
        WorkoutRepo workoutRepo,
        TemplateRepo templateRepo
        ) {
        this.workoutRepo = workoutRepo;
        this.templateRepo = templateRepo;
    }

 

    @GetMapping("/template")
    public List<Template> gettemplate(){

        List<Template> wList= new ArrayList<>();
        wList.addAll(templateRepo.findAll());
        return wList;
    }

    @GetMapping("/{userId}/template")
    public List<Template> getUsertemplate(@PathVariable Integer userId){
        int limit = 10;
        List<Template> wList= new ArrayList<>();
        wList.addAll(templateRepo.findAll(userId, limit));
        return wList;
    }

 @PostMapping("/{userId}/template")
    public void postUserTemplate(@RequestBody String entity, @PathVariable Integer userId) {
         try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Template> list = objectMapper.readValue(entity, objectMapper.getTypeFactory().constructCollectionType(List.class, Template.class));
            list.forEach((template) -> templateRepo.save(template));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}


