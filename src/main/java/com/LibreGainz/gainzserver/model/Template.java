package com.LibreGainz.gainzserver.model;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.io.FileWriter;
import java.util.HashMap;


/**
 * @author Remllez
 */
@Component
public class Template{
private static String csvPath = "data//Template.csv";
public static HashMap<Integer, Template> map = new HashMap<Integer, Template>();
private int templateId;
private String name;
private String desc;
private String workoutType = "Workout";
private int userId;
private ArrayList<String> tags = new ArrayList<String>();

public Template(){
    int newKey = map.size();
    while(map.containsKey(newKey)){
        newKey++;
    }
    templateId = newKey;
    map.putIfAbsent(templateId, this);
}

public Template(int templateId){
    this.templateId = templateId;
    map.putIfAbsent(templateId, this);
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
    /**
     * Get the userId
     * @return
     */
    public int getUserId(){
        return userId;
    }
    /**
     * Set the userID
     * @param userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * get the name of the workout class associated with this 
     * @return workoutType
     */
    public String getWorkoutType() {
        return workoutType;
    }
    /**
     * set the name of the workout class associated with this
     * @param workoutType
     */
    public void setWorkoutType(String workoutType) {
        this.workoutType = workoutType;
    }
/**
 * Get the id of this template
 * @return templateId
 */
public int getId(){
    return templateId;
}
/**
 * set the templateId
 * @param templateId
 */
public void setId(int templateId){
    this.templateId = templateId;
}
/**
 * Gets the display name for the template
 * @return
 */
public String getName(){
    return name;
}
/**
 * Sets the display name for the template
 * @param newName
 */
public void setName(String newName){
    name = newName;
}


/**
 * Get a string describing the workout
 * @return
 */
public String getDesc(){
return desc;
}
/**
 * Set the description for this object
 * @param newDesc
 */
public void setDesc(String newDesc){
this.desc = newDesc;
}


/**
 * Remove this from HashMap. 
 */
public void deMap(){
    //Need to impliment a cascading delete
    //All workouts using this template should be deleted if this template is
    map.remove(templateId);
}

/**
 * Get a String representing this object for use in CSV files
 */
public String toString(){
return templateId +
    ",\"" + name + "\"" + 
    ",\"" + workoutType + "\"" + 
    ",\"" + desc + "\"";
}
/*
 * Append the toString() for this object onto a CSV file.
 */
public void csvAppend(){
    try(FileWriter writer = new FileWriter("data//Template.csv", true)){
        writer.write(this.toString()+"\n");
    } catch(Exception e){

    }
}

}
