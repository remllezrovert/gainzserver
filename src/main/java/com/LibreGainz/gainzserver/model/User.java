package com.LibreGainz.gainzserver.model;

import org.springframework.stereotype.Component;

@Component
public class User {
    int id;
    String name;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


}
