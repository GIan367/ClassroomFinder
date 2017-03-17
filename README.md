# classroomFinder
All up to date code is located in the master branch. The master branch should only be merged to at the end of an iteration OR with the agreement of a majority team.  
Java classes pertaining to Database functionality, Pathfinding, and Map Layout are located in   ClassroomFinder/app/src/main/java/sewisc/classroomfinder/:  
Building.java: This class represents the Building table in the database, with the corresponding getter/setter methods for its fields.   
DataBaseHandler.java: This class has functions that can create, manipulate, and delete a database; to be used with the Building, Favorite, and Location classes.    
Favorite.java: This class represents the Favorite table in the database, with the corresponding getter/setter methods for its fields.    
GridAdapter.java:  
ImageAdapter.java:  
Location.java: This class represents the Location table in the database, with the corresponding getter/setter methods for its fields.    
MainActivity.java:  
MapView.java:
AStar.java: This class implements A star pathfinding.  
PriorityNode.java: This class is used by the PriorityQueue in AStar and associates a Node object with a priority.  
Graph.java: This class represents the Nodes in a map as a graph of nodes and its neighbors.  
Node.java: This class represents a point of interest in a map with coordinate locations and associated type.    
Front end related code, such as xml files, are located at ClassroomFinder/app/src/main/res/layout:  
activity_main.xml: This files describes the 4 tabes on the bottom of the screen and how to interact based on which tab is pressed.
bathroom.xml: This file describes the layout of the drop down search bars for building, and current location with the find button to route to nearest bathroom.
favorite.xml: This file describes the layout of the grid view that is populated with the users' favorite routes
floor_gallery: 
map.xml: This files describes the layout for when a user clicks the find button and the map with route pops up
room_finder.xml: This file describes the layout for the homescreen/ with drop down search bars for building, current location, and destination and a find button.





Test Code is located in the main directory of the project ClassroomFinder/Tests/:  
NodeTest.java: jUnit tests for Node.java and NodeType.java; Node.java returns correct X/Y positions, NodeTypes, and name. 4/4 test cases passed.  
Front end: Available in a Google Sheet at: https://drive.google.com/open?id=15H-pLwcQR3MiKZ4KeR_dQ1jqTcDXPBFDwYtLi1zxxn8. Please see sheet "ITERATION 1"  
