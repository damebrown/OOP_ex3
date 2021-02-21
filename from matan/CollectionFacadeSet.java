import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

public class CollectionFacadeSet extends java.lang.Object implements SimpleSet {

//**********************DATA MEMBERS************************************************************************//

    private java.util.Collection<java.lang.String> collection;

//*****************CONSTRUCTOR******************************************************************************//
    public CollectionFacadeSet(java.util.Collection<java.lang.String> collection) {
        this.collection = collection;
        TreeSet<String> temp = new TreeSet<String>(collection);
        for (int i = 0; i < temp.size(); i++) {
            String value = temp.iterator().next();
            delete(value);
            add(value);
            this.collection = temp;
        }
    }

//**************METHODS*************************************************************************************//

    /**
     * Add a specified element to the set if it's not already in it.
     *
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    public boolean add(String newValue) {
        if (!collection.contains(newValue)) {
            collection.add(newValue);
            return true;
        }
        return false;
    }

    /**
     * Look for a specified value in the set.
     *
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    public boolean contains(String searchVal) {
        if (collection.contains(searchVal)) {
            return true;
        }
        return false;
    }

    /**
     * Remove the input element from the set.
     *
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    public boolean delete(String toDelete) {
        if (collection.contains(toDelete)) {
            collection.remove(toDelete);
            return true;
        }
        return false;

    }

    /**
     * @return The number of elements currently in the set
     */
    public int size() {
        return collection.size();
    }
}
