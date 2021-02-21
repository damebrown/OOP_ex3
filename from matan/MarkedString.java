/**
 * A class used for differentiating between null cells and deleted cells. Strings added to the set are
 * wrapped by this class. A deleted cell's value is set to null, but the MarkedString object still exists.
 */

public class MarkedString {

//**********************DATA MEMBERS************************************************************************//

    private String value;

//*****************CONSTRUCTORS*****************************************************************************//

    /**
     *
     * @param value the value to wrap
     */
    MarkedString(String value){
        this.value = value;

    }

//*************METHODS**************************************************************************************//

    /**
     * @return the value wrapped in the object
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set the object's value to
     * @return true
     */
	public boolean setValue(String value) {
		this.value = value;
		return true;
	}
}
