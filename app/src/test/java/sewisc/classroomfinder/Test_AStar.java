package sewisc.classroomfinder;

/**
 * Created by rfugs on 4/4/17.
 */
import org.junit.Test;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class Test_AStar {
    @Test
    public void findPathTest(){

        Node node1 = new Node(NodeType.hall, 1557, 1467,1,"K",new String[0]);
        Node node2 = new Node(NodeType.normal, 1419, 1287,1,"Master Cuts",new String[0]);
        Node node3 = new Node(NodeType.normal, 1444, 1444,1,"Name of the game",new String[0]);

        Node node4 = new Node(NodeType.hall, 1272, 1197,1,"I",new String[0]);
        Node node5 = new Node(NodeType.normal, 1390, 1237,1,"T-Mobile",new String[0]);
        Node node6 = new Node(NodeType.normal, 1005, 684,1,"TechBank",new String[0]);

        Node node7 = new Node(NodeType.hall, 999, 1638,1,"H",new String[0]);
        Node node8 = new Node(NodeType.normal, 1005, 1020,1,"Fred Meyer Jewelers",new String[0]);
        Node node9 = new Node(NodeType.normal, 1086, 1246,1,"Express",new String[0]);

        Node node10 = new Node(NodeType.hall, 999, 1197,1,"D",new String[0]);
        Node node11 = new Node(NodeType.normal, 903, 1248,1,"Bath and Body Works",new String[0]);
        Node node12 = new Node(NodeType.normal, 820, 125,1,"Maurices",new String[0]);

        Node node13 = new Node(NodeType.hall, 999, 1469,1,"G",new String[0]);
        Node node14 = new Node(NodeType.normal, 1036, 1458,1,"The B-12 Store",new String[0]);
        Node node15 = new Node(NodeType.normal, 1036, 1513,1,"Life Uniform",new String[0]);

        HashMap<Node, Node[]> map = new HashMap<Node, Node[]>();

        //Node A and its neighbors, Best Buu, Kato, B
        map.put(node1, new Node[]{node2, node3, node4});
        map.put(node2, new Node[]{node1});
        map.put(node3, new Node[]{node1});

        //Node B and its neighbors, A, zuminez, vanity, D
        map.put(node4, new Node[]{node1, node5, node6, node7});
        map.put(node5, new Node[]{node4});
        map.put(node6, new Node[]{node4});

        //Node D and its neighbors, B, bath and body works, maurices
        map.put(node7, new Node[]{node4, node8, node9,node10});
        map.put(node8, new Node[]{node7});
        map.put(node9, new Node[]{node7});

        //Node D and its neighbors, B, bath and body works, maurices
        map.put(node10, new Node[]{node7, node11, node12,node13});
        map.put(node11, new Node[]{node10});
        map.put(node12, new Node[]{node10});

        //Node D and its neighbors, B, bath and body works, maurices
        map.put(node13, new Node[]{node10, node14, node15});
        map.put(node14, new Node[]{node13});
        map.put(node15, new Node[]{node13});

        Graph mockGraph = new Graph(map);

        AStar DUT = new AStar(mockGraph);
        List<Node> testList = DUT.findPath(node2,node14);
        assertEquals(testList.get(0), node14);
        assertEquals(testList.get(1), node13);
        assertEquals(testList.get(2), node10);
        assertEquals(testList.get(3), node7);
        assertEquals(testList.get(4), node4);
        assertEquals(testList.get(5), node1);
        assertEquals(testList.get(6), node2);
    }

}
