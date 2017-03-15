/**
 * Created by Shreyash on 3/13/2017.
 */

public class Node {
    //Private Variables
    private NodeType type;              //Type of Node
    private int relativeX, relativeY;	//Relative x and y coordinates on the map
    private String name;                //Name of this Node object

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

    public String getName() {
        return name;
    }

    //Constructor
    public Node (NodeType type, int relX, int relY, String name){
        this.type = type;
        this.relativeX = relX;
        this.relativeY = relY;
        this.name = name;
    }
}
