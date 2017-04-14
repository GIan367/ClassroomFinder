package sewisc.classroomfinder;

/**
 * Created by Jared on 4/13/2017.
 */

import org.junit.Test;
import org.xmlpull.v1.XmlPullParserException;

import java.util.*;

import static org.junit.Assert.assertEquals;
import android.content.Context;
import android.test.AndroidTestCase;

import static org.mockito.Mockito.*;

public class Test_Building{

    @Test
    public void basicTest (){
        Node node1 = new Node(NodeType.hall, 100, 100,1,"a",new String[]{"1","2","b"});
        Node node2 = new Node(NodeType.normal, 100, 200,1,"1",new String[]{"a"});
        Node node3 = new Node(NodeType.bathroom, 100, 0,1,"2",new String[]{"a"});

        Node node4 = new Node(NodeType.hall, 200, 100,1,"b",new String[]{"3","4","a","c"});
        Node node5 = new Node(NodeType.normal, 200, 200,1,"3",new String[]{"b"});
        Node node6 = new Node(NodeType.bathroom, 200, 0,1,"4",new String[]{"b"});

        Node node7 = new Node(NodeType.hall, 300, 100,1,"c",new String[]{"5","6","b"});
        Node node8 = new Node(NodeType.normal, 300, 200,1,"5",new String[]{"c"});
        Node node9 = new Node(NodeType.normal, 300, 0,1,"6",new String[]{"c"});

        List<Node> nodeList = new ArrayList<Node>();
        nodeList.add(node1);
        nodeList.add(node2);
        nodeList.add(node3);
        nodeList.add(node4);
        nodeList.add(node5);
        nodeList.add(node6);
        nodeList.add(node7);
        nodeList.add(node8);
        nodeList.add(node9);
        Building DUT = new Building("Building_Test",nodeList,null);

        HashMap<Node, Node[]> map = new HashMap<Node, Node[]>();

        List<Node> bathrooms = new ArrayList<Node>();
        bathrooms.add(node3);
        bathrooms.add(node6);
        assertEquals("ERROR: Bathroom list not properly created",bathrooms,DUT.getBathrooms());

        //Node A and its neighbors, Best Buu, Kato, B
        map.put(node1, new Node[]{node2, node3, node4});
        map.put(node2, new Node[]{node1});
        map.put(node3, new Node[]{node1});

        //Node B and its neighbors, A, zuminez, vanity, D
        map.put(node4, new Node[]{node1, node5, node6, node7});
        map.put(node5, new Node[]{node4});
        map.put(node6, new Node[]{node4});

        //Node D and its neighbors, B, bath and body works, maurices
        map.put(node7, new Node[]{node4, node8, node9});
        map.put(node8, new Node[]{node7});
        map.put(node9, new Node[]{node7});

        Graph mockGraph = new Graph(map);

        assertEquals("ERROR: Constructed graph is not equal to the hand-made graph",mockGraph.getAllNodes(),DUT.getBuildingGraph().getAllNodes());

        List<Node> pathNodes = DUT.FindPath(node2,node9);
        Iterator<Node> pathIterator = pathNodes.iterator();
        assertEquals("ERROR: The given AStar path did not start with initial node",pathIterator.next(),node9);
        assertEquals("ERROR: The given AStar path did not properly find the only path",pathIterator.next(),node7);
        assertEquals("ERROR: The given AStar path did not properly find the only path",pathIterator.next(),node4);
        assertEquals("ERROR: The given AStar path did not properly find the only path",pathIterator.next(),node1);
        assertEquals("ERROR: The given AStar path did not end with the destination node",pathIterator.next(),node2);


        pathNodes = DUT.FindNearestBathroom(node2);
        pathIterator = pathNodes.iterator();
        assertEquals("ERROR: Find nearest bathroom did not find bathroom node 3",pathIterator.next(),node3);
        assertEquals("ERROR: Find nearest bathroom did not correctly navigate through node 1",pathIterator.next(),node1);
        assertEquals("ERROR: Find nearest bathroom did not start at the current node 2",pathIterator.next(),node2);

        pathNodes = DUT.FindNearestBathroom(node9);
        pathIterator = pathNodes.iterator();
        assertEquals("ERROR: Find nearest bathroom did not find bathroom node 6",pathIterator.next(),node6);
        assertEquals("ERROR: Find nearest bathroom did not correctly navigate through node 4",pathIterator.next(),node4);
        assertEquals("ERROR: Find nearest bathroom did not correctly navigate through node 7",pathIterator.next(),node7);
        assertEquals("ERROR: Find nearest bathroom did not start at the current node 9",pathIterator.next(),node9);

    }
}