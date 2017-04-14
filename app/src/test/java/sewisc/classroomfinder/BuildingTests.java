package sewisc.classroomfinder;

import java.util.Iterator;
import java.util.List;
import org.junit.Test;

import org.xmlpull.v1.XmlPullParserException;

public class BuildingTests {
    /*
	@Test
	public static void RunTests () {
		basicTest();
		badTypeTest();
		noNeighborTest();
	}
	@Test
	public static void basicTest (){
		Building myBuilding = null;
		try {
			myBuilding = new Building("buildingName","Clean.xml",null);
		}catch (XmlPullParserException e){
			assert false : "ERROR: Building was not created after parsing a valid xml file (Clean.xml)";
		}
		astarTest (myBuilding);
	}
	@Test
	public static void astarTest(Building myBuilding){
		Node[] nodeList = (Node[]) myBuilding.getBuildingGraph().getAllNodes().toArray();
		List<Node> pathNodes = myBuilding.FindPath(nodeList[0],nodeList[2]);
		Iterator<Node> pathIterator = pathNodes.iterator();
		assert pathIterator.next() == nodeList[0] : "ERROR: The given AStar path did not start with initial node";
		assert pathIterator.next() == nodeList[1] : "ERROR: The given AStar path did not properly find the only path";
		assert pathIterator.next() == nodeList[2] : "ERROR: The given AStar path did not end with the destination node";
	}
	@Test
	public static void badTypeTest () {
		try {
			Building myBuilding = new Building("buildingName","Bad_Type.xml",null);
		}catch (XmlPullParserException e){
			return;
		}
		assert false : "ERROR: Building.java did not throw an exception when a node contained an invalid type";
	}
	@Test
	public static void noNeighborTest () {
		Building myBuilding = null;
		try {
			myBuilding = new Building("buildingName","No_Neighbors.xml",null);
		}catch (XmlPullParserException e){
			assert false : "ERROR: Building was not created after parsing a valid xml file (No_Neighbors.xml)";
		}
		Node[] nodeList = (Node[]) myBuilding.getBuildingGraph().getAllNodes().toArray();
		try {
			List<Node> pathNodes = myBuilding.FindPath(nodeList[0],nodeList[2]);
		}catch (Exception e){
			return;
		}
		assert false : "ERROR: AStar did not throw an exception when a path could not be formed";
	}
*/
}