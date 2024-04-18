
package com.LibreGainz.gainzserver.model;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;
/**
 * @author Remllez
 * This class is for users, which have a name and unqiue id.
 * Possible plans to impliment multi-user mode
 */

 @Component
public class User{
    private String dateFormatStr = "MM/dd/yyyy";
    public Unit weightUnit = Unit.LB;
    public Unit longDistanceUnit = Unit.MI;
    private int userId = 1;
    private String name;
    private static String csvPath = "data//User.csv";
    public static HashMap<Integer,User> map = new HashMap<Integer,User>();

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
    
    public void setWeightUnit(Unit newWeightUnit) {
    	weightUnit = newWeightUnit;
    }
    
    public void setLongDistanceUnit (Unit newLongDistanceUnit) {
    	longDistanceUnit = newLongDistanceUnit;
    }
   
    public Unit getWeightUnit() {
    	return weightUnit;
    }
    
    public Unit getLongDistanceUnit() {
    	return longDistanceUnit;
    }
    
    public String getDateFormatStr() {
        return dateFormatStr;
    }
    public void setDateFormatStr(String dateFormatStr) {
        this.dateFormatStr = dateFormatStr;
    }

    public static String getCsvPath() {
        return csvPath;
    }
    public static void setCsvPath(String csvPath) {
        User.csvPath = csvPath;
    }


/**
 * This coverts a single row from a CSV file into a User object
 * @param line
 * @return StrengthObject
 */
public static User csvParse(String csvStr) throws Exception
    {
    List<String> read = new ArrayList<String>();
    read = Arrays.asList(CsvHandler.csvParse(csvStr).toArray(new String[0]));
    User u = new User(read.get(0));
    u.setId(Integer.valueOf(read.get(1)));
    u.setDateFormatStr(read.get(2));
    u.setLongDistanceUnit(Unit.valueOf(read.get(3)));
    u.setWeightUnit(Unit.valueOf(read.get(4)));

    return u;
}

/**
 * Opens csv file and turns it's contents into strength objects
 * @param path
 */
public static void csvLoad(String path)
{
    String file = path;
    BufferedReader reader = null;
    String line = "";
    try{
        reader = new BufferedReader(new FileReader(file));
        while((line = reader.readLine())!= null){
            User u = csvParse(line);
        }
    }
    catch(Exception e){

    }
    finally {

    }


}

public void csvAppend(){
    CsvHandler.csvAppendStr(csvPath,this.toString());
}


public static void csvOverwrite(){
    CsvHandler.csvWipe(csvPath);
    map.forEach((k,v) -> v.csvAppend());
    }

    public String toString(){
    return 
    
    name + "," +
    userId + "," + 
    dateFormatStr + "," +
    longDistanceUnit.toString() + "," +
    weightUnit.toString() + ","; 
    }








}
