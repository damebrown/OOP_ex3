import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;


public class SimpleSetPerformanceAnalyzer {

	private static final String[] DATA_TYPES =
			new String[]{"\nOpenHashSet:\n", "\nClosedHashSet:\n", "\nTreeSet:\n", "\nLinkedList:\n", "\nHashSet:\n"};
	private static final int TO_MILLISECONDS = 1000000;
	private static final int ITERATIONS = 25000;
	private static final int WARM_UP_ITERATIONS = 50000;
	private static final int NUMBER_OF_SETS = 5;

	public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

		//Used for reflection
		java.lang.Class<SimpleSetPerformanceAnalyzer> thisClass = SimpleSetPerformanceAnalyzer.class;


		String[] data1 = Ex3Utils.file2array("data1.txt");
		String[] data2 = Ex3Utils.file2array("data2.txt");


		/* test 1 - add data 1. */

		SimpleSet[] data1Structures = newDataStructureArray();
		Method addAll = thisClass.getDeclaredMethod("addAll", String[].class, SimpleSet.class);
		long[]addTimesData1 = findTimes(data1Structures, addAll, true,  data1);
		printTimes(addTimesData1, "AddData1");

		/* test 2 - add data 2. */

		SimpleSet[] data2Structures = newDataStructureArray();
		long[]addTimesData2 = findTimes(data2Structures, addAll, true,  data2);
		printTimes(addTimesData2, "AddData2");
		/* Test 3 - contains "hi" with data 1 */

		Method contains = thisClass.getDeclaredMethod("contains", String[].class, SimpleSet.class);


		long[] containsHiTimesData1 = findTimes(data1Structures, contains, false, "hi");
		printTimes(containsHiTimesData1, "Contains_hi1");
		/* Test 4 - contains "-13170890158" with data 1 */



		long[] containsBigTimesData1 = findTimes(data1Structures, contains, false, "-13170890158");
		printTimes(containsBigTimesData1, "Contains_negative");

		/* Test 5 - contains "23" with data 2 */

		System.out.println("\nTEST 5");

		long[] contains23TimesData2 = findTimes(data2Structures, contains, false, "23");
		printTimes(contains23TimesData2, "Contains_23");

		System.out.println("\nTEST 6" + "");


		/* Test 6 - contains "hi" with data 2 */
		long[] containsHiTimesData2 = findTimes(data2Structures, contains, false, "hi");
		printTimes(containsHiTimesData2, "Contains_hi2");
	}

	private static long[] findTimes(SimpleSet[] dataStructures, Method action, boolean isAddTest, String... valueOrData)
			throws InvocationTargetException, IllegalAccessException {
		long[]times = new long[NUMBER_OF_SETS];
		for (int i = 0; i< dataStructures.length; i++){
			SimpleSet dataStructure = dataStructures[i];
			warmUp(valueOrData, dataStructure, isAddTest);
			System.out.println(DATA_TYPES[i]);
			long start = System.nanoTime();
			action.invoke(null, valueOrData, dataStructure);
			long difference = System.nanoTime() - start;
			difference = convertDifference(isAddTest, difference);
			times[i] = difference;
		}
		return times;
	}

	private static SimpleSet[] newDataStructureArray(){
		SimpleSet openHashSet = new OpenHashSet();
		SimpleSet closedHashSet = new ClosedHashSet();
		SimpleSet treeSet = new CollectionFacadeSet(new TreeSet<String>());
		SimpleSet linkedList = new CollectionFacadeSet(new LinkedList<String>());
		SimpleSet hashSet = new CollectionFacadeSet(new HashSet<String>());
		return new SimpleSet[]{openHashSet, closedHashSet, treeSet, linkedList, hashSet};
	}

	private static void addAll(String[] data, SimpleSet dataStructure){
		int iter = 1;//TODO delete
		for (String value:data){
			dataStructure.add(value);
			iter++;//TODO delete
			if (iter%1000 == 0){//TODO delete
				System.out.print(iter/1000+"% "); //TODO delete
			}
		}
	}


	private static void contains(String[] varargs, SimpleSet dataStructure){
		String value = varargs[0];
		int iter = 1;//TODO delete
		for (int i=0; i< ITERATIONS; i++){
			dataStructure.contains(value);
			iter++;//TODO delete
			if (iter%1000 == 0){//TODO delete
				System.out.print(iter/1000+"% "); //TODO delete
			}
		}
	}

	private static long convertDifference(boolean isAddTest, long difference){
		if (isAddTest)
			difference /= TO_MILLISECONDS;
		else
			difference /= ITERATIONS;
		return difference;
	}

	private static void printTimes(long[] times, String currentTest){
		System.out.printf("\nOpenHashSet_%1$s = %2$s\nClosedHashSet_%1$s = %3$s\nTreeSet_%1$s =" +
						" %4$s\nLinkedList_%1$s = %5$s\nHashSet_%1$s = %6$s",
				currentTest, times[0], times[1], times[2], times[3], times[4]);
	}

	private static void warmUp(String[] varargs, SimpleSet dataStructure, boolean isAddOperation){
		if (!isAddOperation) {
			String value = varargs[0];
			for (int i = 0; i < WARM_UP_ITERATIONS; i++)
				dataStructure.contains(value);
		}
	}
}



