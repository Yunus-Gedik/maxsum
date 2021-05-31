/*
java -version:
    java version "1.8.0_291"
    Java(TM) SE Runtime Environment (build 1.8.0_291-b10)
    Java HotSpot(TM) 64-Bit Server VM (build 25.291-b10, mixed mode)
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


class maxsum {

    private static List<List<Integer>> triangle;

    public static void main(String[] args) {
        if(args.length != 1 ){
            System.out.println("Example usage: java maxsum input.txt");
            return;
        }

        // Recursive brute force approach.
        System.out.println(max(args[0]));
        // Dynamic programming with bottom to top approach.
        System.out.println(rollback(args[0]));

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
                triangle.add( new ArrayList<Integer>(Collections.singletonList(sc.nextInt())));
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

    public static int max(String filename){

        triangle = new ArrayList<List<Integer>>();
        read_and_list(filename);

        int maxvalue = max_wrapped(0,0,0);

        if(maxvalue == -1){
            System.out.println("There is no possible way reaching the end of pyramid! Prime numbers are blocking way.");
        }

        return maxvalue;
    }

    public static int max_wrapped(int layer,int index,int accumulated){
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
                max_wrapped(layer+1, index, accumulated) ,
                max_wrapped(layer+1, index+1,   accumulated) ) ;

    }

    public static int rollback(String filename){
        triangle = new ArrayList<List<Integer>>();
        read_and_list(filename);

        int maxvalue = rollback_wrapped(filename);

        if(maxvalue == -1){
            System.out.println("There is no possible way reaching the end of pyramid! Prime numbers are blocking way.");
        }
        return maxvalue;
    }

    public static int rollback_wrapped(String filename){

        for(int i=triangle.size()-1; i >= 0; --i ){
            for(int j=0; j<triangle.get(i).size(); ++j ){

                // -1 as blocked way.
                if(prime(triangle.get(i).get(j))){
                    triangle.get(i).set(j,-1);
                    continue;
                }

                // Last layer
                if( i == triangle.size()-1){
                    continue;
                }

                // Bottom left, right and down values.
                int[] temp = {0,0};

                temp[0] = triangle.get(i+1).get(j+1);
                temp[1] = triangle.get(i+1).get(j);

                // Cannot reach end of pyramid
                if(temp[0] == -1 &&temp[1] == -1){
                    triangle.get(i).set(j,-1);
                }
                else {
                    triangle.get(i).set(j, Math.max(temp[0], temp[1]) + triangle.get(i).get(j));
                }
            }
        }

        return triangle.get(0).get(0);
    }

}