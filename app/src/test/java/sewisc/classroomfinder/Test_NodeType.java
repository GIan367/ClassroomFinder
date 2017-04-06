package sewisc.classroomfinder;

/**
 * Created by rfugs on 4/4/17.
 */
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class Test_NodeType {
    @Test
    public void testGetType() {
        Node testNode1 = new Node(NodeType.normal, 1000, 5000, 1, "Lids", new String[0]);
        Node testNode2 = new Node(NodeType.stair, 7, 6657, 1, "Stair 2", new String[0]);
        Node testNode3 = new Node(NodeType.elevator, 1223, 4352, 1, "Elevator 22", new String[0]);
        assertEquals(NodeType.normal, testNode1.getType());
        assertEquals(NodeType.stair, testNode2.getType());
        assertEquals(NodeType.elevator, testNode3.getType());
    }

    @Test
    public void testGetRelativeX() {
        Node testNode1 = new Node(NodeType.normal, 1000, 5000, 1, "Lids", new String[0]);
        Node testNode2 = new Node(NodeType.stair, 7, 6657, 1, "Stair 2", new String[0]);
        Node testNode3 = new Node(NodeType.elevator, 1223, 4352, 1, "Elevator 22", new String[0]);
        assertEquals(1000, testNode1.getRelativeX());
        assertEquals(7, testNode2.getRelativeX());
        assertEquals(1223, testNode3.getRelativeX());
    }

    @Test
    public void testGetRelativeY() {
        Node testNode1 = new Node(NodeType.normal, 1000, 5000, 1, "Lids", new String[0]);
        Node testNode2 = new Node(NodeType.stair, 7, 6657, 1, "Stair 2", new String[0]);
        Node testNode3 = new Node(NodeType.elevator, 1223, 4352, 1, "Elevator 22", new String[0]);
        assertEquals(5000, testNode1.getRelativeY());
        assertEquals(6657, testNode2.getRelativeY());
        assertEquals(4352, testNode3.getRelativeY());
    }

    @Test
    public void testGetName() {
        Node testNode1 = new Node(NodeType.normal, 1000, 5000, 1, "Lids", new String[0]);
        Node testNode2 = new Node(NodeType.stair, 7, 6657, 1, "Stair 2", new String[0]);
        Node testNode3 = new Node(NodeType.elevator, 1223, 4352, 1, "Elevator 22", new String[0]);
        assertEquals("Lids", testNode1.getName());
        assertEquals("Stair 2", testNode2.getName());
        assertEquals("Elevator 22", testNode3.getName());
    }

    @Test
    public void testGetFloor(){
        Node testNode1 = new Node(NodeType.normal, 1000, 5000, 1, "Lids", new String[0]);
        Node testNode2 = new Node(NodeType.stair, 7, 6657, 1, "Stair 2", new String[0]);
        Node testNode3 = new Node(NodeType.elevator, 1223, 4352, 1, "Elevator 22", new String[0]);
        assertEquals(1, testNode1.getFloor());
        assertEquals(1, testNode2.getFloor());
        assertEquals(1, testNode3.getFloor());
    }

}