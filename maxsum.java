/*
java -version:
    openjdk version "1.8.0_232"
    OpenJDK Runtime Environment (AdoptOpenJDK)(build 1.8.0_232-b09)
    OpenJDK 64-Bit Server VM (AdoptOpenJDK)(build 25.232-b09, mixed mode)
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
        System.out.println(max_wrapper(args[0]));
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

    public static int rollback(String filename){
        triangle = new ArrayList<List<Integer>>();
        read_and_list(filename);

        for(int i=triangle.size()-2; i >= 0; --i ){
            for(int j=0; j<triangle.get(i).size(); ++j ){
                if(prime(triangle.get(i).get(j)) ){
                    triangle.get(i).set(j,0);
                    continue;
                }

                int[] temp = {0,0,0};

                if( j-1 >= 0 ){
                    int consider = triangle.get(i+1).get(j-1);
                    if( !prime(consider) ){
                        temp[0] = consider;
                    }
                }
                if( j+1 < triangle.get(i+1).size()){
                    int consider = triangle.get(i+1).get(j+1);
                    if( !prime(consider) ){
                        temp[1] = consider;
                    }
                }
                int consider = triangle.get(i+1).get(j);
                if( !prime(consider) ){
                    temp[2] = consider;
                }
                triangle.get(i).set(j,Math.max(Math.max(temp[0],temp[1]),temp[2]) + triangle.get(i).get(j));
            }
        }

        return triangle.get(0).get(0);
    }

}