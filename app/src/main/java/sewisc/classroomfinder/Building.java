package sewisc.classroomfinder;

import android.content.Context;

import org.xmlpull.v1.XmlPullParserException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Building {
    private Context context;
    private String name;
    private List<Node> rooms;
    private List<Node> bathrooms;
    private List<Node> entrances;
    private List<String> floorMaps;
    private Graph buildingGraph;

    public Building(Context context, String name, String xmlFile, List<String> floorMaps) throws XmlPullParserException{
        this.context = context;
        List<Node> nodeList = null;
        try {
            InputStream in = context.getAssets().open(xmlFile);
            nodeList = XMLParser.parse(in);
        } catch (FileNotFoundException e) {
            // TODO: SOMETHING
            System.out.println(e);
        } catch (IOException e) {
            // TODO: SOMETHING
            System.out.println(e);
        }
        HashMap<Node,Node[]> hashMap = new HashMap<Node,Node[]>();
        Iterator<Node> nodeIterator = nodeList.iterator();
        rooms = new ArrayList<Node>();
        bathrooms = new ArrayList<Node>();
        entrances = new ArrayList<Node>();
        while (nodeIterator.hasNext()){
            Node nextNode = nodeIterator.next();
            Node[] neighbors = new Node[nextNode.getNeighbors().length];
            for (int i = 0; i < nextNode.getNeighbors().length; i++){
                Iterator<Node> neighborIterator = nodeList.iterator();
                while (neighborIterator.hasNext()){
                    Node nextNeighbor = neighborIterator.next();
                    if (nextNode.getNeighbors()[i].equalsIgnoreCase(nextNeighbor.getName())){
                        neighbors[i] = nextNeighbor;
                    }
                }
            }
            hashMap.put(nextNode, neighbors);
            if (nextNode.getType().equals(NodeType.bathroom)){
                bathrooms.add(nextNode);
            } else if (nextNode.getType().equals(NodeType.normal)) {
                rooms.add(nextNode);
            } // TODO: handle entrances, etc.?
        }

        this.name = name;
        this.buildingGraph = new Graph(hashMap);
        this.floorMaps = floorMaps;
    }

    public List<Node> FindPath(Node currentLocation, Node destination){
        AStar pathFinder = new AStar(buildingGraph);
        return pathFinder.findPath(currentLocation, destination);
    }

    public List<Node> FindNearestBathroom (Node currentLocation){
        Iterator<Node> bathroomIterator = bathrooms.iterator();
        Node bathroomNode = bathroomIterator.next();
        int distance = buildingGraph.heuristic(currentLocation, bathroomNode);
        while (bathroomIterator.hasNext()){
            Node nextBathroomNode = bathroomIterator.next();
            int nextDistance = buildingGraph.heuristic(currentLocation, nextBathroomNode);
            if (nextDistance < distance){
                distance = nextDistance;
                bathroomNode = nextBathroomNode;
            }
        }
        return FindPath(currentLocation,bathroomNode);
    }

    public List<Node> getBathrooms() {
        return bathrooms;
    }

    public Graph getBuildingGraph() {
        return buildingGraph;
    }

    public List<Node> getEntrances() {
        return entrances;
    }

    public List<String> getFloorMaps() {
        return floorMaps;
    }

    public String getName() {
        return name;
    }

    public List<Node> getRooms() {
        return rooms;
    }

}