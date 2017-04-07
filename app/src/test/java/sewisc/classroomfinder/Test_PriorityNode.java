package sewisc.classroomfinder;

/**
 * Created by rfugs on 4/4/17.
 */
import org.junit.Test;

import static org.junit.Assert.*;

public class Test_PriorityNode {

    @Test
    public void node1Greater(){
        Node testNode1 = new Node(NodeType.normal, 1000, 5000, 1, "Lids", new String[0]);
        Node testNode2 = new Node(NodeType.stair, 7, 6657, 1, "Stair 2", new String[0]);
        PriorityNode test1 = new PriorityNode(testNode1, 5);
        PriorityNode test2 = new PriorityNode(testNode2, 1);
        assertEquals(1, test1.compareTo(test2));
    }

    @Test
    public void node1Less(){
        Node testNode1 = new Node(NodeType.normal, 1000, 5000, 1, "Lids", new String[0]);
        Node testNode2 = new Node(NodeType.stair, 7, 6657, 1, "Stair 2", new String[0]);
        PriorityNode test1 = new PriorityNode(testNode1, -5);
        PriorityNode test2 = new PriorityNode(testNode2, 1);
        assertEquals(-1, test1.compareTo(test2));
    }

    @Test
    public void nodesEqual(){
        Node testNode1 = new Node(NodeType.normal, 1000, 5000, 1, "Lids", new String[0]);
        Node testNode2 = new Node(NodeType.stair, 7, 6657, 1, "Stair 2", new String[0]);
        PriorityNode test1 = new PriorityNode(testNode1, 10);
        PriorityNode test2 = new PriorityNode(testNode2, 10);
        assertEquals(0, test1.compareTo(test2));
    }

}
