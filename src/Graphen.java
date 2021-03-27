import java.lang.reflect.Array;
import java.util.*;

public class Graphen {

    public static void main(String[] args){

        //Havel-Hakimi
        //havelHakimi(new int[]{1,2,2,2,3,4});


        //basicInfo(new int[] {4,4,4,4,5,5});


        // Automorphismen
        /*
https://sagecell.sagemath.org/

H = Graph()
H.add_vertices([1,2,3,4,5,6,7,8])
H.add_edges([(,),(,),(,),(,),(,),(,),(,),(,),(,),(,),(,),(,),(,),(,),(,),(,)])
H.show()
H.automorphism_group().list()

         */
        //combineCycle("(1,2,3)", "(1,2,3)(4,5)", 8);        // automorphismus1, automorphismus2, knotenmange

        int a[][]= {
                {0,1,0,1},
                {0,0,1,1},
                {1,0,0,0},
                {0,1,0,1}
        };

        //Adjazenzmatrix
        //adjazenzmatrix(a);
        //übergangsMatrix(a);


        //Gale-Shapley
        int[][] frauen = {
                {3,4,2,1},
                {2,1,3,4},
                {3,1,2,4},
                {4,3,2,1}
        };

        int[][] männer = {
                {1,3,4,2},
                {4,1,3,2},
                {4,3,1,2},
                {3,4,1,2}
        };

        //Algorithmus 1:
        /*
            Aus der Vorlesung
         */
        //System.out.println(galeShapleyAlg1(frauen, männer));

        //Algorithmus 2:
        /*
          Beschreibung:
            - Wäahlen Sie stets unter allen noch nicht verlobten Mäannern denjenigen
              mit dem niedrigsten Index.
            - Bestimmen Sie fur diesen Mann seine nächste Verlobte
         */
        //System.out.println(galeShapleyAlg2(frauen, männer));

        //Algorithmus 3:
        /*
          Beschreibung:
            - Wäahlen Sie stets unter allen noch nicht verlobten Mäannern denjenigen
              mit dem höchsten Index.
            - Bestimmen Sie fur diesen Mann seine nächste Verlobte
         */
        //System.out.println(galeShapleyAlg3(frauen, männer));
    }

    //Havel Hakimi
    public static void havelHakimi(int[] array){
        Arrays.sort(array);

        System.out.print("(" + array[0]);
        for(int i = 1; i < array.length; i++){
            System.out.print(", " + array[i]);
        }
        System.out.println(")");

        int reduce = array[array.length-1];
        int[] newArray = new int[array.length-1];

        for(int i = 0; i< newArray.length; i++){
            newArray[i] = array[i];
        }
        for(int i = 0; i < reduce; i++){
            newArray[newArray.length -1 - i]--;
        }

        int length = 0;
        for(int i = 0; i< newArray.length; i++){
            if(newArray[i] < 0) {
                System.out.println("Fehler");

            }
            if(newArray[i] != 0)
                length++;
        }
        int[] neuesArray = new int[length];
        int count = 0;
        for(int i = 0; i< newArray.length; i++){
            if(newArray[i] != 0){
                neuesArray[count] = newArray[i];
                count++;
            }
        }
        Arrays.sort(neuesArray);

        if(neuesArray.length > 0)
            havelHakimi(neuesArray);
    }


