public class ClosedHashSet extends SimpleHashSet {

    /**the hash closedHashTable**/
    private ClosedCell[] closedHashTable;
    /**size counter-every time an element is added of deleted, the counter is updated accordingly **/
    private int sizeCounter=0;
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
    public ClosedHashSet(float upperLoadFactor, float lowerLoadFactor){
        closedHashTable = closedHashTableFactory(upperLoadFactor, lowerLoadFactor, INITIAL_CAPACITY);
    }

    /**
     * A default constructor. Constructs a new, empty closedHashTable with default initial capacity (16), upper load factor
     * (0.75) and lower load factor (0.25).
     */
    public ClosedHashSet(){
        closedHashTable = closedHashTableFactory(DEFAULT_HIGHER_CAPACITY, DEFAULT_LOWER_CAPACITY, INITIAL_CAPACITY);
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one. Duplicate values should be ignored.
     * The new closedHashTable has the default values of initial capacity (16), upper load factor (0.75),
     * and lower load factor (0.25).
     * @param data Values to add to the set.
     */
    public ClosedHashSet(java.lang.String[] data){
        closedHashTable = closedHashTableFactory(DEFAULT_HIGHER_CAPACITY, DEFAULT_LOWER_CAPACITY, INITIAL_CAPACITY);
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
        //check if newValue is in the hash table
        if (!contains(newValue)){
            //checking size and calling rehash function accordingly
            if (getFutureLoadFactor()>=upperLoadFactor){
                //if load factor is bigger the the upper rehash
                reHash(SimpleHashSet.INCREASING_EXPONENT);
                //makes sure the table doesnt contain the wanted string
            } if (hashingAdd(newValue, closedHashTable)){
                sizeCounter++;
                return true;
            }
        } return false;
    }

    /**
     * an aid function for the add and reHash function. adds an element without checking if there is a need
     * to rehash/
     * @param newValue the string to add
     * @param hashTable the table to add to
     * @return true if added, false otherwise
     */
    private boolean hashingAdd(String newValue, ClosedCell[] hashTable){
            //if it doesn't contain, we get an index of a suitable cell which is empty
            int index = getEmptyIndex(newValue, hashTable);
            //making sure the index is valid-
            if (index>=0){
                //if it is- updating the cell's isDeleted and string fields-
                hashTable[index].setString(newValue);
                hashTable[index].setIsDeleted(true);
                return true;
            }
         return false;
    }

    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    public boolean contains(java.lang.String searchVal){
        //iterating over the array in order to find the searched String
        for (int index=0; index<capacity ;index++){
            //get clamped possible index
            int possibleIndex = clamp(getPreClamped(index, searchVal.hashCode()));
            //if the cell's isDeleted is false and the cell's string is null, break
            if ((!closedHashTable[possibleIndex].getIsDeleted())&&(closedHashTable[possibleIndex].getString()==null)){
                break;
            }
            //check if the string is identical to searchVal
            if (closedHashTable[possibleIndex].getString()!=null){
                if (closedHashTable[possibleIndex].getString().equals(searchVal)) {
                    //returns true if found
                    return true;
                }
            } //false otherwise
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
            //finds the index in which toDelete is
            int index = getIndexToDelete(toDelete);
            //making sure it's valid-
            if (index>-1){
                //updating the fields accordingly, if it is-
                closedHashTable[index].setString(null);
                sizeCounter--;
                //closedHashTable[index].setIsDeleted(true);
                //return true if all went well
                return true;
            }
            //and false otherwise
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
     * this is a 'factory' function that creates a closed hash table with the wanted upped and lower load factors,
     * and wanted capacity.
     * @param wantedUpperLoadFactor wantedUpperLoadFactor
     * @param wantedLowerLoadFactor wantedLowerLoadFactor
     * @return the created hash table
     */
    private ClosedCell[] closedHashTableFactory(float wantedUpperLoadFactor,float wantedLowerLoadFactor,int wantedCapacity){
        //creating a new string array in the size of capacity
        ClosedCell[] hashTable = new ClosedCell[wantedCapacity];
        //initializing the capacity and the lower and upper load factors
        lowerLoadFactor = wantedLowerLoadFactor;
        upperLoadFactor = wantedUpperLoadFactor;
        capacity = wantedCapacity;
        //initiating in each cell a ClosedCell instance-
        for (int index=0;index<capacity;index++){
            hashTable[index]=new ClosedCell();
        }
        //returning the created hash table
        return hashTable;
    }

    /**
     * this function is getting a string and returning a suiteable index to which the String can be inserted
     * @param searchedString the string wanted to be insterted
     * @return the index if found, -1 if not
     */
    private int getEmptyIndex(String searchedString, ClosedCell[] searchedArray) {
        //calling the hashCode java function
        int hashCode = searchedString.hashCode();
        //iterating over all numbers smaller the capacity in order to find a suitable quadratic index
        for (int i = 0; i < searchedArray.length; i++) {
            //pre-clamped index
            int preClamped = getPreClamped(i, hashCode);
            //corrected index
            int index = clamp(preClamped);
            //if an empty slot is found the index is returned
            if (searchedArray[index].getString() == null) {
                return index;
            }
            //else (shouldn't happen) -1 is returned
        } return -1;
    }

    /**
     * this function is getting a string and returning a the index in which it exists, so the delete function can
     * delete it
     * @param toDelete the string wanted to be insterted
     * @return the index of searchedString, -1 if not found
     */
    private int getIndexToDelete(String toDelete) {
        //calling the hashCode java function
        int hashCode = toDelete.hashCode();
        //iterating over all numbers smaller the capacity in order to find a suitable quadratic index
        for (int i = 0; i < capacity(); i++) {
            //pre-clamped index
            int preClamped = getPreClamped(i, hashCode);
            //corrected index
            int index = clamp(preClamped);
            //checking there's a string in index-
            if (closedHashTable[index].getString()!=null){
                //if an the item is identical to toDelete, the index is returned
                if (closedHashTable[index].getString().equals(toDelete)){
                    return index;
                }
            }
            //else (shouldn't happen) -1 is returned
        } return -1;
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
        //initializing the right size for the new array using the power input
        capacity = (int)(power*tempCapacityVal);
        //initializing an array ClosedCell's instances in the right size
        ClosedCell[] newTable = closedHashTableFactory(upperLoadFactor, lowerLoadFactor, capacity);
        //iterating over the existing array and moving all the elements to the new one
        for (int index=0; index<tempCapacityVal;index++){
            //if the cell's String is null, continue
            if (closedHashTable[index].getString()!=null){
                hashingAdd(closedHashTable[index].getString(), newTable);
            } //assigning the new table to the closedHashTable field
        } closedHashTable = newTable;
    }

    /**
     * getter function for the load factor after one item is added. called before adding to check
     * if there's a need to rehash
     * @return the load factor after addition-to-be
     */
    private float getFutureLoadFactor(){
        return ((float)(size()+1)/(float) capacity);
    }

    /**
     * getter function for the load factor after one item is deleted. called before adding to check
     * if there's a need to rehash
     * @return the load factor after deletion
     */
    private float getDeletionLoadFactor(){
        return ((float)(size()-1)/(float) capacity);
    }

    /**
     * this function gets 'i' and the hash code of a string and returns the expression to send to 'clamp' function
     * @param iteration the iteration, i
     * @param hashCode the hashcode of the string
     * @return returns the expression to be input to clamp function
     */
    private int getPreClamped(int iteration, int hashCode){
        return (hashCode + (iteration + iteration * iteration) / 2);
    }
}
