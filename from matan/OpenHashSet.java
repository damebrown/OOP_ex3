public class OpenHashSet extends SimpleHashSet {

//**********************DATA MEMBERS************************************************************************//

    private LinkedListWrapper[] hashSet = new LinkedListWrapper[capacity];


//*****************CONSTRUCTORS*****************************************************************************//

    /**
     * A default constructor. Constructs a new, empty table with default initial capacity (16), upper load
     * factor (0.75) and lower load factor (0.25).
     */
    public OpenHashSet() {
        super();
    }

    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
     *
     * @param upperLoadFactor The upper load factor of the hash table.
     * @param lowerLoadFactor The lower load factor of the hash table.
     */
    public OpenHashSet(float upperLoadFactor, float lowerLoadFactor) {
        super(upperLoadFactor, lowerLoadFactor);
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one. Duplicate values should be
     * ignored. The new table has the default values of initial capacity (16), upper load factor (0.75), and
     * lower load factor (0.25).
     *
     * @param data Values to add to the set.
     */
    public OpenHashSet(java.lang.String[] data) {
        super();
        for (String value : data) {
            add(value);
        }
    }

//**************PUBLIC METHODS******************************************************************************//

    /**
     * Add a specified element to the set if it's not already in it.
     *
     * @param newValue - New value to add to the set
     * @return False iff newValue already exists in the set
     */
    @Override
    public boolean add(String newValue) {
        if (!contains(newValue)) {
            loadCheck(true);
            int index = indexOf(newValue);
            hashSetSize++;
            return newBucket(hashSet, index, newValue);
        }
        return false;
    }

    /**
     * Look for a specified value in the set.
     *
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    @Override
    public boolean contains(String searchVal) {
        int index = indexOf(searchVal);
        boolean cellNotEmpty = hashSet[index] != null;
        if (cellNotEmpty)
            return hashSet[index].contains(searchVal);
        return false;
    }

    /**
     * Remove the input element from the set.
     *
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    @Override
    public boolean delete(String toDelete) {
        if (contains(toDelete)) {
            loadCheck(false);
            int index = indexOf(toDelete);
            LinkedListWrapper list = hashSet[index];
            hashSetSize--;
            return (list.remove(toDelete));
        }
        return false;
    }

    /**
     * @return the size (number of elements) of the hashSet.
     */
    @Override
    public int size() {
        return hashSetSize;
    }
//***************PROTECTED METHODS**************************************************************************//

    /**
     * Perform a rehash on the set.
     *
     * @param extendSet If extendSet is True, double the size. Otherwise halve the size.
     */
    protected void rehash(boolean extendSet) {
        updateCapacity(extendSet);
        LinkedListWrapper[] temp = new LinkedListWrapper[capacity];
        for (LinkedListWrapper list : hashSet) {
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    String value = list.get(i);
                    int index = indexOf(value);
                    newBucket(temp, index, value);
                }
            }
        }
        hashSet = temp;
    }

//*******************PRIVATE METHODS************************************************************************//

    /**
     * If the cell is null, insert a new linked list of strings. Append the new value to the linked list
     * that was created (or existed before).
     *
     * @param set   the set object
     * @param index the index of the cell
     * @param value the string to add
     * @return true
     */
    private boolean newBucket(LinkedListWrapper[] set, int index, String value) {
        if (set[index] == null)
            set[index] = new LinkedListWrapper();
        return set[index].add(value);
    }

}
