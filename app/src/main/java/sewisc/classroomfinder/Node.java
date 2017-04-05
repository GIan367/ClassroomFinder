package sewisc.classroomfinder;

/**
 * Created by Shreyash on 3/13/2017.
 */

public class Node {
    //Private Variables
    private NodeType type;              //Type of sewisc.classroomfinder.Node
    private int relativeX, relativeY;	//Relative x and y coordinates on the map
    private int floor;                  //The floor this node is located in
    private String name;                //Name of this sewisc.classroomfinder.Node object
    private String[] neighbors;

    //Getters for type, relativeX, relativeY, and name
    public NodeType getType() {
        return type;
    }

    public int getRelativeX() {
        return relativeX;
    }

    public int getRelativeY() {
        return relativeY;
    }

    public int getFloor() {
        return floor;
    }

    public String getName() {
        return name;
    }
    
    public String[] getNeighbors () {
    	return neighbors;
    }

    //Constructor
    public Node (NodeType type, int relX, int relY, int floor, String name, String[] neighbors){
        this.type = type;
        this.relativeX = relX;
        this.relativeY = relY;
        this.floor = floor;
        this.name = name;
        this.neighbors = neighbors;
    }
}
