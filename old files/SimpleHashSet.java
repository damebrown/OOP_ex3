public abstract class SimpleHashSet implements SimpleSet {

    //class fields
    /**Describes the higer load factor of a newly created hash set**/
    protected static final float DEFAULT_HIGHER_CAPACITY=0.75f;
    /**Describes the lower load factor of a newly created hash set**/
    protected static final float DEFAULT_LOWER_CAPACITY=0.25f;
    /**Describes the capacity of a newly created hash set**/
    protected static final int INITIAL_CAPACITY=16;
    /**the power in which the data str. is raised to when decreasing size**/
    protected static final double DECREASING_EXPONENT = 0.5;
    /**the power in which the data str. is raised to when increasing size**/
    protected static final int INCREASING_EXPONENT = 2;
    /**the upper capacity**/
    private float currentUpperLoadFactor;
    /**the lower capacity**/
    private float currentLowerLoadFactor;
    /**the capacity of the set**/
    private int capacity;




    /**
     *Constructs a new hash set with the default capacities given in DEFAULT_LOWER_CAPACITY and DEFAULT_HIGHER_CAPACITY
     */
    public SimpleHashSet(){
    }

    /**
     * Constructs a new hash set with capacity INITIAL_CAPACITY
     * @param upperLoadFactor the upper load factor before rehashing
     * @param lowerLoadFactor the lower load factor before rehashing
     */
    public SimpleHashSet(float upperLoadFactor, float lowerLoadFactor){}

    /**
     * returns the current capacity of the closedHashTable
     * @return the current capacity (number of cells) of the closedHashTable
     */
    public abstract int capacity();

    /**
     * Clamps hashing indices to fit within the current closedHashTable capacity (see the exercise description for details)
     * @param index the index before clamping
     * @return an index properly clamped
     */
    protected int clamp(int index){
        return index&(capacity()-1);
    }

    /**
     * getter function for the lower load factor
     * @return the lower load factor
     */
    protected float getLowerLoadFactor(){
        return currentLowerLoadFactor;
    }

    /**
     * getter function for the upper load factor
     * @return the upper load factor
     */
    protected float getUpperLoadFactor(){
        return currentUpperLoadFactor;
    }


}

