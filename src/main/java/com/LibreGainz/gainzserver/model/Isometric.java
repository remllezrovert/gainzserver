package com.LibreGainz.gainzserver.model;
import java.util.ArrayList;

import org.springframework.stereotype.Component;
@Component
public class Isometric {
    long id;
    ArrayList<String> set = new ArrayList<String>();
    Unit unit;
    short weight;
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public Unit getUnit() {
        return unit;
    }
    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public ArrayList<String> getSet() {
        return set;
    }
    public void setSet(ArrayList<String> set) {
        this.set = set;
    }

    public short getWeight() {
        return weight;
    }

    public void setWeight(short weight) {
        this.weight = weight;
    }
    
}