    //basic Information
    public static void basicInfo(int[] array){
        int knoten = array.length;
        System.out.println("|V| = " + knoten);
        double kanten1 = 0;
        for(int i = 0; i < array.length; i++){
            kanten1 += array[i];
        }
        kanten1 /= 2;
        System.out.println("|E| = "+ kanten1);
        if(kanten1 - (int)kanten1 != 0) {
            System.out.println("Graph lässt sich nicht konstruieren! Die Summe der Knotengerade ist ungerade");
            return;
        }
        System.out.println("");
        System.out.println("-----Havel Hakimi----");
        try {
            havelHakimi(array);
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("! Havel Hakimi sagt der Graph lässt sich nicht konstruieren !");
            return;
        }

        int kanten = (int)kanten1;
        System.out.println("");
        System.out.println("-----Zusammenhang:----");
        int highestDegree = 0;
        boolean hasNull = false;
        for(int i = 0; i < array.length; i++){
            if(array[i] == 0)
                hasNull = true;
            if(array[i] > highestDegree)
                highestDegree = array[i];
        }


        if(highestDegree >= knoten -1)
            System.out.println("Der Graph ist Zusammenhängend, da der Knoten mit Grad "+ highestDegree+ " mit allen anderen \n Knoten verbunden sein muss");
        else if(highestDegree >= knoten-2 && !hasNull)
            System.out.println("Der Knoten mit dem Grad "+ highestDegree + "(ab jetzt v) ist mit allen außer einem Knoten verbunden. Da es keinen Knoten mit Grad 0 gibt,\nmuss der Knoten der kein Nachbar von v ist eine Kante zu einem Nachbarn von v haben. Damit ist der Graph zusammenhängend");
        else
            System.out.println("Keine Aussage versuche ein Beispiel zu finden");
        System.out.println("");
        System.out.println("-----Planarität:----");
       //planarität |E| <= 3 * |V| - 6
        System.out.println("|E| <= 3 * |V| - 6");
        System.out.println(kanten + " <= " + (3 * knoten - 6));
        System.out.println( "=> " +(kanten <= (3 * knoten - 6) ? "der Graph kann planar sein" : "der graph kann nicht Planar sein"));
        System.out.println("");
        System.out.println("Auf Minoren prüfen");
        int k33Count = 0;
        int k5Count = 0;
        for(int i = 0; i < array.length; i++){
            if(array[i] >= 4)
                k5Count++;
            if(array[i] >= 3)
                k33Count++;
        }

        if(k5Count < 5 && k33Count < 6)
            System.out.println("Der K5 und der K33 sind nicht als Minor enthalten, da es weniger als 6 Knoten mit deg(v) >= 3 und weniger als 5 Knoten mit deg(v) >= 4 gibt \n=> Damit ist der Graph planar");

        else if(k5Count < 5)
            System.out.println("Der K5 kann kein Minor sein, da es weniger als 5 Knoten mit deg(v) >= 4 gibt \nder K33 kann jedoch ein Minor sein das muss manuel geprüft werden");

        else if(k33Count < 6)
            System.out.println("Der K33 kann kein Minor sein, da es weniger als 6 Knoten mit deg(v) >= 3 gibt \n!der K5 kann jedoch ein Minor sein das muss manuel geprüft werden");
        else
            System.out.println("Ob der K3 und der K55 als Minoren enthalten sind muss manuel geprüft werden");
        System.out.println("");
        System.out.println("Hast du Informationen über die Flächen? Dann prüfe: \n f - |E| +|V| = 2 ");




        System.out.println("");
        System.out.println("-----Baum----");
        System.out.println("|E| = |V| - 1");
        System.out.println(kanten + " = " + (knoten - 1));
        if(kanten == (knoten - 1)){
            System.out.println("Der Graph ist kreisfrei \n=> WENN der Graph zusammenhängend ist,\n" +
                    "dann ist er auch ein Baum");
        }else if( kanten < (knoten - 1)){
                System.out.println("=> Der Graph ist KEIN Baum, da er nicht zusammenhängend sein kann");
        }else{
            System.out.println("=> Der Graph ist KEIN Baum, da er einen Kreis hat");
        }

        System.out.println("");
        System.out.println("-----Euler Kreis----");
        boolean hatEuler = true;
        for(int i = 0; i< array.length; i++){
            if(array[i] % 2 != 0)
                hatEuler = false;
        }
        if(hatEuler)
            System.out.println("WENN der Graph zusammenhängend ist,\n" +
                    "dann hat er EINEN Eulerkreis, da alle Knoten einen gerade positiven Knotengrad haben");
        else
            System.out.println("Der Graph hat KEINEN Eulerkreis, da NICHT alle Knoten einen gerade positiven Knotengrad haben");


        System.out.println("");
        System.out.println("-----Euler Tour----");
        boolean hatEulerTour = true;
        int tourCounter = 0;
        for(int i = 0; i< array.length; i++){
            if(array[i] % 2 != 0)
                tourCounter++;
        }
        if(tourCounter == 0 || tourCounter == 2)
            System.out.println("WENN der Graph zusammenhängend ist,\n" +
                    "dann hat er EINE Eulertour, da alle Knoten oder alle außer 2 einen gerade positiven Knotengrad haben");
        else
            System.out.println("Der Graph hat KEINE Eulertour, da NICHTalle Knoten oder alle außer 2 einen gerade positiven Knotengrad haben");

        System.out.println("");
        System.out.println("-----Hamilton Kreis----");
        double bedingung = knoten;
        bedingung /= 2;
        System.out.println( "deg(v) >= |V|/2 = "+bedingung +"  , für alle v aus V" );

        boolean hasHamilton = true;
        boolean hasEins = false;
        for(int i = 0; i < array.length; i++){
            if(array[i]<bedingung){
                hasHamilton = false;
            }
            if(array[i] == 1)
                hasEins = true;
        }
        if(hasEins)
            System.out.println("Der Graph hat KEINEN Hamiltonkreis. Es gibt einen Knoten u vom Grad 1. Der einzige Nachbar von u muss somit zweimal besucht werden. Ein Kreis\n" +
                    "besucht (nach Vorlesung) aber einen Knoten h\u007Fochstens einmal.");
        else if(hasHamilton)
            System.out.println("WENN der Graph zusammenhängend ist,\n" +
                    "dann hat EINEN Hamiltonkreis.");
        else
            System.out.println("Es lässt sich anhand dieser Bedingung nicht sagen \n"+
                    "=> finde ein Beispiel (vielleicht der bipartite Graph Kmn)");

        System.out.println("");
        System.out.println("-----Färbbarkeit----");
        System.out.println("Der Graph ist 4 färbbar, wenn er planar ist.");
        System.out.println("Meistens gibt es nur eine Möglichkeit bei dieser Fragestellung den Graphen zu zeichenen \nfinde diese und zeige, dass sie x-färbbar ist");
    }


