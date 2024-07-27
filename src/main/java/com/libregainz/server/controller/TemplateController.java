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
public class TemplateController {

    private final TemplateRepo templateRepo;

    public TemplateController(
        TemplateRepo templateRepo
    ){
        this.templateRepo = templateRepo;
    }


    @GetMapping("/template")
    public List<Template> getTemplateAll(){

        List<Template> wList= new ArrayList<>();
        wList.addAll(templateRepo.findAll());
        return wList;
    }

    @GetMapping("/template/{templateId}")
    public List<Template> getTemplate(@PathVariable Integer templateId){
        List<Template> wList= new ArrayList<>();
        wList.addAll(templateRepo.find(templateId));
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

@PatchMapping("/{userId}/template")
 public boolean patchUserTemplate(@RequestBody String entity, @PathVariable Integer userId){
    try {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Template> list = objectMapper.readValue(entity, objectMapper.getTypeFactory().constructCollectionType(List.class, Template.class));
        list.forEach((template) -> templateRepo.update(userId, template));
        return true;
        
        } catch (Exception e) {
        e.printStackTrace();
        return false;
        }

    }

@PatchMapping("/template/{templateId}")
 public boolean patchTemplate(@RequestBody String entity, @PathVariable Integer templateId){
    try {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Template> list = objectMapper.readValue(entity, objectMapper.getTypeFactory().constructCollectionType(List.class, Template.class));
        list.forEach((template) -> templateRepo.update(list.get(0).getClientId(), template));
        return true;
        
        } catch (Exception e) {
        e.printStackTrace();
        return false;
        }

    }

@DeleteMapping("/template/{templateId}")
public boolean deleteTemplate(@PathVariable Integer templateId){
    try {
        templateRepo.delete(templateId);
        return true;
    }
    catch (Exception e){
        e.printStackTrace();
        return false;
    }
}


 @DeleteMapping("/{userId}/template/{id}")
    public boolean deleteUserTemplate(@PathVariable Integer userId, @PathVariable Integer id){
        return templateRepo.delete(userId, id);
    }











}
