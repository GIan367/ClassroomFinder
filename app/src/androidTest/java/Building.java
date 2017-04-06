import java.awt.image.BufferedImage;
import java.util.List;

public class Building {
	private String name;
	private List<Node> rooms;
	private List<Node> bathrooms;
	private List<Node> entrances;
	private List<BufferedImage> floorMaps;
	private Graph buildingGraph;

	public Building(String name, List<Node> rooms, List<Node> bathrooms,
			List<Node> entrances, List<BufferedImage> floorMaps,
			Graph buildingGraph){
		this.name = name;
		this.rooms = rooms;
		this.bathrooms = bathrooms;
		this.entrances = entrances;
		this.floorMaps = floorMaps;
		this.buildingGraph = buildingGraph;
	}
	
	public List<Node> FindPath(Node currentLocation, Node destination){
		AStar pathFinder = new AStar(buildingGraph);
		return pathFinder.findPath(currentLocation, destination);
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
	
	public List<BufferedImage> getFloorMaps() {
		return floorMaps;
	}
	
	public String getName() {
		return name;
	}
	
	public List<Node> getRooms() {
		return rooms;
	}
	
}
