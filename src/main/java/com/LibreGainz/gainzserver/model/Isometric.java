package com.LibreGainz.gainzserver.model;
import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.stereotype.Component;
import java.sql.*;
import java.io.*;
import java.util.List;
import java.util.Arrays;

/** @author Remllez * This class stores an ArrayList<Time> and a weight object.  */
@Component
public class Isometric extends Workout{
    private static String csvPath = "data//Isometric.csv";
    public static HashMap<Long, Isometric> map = new HashMap<Long, Isometric>();
    public Isometric(int templateId, long workoutId){
        super(templateId, workoutId);
        map.putIfAbsent(workoutId, this);
    }

    public Isometric(int templateId){
    super(templateId);
    map.putIfAbsent(workoutId, this);
    }

    public Isometric(){
        super(0);
    }
    /**
     * Get the path where the csv file for the object is saved
     * @return
     */
    public static String getCsvPath(){
        return csvPath;
    }
    /**
     * Set the path where this csv file is saved
     * @return
     */
    public static void setCsvPath(String newCsvPath){
        csvPath = newCsvPath;
    }

    private WeightObj weight;
    private ArrayList<Time> set = new ArrayList<Time>();

    /**
     * Add a time to the time ArrayList
     * @param newTime
     */
    public void addTime(String newTime){
        set.add(Time.valueOf(newTime));
    }
    /**
     * Delete at time from the time ArrayList
     * @param timeIndex
     */
    public void delTime(int timeIndex){
        set.remove(timeIndex);
    }
    /**
     * Edit a time in the time ArrayList
     * @param timeIndex
     * @param newTime
     */
    public void editTime(int timeIndex, String newTime){
        set.set(timeIndex, Time.valueOf(newTime));
    }

/**
 * Convert a String into an ArrayList<TimeOj>
 * @param commaList
 * @return
 */
public static ArrayList<Time> strToSet(String commaList){
    ArrayList<Time> retArr = new ArrayList<Time>();
    for (String str : commaList.split(",")){
        retArr.add(Time.valueOf(str.trim()));
    }
    return retArr;
}




    /**
     * Replace the time ArrayList with new ArrayList
     * @param newSet
     */
    public void setSet(ArrayList<Time> newSet){
        set = newSet;
    }
    /**
     * Get the Object's time ArrayList
     * @return ArrayList<Time>
     */
    public ArrayList<Time> getSet(){
        return set;
    }


/**
 * Set the weight for this workout
 * @param newWeight
 */
public void setWeight(WeightObj newWeight){
    weight = newWeight;
}
/**
 * Get the weight for this workout session
 * @return
 */
public WeightObj getWeight(){
    return weight;
}
    /**
     * Remove this object from maps
     */
    public void deMap(){
        map.remove(workoutId);
        Workout.map.remove(workoutId);
    }
/**
 * This coverts a single row from a CSV file into a Isometric object
 * @param line
 * @return
 */
public static Isometric csvParse(String csvStr) throws Exception
    {
    List<String> read = new ArrayList<String>();
    read = Arrays.asList(CsvHandler.csvParse(csvStr).toArray(new String[0]));
    Isometric iso = new Isometric(Integer.valueOf(read.get(0)),Integer.valueOf(read.get(1)));
    iso.setWeight(WeightObj.strToWeight(read.get(2)));
    iso.setSet(strToSet(read.get(3)));
    return iso;
    }


 /**
 * Opens a csv file and turns it's contents into isometric objects
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
            Isometric iso = csvParse(line);
            Workout wo = map.get(iso.workoutId);
            iso.setDate(wo.getDate());
            iso.setAnnotation(wo.getAnnotation());
        }
    }
    catch(Exception e){

    }
    finally {
    }
}

 

    /**
     * Return a string for use in CSV files
     * @return String
     */
    public String toString(){
        return super.templateId +
        "," + super.workoutId +
        "," + weight.toString() +
        ",\"" + set.toString().substring(1, set.toString().length() - 1) + "\"";
    }


 /* get a CSV friendly string representing this object's superclass
 * @return csvStr
 */
    public String superToString(){
        return super.toString();
    }
    public void csvAppend(){
        CsvHandler.csvAppendStr(csvPath, this.toString());
        //CsvHandler.csvAppendStr(super.getCsvPath(), super.toString());

    }

    
}