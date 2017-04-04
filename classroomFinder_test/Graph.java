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
        System.out.println("Graph.cost() has not been implemented");
        return -1;
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