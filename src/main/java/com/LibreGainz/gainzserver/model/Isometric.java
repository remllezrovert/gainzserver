package com.LibreGainz.gainzserver.model;
import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.stereotype.Component;

/** @author Remllez
 * This class stores an ArrayList<TimeObj> and a weight object.
 */
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
    private ArrayList<TimeObj> set = new ArrayList<TimeObj>();

    /**
     * Add a time to the time ArrayList
     * @param newTime
     */
    public void addTime(String newTime){
        set.add(StrParse.toTime(newTime));
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
        set.set(timeIndex, StrParse.toTime(newTime));
    }
    /**
     * Replace the time ArrayList with new ArrayList
     * @param newSet
     */
    public void setSet(ArrayList<TimeObj> newSet){
        set = newSet;
    }
    /**
     * Get the Object's time ArrayList
     * @return ArrayList<TimeObj>
     */
    public ArrayList<TimeObj> getSet(){
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
        CsvHandler.csvAppendStr(super.getCsvPath(), super.toString());

    }
}