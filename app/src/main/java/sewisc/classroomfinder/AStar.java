package sewisc.classroomfinder; /**
 * Created by Shreyash on 3/14/2017.
 */

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class AStar {
    private Graph g;

    public AStar (Graph g){
        this.g = g;
    }

    //Pathfinding using sewisc.classroomfinder.AStar
    public List<Node> findPath(Node start, Node goal){
        PriorityQueue<PriorityNode> frontier = new PriorityQueue<PriorityNode>();	//Priority queue that holds node and current cost to get there
        HashMap<Node, Node> cameFrom = new HashMap<Node, Node>();	//Breadcrumb trail from goal to start
        HashMap<Node, Integer> costSoFar = new HashMap <Node, Integer>();	//Keep track of the cost of movement so far
        List<Node> path = new LinkedList<Node>();

        frontier.add(new PriorityNode(start, 0));
        cameFrom.put(start,null);
        costSoFar.put(start,0);

        while(!(frontier.isEmpty())){
            Node current = frontier.poll().getNode();

            System.out.println("NAME InFrontier----------------------: " + current.getName());

            //Early Exit
            if (current.equals(goal)){
                break;
            }

            Node[] edges = g.neighbors(current);

            for(Node node: edges){
                System.out.println("Neighbor________________________: " + node.getName());
                int newCost = costSoFar.get(current) + g.cost(current, node);
                if (!(costSoFar.containsKey(node)) || (newCost < costSoFar.get(node))){
                    costSoFar.put(node, newCost);
                    int priority = newCost + g.heuristic(node, goal);
                    frontier.add(new PriorityNode(node,priority));
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