    // Automorphismen

    public static void combineCycle(String a1, String a2, int knotenMenge){
        System.out.println(a1 + " ∘ " + a2);
        System.out.println(combineAutomorphism(a1, a2, knotenMenge));

    }
    public static String combineAutomorphism(String a1, String a2, int knotenMenge){
        a1 = a1.replaceAll(" ", "");
        a2 = a2.replaceAll(" ", "");
        if(a1.equals("()")) {
            if (a2.equals("()"))
                return "()";
            return buildCircleNotation(buildMapKomma(a2, knotenMenge));
        }
        if(a2.equals("()"))
            return buildCircleNotation(buildMapKomma(a1, knotenMenge));

        Map<Integer, Integer> abbildung1 = buildMapKomma(a1, knotenMenge);
        Map<Integer, Integer> abbildung2 = buildMapKomma(a2, knotenMenge);


        Map<Integer, Integer> combinedMap = new HashMap();
        for(int i = 1; i < knotenMenge+1; i++){
            int urbild = i;
            int bild;
            if(abbildung2.containsKey(i))
                bild = abbildung2.get(i);
            else
                bild = urbild;

            if(abbildung1.containsKey(bild))
                bild =abbildung1.get(bild);
            else
                bild = bild;

            combinedMap.put(urbild, bild);
        }

        return buildCircleNotation(combinedMap);

    }

    static Map buildAbbildungsMap(String auto, int knotenMenge){
        auto = auto.substring(1, auto.length()-1);
        String[] array = auto.split("\\)\\(");

        Map<Integer, Integer> map = new HashMap();
        for(int i = 0; i < array.length; i++){

            for(int j = 0; j < array[i].length()-1; j++){
                int urbild = Integer.parseInt(array[i].charAt(j) + "");
                int bild = Integer.parseInt(array[i].charAt(j+1) + "");
                map.put(urbild, bild);
            }
            int urbild = Integer.parseInt(""+array[i].charAt(array[i].length()-1));
            int bild = Integer.parseInt("" + array[i].charAt(0));
            map.put(urbild, bild);
        }
        for(int i = 1; i < knotenMenge+1; i++){
            if(!map.containsKey(i)){
                map.put(i,i);
            }
        }

        return map;
    }

