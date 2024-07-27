package com.libregainz.server.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.libregainz.server.model.*;
import com.libregainz.server.repo.*;
import com.fasterxml.jackson.core.JsonProcessingException;

//import org.apache.catalina.core.ApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers.IntegerDeserializer;




import org.springframework.context.ApplicationContext;
import org.springframework.dao.DuplicateKeyException;

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
public class ClientController {
@Autowired
    private ClientRepo clientRepo;
    public ClientController(ClientRepo clientRepo){
        this.clientRepo = clientRepo;
    }

 @DeleteMapping("/client/{clientId}")
    public boolean deleteClient(@PathVariable Integer clientId){
        return clientRepo.delete(clientId);
    }
@GetMapping("/client/{clientId}")
    public Client getClient(@PathVariable Integer clientId){
    List<Client> list = clientRepo.find(clientId);
    return (list.size() > 0) ? list.get(0) : null;
    }


@GetMapping("/client")
    public Client getClient(@RequestParam String name){
    try {
        Client u = clientRepo.find(name).get(0);
        return u;
    } catch(Exception e){
        return null;
    }
    }



//newClient post request returns clientId of a brand new client

@PostMapping("/client")
public int postNew(@RequestBody String entity){
    try {
    Client client = new ObjectMapper().readValue(entity, Client.class);
    try{
    clientRepo.save(client);
    } catch (DuplicateKeyException e) {
        client = clientRepo.find(client.getTitle()).get(0);
        return client.getId();
    }
    client = clientRepo.find(client.getTitle()).get(0);
    return client.getId();

    }catch(JsonProcessingException e){
        System.out.println("Exception in ClientController");
        e.printStackTrace();
        return -1;
    }
}   
}
