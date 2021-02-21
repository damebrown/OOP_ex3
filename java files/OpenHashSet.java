public class OpenHashSet extends SimpleHashSet {

    /**the class's constants**/
    /**counts the set's size. updated when object deleted or added */
    private int sizeCounter=0;
    /**the hash table**/
    private OpenCell[] openHashTable;
    /**the upper capacity**/
    private float upperLoadFactor;
    /**the lower capacity**/
    private float lowerLoadFactor;
    /**the capacity of the set**/
    private int capacity;

    /**
     * Constructs a new, empty closedHashTable with the specified load factors, and the default initial capacity (16).
     * @param upperLoadFactor The upper load factor of the hash closedHashTable.
     * @param lowerLoadFactor The lower load factor of the hash closedHashTable.
     */
    public OpenHashSet(float upperLoadFactor, float lowerLoadFactor){
        openHashTable = openHashTableFactory(upperLoadFactor,lowerLoadFactor, INITIAL_CAPACITY);
    }

    /**
     * A default constructor. Constructs a new, empty closedHashTable with default initial capacity (16), upper load factor (0.75)
     * and lower load factor (0.25).
     */
    public OpenHashSet(){
        openHashTable = openHashTableFactory(DEFAULT_HIGHER_CAPACITY,DEFAULT_LOWER_CAPACITY, INITIAL_CAPACITY);
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one. Duplicate values should be ignored.
     * The new closedHashTable has the default values of initial capacity (16), upper load factor (0.75),
     * and lower load factor (0.25).
     * @param data Values to add to the set.
     */
    public OpenHashSet(java.lang.String[] data){
        openHashTable = openHashTableFactory(DEFAULT_HIGHER_CAPACITY, DEFAULT_LOWER_CAPACITY, INITIAL_CAPACITY);
        for (int index=0; index<data.length;index++){
            add(data[index]);
        }
    }

    /**
     * Add a specified element to the set if it's not already in it.
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    public boolean add(java.lang.String newValue){
        //if (contains)- return false
        if (!contains(newValue)){
            //checking size and calling rehash function accordingly
            if (getFutureLoadFactor()>=upperLoadFactor){
                //if load factor is bigger the the upper rehash
                reHash(SimpleHashSet.INCREASING_EXPONENT);
            }
            //get proper index from 'getCorrectedIndex'
            int index = getCorrectedIndex(newValue);
            //assigning new value to the index, updating sizeCounter
            openHashTable[index].cell.add(newValue);
            sizeCounter++;
            //return true-
            return true;
        }
        //if newValue is contained, return false-
        return false;
    }

    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    public boolean contains(java.lang.String searchVal){
        //getting hash value for searchValue
        int index = getCorrectedIndex(searchVal);
        //making sure the cell is not null
        if (openHashTable[index].getSize()>0){
            //using the hash value, check if searchValue in the cell index
            if (openHashTable[index].cell.contains(searchVal)){
            //if so, return true
                return true;
            }
        //else return false
        } return false;
    }

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    public boolean delete(java.lang.String toDelete){
        //making sure toDelete is really in the array-
        if (contains(toDelete)){
            //to check size and rehash if needed
            if (getDeletionLoadFactor()<lowerLoadFactor){
                //rehash
                reHash(SimpleHashSet.DECREASING_EXPONENT);
            }
            //finds toDelete's suitable index
            int index = getCorrectedIndex(toDelete);
            //delete-
            openHashTable[index].cell.remove(toDelete);
            //update the size counter
            sizeCounter--;
            //return true if all went well
            return true;
        } return false;
    }

    /**
     * @return The number of elements currently in the set
     */
    public int size(){
        return sizeCounter;
    }

    /**
     * returns the current capacity of the closedHashTable
     * @return the current capacity (number of cells) of the closedHashTable
     */
    public int capacity(){
        return capacity;
    }

    /**
     * getter function for the load factor
     * @return the load factor
     */
    private float getFutureLoadFactor(){
        return ((float)(size()+1)/(float) capacity);
    }

    /**
     * getter function for the load factor after one item is deleted. called before deleting, to check
     * if the load factor will change so that there will be a need to rehash
     * @return the load factor after deletion
     */
    private float getDeletionLoadFactor(){
        return ((float)(size()-1)/(float) capacity);
    }

    /**
     * this is a 'factory' function that creates a open hash table in which every cell is an OpenCell instance,
     * which has a field of a linked list. with the wanted upper and lower load factors,
     * and wanted capacity.
     * @param wantedUpperLoadFactor the wanted Upper Load Factor
     * @param wantedLowerLoadFactor the wanted Lower Load Factor
     * @param wantedCapacity the wanted capacity of the new array
     * @return the new created OpenCell array
     */
    private OpenCell[] openHashTableFactory(float wantedUpperLoadFactor,float wantedLowerLoadFactor,int wantedCapacity){
        //creating a new array of OpenCell in the size of capacity
        OpenCell[] hashTable = new OpenCell[wantedCapacity];
        //initializing the capacity and the lower and upper load factors
        lowerLoadFactor = wantedLowerLoadFactor;
        upperLoadFactor = wantedUpperLoadFactor;
        capacity = wantedCapacity;
        //initiating an OpenCell instance in each cell-
        for (int index=0; index<capacity;index++){
            hashTable[index]=new OpenCell();
        }
        //returning the created hash table
        return hashTable;
    }

    /**
     * this function is doing the rehashing and assigning the new table to the closedHashTable field.
     * it recieves 1/2 if the function was called from the delete function (meaning if a decreasing in size is needed)
     * and recieves 2 if the function was called from the add function (meaning if an increasing in size is needed)
     * @param power the power in which to multiply the size of the existing array to get the size of the new one
     */
    private void reHash(double power){
        //saving the current capacity in order to search in the right range-
        int tempCapacityVal = capacity();
        //initializing an array of openHashCell instances, by taking the size of the original set and raising it to the
        //power of power
        capacity = (int)(power*tempCapacityVal);
        //initializing an array OpenCell's instances in the right size
        OpenCell[] newTable = openHashTableFactory(upperLoadFactor, lowerLoadFactor, capacity);
        //iterating over the existing table and moving all the elements to the new one
        for (int index=0; index<tempCapacityVal;index++){
            //making sure the current cell is not empty
            if(openHashTable[index].getSize()>0) {
                //iterating over linked list in the cell in order to rehash
                for (int subindex=0; subindex<openHashTable[index].getSize(); subindex++){
                    String stringToMove = openHashTable[index].cell.get(subindex);
                    //if it isn't, we will move all of the cell's strings to the new one in it's new suitable index
                    int correctIndex = clamp(stringToMove.hashCode());
                    //initiating a new OpenCell in the new array-
                    newTable[correctIndex].cell.add(stringToMove);
            }}
        }
        //assigning the new table to the openHashTable field
        openHashTable = newTable;
    }

    /**
     * this function is getting a string and returning a suiteable index to which the String can be inserted
     * @param searchedString the string wanted to be insterted
     * @return the index if found, -1 if not
     */
    private int getCorrectedIndex(String searchedString) {
        //calling the hashCode java function
        int hashCode = searchedString.hashCode();
        //to create corrected index- calling clamp
        int index = clamp(hashCode);
        return index;
    }


}
