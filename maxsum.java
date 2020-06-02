/*
java -version:
    openjdk version "1.8.0_232"
    OpenJDK Runtime Environment (AdoptOpenJDK)(build 1.8.0_232-b09)
    OpenJDK 64-Bit Server VM (AdoptOpenJDK)(build 25.232-b09, mixed mode)
*/

import java.io.File; 
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;


class maxsum {

    private static List<List<Integer>> triangle;

    public static void main(String[] args) {
        if(args.length != 1 ){
            System.out.println("Example usage: java maxsum input.txt");
            return;
        }

        System.out.println(max_wrapper(args[0]));

    }

    public static boolean prime(Integer isprime){
        if( isprime ==1 || isprime == 0 ){
            return false;
        }

        else if(isprime == 2){
            return true;
        }

        for(int i=2; i<= Math.sqrt(isprime ); i += 1){
            if (isprime % i == 0){
                return false;
            }
        }

        return true;
    }

    public static void read_and_list(String filename){
        Scanner sc = null;
        try{
            File file = new File(filename);
            sc = new Scanner(file);
        }
        catch(FileNotFoundException e){
            System.out.printf("File not found! %s",filename);
            e.printStackTrace();
            System.exit(0);
        }

        int layer =0;
        int index =0;
        
        while(sc.hasNextInt()){

            if(index == 0){
                triangle.add( new ArrayList<Integer>(Arrays.asList(sc.nextInt())));
            }
            else{
                triangle.get(layer).add(sc.nextInt());
            }

            ++index;
            if(index > layer){
                ++layer;
                index = 0;
            }
        }
        sc.close();

    }

    public static int max_wrapper(String filename){

        triangle = new ArrayList<List<Integer>>(); 
        read_and_list(filename);

        int maxvalue = max(0,0,0);

        if(maxvalue == -1){
            System.out.println("There is no possible way reaching the end of pyramid! Prime numbers are blocking way.");
        }

        return maxvalue;
    }

    public static int max(int layer,int index,int accumulated){
        if(index <0 || index > layer ){
            return -1;
        }

        int myvalue = triangle.get(layer).get(index);

        if( prime(myvalue) ){
            return -1;
        }

        else if(layer == triangle.size()-1){
            return accumulated + myvalue;
        }

        accumulated += myvalue;
        return Math.max( 
               Math.max(      max(layer+1, index-1, accumulated) ,
                              max(layer+1, index,   accumulated) ) , 
                              max(layer+1, index+1, accumulated) )  ;

    }

}