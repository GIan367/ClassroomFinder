package sewisc.classroomfinder;

/**
 * Created by rfugs on 4/4/17.
 */
import org.junit.Test;

import java.util.HashMap;
import static org.junit.Assert.*;

public class Test_Graph {

  @Test
  public void neighborTest (){
    Node node1 = new Node(NodeType.hall, 1557, 1467,1,"K",new String[0]);
		Node node2 = new Node(NodeType.hall, 1272, 1197,1,"I",new String[0]);
		Node node3 = new Node(NodeType.normal, 1390, 1237,1,"T-Mobile",new String[0]);
		Node node4 = new Node(NodeType.normal, 1005, 684,1,"TechBank",new String[0]);
    
    HashMap<Node, Node[]> map = new HashMap<Node, Node[]>();
    
		map.put(node1, new Node[]{node2});
		map.put(node2, new Node[]{node1, node3, node4});
		map.put(node3, new Node[]{node2});
		map.put(node4, new Node[]{node2});
    
    Graph g = new Graph(map);
    Node[] expectedResult = new Node[] {node1,node3,node4};
    assertArrayEquals(expectedResult,g.neighbors(node2));
  }
  
  @Test
  public void heuristicTest(){
    Node node1 = new Node(NodeType.hall, 1000, 1000,1,"K",new String[0]);
		Node node4 = new Node(NodeType.normal, 2500, 2500,1,"TechBank",new String[0]);
    
    HashMap<Node, Node[]> map = new HashMap<Node, Node[]>();
    
		map.put(node1, new Node[]{node4});
		map.put(node4, new Node[]{node1});
    
    Graph g = new Graph(map);
    int expectedHeuristic = 3000;
    assertEquals(expectedHeuristic,g.heuristic(node1,node4));
  }
}
