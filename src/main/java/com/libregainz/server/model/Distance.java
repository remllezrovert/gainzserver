package com.libregainz.server.model;

public class Distance {

    private Unit unit = Unit.BW;
    private Float value = 0.0f;

    public Float getValue() {
        return value;
    }
    public void setValue(Float value) {
        this.value = value;
    }
    public Unit getUnit() {
        return unit;
    }
    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return this.value.toString() + this.unit.toString();
    }

}
