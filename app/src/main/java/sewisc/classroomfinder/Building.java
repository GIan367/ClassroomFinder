package sewisc.classroomfinder;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Building {
	private String name;
	private List<Node> rooms;
	private List<Node> bathrooms;
	private List<Node> entrances;
	private List<String> floorMaps;
	private Graph buildingGraph;
	
	public Building(String name, String xmlFile, List<String> floorMaps) throws XmlPullParserException{
		InputStream in = new FileInputStream(new File(xmlFile));
		List<Node> nodeList = XMLParser.parse(in);
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
				while (nodeIterator.hasNext()){
					Node nextNeighbor = neighborIterator.next();
					if (nextNode.getNeighbors()[i].equalsIgnoreCase(nextNeighbor.getName())){
						neighbors[i] = nextNeighbor;
					}
				}
			}
			hashMap.put(nextNode, neighbors);
			if (nextNode.getType().equals(NodeType.bathroom)){
				bathrooms.add(nextNode);
			}//TODO rooms vs entrances?
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