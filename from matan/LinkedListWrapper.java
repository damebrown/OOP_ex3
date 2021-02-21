import java.util.LinkedList;

/**
 * A class that wraps a linked list of strings. It uses some of the exact same methods that linked list does.
 */

public class LinkedListWrapper {
//**********************DATA MEMBERS************************************************************************//

    private LinkedList<String> linkedList;

//*****************CONSTRUCTORS*****************************************************************************//

    LinkedListWrapper() {
        linkedList = new LinkedList<String>();
    }

//*************METHODS**************************************************************************************//

    public boolean contains(String value) {
        return linkedList.contains(value);
    }

    public boolean add(String value) {
        return linkedList.add(value);
    }

    public boolean remove(String value) {
        return linkedList.remove(value);
    }

    public int size() {
        return linkedList.size();
    }

    public String get(int index) {
        return linkedList.get(index);
    }
}
