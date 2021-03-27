import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Kombinatorik {

    public static void main(String[] args){
        System.out.println(Pnk(12, 3));

        //System.out.println(Snk(5, 3));

        //teilbar(100000, new int[]{3,13,17});


    }


    public static int Pnk(int n, int k){
        if(n == 0 || k == 0)
            return 0;
        if(k > n)
            return 0;
        if( n == k)
            return 1;

        return Pnk(n - 1, k - 1) + Pnk(n - k , k);
    }

    public static int Snk(int n, int k){
        if(n == k)
            return 1;

        if(k == 0)
            return 0;

        return (Snk(n-1, k-1) + k * Snk(n-1, k));
    }

    //teilbar durch


    public static void teilbar(int n, int[] array){

        int count = 0;
        for(int i = 1; i <= n; i++){

            for(int k = 0; k < array.length; k++){
                if(i % array[k] == 0){
                    count++;
                    break;
                }
            }
        }
        System.out.println("Beispile mit 3 Teilern:");
        System.out.println("|Ai| = "+n+"/i   <- abrunden");
        System.out.println("|Ai ∩ Aj| = "+n+"/kgV(i,j)   <- abrunden");
        System.out.println("|Ai ∩ Aj ∩ Ay| = "+n+"/kgV(i,j,y)   <- abrunden");
            System.out.println("");
            System.out.println("|Ai| + |Ai| + |Ai|");
            System.out.println("- ( |Ai ∩ Aj| + |Ai ∩ Aj| + |Ai ∩ Aj| )");
            System.out.println("+ |Ai ∩ Aj ∩ Ay|");


        System.out.print("Es gibt "+count+" viele Zahlen in ["+n+"], die durch ");
        for(int i = 0; i < array.length-1; i++){
            System.out.print(array[i] + ", ");
        }
        System.out.println("oder"+array[array.length-1] +" teilbar sind");

    }

}


