import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Algebra {
    public static void main(String[] args){


        //multiplikative Gruppe modluo n
        //ordnungBestimmen(17, 1087);      //1.Wert 2. mod-Gruppe

        //alleOrdnungen(89);

        //modulo(new BigInteger("17").pow(10).toString(), "1087");     // x^y mod z :

        //modulo("-7" , "911");   // x mod z :

        //invers(23, 9991);         //1.wert  2.mod-Gruppe

        //gruppenInfo(9991);          //1. mod-Gruppe

        //tabellierung(9);         //1. modGruppe

        //EEA(91, 211);

        //Primfaktorzerlegung
        //System.out.println(primFaktorzerlegung(70).toString());
        graphModulo(16);

        //alleTeiler(1086);

        //Automorphismen Gruppe

        /*
        Bestimmen:
        https://sagecell.sagemath.org/

H = Graph()
H.add_vertices([1,2,3,4,5,6,7,8])
H.add_edges([(,),(,),(,),(,),(,),(,),(,),(,),(,),(,),(,),(,),(,),(,),(,),(,)])
H.show()
H.automorphism_group().list()
         */

        //automorphismenTabellieren("[(),(1,7)(6,8),(1,7)(3,4)(6,8),(3,4)]", 8);       //1. Automorphismen der form [()(),()] 2. Knotenmenge

        //Graphen.combineCycle("(1,2,3,4)(5,6)","(1,2,3,4)(5,6)",6);        //1./2. Automorphismen 3. Knotenmenge

        //autoOrdnung("(1,2,4)(3, 5)", 5);              //1. Automorphismus 2. Knotenmenge

        //automorphismusHoch("(1,2,3)(4,5)", 785, 6);                       //1. Automorphismus 2. hoch was?

        graphAuto(new String[]{"()", "(1,7)(6,8)","(1,7)(3,4)(6,8)","(3,4)"}, 8);

    }

    static void graphModulo(int n){
        List<Integer> list = gruppe(n);
        for(int a = 0; a < list.size()-1; a++){
            for(int b =a+1; b <list.size(); b++){
                List<Integer> all = new ArrayList<>();
                checkMod(1, list.get(a), list.get(b), n, all, list);

                if(all.size() == list.size()){
                    System.out.println("Wenn σ = " + list.get(a)+" und τ = "+ list.get(b) + " dann ist der Graph {Z*,(x, x ·n a) | a ∈{σ, τ}} zusammenhängend");
                    return;
                }
            }

        }
        System.out.println("der Graph {Z*,(x, x ·n a) | a ∈{σ, τ}} kann nicht zusammenhängend sein");
    }

    static void checkMod(int current, int a, int b,int n, List<Integer> all, List<Integer> list){
        Integer num = (current*a)%n;
        if(!all.contains(num)){
            all.add(num);
            checkMod(num, a, b,n, all, list);
        }
        num = (current*b)%n;
        if(!all.contains(num)){
            all.add(num);
            checkMod(num, a, b,n, all, list);
        }

    }

    static void graphAuto(String[] autos, int knotenmenge){
        for(int a = 0; a < autos.length-1; a++){
            for(int b =a+1; b <autos.length; b++){
                List<String> all = new ArrayList<>();
                checkAuto("()", autos[a], autos[b], knotenmenge, all);

                if(all.size() == autos.length){
                    System.out.println("Wenn σ = " + autos[a]+" und τ = "+ autos[b] + " dann ist der Graph {Z*,(x, x ·n a) | a ∈{σ, τ}} zusammenhängend");
                    return;
                }
            }

        }
        System.out.println("der Graph {Z*,(x, x ·n a) | a ∈{σ, τ}} kann nicht zusammenhängend sein");
    }

    static void checkAuto(String current, String a, String b,int knotenmenge, List<String> all){
        String auto = Graphen.combineAutomorphism(current, a,knotenmenge );
        if(!all.contains(auto)){
            all.add(auto);
            checkAuto(auto, a, b, knotenmenge, all);
        }
        auto = Graphen.combineAutomorphism(current, b,knotenmenge );
        if(!all.contains(auto)){
            all.add(auto);
            checkAuto(auto, a, b, knotenmenge, all);
        }
    }

    static void alleOrdnungen(int n){
        List<Integer> list = gruppe(n);
        for(Integer i : list){
            ordnungBestimmen(i, n);
        }
    }

    static void alleTeiler(int n){
        String str = "Teiler von " + n +" sind: [1";
        for(int i = 2; i <= n;i++){
            if(n % i == 0)
                str += ","+i+" ";
        }
        System.out.println(str+"]");
    }

    static void gruppenMember(int n){
        System.out.println(gruppe(n).toString());
    }

    static List<Integer> gruppe(int n){
        List<Integer> list = new ArrayList<>();

        for(int i = 1; i < n; i++){
            if(isTeilerFremd(i, n))
                list.add(i);
        }
        return list;
    }

    static boolean isTeilerFremd(int a, int b){
        for(int i = 2; i <= Math.min(a, b); i++){
            if(a%i == 0 && b % i == 0)
                return false;
        }
        return true;
    }

    static void tabellierung(int n){
        List<Integer> list = gruppe(n);
        int[][] array = new int[list.size()+1][list.size()+1];
        for(int i = 1; i < array.length; i++){
            array[0][i] = list.get(i-1);
            array[i][0] = list.get(i-1);
        }


        for(int y = 1; y < array.length; y++){
            for(int x = 1; x < array.length; x++){
                    array[y][x] = (array[0][x] * array[y][0])%n;
            }
        }

        for(int y = 0; y < array.length; y++){
            for(int x = 0; x < array.length; x++){
                if(x == 0 && y == 0)
                    System.out.print(Euklid.addStr("*" + n, 10));
                else
                System.out.print(Euklid.addStr(array[y][x] + "", 10));
            }
            System.out.println("\n");
        }

    }

    static List primFaktorzerlegung(int a){
        int current = a;
        int teiler = 2;
        List<Integer> faktoren = new ArrayList<>();

        while(true){
            if(current % teiler == 0) {
                if(isPrim(teiler)){
                    faktoren.add(teiler);
                    current /= teiler;
                    continue;
                }
            }
            teiler++;
            if(current == 1)
                break;
        }
        return faktoren;
    }

    static boolean isPrim(int a){

        for(int i = a -1; i > 1; i--){
            if(a % i == 0)
                return false;
        }
        return true;
    }


    public static void gruppenInfo(int n){
        List<Integer> faktoren = primFaktorzerlegung(n);
        System.out.println("Die Primfaktorzerlegung von "+n+" ist: "+ faktoren.toString());
        double size = n;
        String formel = n +" * ";
        List<Integer> used = new ArrayList<>();
        for(Integer i : faktoren){
            if(!used.contains(i)) {
                formel+= "(1 - 1/"+ i +")";
                size *= (1.0 - (1.0 / i));
                used.add(i);
            }
        }
        System.out.print("Die Gruppenmember sind: ");
        gruppenMember(n);
        System.out.println("Die Gruppengröße ist: "+formel +" = " + size);
    }

    public static void invers(int a, int b){
        Euklid e = new Euklid();
        int alpha = EEA(a, b, e)[0];
        System.out.println(e.toString());
        int invers = alpha;
        while(a < 0){
            a += b;
        }
        System.out.println("Das Inverse von "+a+" der Gruppe *"+b+" ist: (" + alpha + ")mod" +b +" = "+modulo(invers, b));
    }

    public static void modulo(String a, String b){

        BigInteger bi1, bi2, bi3;

        bi1 = new BigInteger(a);
        bi2 = new BigInteger(b);

        // perform mod operation on bi1 using bi2
        bi3 = bi1.mod(bi2);

        String str = bi1 + "\nmod " + bi2 + "= " +bi3;

        // print bi3 value
        System.out.println( str );


    }

    public static String modulo(int a, int b){

        BigInteger bi1, bi2, bi3;

        bi1 = new BigInteger(a+"");
        bi2 = new BigInteger(b+"");

        // perform mod operation on bi1 using bi2
        bi3 = bi1.mod(bi2);


        // print bi3 value
        return bi3+"";


    }

    public static void ordnungBestimmen(int a, int b){
        String output = "<"+a+"> = {";
        int current = a;
        int counter = 0;
        output += current;
        while(current != 1){

            current = (current * a) % b;
            output += ", " + current;
            counter++;
        }
        output += "}    Ordnung:" + ++counter;

        System.out.println(output);
    }

    public static void EEA(int a, int b){
        Euklid sequenz = new Euklid();
        EEA(a, b, sequenz);
        System.out.println(sequenz.toString());
    }

    static int[] EEA(int a, int b, Euklid sequenz){
        int[] alphaBeta;
        int k = 0;

        if(b % a == 0){
            sequenz.add(a,b,"-", 1, 0);
            return new int[]{1,0};
        }
            k = b/a;
            alphaBeta = EEA(b%a, a, sequenz);
            sequenz.add(a,b,k+"", alphaBeta[1] - (k * alphaBeta[0]), alphaBeta[0]);
            return new int[]{ alphaBeta[1] - (k * alphaBeta[0]), alphaBeta[0]};

    }


    //Automorphismen

    static void automorphismenTabellieren(String autos, int knotenmenge){
        autos = autos.substring(2, autos.length()-2);
        autos = autos.replaceAll(" ", "");
        String[] autosArray = autos.split("\\),\\(");
        for(int i = 0; i < autosArray.length; i++){
            autosArray[i] = "(" + autosArray[i] +")";
        }

        String[][] tabelle = new String[autosArray.length +1][autosArray.length +1];

        for(int i = 1; i < tabelle.length; i++){
            tabelle[0][i] = autosArray[i-1];
            tabelle[i][0] = autosArray[i-1];
        }


        for(int y = 1; y < tabelle.length; y++){
            for(int x = 1; x < tabelle.length; x++){
                tabelle[y][x] = Graphen.combineAutomorphism(tabelle[y][0],tabelle[0][x], knotenmenge);;
            }
        }


        for(int y = 0; y < tabelle.length; y++){
            for(int x = 0; x < tabelle.length; x++){
                if(x == 0 && y == 0)
                    System.out.print(Euklid.addStr("∘", 20));
                else
                    System.out.print(Euklid.addStr(tabelle[y][x] + "", 20));
            }
            System.out.println("\n");
        }





    }

    static void automorphismusHoch(String auto, int n, int knotenmenge){

        String current = auto;
        for(int i = 1; i < n; i++){
            current = Graphen.combineAutomorphism(current, auto, knotenmenge);
        }
        System.out.println(current);
    }

    static void autoOrdnung(String auto, int knotenmenge){
        int counter = 1;
        String current = auto;
        String ordnung = "< "+ auto+ " > = {"+current;
        while(true){
            if(current.equals("()"))
                break;
            current = Graphen.combineAutomorphism(current, auto, knotenmenge);
            ordnung += ", " + current;
            counter++;

        }
        ordnung += "}";
        System.out.println(ordnung);
        System.out.println("die Ordnung ist damit" +counter);
    }


    //Sonsiteges

    static void GraphAnalysieren(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("regularität:");
        int reg = Integer.parseInt(scanner.next());

        List<Integer> formen = new ArrayList<>();
        System.out.println("Was gibt es für Formen?  Enter zum beenden");

        while(true){
            String temp = scanner.next();
            if(temp == "\n")
                break;
            formen.add(Integer.parseInt(temp));
        }

    }

}

class Euklid{
    String alg = "";

    public void add(int a, int b, String k, int alpha, int beta){
        String strA = ""+a;
        String strB = ""+b;
        String strAlpha = ""+alpha;
        String strBeta = ""+beta;

        alg = addStr(strA, 10)  + addStr(strB, 10) + addStr(k,10) + addStr(strAlpha,10) + addStr(strBeta,10) + "\n" + alg;
    }

    static String addStr(String a, int abstand){
        int count = abstand - a.length();
        String returnString = a;
        for(int i = 0; i < count; i++){
            returnString +=" ";
        }
        return returnString;
    }

    public String toString(){
        return "a    |    b     |   k   |     α     |   β\n"
                + alg;
    }
}
