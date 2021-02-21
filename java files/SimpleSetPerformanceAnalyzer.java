import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;

public class SimpleSetPerformanceAnalyzer {

    /** class constants**/
    /**the array of the sets**/
    private static SimpleSet[] setsArray;
    /**the string 'hi' **/
    private static String HI ="hi";
    /**the string of the wanted hash **/
    private static String HASH_NUM ="-13170890158";
    /**the string of 23**/
    private static String TWENTY_THREE ="23";
    /**the number to divide in inorder to make nanoseconds to milliseconds**/
    private static int NANO_TO_MILLI = 1000000;
    /**the array of the data1 file**/
    private static String[] array1 = Ex3Utils.file2array("data1.txt");
    /**the array of the data2 file**/
    private static String[] array2 = Ex3Utils.file2array("data2.txt");


    public static void main(String[] args) {
        //generating array of sets
        arrayGenerator();
        //adding all words in array 1
        iterateToAdd(array1);
        //checking if contains hi and the hash number-
        checkIfContains(HI);
        checkIfContains(HASH_NUM);
        //generating a new array of sets
        arrayGenerator();
        iterateToAdd(array2);
        //checking if contains hi and 23-
        checkIfContains(TWENTY_THREE);
        checkIfContains(HI);
    }

    /**
     * generates the wanted array of sets
     */
    private static void arrayGenerator(){
        SimpleSet[] array = new SimpleSet[5];
        array[0] = new OpenHashSet();
        array[1] = new ClosedHashSet();
        TreeSet<String> treeSet = new TreeSet<>();
        array[2] = new CollectionFacadeSet(treeSet);
        LinkedList<String> linkedList = new LinkedList<>();
        array[3] = new CollectionFacadeSet(linkedList);
        HashSet<String> hashSet = new HashSet<>();
        array[4] = new CollectionFacadeSet(hashSet);
        setsArray = array;
    }

    /**
     * iterates over the set's array and over the inputed array, and adds the strings from the array to the set
     * @param array of strings array to iterate over
     */
    private static void iterateToAdd(String[] array){
        //iterates of the setsArray
        for (int index=0; index<setsArray.length;index++){
            //initializing timing
            long timeBefore = System.nanoTime();
            //initializing iteration over the array of strings
            for(int subindex=0; subindex<array.length;subindex++){
                setsArray[index].add(array[subindex]);
                //pause time count
            } long difference = ((System.nanoTime() - timeBefore)/NANO_TO_MILLI);
            System.out.println("time for adding to "+index+" : "+difference+ " ms");
        }
    }

    private static void checkIfContains(String string){
        //iterates of the setsArray
        for (int index=0; index<setsArray.length;index++) {
            //the warmup-
            for (int times=0;times<20000;times++){
                setsArray[index].contains(string);
            }
            //initializing timing
            long timeBefore = System.nanoTime();
            //initializing iteration to get a precise result
            for (int times=0;times<1000;times++) {
                setsArray[index].contains(string);
            }
            //pause timing
            long difference = ((System.nanoTime() - timeBefore)/(1000));
            System.out.println("time for 'contains' "+string+" to "+index+" : "+difference+ " ns");
        }
    }

}

