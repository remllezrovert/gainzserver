package com.libregainz.server.model;

public class Weight {

    private Unit unit = Unit.BW;
    private Short value = 0;

    public Short getValue() {
        return value;
    }
    public void setValue(Short value) {
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
