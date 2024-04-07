package com.LibreGainz.gainzserver.model;


// TODO: fix the getjStr() method to actually work
/**
 * @author remllez
 */

public class WeightObj{
short weight;
Unit unit;
public WeightObj(short weight, Unit unit){
    this.weight = weight;
    this.unit = unit;
}

/**
 * Converts pounds to kilograms
 * @param lb
 * @return
 */
public static short lb2Kg(short lb){
    return (short)Math.round(lb * 2.20462);
}
/**
 * Converts kilograms to pounds
 * @param kg
 * @return
 */
public static short kg2Lb(short kg){
    return (short)Math.round(kg * 0.453592);
}
public String toString(){
    return weight + this.unit.toString();
}

public short getWeight(){
    return weight;
}
public void setWeight(){

}
public Unit getUnit(){
    return this.unit;
}
/**
 * Convert this WeightObj from kg to lb, or vise-versa.
 */
public void convert(){
    switch(unit){
    case KG:
        this.weight = kg2Lb(weight);
        this.unit = Unit.LB;
        break;
    case LB:
        this.weight = lb2Kg(weight);
        this.unit = Unit.KG;
        break;
    }
}


/**
 * Convert a string into a weight object
 * @param str
 * @return WeightObj
 */
public static WeightObj strToWeight(String str){
    StringBuffer unitBuffer = new StringBuffer(); 
    StringBuffer weightBuffer = new StringBuffer();
    for (int i=0; i < str.trim().length(); i++){
        if (Character.isDigit(str.charAt(i))) 
            weightBuffer.append(str.charAt(i));
        else if (Character.isAlphabetic(str.charAt(i)))
            unitBuffer.append(str.charAt(i));
    }
    Unit unit; 
    switch(unitBuffer.toString().toLowerCase()){
    case "kg":
    case "kgs":
    case "kilo":
    case "kilos":
        unit = Unit.KG; 
        break;
    case "lb":
    case "lbs":
    case "pounds":
        unit = Unit.LB;
        break;
    default:
        unit = User.getWeightUnit();
        break;
    }
    try{
    WeightObj retObj = new WeightObj(Short.parseShort(weightBuffer.toString()), unit);
    return retObj;
    }
    catch (Exception e){
        System.out.println(e);
    return null;
    }

}



}
