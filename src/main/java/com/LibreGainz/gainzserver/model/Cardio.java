package com.LibreGainz.gainzserver.model;
import org.springframework.stereotype.Component;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.sql.*;
import java.io.*;
import java.util.*;
@Component
public class Cardio extends Workout{
    private static String csvPath = "data//Cardio.csv";
    public static HashMap<Long, Cardio> map = new HashMap<Long, Cardio>();
	protected double distance;
	private Time time;
	Unit distanceUnit;
    public Cardio(int templateId, long workoutId){
        super(templateId, workoutId);
        map.putIfAbsent(workoutId, this);
    }
    public Cardio(int templateId){
        super(templateId);
        map.putIfAbsent(workoutId, this);
    }
    public Cardio(){
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

  

    //set the units for the distance ran 
    public void setUnit(Unit newUnit) {
    	this.distanceUnit = newUnit; 
    }
    
    /** 
     * 
     * @return distanceUnit
     */
    public Unit getUnit() {
    	return distanceUnit; 
    }
    
    /**
     * sets the distance ran 
     * @param distance1
     */
  
 
    public void setDistance(double distance1) {
    	distance = distance1;
    }

    /**
     * get the distance ran
     * @return distance
     */
    public double getDistance() {
        return distance;
    }
    
    //Convert miles to kilos
    public static String Miles2Kilos(double miles){
    	DecimalFormat df = new DecimalFormat("0.000");
    	double kilometers = miles *1.609344;
        return df.format(kilometers);
    }
    
    public static String Kilos2Miles(double kilos){
    	DecimalFormat df = new DecimalFormat("0.000");
        double miles = kilos * .62137119;
        return df.format(miles);
    }

   
    /**
     * input minutes and seconds as parameters for time object
     * @param minutes
     * @param seconds
     */
    public void setTime(Time newTime) {
        time = newTime;
    }

    /**
     * Get the time object corisponding to this Cardio object
     * @return
     */
    public Time getTime(){
        return time;
    }
  
    
    //user inputs if needs to edit a run 
    public void editRun(int setDistance, int setMinutes, int setSeconds){
        distance = setDistance;
        Time t1 = Time.valueOf(setMinutes + ":" + setSeconds);
        time = t1; 
    }

   /**
     * Remove this object from maps
     */
    public void deMap(){
        map.remove(workoutId);
        Workout.map.remove(workoutId);
    }
  
   /**
 * This converts a csv string into a Cardio object
 * @param csvStr
 * @return
 * @throws Exception
 */
public static Cardio csvParse(String csvStr) throws Exception
    {
    List<String> read = new ArrayList<String>();
    read = Arrays.asList(CsvHandler.csvParse(csvStr).toArray(new String[0]));
    Cardio cdo = new Cardio(Integer.valueOf(read.get(0)),Integer.valueOf(read.get(1)));
    //cdo.setUnit(Isometric.strToSet(read.get(3)));
    //String alphaStr = read.get(3).replaceAll("[^A-Za-z]+", "");
    cdo.setDistance(Double.parseDouble(read.get(2).replaceAll("[^\\d.]", "")));
    cdo.setTime(Time.valueOf(read.get(3)));
    Unit unit; 
    switch(read.get(3).replaceAll("[^A-Za-z]+", "").toUpperCase()){
    case "KM":
    case "KILOMETER":
    case "K":
        unit = Unit.KM; 
        break;
    case "MI":
    case "MILES":
    case "MILE":
        unit = Unit.MI;
        break;
    default:
        unit = Unit.MI;
        break;
    }
    cdo.setUnit(unit);
    return cdo;
    }

/**
 * Opens a csv file and turns it's contents into cardio objects
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
            Cardio cdo = csvParse(line);
            Workout wo = Cardio.map.get(cdo.workoutId);
            cdo.setDate(wo.getDate());
            cdo.setAnnotation(wo.getAnnotation());
        }
    }
    catch(Exception e){
    }
    finally {
    }
}




    
    /**
     * Return a CSV friendly string representing this object
     */
    public String toString(){
        if (time != null)
        return super.templateId+
        "," + super.workoutId +
        "," + distance + distanceUnit + "," + time.toString();
        else

        return super.templateId+
        "," + super.workoutId +
        "," + distance + distanceUnit + "," + "null";
    }
    /**
     * get a CSV friendly string representing this object's superclass
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