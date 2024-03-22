package com.LibreGainz.gainzserver.model;
import org.springframework.stereotype.Component;
import java.util.ArrayList;



@Component
public class Strength extends Workout{
    ArrayList<Short> set = new ArrayList<Short>();
    Unit unit;
    short weight;

    public Unit getUnit() {
        return unit;
    }
    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public ArrayList<Short> getSet() {
        return set;
    }
    public void setSet(ArrayList<Short> set) {
        this.set = set;
    }

    public short getWeight() {
        return weight;
    }

    public void setWeight(short weight) {
        this.weight = weight;
    }

}
