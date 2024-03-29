package com.LibreGainz.gainzserver.model;
import java.sql.Time;

import org.springframework.stereotype.Component;

@Component
public class Cardio extends Workout{
    private float distance;
    private Time time;
    private Unit unit;

    public Time getTime() {
        return time;
    }
    public void setTime(Time time) {
        this.time = time;
    }
    public float getDistance() {
        return distance;
    }
    public void setDistance(float distance) {
        this.distance = distance;
    }
    public Unit getUnit() {
        return unit;
    }
    public void setUnit(Unit unit) {
        this.unit = unit;
    }

}
