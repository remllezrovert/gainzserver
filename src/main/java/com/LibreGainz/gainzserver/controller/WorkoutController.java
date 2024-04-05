package com.LibreGainz.gainzserver.controller;

import org.springframework.web.bind.annotation.RestController;
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
@RequestMapping("/workout")
public class WorkoutController{

@GetMapping()
public String getMethodName(@RequestParam String param) {
    return new String();
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