    static Map buildMapKomma(String auto, int knotenMenge){
        auto = auto.substring(1, auto.length()-1);
        String[] array = auto.split("\\)\\(");
        Map<Integer, Integer> map = new HashMap();

        for(int i = 0; i < array.length; i++){
            String[] cycle = array[i].split(",");
            for(int j = 0; j < cycle.length-1; j++){

                int urbild = Integer.parseInt(cycle[j]);
                int bild = Integer.parseInt(cycle[j+1]);
                map.put(urbild, bild);
            }
            int urbild = Integer.parseInt(cycle[cycle.length - 1]);
            int bild = Integer.parseInt("" + cycle[0]);
            map.put(urbild, bild);
        }

        for(int i = 1; i < knotenMenge+1; i++){
            if(!map.containsKey(i)){
                map.put(i,i);
            }
        }
        return map;
    }

    static String buildCircleNotation(Map<Integer, Integer> map){
        int knots = map.size();

        String returnString = "";
        for(int i = 1; i < knots+1; i++){
            int startBild = i;
            if(map.get(startBild) == startBild)
                continue;
            String circle = "(";


            if(!returnString.contains(startBild+"")){
                circle += startBild;
                boolean circleColsed = false;
                int current = startBild;
                while (!circleColsed){
                    if(map.get(current) == startBild)
                        circleColsed = true;
                    else {
                        circle += "," + map.get(current);
                        current = map.get(current);
                    }
                }
                circle += ")";
                returnString += circle;
            }

        }
        if(returnString.equals(""))
            return "()";

        return returnString;
    }

    //galeShapley-Alg:

    //aus Vorlesung/Übung
    static String galeShapleyAlg1(int[][] frauenPräferenzen, int[][] männerPräferenzen){

        Map<Integer, Integer> verlobungen = new HashMap<>(); //Verlobungen f -> m
        String verlobungSequenz = "";
        boolean alleVerlobt = false;
        int currentMan = 1;
        int counter = 1;
        while(!alleVerlobt){
            int nextTry = getNextTry(männerPräferenzen[currentMan-1]);

            if(!verlobungen.containsKey(nextTry)){
                verlobungen.put(nextTry, currentMan);
                verlobungSequenz += counter + ": (f" + nextTry + " , m" + currentMan + ")\n";
                counter++;
            }else{
                int alterMann = verlobungen.get(nextTry);
                if(istBesser(currentMan, alterMann, frauenPräferenzen[nextTry-1])){
                    löschFrau(nextTry, männerPräferenzen[alterMann-1]);
                    verlobungen.remove(nextTry);
                    verlobungen.put(nextTry, currentMan);
                    verlobungSequenz += counter + ": (f" + nextTry + " , m" + currentMan + ")\n";
                    counter++;
                }else{
                    löschFrau(nextTry, männerPräferenzen[currentMan-1]);
                }
            }

            //determine next Man
            List<Integer> verlobteMänner = new ArrayList<>();
            for(Map.Entry<Integer, Integer> e : verlobungen.entrySet()){
                verlobteMänner.add(e.getValue());
            }
            if(verlobteMänner.size() == männerPräferenzen.length){
                alleVerlobt = true;
                verlobungSequenz += "-Matching am Ende- \n";
                for(Map.Entry<Integer, Integer> e : verlobungen.entrySet()){
                    verlobungSequenz += "Frau" + e.getKey() + " heiratet Mann" + e.getValue() + "\n";
                }
                break;
            }
            do{
                if(currentMan == männerPräferenzen.length)
                    currentMan =1;
                else
                    currentMan++;
            }
            while (verlobteMänner.contains(currentMan));
        }
        return verlobungSequenz;
    }

