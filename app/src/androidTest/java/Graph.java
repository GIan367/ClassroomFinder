/**
 * Created by Shreyash on 3/13/2017.
 */
import java.util.*;

public class Graph {
    public HashMap <Node, Node[]> edges;	//A graph of each node and it's neighbors

    //Constructor
    public Graph (HashMap <Node, Node[]> edges){
        this.edges = edges;
    }

    //Function that returns the cost of movement from one node to another
    public int cost (Node A, Node B){
        //TODO: We will need to change cost function to reflect actual cost. This will be based on dimension of the map.
        if (A.getType() == NodeType.normal) return 10;
        if (A.getType() == NodeType.stair) return 15;
        if (A.getType() == NodeType.elevator) return 11;
        return 10;
    }

    //Return an array of neighboring Nodes for the arg Node
    public Node[] neighbors(Node A){
        Node[] ret = edges.get(A);
        if (ret == null){
            System.err.println("Graph.neighbors() - arg Node object does not exist in current Graph!");
        }
        return ret;
    }
}
