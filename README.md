# classroomFinder
All up to date code is located in the master branch. The master branch should only be merged to at the end of an iteration OR with the agreement of a majority team.  
Java classes pertaining to Database functionality, Pathfinding, and Map Layout are located in ClassroomFinder/app/src/main/java/sewisc/classroomfinder/:
BuildingDB.java: This class represents the Building table in the database, with the corresponding getter/setter methods for its fields.   
DataBaseHandler.java: This class has functions that can create, manipulate, and delete a database; to be used with the BuildingDB, Favorite, and Location classes.    
Favorite.java: This class represents the Favorite table in the database, with the corresponding getter/setter methods for its fields.    
GridAdapter.java: This class assembles text in a format that can be interpreted by the Favorites View's GridView.
ImageAdapter.java: This class assembles images in a format that can be interpreted by the FloorGallery View's GridView.
Location.java: This class represents the Location table in the database, with the corresponding getter/setter methods for its fields.    
MainActivity.java: This class gives functionality to the 4 tabs representing the primary views.
MapView.java: This class describes the View containing the requested map, with or without a computed path.
SwipeDetector.java: This class handles analyzing touch events to determine if the action qualifies as a directional swipe.

AStar.java: This class implements A star pathfinding.  
PriorityNode.java: This class is used by the PriorityQueue in AStar and associates a Node object with a priority.  
Graph.java: This class represents the Nodes in a map as a graph of nodes and its neighbors.  
Node.java: This class represents a point of interest in a map with coordinate locations and associated type.
Building.java: This class represents a selected building which holds all of the Nodes and is responsible for creating a Graph of the Nodes and calling the AStar function.
SwipeDetector.java: Added functionality to delete a saved favorite by swiping left.

Front end xml files:
activity_main.xml: This file describes the 4 tabs on the bottom of the screen and how to interact based on which tab is pressed.
bathroom.xml: This file describes the layout of the drop down search bars for building, and current location with the find button to route to nearest bathroom.
favorite.xml: This file describes the layout of the grid view that is populated with the user's favorite routes.
floor_gallery.xml: This file describes the layout of the drop down search bar for building, and the grid view that is populated with the floors of the selected building.
map.xml: This file describes the layout for when a user clicks the find button and the map with/without computed map pops up.
room_finder.xml: This file describes the layout for the homescreen, with drop down search bars for building, current location, and destination, and a find button.


Test Code is located in the main directory of the project ClassroomFinder/Tests/:  
Test_NodeType.java: jUnit tests for Node.java and NodeType.java; Node.java returns correct X/Y positions, NodeTypes, and name. 4/4 test cases passed.  
Test_BuildingDB.java: jUnit tests for BuildingDB.java.
Test_Favorite.java: jUnit tests for Favorite.java.Tests getters and setters for Favorite class.
Test_Graph.java: jUnit tests for Graph.java. Tests the neighbors and heuristic functions for Favorite Class
Test_Location.java: jUnit tests for Location.java. Tests the getters and setters in Location class
Test_PriorityNode.java: jUnit tests for PriorityNode.java. Tests the functions in PriorityNode class includeing object comparisons.

Front end: Available in a Google Sheet at: https://drive.google.com/open?id=15H-pLwcQR3MiKZ4KeR_dQ1jqTcDXPBFDwYtLi1zxxn8. Please see sheet "ITERATION 1"  
