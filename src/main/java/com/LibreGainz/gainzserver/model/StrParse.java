package com.LibreGainz.gainzserver.model;
import java.util.ArrayList;
import java.text.ParseException;
import java.util.Date;
/**
 * @author Remllez
 * This class converts Strings into useful objects and ArrayLists
 */



public class StrParse{
/**
 * Create time objects using strings 
 * @param timeStr
 * @return TimeObj
 */
public static TimeObj toTime(String timeStr) throws NullPointerException
    {
    int[] tempArr = {0,0,0};
    int i = 0;
    try {
    for (String str : timeStr.split(":")) {
        int newInt = Integer.parseInt(str.trim());
        tempArr[i] = newInt;
        i += 1;
    }
    }
    catch (NullPointerException npe){
        throw new NullPointerException("null values given");
    }
    return new TimeObj(tempArr[0], tempArr[1], tempArr[2]);
}

/**
 * Convert a string into a weight object
 * @param str
 * @return WeightObj
 */
public static WeightObj toWeight(String str){
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



/**
 * Convert comma separted list into an array of strings, or 'tags' for templates
 * @param commaList
 * @return
 */
public static ArrayList<String> toTagArray(String commaList){
    ArrayList<String> retArr = new ArrayList<String>();
    for (String str : commaList.split(","))
        retArr.add(str.trim());
    return retArr;
}




/**
 * Convert a String into an array of reps ArrayList<int> 
 * @param commaList
 * @return ArrayList<Integer>
 */
public static ArrayList<Short> toStrengthSet(String commaList){
    ArrayList<Short> retArr = new ArrayList<Short>();

    try{
    for (String str : commaList.split(","))
        retArr.add(Short.parseShort(str.trim()));
    }
    catch (NumberFormatException nfe){
        System.out.println("NFE");
        return retArr;
    }

    return retArr;

    }
/**
 * Convert a String into an ArrayList<TimeOj>
 * @param commaList
 * @return
 */
public static ArrayList<TimeObj> toIsometricSet(String commaList){
    ArrayList<TimeObj> retArr = new ArrayList<TimeObj>();
    for (String str : commaList.split(",")){
        retArr.add(toTime(str.trim()));
    }
    return retArr;
}

/**
 * Convert string into date object
 * @param dateStr
 * @return
 * @throws ParseException
 */
public static Date toDate(String dateStr) throws ParseException
{
    Date d = User.dateFormat.parse(dateStr);
    return d;
}



}