    //abweichend von Übung niedrigster Index verlobt sich als nächstes
    static String galeShapleyAlg2(int[][] frauenPräferenzen, int[][] männerPräferenzen){

        Map<Integer, Integer> verlobungen = new HashMap<>(); //Verlobungen f -> m
        String verlobungSequenz = "";
        boolean alleVerlobt = false;
        int currentMan = 1;
        int counter = 1;
        while(!alleVerlobt){
            int nextTry = getNextTry(männerPräferenzen[currentMan-1]);

            if(!verlobungen.containsKey(nextTry)){
                verlobungen.put(nextTry, currentMan);
                verlobungSequenz += counter + ": (f" + nextTry + " , m" + currentMan + ")\n";
                counter++;
            }else{
                int alterMann = verlobungen.get(nextTry);
                if(istBesser(currentMan, alterMann, frauenPräferenzen[nextTry-1])){
                    löschFrau(nextTry, männerPräferenzen[alterMann-1]);
                    verlobungen.remove(nextTry);
                    verlobungen.put(nextTry, currentMan);
                    verlobungSequenz += counter + ": (f" + nextTry + " , m" + currentMan + ")\n";
                    counter++;
                }else{
                    löschFrau(nextTry, männerPräferenzen[currentMan-1]);
                }
            }

            //determine next Man
            List<Integer> verlobteMänner = new ArrayList<>();
            for(Map.Entry<Integer, Integer> e : verlobungen.entrySet()){
                verlobteMänner.add(e.getValue());
            }
            for(int i = 1; i < männerPräferenzen.length+5; i++){
                if(i == männerPräferenzen.length+1){
                    alleVerlobt = true;
                    verlobungSequenz += "-Matching am Ende- \n";
                    for(Map.Entry<Integer, Integer> e : verlobungen.entrySet()){
                        verlobungSequenz += "Frau" + e.getKey() + " heiratet Mann" + e.getValue() + "\n";
                    }
                    break;
                }

                if(verlobteMänner.contains(i))
                    continue;
                else{
                    currentMan = i;
                    break;
                }

            }
        }
        return verlobungSequenz;
    }

    //abweichend von Übung höchster Index verlobt sich als nächstes
    static String galeShapleyAlg3(int[][] frauenPräferenzen, int[][] männerPräferenzen){

        Map<Integer, Integer> verlobungen = new HashMap<>(); //Verlobungen f -> m
        String verlobungSequenz = "";
        boolean alleVerlobt = false;
        int currentMan = männerPräferenzen.length;
        int counter = 1;
        while(!alleVerlobt){
            int nextTry = getNextTry(männerPräferenzen[currentMan-1]);

            if(!verlobungen.containsKey(nextTry)){
                verlobungen.put(nextTry, currentMan);
                verlobungSequenz += counter + ": (f" + nextTry + " , m" + currentMan + ")\n";
                counter++;
            }else{
                int alterMann = verlobungen.get(nextTry);
                if(istBesser(currentMan, alterMann, frauenPräferenzen[nextTry-1])){
                    löschFrau(nextTry, männerPräferenzen[alterMann-1]);
                    verlobungen.remove(nextTry);
                    verlobungen.put(nextTry, currentMan);
                    verlobungSequenz += counter + ": (f" + nextTry + " , m" + currentMan + ")\n";
                    counter++;
                }else{
                    löschFrau(nextTry, männerPräferenzen[currentMan-1]);
                }
            }

            //determine next Man
            List<Integer> verlobteMänner = new ArrayList<>();
            for(Map.Entry<Integer, Integer> e : verlobungen.entrySet()){
                verlobteMänner.add(e.getValue());
            }
            for(int i = männerPräferenzen.length; i >= 0; i--){
                if(i == 0){
                    alleVerlobt = true;
                    verlobungSequenz += "-Matching am Ende- \n";
                    for(Map.Entry<Integer, Integer> e : verlobungen.entrySet()){
                        verlobungSequenz += "Frau" + e.getKey() + " heiratet Mann" + e.getValue() + "\n";
                    }
                    break;
                }

                if(verlobteMänner.contains(i))
                    continue;
                else{
                    currentMan = i;
                    break;
                }

            }
        }
        return verlobungSequenz;
    }

    //Hilfsfunktionen
    static int getNextTry(int[] präferenzen){
        for(int i = 0; i< präferenzen.length; i++){
            if(präferenzen[i] != -1)
                return präferenzen[i];
        }
        System.out.println("Fehler kann nicht sein ein Mann wurde von allen abgelehnt");
        return -1;
    }

