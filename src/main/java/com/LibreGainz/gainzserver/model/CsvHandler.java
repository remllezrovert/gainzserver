package com.LibreGainz.gainzserver.model;
import java.io.*;
import java.util.regex.*;
import java.util.*;
import java.nio.file.*;
import java.util.stream.*; 
import java.sql.*;


//TODO: all toStrength/toIsometric etc. need error handling for null values

/** 
 * @author Remllez
 * This is tools for handling CSV files
 */
public class CsvHandler{



final static Pattern quote = Pattern.compile("^\\s*\"((?:[^\"]|(?:\"\"))*?)\"\\s*,");

/**
 * This turns one line of a CSV file into a ArrayList
 * @param csvLine
 */
public static List<String> csvParse(String line) throws Exception
{       
    List<String> list = new ArrayList<String>();
    line += ",";
    for (int x = 0; x < line.length(); x++){
        String s = line.substring(x);
        if (s.trim().startsWith("\"")){
            Matcher m = quote.matcher(s);
            if (!m.find())
                throw new Exception("CSV is malformed");
            list.add(m.group(1).replace("\"\"", "\""));
            x += m.end() - 1;
        }
        else{
            int y = s.indexOf(",");
            if (y == -1)
                throw new Exception("CSV is malformed");
            list.add(s.substring(0, y));
            x += y;
        }
    } return list;
}

/**
 * This prints every line in the CSV file in human readable format
 * @param path
 */
public static void csvPrint(String path)
{
    String file = path;
    BufferedReader reader = null;
    String line = "";
    try{
        reader = new BufferedReader(new FileReader(file));
        while((line = reader.readLine())!= null){
            String[] row = csvParse(line).toArray(new String[0]);
            for(String index : row){
                System.out.printf("%-30s", index);
            }
            System.out.println();
        }
    }
    catch(Exception e){

    }
    finally {

    }
}


/**
 * get ArrayList<String> for a specific csv file line (aka 'lineNum') from file at 'path'
 * @param path
 * @param lineNum
 * @return rowArrayList
 */

public static ArrayList<String> getRow(String path, int lineNum)
{
try{
    Stream<String> lines = Files.lines(Paths.get(path));
    String ret =  lines.skip(lineNum).findFirst().get();
    ArrayList<String> retList = new ArrayList<String>();
for ( String str : csvParse(ret))
        retList.add(str);

    lines.close();
    return retList;
    }
catch(NullPointerException np) {System.out.println("Row does not exist");return null;}
catch(IOException io) {System.out.println("File does not exist");return null;}
catch(Exception e) {return null;}
}

public static String getRowStr(String path, int lineNum){
List<String> li = getRow(path,lineNum);
return li.stream().collect(Collectors.joining(","));

}

/**
 * This returns every value in the given column of the given CSV file
 * @param path
 * @param col
 * @return ArrayList<String>
 */
public static ArrayList<String> getCol(String path, int col)
{
    ArrayList<String> retList = new ArrayList<String>();
    BufferedReader reader = null;
    String line = "";
    try{
        reader = new BufferedReader(new FileReader(path));
        while((line = reader.readLine())!= null){
            retList.add(csvParse(line).toArray(new String[0])[col]);
        }
        reader.close();
        return retList;
    }
    catch(Exception e){
    }
        return null;
}

/**
 * This gets every values of the given column and checks if it matches an input String
 * @param path
 * @param col
 * @param checkString
 * @return boolean
 */
public static boolean checkUnique(String path, int col, String checkString){
    return getCol(path, col).contains(checkString)? false : true;
}





/**
 * This appends a string onto the end of a CSV file
 * @param path
 * @param str
 */
public static void csvAppendStr(String path, String str){
    try(FileWriter writer = new FileWriter(path, true)){
        writer.write(str + "\n");
    } catch(Exception e){
    }
    }



/**
 * Erases all data in a csv file
 * @param path
 */
public static void csvWipe(String path){
    try(FileWriter writer = new FileWriter(path, false)){
    } catch(Exception e){
    }
    }

    /**
     * Wipes CSV files, overwriting them with data from maps
     */
public static void overWrite(){
    Template.csvOverwrite();
    Workout.csvOverwrite();
    Strength.csvOverwrite();
    Isometric.csvOverwrite();
    Cardio.csvOverwrite();




}

}



