package com.LibreGainz.gainzserver.controller;
import org.springframework.web.bind.annotation.RestController;

import com.LibreGainz.gainzserver.model.Device;
import com.LibreGainz.gainzserver.repo.DeviceRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class DeviceController {
    private DeviceRepo deviceRepo;
    public DeviceController(DeviceRepo deviceRepo){
        this.deviceRepo = deviceRepo;
    }
//create a sync post request

@PostMapping("/device")
public int postDevice(@RequestBody String entity){
    try {
    Device device = new ObjectMapper().readValue(entity, Device.class);
    deviceRepo.save(device);
    return 200;
    }catch(JsonProcessingException jpe){
        System.out.println("Json Parse Exception in DeviceController");
        return -1;
    }



}

}
