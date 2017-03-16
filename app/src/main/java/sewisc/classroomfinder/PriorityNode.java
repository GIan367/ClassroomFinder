package sewisc.classroomfinder; /**
 * Created by Shreyash on 3/14/2017.
 */

import java.lang.Integer;

public class PriorityNode implements Comparable<PriorityNode> {
    //Private Variables
    private Node node;
    private int priority;

    //Getters
    public Node getNode() {
        return node;
    }

    public int getPriority() {
        return priority;
    }

    //Constructor
    public PriorityNode(Node node, int priority){
        this.node = node;
        this.priority = priority;
    }

    @Override
    public int compareTo(PriorityNode other) {
        //This method is not available in the current API (14) so I am implementing it as it is
        //actually implemented in the Integer class.
        //return Integer.compare(this.priority, other.priority);
        return  Integer.valueOf(this.priority).compareTo(Integer.valueOf(other.priority));
    }
}
