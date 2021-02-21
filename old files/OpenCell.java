import java.util.LinkedList;

public class OpenCell {

    /**class constans**/
    /** the cell's linked list**/
    public LinkedList<String> cell;

    /***
     * an OpenCell constructor
     */
    public OpenCell(){
        cell = new LinkedList<>();
    }

    /**
     * getter function for the open cell's linked list
     * @return size of the cell
     */
    int getSize(){
        return cell.size();
    }

}