    static boolean istBesser(int neuerMann, int alterMann, int[] präferenzen){
        int neuerMannScore = 100000;
        for(int i = 0; i < präferenzen.length; i++){
            if(neuerMann == präferenzen[i]){
                neuerMannScore = i;
                break;
            }
        }
        int alterMannScore = 100000;
        for(int i = 0; i < präferenzen.length; i++){
            if(alterMann == präferenzen[i]){
                alterMannScore = i;
                break;
            }
        }
        //System.out.println("Alter Mann"+ alterMann + ": " + alterMannScore + ", Neuer Mann"+neuerMann+": "+neuerMannScore);
        return neuerMannScore < alterMannScore;
    }

    static void löschFrau(int frau, int[] präferenzen){
        for(int i = 0; i < präferenzen.length; i++){
            if(präferenzen[i] == frau) {
                präferenzen[i] = -1;
                return;
            }
        }
        return;
    }

    //Adjasenzmatrix
    public static void adjazenzmatrix(int[][] matrix){
        System.out.println("Adjanzenzmatrix (Pfade der Länge 1):");
        printMatrix(matrix);

        int[][] m2 = multiplyMatrices(matrix, matrix);

        System.out.println("Pfade der Länge 2:");
        printMatrix(m2);
        System.out.println("");

        int[][] m3 = multiplyMatrices(m2, matrix);

        System.out.println("Pfade der Länge 3:");
        printMatrix(m3);
        System.out.println("");

        int[][] m4 = multiplyMatrices(m3, matrix);

        System.out.println("Pfade der Länge 4:");
        printMatrix(m4);
        System.out.println("");


        System.out.println("------- Pfade der Länge <= x --------");
        int[][] m0 = new int[matrix.length][matrix[0].length];

        for(int i = 0; i< m0.length; i++){
            m0[i][i] = 1;
        }

        System.out.println("Pfade der Länge <= 1:");
        int [][] mKG1 = matrixAdd(matrix, m0);
        printMatrix(mKG1);
        System.out.println("");

        System.out.println("Pfade der Länge <= 2:");
        int [][] mKG2 = matrixAdd(mKG1, m2);
        printMatrix(mKG2);
        System.out.println("");

        System.out.println("Pfade der Länge <= 3:");
        int [][] mKG3 = matrixAdd(mKG2, m3);
        printMatrix(mKG3);
        System.out.println("");

        System.out.println("Pfade der Länge <= 4:");
        int [][] mKG4 = matrixAdd(mKG3, m4);
        printMatrix(mKG4);
        System.out.println("");

    }

    public static void übergangsMatrix(int[][] m){
        System.out.println("Adjanzenzmatrix (Pfade der Länge 1):");
        double[][] matrix = buildÜbergang(m);
        printMatrix(matrix);

        double[][] m2 = multiplyMatrices(matrix, matrix);

        System.out.println("Pfade der Länge 2:");
        printMatrix(m2);
        System.out.println("");

        double[][] m3 = multiplyMatrices(m2, matrix);

        System.out.println("Pfade der Länge 3:");
        printMatrix(m3);
        System.out.println("");

        double[][] m4 = multiplyMatrices(m3, matrix);

        System.out.println("Pfade der Länge 4:");
        printMatrix(m4);
        System.out.println("");


        System.out.println("------- Pfade der Länge <= x --------");
        double[][] m0 = new double[matrix.length][matrix[0].length];

        for(int i = 0; i< m0.length; i++){
            m0[i][i] = 1;
        }

        System.out.println("Pfade der Länge <= 1:");
        double [][] mKG1 = matrixAdd(matrix, m0);
        printMatrix(mKG1);
        System.out.println("");

        System.out.println("Pfade der Länge <= 2:");
        double [][] mKG2 = matrixAdd(mKG1, m2);
        printMatrix(mKG2);
        System.out.println("");

        System.out.println("Pfade der Länge <= 3:");
        double [][] mKG3 = matrixAdd(mKG2, m3);
        printMatrix(mKG3);
        System.out.println("");

        System.out.println("Pfade der Länge <= 4:");
        double [][] mKG4 = matrixAdd(mKG3, m4);
        printMatrix(mKG4);
        System.out.println("");

    }

