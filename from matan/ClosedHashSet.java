import java.util.Objects;

public class ClosedHashSet extends SimpleHashSet {

//**********************DATA MEMBERS************************************************************************//
    MarkedString[] hashSet = new MarkedString[capacity];

//*****************CONSTRUCTORS*****************************************************************************//

    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
     *
     * @param upperLoadFactor The upper load factor of the hash table.
     * @param lowerLoadFactor The lower load factor of the hash table.
     */
    ClosedHashSet(float upperLoadFactor, float lowerLoadFactor) {
        super(upperLoadFactor, lowerLoadFactor);
    }

    /**
     * A default constructor. Constructs a new, empty table with default initial capacity (16),
     * upper load factor (0.75) and lower load factor (0.25).
     */
    ClosedHashSet() {
        super();
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one. Duplicate values should be
     * ignored. The new table has the default values of initial capacity (16), upper load factor (0.75), and
     * lower load factor (0.25).
     *
     * @param data Values to add to the set.
     */
    ClosedHashSet(java.lang.String[] data) {
        super();
        for (String value : data)
            add(value);
    }

//**************PUBLIC METHODS******************************************************************************//

    /**
     * Add a specified element to the set if it's not already in it.
     *
     * @param newValue New value to add to the set.
     * @return False iff newValue already exists in the set.
     */
    @Override
    public boolean add(String newValue) {
        if (!contains(newValue)) {
            loadCheck(true);
            int index = indexOf(newValue, hashSet);
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
        int index = indexOf(searchVal, hashSet);
        boolean isCellEmpty = hashSet[index] == null;
        return !isCellEmpty;
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
            int index = indexOf(toDelete, hashSet);
            MarkedString object = hashSet[index];
            hashSetSize--;
            return object.setValue(null);
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
     * @param extendSet If extendSet is True, double the size. Otherwise halve the size.
     */
    @Override
    protected void rehash(boolean extendSet) {
        updateCapacity(extendSet);
        MarkedString[] temp = new MarkedString[capacity];
        for (MarkedString object : hashSet) {
            if (object != null && object.getValue() != null) {
                String value = object.getValue();
                int index = indexOf(value, temp);
                newBucket(temp, index, value);
            }
        }
        hashSet = temp;
    }


//*******************PRIVATE METHODS************************************************************************//

    /**
     * @param expected the value to search for in the set.
     * @param set      the set to search (usually this is the ClosedHashSet itself, but in rehashing this is a
     *                 temporary set.
     * @return the location of the item inside the set. If the value is not in the set, return the first null
     * cell reached with quadratic probing.
     */
    private int indexOf(String expected, MarkedString[] set) {
        int hash = Objects.hashCode(expected);
        int index = clamp(hash);
        boolean isIndexFound = false;
        for (int i = 0; !isIndexFound; i++) {
            index = (index + (i + i * i) / 2) & (capacityMinusOne);
            boolean isCellNull = set[index] == null;
            if (!isCellNull) {
                MarkedString object = set[index];
                String actual = object.getValue();
                boolean isCellValueNull = object.getValue() == null;
                if (!isCellValueNull && actual.equals(expected)) {
                    isIndexFound = true;
                }
            } else
                isIndexFound = true;
        }
        return index;
    }

    /**
     * Add a new MarkedString object to the cell at the specified index of the specified set. Initialize
     * the value to the specified value.
     *
     * @param set   described above.
     * @param index described above.
     * @param value described above.
     * @return true
     */
    private boolean newBucket(MarkedString[] set, int index, String value) {
        if (set[index] == null)
            set[index] = new MarkedString(value);
        else
            set[index].setValue(value);
        return true;
    }


}
