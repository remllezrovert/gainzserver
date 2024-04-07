
package com.LibreGainz.gainzserver.model;


import java.util.Scanner; 

/**
 * @author remllez
 * This is an experimental unfinished launcher for the app.
 */
public class MVP{



/* main menu
*   - user menu
*   - view previous workouts 
*   - Create workout
*   - Edit/delete workouts
*   - template menu 
*/

private void MainMenu(Scanner scnr){
    System.out.println(

    "\n==================== Menu ====================\n" +
    "q - Quit out of program\n" +
    "a - Add/Alter workout\n" +
    "t - Template menu\n\n"
    );
    String input = scnr.nextLine();
    switch(input){
        case "q":
        case "a":
        case "t":
    }
}




/* template menu
 * - add tags
 * - remove tags
 * - edit desc
 * - back to main menu
 *  - quit
 */

private void TemplateMenu(Scanner scnr){ System.out.println(
"\n================ Template Menu ===============\n" +
"b - Back to main menu\n" +
"a - Add tags to template\n" +
"r - Remove tags from template\n" +
"e - Edit template description\n" +
"q - Quit out of program\n\n"
);
    String input = scnr.nextLine();
    switch(input){
        case "b":
        case "a":
        case "r":
        case "e":
        case "q":
    }

}


private void MVPMenu(Scanner scnr){
    System.out.println(
    "i"

    );
    String input = scnr.nextLine();
    switch(input.toLowerCase()){
        case "i":
        case "s":
        case "c":
        case "q":
    }

}

private static void strengthMenu(Scanner scnr){
    Template.map.forEach((k,v)-> System.out.println(k + " - " + v.getName()));
   try{
    System.out.println("Enter a template number:");
    Strength s = new Strength(scnr.nextInt());
    System.out.println("Enter a Weight:");
    s.setWeight(WeightObj.strToWeight(scnr.next()));
    System.out.println("Enter Repetitions (comma separated):");
    s.setSet(Strength.strToSet(scnr.next()));
   } 
   catch(Exception e){
    //System.out.println(e);
    System.out.println("Invalid input");
   }


}



public static void main(String[] args){

CsvHandler.templateLoad("data//Template.csv");
CsvHandler.workoutLoad("data//Workout.csv");
CsvHandler.isometricLoad("data//Isometric.csv");
CsvHandler.cardioLoad("data//Cardio.csv");
CsvHandler.strengthLoad("data//Strength.csv");



Scanner scnr = new Scanner(System.in);
strengthMenu(scnr);


Cardio.map.forEach((k, v) -> System.out.println(v.toString()));
Isometric.map.forEach((k, v) -> System.out.println(v.toString()));
Strength.map.forEach((k, v) -> System.out.println(v.toString()));

//Workout.map.forEach((k, v) -> System.out.println(v.toString()));


//Strength strength1 = Main.map.get(3);
//strength1.setSet(StrParse.toStrengthSet("9,9,9,9"));

CsvHandler.overWrite();






}


}






