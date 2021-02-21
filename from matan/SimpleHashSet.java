import java.util.Objects;

public abstract class SimpleHashSet implements SimpleSet {

    protected static int INITIAL_CAPACITY = 16;

    protected int capacity = INITIAL_CAPACITY;

    private static final int MINIMAL_CAPACITY = 1;

    protected int hashSetSize = 0;

    protected int capacityMinusOne = capacity - 1;

    protected static float DEFAULT_HIGHER_CAPACITY = 0.75f;

    protected static float DEFAULT_LOWER_CAPACITY = 0.25f;

    protected float lowerLoadFactor;

    protected float upperLoadFactor;

//_______________**CONSTRUCTORS**_____________________________________________________________________________________//

    protected SimpleHashSet(float upperLoadFactor,
                            float lowerLoadFactor){
        this.upperLoadFactor = upperLoadFactor;
        this.lowerLoadFactor = lowerLoadFactor;
    }

    protected SimpleHashSet(){
        this.upperLoadFactor = DEFAULT_HIGHER_CAPACITY;
        this.lowerLoadFactor = DEFAULT_LOWER_CAPACITY;
    }

//__________________**METHODS**_______________________________________________________________________________________//

    /**
     * @return The current capacity (number of cells) of the table.
     */
    protected int capacity(){
        return capacity;
    }

    protected int clamp(int index) {
        return index&(capacityMinusOne);
    }

    protected void loadCheck(boolean add){

		float load;
		if (add){
			load = (float) (hashSetSize + 1) / capacity;
			if (load>upperLoadFactor)
				rehash(true);
		} else {
			load = (float) (hashSetSize - 1) / capacity;
			if (load < lowerLoadFactor && capacity > MINIMAL_CAPACITY)
				rehash(false);
		}
    }



    protected abstract void rehash(boolean extend);

    protected void updateCapacity(boolean extendSet){
        if (extendSet){
            capacity *= 2;
            capacityMinusOne = capacity - 1;
        } else {
            capacity /= 2;
            capacityMinusOne = capacity - 1;
        }
    }

    protected float getLowerLoadFactor(){
        return lowerLoadFactor;
    }

    protected float getUpperLoadFactor(){
        return upperLoadFactor;
    }



    protected int indexOf(String string){
        int hash = Objects.hashCode(string);
        return clamp(hash);
    }

}
