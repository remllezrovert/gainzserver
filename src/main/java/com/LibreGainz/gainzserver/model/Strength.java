package com.LibreGainz.gainzserver.model;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.io.*;
import java.sql.*;

import org.springframework.stereotype.Component;
/**
 * This class contains an ArrayList<Integer> of reps, and a weight
 */

@Component
public class Strength extends Workout {
    private static String csvPath = "data//Strength.csv";
    public static HashMap<Long, Strength> map = new HashMap<Long, Strength>();
    private ArrayList<Short> set = new ArrayList<Short>();
    private WeightObj weight;

    public Strength(int templateId, Long workoutId){
        super(templateId, workoutId);
        this.workoutId = super.workoutId;
        map.putIfAbsent(workoutId, this);
    }

    Strength(int templateId){
        super(templateId);
        this.workoutId = super.workoutId;
        map.putIfAbsent(workoutId, this);
    }

    Strength(){
        super();
        map.putIfAbsent(workoutId, this);
    }


public Strength(ResultSet rs) throws SQLException {
    super(rs.getInt("Template_id"),rs.getLong("id"));
    setDate(rs.getDate("workoutDate"));

    String str = rs.getString("tagArr").replace("\\","").replace("\"", "");
    setTags(Workout.strToTags(str.substring(1,str.length() -1)));
    try{
    String repStr = rs.getString("repArr").trim();
    setSet(strToSet(repStr.substring(1,repStr.length() -1)));
    } catch(Exception e){

    }
    setWeight(WeightObj.strToWeight(rs.getString("weight") + rs.getString("unit")));

    map.putIfAbsent(workoutId, this);
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
    


public static ArrayList<Short> strToSet(String commaList){
    ArrayList<Short> retArr = new ArrayList<Short>();

    try{
    for (String str : commaList.split(",")) {
        retArr.add(Short.parseShort(
            str.replaceAll("[^0-9]", "")
        ));
    }
    }
    catch (NumberFormatException nfe){
        nfe.printStackTrace();
        return retArr;
    }

    return retArr;

    }



    
    /**
     * Add reps to the ArrayList<int>
     * @param newReps
     */
    public void addReps(short newReps){
        set.add(newReps);
    }
    /**
     * Delete reps from the ArrayList<int>
     * @param setIndex
     */
    public void delReps(short setIndex){
        set.remove(setIndex);
    }
    /**
     * Edit the ArrayList<int>
     * @param setIndex
     * @param newReps
     */
    public void editReps(int setIndex, short newReps){
        set.set(setIndex, newReps);
    }
    /**
     * Replace the ArrayList<int> with a new ArrayList<int>
     * @param newSet
     */
    public void setSet(ArrayList<Short> newSet){
        set = newSet;
    }
    /**
     * Get the rep ArrayList<int>
     * @return
     */
    public ArrayList<Short> getSet(){
        return set;
    }
    
    /**
     * Set the weight for this workout
     * @param newWeight
     */
    public void setWeight(WeightObj newWeight){
        this.weight = newWeight;
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
 * This coverts a single row from a CSV file into a Strength object
 * @param line
 * @return StrengthObject
 */
public static Strength csvParse(String csvStr) throws Exception
    {
    List<String> read = new ArrayList<String>();
    read = Arrays.asList(CsvHandler.csvParse(csvStr).toArray(new String[0]));
    Strength st = new Strength(Integer.valueOf(read.get(0)),Long.valueOf(read.get(1)));
    st.setWeight(WeightObj.strToWeight(read.get(2)));
    st.setSet(strToSet(read.get(3)));
    return st;
}

/**
 * Opens csv file and turns it's contents into strength objects
 * @param path
 */
public static void csvLoad()
{
    BufferedReader reader = null;
    String line = "";
    try{
        reader = new BufferedReader(new FileReader(csvPath));
        while((line = reader.readLine())!= null){
            Strength st = csvParse(line);
            Workout wo = Workout.map.get(st.workoutId);
            st.setDate(wo.getDate());
            st.setAnnotation(wo.getAnnotation());
        }
    }
    catch(Exception e){

    }
    finally {

    }
}




    /**
     * Get the CSV friendly String that represents this object 
     * @return String 
     */
    public String toString(){

        return super.templateId+
        "," + super.workoutId +
        "," + weight.toString() +
        ",\"" + set.toString().substring(1, set.toString().length() - 1) + "\"";
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
    }

}