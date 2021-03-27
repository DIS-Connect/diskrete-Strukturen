import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Logik {

    public static void main(String[] args) {
        //https://www.erpelstolz.at/gateway/formular-zentral.html
        klauselzuKNF("{{p, ¬r, s}, {p, ¬r, t}, {¬r, s, ¬t}, {¬s, t}}");

        //formelUmwandeln("(A ∨ ¬B ∨ ¬C) ∧ (¬A ∨ B) ∧ (¬A ∨ C)");
        //tableTurnAround("");


    }


    //Logik
    public static void formelUmwandeln(String str){
        String returnString = "";
        for(int i = 0; i < str.length(); i++){
           switch (str.substring(i,i+1)){
               case "⊕" -> returnString += "↔ ¬";
               case "→" -> returnString += "→";
               case "↔" -> returnString += "↔";
               case "¬" -> returnString += "¬";
               case "∨" -> returnString += "∨";
               case "∧" -> returnString += "∧";
               default -> returnString += str.substring(i,i+1);
           }
        }
        System.out.println(returnString);
    }

    public static void tableTurnAround(String table){
        String[] array = table.split("\n");

        String returnTable = "";
        returnTable += array[0] + "\n";
        returnTable += array[1] + "\n";
        for(int i = array.length - 1; i > 1; i --){
            returnTable += array[i] + System.lineSeparator();
        }
        System.out.println(returnTable);
    }

    public static void klauselzuKNF(String klausel) {
        klausel = klausel.replaceAll(" ", "");
        klausel = klausel.substring(2, klausel.length()-2);
        String[] array = klausel.split("\\},\\{");

        String returnString = "(";

        for(int i = 0; i < array.length; i++){
            returnString += "(";
            for(int j = 0; j < array[i].length(); j++){
                switch (array[i].charAt(j)+""){
                    case "," -> returnString += "∨";
                    case "¬" -> returnString += "¬";
                    default -> returnString += array[i].charAt(j);
                }
            }
            returnString += ")";
            if(i < array.length-1)
                returnString += "∧";
        }
        returnString += ")";
        System.out.println(returnString);
    }

    public static void dpll(String[][] array, String[] priority, int mode){
        //OLR
        //PLR
        //true, false


        // do something here


        //check if true

        //call recursive



        for(int i = 0; i< array.length; i++){
            if(array[i].length == 0)
                System.out.println();
                return;
        }

    }

}
