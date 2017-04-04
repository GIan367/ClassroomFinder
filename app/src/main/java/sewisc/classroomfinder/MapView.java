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
        List<XMLParser.Entry> entries = null;
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
        testNodes.add(new Node(NodeType.normal, 4710, 3735, 1, "Amplified Phones"));
        testNodes.add(new Node(NodeType.normal, 5215, 4905, 1, "Dick's"));
        testNodes.add(new Node(NodeType.normal, 1905, 3975, 1, "Boston Store"));
        testNodes.add(new Node(NodeType.normal, 995, 3165, 1, "Sears"));
        testNodes.add(new Node(NodeType.normal, 5847, 3160, 1, "JCPenney"));
        testNodes.add(new Node(NodeType.normal, 4710, 3735, 1, "Amplified Phones 2"));
        //uses parser to add all the nodes to a list; list should be used to construct building
        //class which will then be used to make the graph for the A* which will then be used to
        //choose a list of nodes for the path used for drawPath (I believe)
        for(XMLParser.Entry entry: entries) {
            Node node = null;
            //I have to use a switch case here because I cannot use one instantiation statement
            //to account for all the enum values required for the first parameter.
            switch (entry.type){
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
        }



        if(dest != null) { // All text fields populated -- standard Room Finder AStar
            //TODO: call AStar, determine correct map image to draw on (currently dummy value)
            int id = getResources().getIdentifier("east_towne1", "mipmap", getPackageName());
            drawPath(id, testNodes);
        } else if(loc != null) { // No destination populated -- Bathroom Finder AStar
            //TODO: call AStar, determine correct map image to draw on (currently dummy value)
            int id = getResources().getIdentifier("east_towne1", "mipmap", getPackageName());
            drawPath(id, testNodes);
        } else { // No text fields populated -- only displaying a floor
            imageView.setImageResource(ref);
        }

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(loc != null) {
                    int idx = dataBaseHandler.getFavoritesCount() + 1;
                    dataBaseHandler.addFavorite(new Favorite(idx, building, loc, dest));
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
        myOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), ref, myOptions);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setFilterBitmap(true);
        paint.setStrokeWidth(15);

        Bitmap workingBitmap = Bitmap.createBitmap(bitmap);
        Bitmap mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888, true);

        double scale = (2100.0/6998.0);
        System.out.println(scale);
        Canvas canvas = new Canvas(mutableBitmap);
        //canvas.drawCircle(60, 50, 25, paint);

        // TODO: loop through list of nodes from AStar; currently draws a simple test line
       // canvas.drawLine(0, 0, 2000, 2000, paint);
        Node prevNode = null;
        for (Node n: nodes){

            if (prevNode != null){
                //first case
                canvas.drawLine((int) (prevNode.getRelativeX() * scale), (int) (prevNode.getRelativeY() * scale), (int) (n.getRelativeX() * scale), (int) (n.getRelativeY() * scale), paint);
                //System.out.println((int) (prevNode.getRelativeX() * scale));
            }
            prevNode = n;
        }

        imageView.setAdjustViewBounds(true);
        imageView.setImageBitmap(mutableBitmap);
    }



}