    static double[][] buildÜbergang(int[][] matrix){
        double[][] übergang = new double[matrix.length][matrix[0].length];
        for(int y = 0; y < matrix.length; y++){
            double sum = 0;
            for(int x = 0; x < matrix[0].length; x++){
                sum += matrix[y][x];
            }
            for(int x = 0; x < matrix[0].length; x++){
                übergang[y][x] = matrix[y][x] /  sum;
            }
        }
        return übergang;
    }

    static void printMatrix(int[][] matrix){
        for(int y = 0; y < matrix.length; y++){
            for(int x = 0; x < matrix[0].length; x++){
                System.out.print(matrix[y][x] + " ");
            }
            System.out.println("");
        }
    }

    static void printMatrix(double[][] matrix){
        for(int y = 0; y < matrix.length; y++){
            for(int x = 0; x < matrix[0].length; x++){
                System.out.print(matrix[y][x] + "   ");
            }
            System.out.println("");
        }
    }

    public static int[][] matrixAdd(int[][] A, int[][] B) {
        // Check if matrices have contents
        if ((A.length < 0) || (A[0].length < 0)) return B;
        if ((B.length < 0) || (B[0].length < 0)) return A;

        // create new matrix to store added values in
        int[][] C = new int[A.length][A[0].length];

        for (int i = 0; i < A.length; i++)
        {
            for (int j = 0; j < A[i].length; j++)
            {
                C[i][j] = A[i][j] + B[i][j];
            }
        }
        return C;
    }

    public static int[][] multiplyMatrices(int[][] m1, int[][] m2) {
        int[][] ergebnismatrix = null;

        if (m1[0].length == m2.length) {
            int zeilenm1 = m1.length;
            int spaltenm1 = m1[0].length;
            int spalenm2 = m2[0].length;

            ergebnismatrix = new int[zeilenm1][spalenm2];

            for (int i = 0; i < zeilenm1; i++) {
                for (int j = 0; j < spalenm2; j++) {
                    ergebnismatrix[i][j] = 0;
                    for (int k = 0; k < spaltenm1; k++) {
                        ergebnismatrix[i][j] += m1[i][k] * m2[k][j];
                    }
                }
            }
        } else {
            int zeilen = m1.length;
            int spalten = m1[0].length;

            ergebnismatrix = new int[zeilen][spalten];
            for (int i = 0; i < m1.length; i++) {
                for (int j = 0; j < m1[0].length; j++) {
                    ergebnismatrix[i][j] = 0;
                }
            }
        }
        return ergebnismatrix;
    }


    public static double[][] matrixAdd(double[][] A, double[][] B) {
        // Check if matrices have contents
        if ((A.length < 0) || (A[0].length < 0)) return B;
        if ((B.length < 0) || (B[0].length < 0)) return A;

        // create new matrix to store added values in
        double[][] C = new double[A.length][A[0].length];

        for (int i = 0; i < A.length; i++)
        {
            for (int j = 0; j < A[i].length; j++)
            {
                C[i][j] = A[i][j] + B[i][j];
            }
        }
        return C;
    }

    public static double[][] multiplyMatrices(double[][] m1, double[][] m2) {
        double[][] ergebnismatrix = null;

        if (m1[0].length == m2.length) {
            int zeilenm1 = m1.length;
            int spaltenm1 = m1[0].length;
            int spalenm2 = m2[0].length;

            ergebnismatrix = new double[zeilenm1][spalenm2];

            for (int i = 0; i < zeilenm1; i++) {
                for (int j = 0; j < spalenm2; j++) {
                    ergebnismatrix[i][j] = 0;
                    for (int k = 0; k < spaltenm1; k++) {
                        ergebnismatrix[i][j] += m1[i][k] * m2[k][j];
                    }
                }
            }
        } else {
            int zeilen = m1.length;
            int spalten = m1[0].length;

            ergebnismatrix = new double[zeilen][spalten];
            for (int i = 0; i < m1.length; i++) {
                for (int j = 0; j < m1[0].length; j++) {
                    ergebnismatrix[i][j] = 0;
                }
            }
        }
        return ergebnismatrix;
    }




}
