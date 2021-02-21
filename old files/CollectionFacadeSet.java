import java.util.TreeSet;

public class CollectionFacadeSet implements SimpleSet {

    /**class fields**/
    //collection:
    protected java.util.Collection<java.lang.String> collection;

    /**
     * Creates a new facade wrapping the specified collection.
     * @param inputCollection The Collection to wrap.
     */
    public CollectionFacadeSet(java.util.Collection<java.lang.String> inputCollection){
        TreeSet<String> collectionSet = new TreeSet<>();
        collectionSet.addAll(inputCollection);
        if (collectionSet.size()!=inputCollection.size()){
        inputCollection.clear();
        inputCollection.addAll(collectionSet);
        }
        collection = inputCollection;
    }

    /**
     * Add a specified element to the set if it's not already in it.
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    public boolean add(java.lang.String newValue){
        if (collection.contains(newValue)){
            return false;
        } collection.add(newValue);
        return true;
    }

    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    public boolean contains(java.lang.String searchVal){
        return collection.contains(searchVal);
    }

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    public boolean delete(java.lang.String toDelete){
        if(contains(toDelete)){
            collection.remove(toDelete);
            return true;
        } return false;
    }
    /**
     * @return The number of elements currently in the set
     */
    public int size(){
        return collection.size();
    }
}
