/**
 * Created by Shreyash on 3/13/2017.
 */
import java.util.*;

public class BFS {
    private Graph g;

    //Constructor
    public BFS (Graph g){
        this.g = g;
    }

    //Pathfinding using BFS
    public List<Node> findPath(Node start, Node goal){
        List<Node> frontier = new LinkedList<Node>();	//List of nodes in the frontier
        HashMap<Node, Node> cameFrom = new HashMap<Node, Node>();	//Breadcrumb trail from goal to start
        List<Node> path = new LinkedList<Node>();

        frontier.add(start);
        cameFrom.put(start,null);

        while(!(frontier.isEmpty())){
            Node current = frontier.get(0);
            System.out.println(current.getName());

            //Early Exit
            if (current.equals(goal)){
                break;
            }

            Node[] edges = g.neighbors(current);

            for(Node node: edges){
                if (!(cameFrom.containsKey(node))){
                    frontier.add(0, node);
                    cameFrom.put(node, current);
                }
            }
        }

        //Trace Nodes backwards
        Node current = goal;
        path.add(current);
        while(!(current.equals(start))){
            current = cameFrom.get(current);
            path.add(current);
        }

        return path;
    }
}
