
package com.LibreGainz.gainzserver.model;
import java.text.SimpleDateFormat;
import org.springframework.stereotype.Component;
/**
 * @author Remllez
 * This class is for users, which have a name and unqiue id.
 * Possible plans to impliment multi-user mode
 */

 @Component
public class User{
    private static String dateFormatStr = "MM/dd/yyyy";
    public static SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatStr);
    public static Unit weightUnit = Unit.LB;
    public static Unit longDistanceUnit = Unit.MI;
    public static Unit shortDistanceUnit = Unit.M;

    private int userId = 1;
    private String name;

    public User(String name){
        this.name = name;
    }

    public User(){}
    /**
     * Get the name of the user
     * @return name
     */
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
    /**
     * Get the id of the user
     * @return userId
     */
    public int getId(){
        return userId;
    }
    public void setId(int id){
        this.userId = id;
    }
    
    public static void setWeightUnit(Unit newWeightUnit) {
    	weightUnit = newWeightUnit;
    }
    
    public static void setLongDistanceUnit (Unit newLongDistanceUnit) {
    	longDistanceUnit = newLongDistanceUnit;
    }
    public static void setShortDistanceUnit (Unit newShortDistanceUnit) {
    	shortDistanceUnit = newShortDistanceUnit;
    }
    
    public static Unit getWeightUnit() {
    	return weightUnit;
    }
    
    public static Unit getLongDistanceUnit() {
    	return longDistanceUnit;
    }
    
    public static Unit getShortDistanceUnit() {
    	return shortDistanceUnit;
    }
    
    
    
    
}
