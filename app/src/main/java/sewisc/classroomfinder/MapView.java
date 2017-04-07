package sewisc.classroomfinder;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Zak on 3/15/2017.
 */

public class MapView extends AppCompatActivity {
    ImageView imageView;
    int ref;
    String building;
    String loc;
    String dest;
    Toast toast;
    DataBaseHandler dataBaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        Intent intent = getIntent();
        ref = intent.getIntExtra(MainActivity.EXTRA_FLOOR, 0);
        building = intent.getStringExtra(MainActivity.EXTRA_BUILDING);
        loc = intent.getStringExtra(MainActivity.EXTRA_LOC);
        dest = intent.getStringExtra(MainActivity.EXTRA_DEST);

        imageView = (ImageView)findViewById(R.id.image);
        toast = Toast.makeText(getApplicationContext(), "Path saved to favorites.", Toast.LENGTH_SHORT);
        dataBaseHandler = new DataBaseHandler(this);

        List<Node> nodes = new ArrayList<Node>();
        InputStream stream = null;
        XMLParser xmlParser = new XMLParser();
        List<Node> entries = null;
        try {
            //new FileInputStream(new File("easttowne.xml"));
            stream = getAssets().open("easttowne.xml");
            entries = xmlParser.parse(stream);
        }
        catch (FileNotFoundException e) {
            System.out.println("xml file not found");
        } catch (XmlPullParserException e) {
            System.out.println("parser not working");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        List<Node> testNodes = new ArrayList<Node>();
        testNodes.add(new Node(NodeType.normal, 4710, 3735, 1, "Amplified Phones", new String[0]));
        testNodes.add(new Node(NodeType.normal, 5215, 4905, 1, "Dick's", new String[0]));
        testNodes.add(new Node(NodeType.normal, 1905, 3975, 1, "Boston Store", new String[0]));
        testNodes.add(new Node(NodeType.normal, 995, 3165, 1, "Sears", new String[0]));
        testNodes.add(new Node(NodeType.normal, 5847, 3160, 1, "JCPenney", new String[0]));
        //testNodes.add(new Node(NodeType.normal, 4710, 3735, 1, "Amplified Phones 2"));


        //uses parser to add all the nodes to a list; list should be used to construct building
        //class which will then be used to make the graph for the A* which will then be used to
        //choose a list of nodes for the path used for drawPath (I believe)
        /** for(Node entry: entries) {
            Node node = null;
            //I have to use a switch case here because I cannot use one instantiation statement
            //to account for all the enum values required for the first parameter.
            switch (entry.getType()){
                case ("Normal"):
                    node = new Node(NodeType.normal, entry.x, entry.y, entry.z, entry.name);
                    break;
                case ("Bathroom"):
                    node = new Node(NodeType.bathroom, entry.x, entry.y, entry.z, entry.name);
                    break;
                case ("Stair"):
                    node = new Node(NodeType.stair, entry.x, entry.y, entry.z, entry.name);
                    break;
                case ("Elevator"):
                    node = new Node(NodeType.elevator, entry.x, entry.y, entry.z, entry.name);
                    break;
            }
            if (node != null){
                nodes.add(node);
            }
            //System.out.println("Name: " + entry.name + " Type " + entry.type + " X: "
           //         + entry.x + " Y: " + entry.y + " Z: " + entry.z);
        } **/


        /** System.out.println("Destination: " + dest);
        System.out.println("Location: " + loc);
        System.out.println("Ref: " + ref);
        System.out.println("Building: " + building); **/
        try {
            if (dest != null) { // All text fields populated -- standard Room Finder AStar
                //TODO: Fix this stuff. Currently calling dummy draw method. Commented lines cause NullPointerException due to A* eventually feeding g.heuristic a null node; haven't discovered why.
                int id = 0;
                List<String> floors = new ArrayList<String>();
                Building buildingObj = null; // Handle this better
                Node locNode = null; // Handle this better
                Node destNode = null; // Handle this better
                if (building.equals("East Towne Mall")) {
                    floors.add("east_towne1");
                    buildingObj = new Building(this, "East Towne Mall", "easttowne.xml", floors);
                    id = getResources().getIdentifier("east_towne1", "mipmap", getPackageName());
                } else if (building.equals("Hogwarts School of Witchcraft and Wizardry")) { // Test garbage; delete eventually
                    buildingObj = new Building(this, "East Towne Mall", "easttowne.xml", floors);
                    id = getResources().getIdentifier("east_towne1", "mipmap", getPackageName());
                }
                List<Node> rooms = buildingObj.getRooms();
                Iterator<Node> itr = rooms.iterator();
                while(itr.hasNext()) {
                    Node curr = itr.next();
                    if(curr.getName().equals(loc)) locNode = curr;
                    if(curr.getName().equals(dest)) destNode = curr;
                }
                //List<Node> pathNodes = buildingObj.FindPath(locNode, destNode);
                //drawPath(id, pathNodes);
                drawPath(id, testNodes);
            } else if (loc != null) { // No destination populated -- Bathroom Finder AStar
                //TODO: call AStar, determine correct map image to draw on (currently dummy value)
                int id = getResources().getIdentifier("east_towne1", "mipmap", getPackageName());
                drawPath(id, testNodes);
            } else { // No text fields populated -- only displaying a floor
                imageView.setImageResource(ref);
            }
        } catch(XmlPullParserException e) {
            // TODO: SOMETHING
        }

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(loc != null) {
                    List<Favorite> favorites = dataBaseHandler.getAllFavorites();
                    int indx;
                    if(favorites.isEmpty()) {
                        indx = 0;
                    } else {
                        indx = favorites.get(favorites.size() - 1).getIndx() + 1;
                    }
                    dataBaseHandler.addFavorite(new Favorite(indx, building, loc, dest));
                    toast.show();
                }
                return true;
            }
        });
    }

    // Draw line on given map image reference from current location to destination via list of nodes
    // TODO: Call AStar to get nodes and pass here -- currently hardcoded to draw a simple line
    void drawPath(int ref, List<Node> nodes){
        BitmapFactory.Options myOptions = new BitmapFactory.Options();
        myOptions.inDither = true;
        myOptions.inScaled = false;
        myOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// important

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), ref, myOptions);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setFilterBitmap(true);
        paint.setStrokeWidth(15);

        Bitmap workingBitmap = Bitmap.createBitmap(bitmap);
        Bitmap mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Bitmap startIcon = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier("mapicon", "mipmap", getPackageName())); //mapicon bitmap creation
        double scale = (2100.0/6998.0); //scale used for pixel conversion
        Canvas canvas = new Canvas(mutableBitmap);

        // TODO: loop through list of nodes from AStar; currently draws a simple test line
        Node prevNode = null;
        Node startNode =null;
        for (Node n: nodes){
            if (prevNode == null){
                //set the first node
                startNode = n;
            }
            else{
                canvas.drawLine((int) (prevNode.getRelativeX() * scale), (int) (prevNode.getRelativeY() * scale) , (int) (n.getRelativeX() * scale), (int) (n.getRelativeY() * scale), paint);
            }
            prevNode = n;
        }

        //draw the start icon
        if (startNode != null)
            canvas.drawBitmap(startIcon, (int) (startNode.getRelativeX() * scale) - 64, (int) (startNode.getRelativeY() * scale) - 120, paint);

        imageView.setAdjustViewBounds(true);
        imageView.setImageBitmap(mutableBitmap);
    }



}
