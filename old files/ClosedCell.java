public class ClosedCell {

    /**the cell's string field**/
    private String cellString;
    /**the cell's flag- if it had a string which was deleted, isDeleted will be true, and false every other way**/
    private boolean isDeleted;

    /**
     * the class's constructor- constructs a new cell with default isDeleted=false
     */
    public ClosedCell(){
        isDeleted = false;
    }

    /**
     * sets isDeleted with the boolean value gotten as input
     * @param boolVar gets the boolean value to assign to isDeleted
     */
    public void setIsDeleted(boolean boolVar){
        isDeleted = boolVar;
    }

    /**
     * getter function for the isDeleted value
     * @return isDeleted variable
     */
    public boolean getIsDeleted(){
        return isDeleted;
    }

    /**
     * sets the cell's string with the gotten input
     * @param string the string to be assigned
     */
    public void setString(String string){
        cellString = string;
    }

    /**
     * returns the string field
     * @return the string of the cell
     */
    public String getString(){
        return cellString;
    }
}
