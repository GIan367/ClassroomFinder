package sewisc.classroomfinder;

import android.app.Activity;
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

        /** System.out.println("Destination: " + dest);
        System.out.println("Location: " + loc);
        System.out.println("Ref: " + ref);
        System.out.println("Building: " + building); **/
        try {
            if((dest != null) && (!dest.equals("Nearest Bathroom"))) { // All text fields populated -- standard Room Finder AStar
                int id = 0;
                List<String> floors = new ArrayList<String>();
                Building buildingObj = null; // Handle this better
                Node locNode = null; // Handle this better
                Node destNode = null; // Handle this better
                if (building.equals("East Towne Mall")) {
                    floors.add("east_towne1");
                    buildingObj = new Building(this, "East Towne Mall", "easttowne.xml", floors);
                    id = getResources().getIdentifier("east_towne1", "mipmap", getPackageName());
                } /** else if (building.equals("Hogwarts School of Witchcraft and Wizardry")) {
                    buildingObj = new Building(this, "East Towne Mall", "easttowne.xml", floors);
                    id = getResources().getIdentifier("east_towne1", "mipmap", getPackageName());
                } **/
                List<Node> rooms = buildingObj.getRooms();
                Iterator<Node> itr = rooms.iterator();
                while(itr.hasNext()) {
                    Node curr = itr.next();
                    if(curr.getName().equals(loc)) locNode = curr;
                    if(curr.getName().equals(dest)) destNode = curr;
                }
                List<Node> pathNodes = buildingObj.FindPath(locNode, destNode);
                /** for(Node node: pathNodes) {
                    System.out.println("FINAL PATH________________________: " + node.getName());
                } **/
                drawPath(id, pathNodes);
            } else if (loc != null) { // No destination populated -- Bathroom Finder AStar
                int id = 0;
                List<String> floors = new ArrayList<String>();
                Building buildingObj = null; // Handle this better
                Node locNode = null; // Handle this better
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
                }
                List<Node> pathNodes = buildingObj.FindNearestBathroom(locNode);
                /** for(Node node: pathNodes) {
                    System.out.println("FINAL PATH________________________: " + node.getName());
                } **/
                drawPath(id, pathNodes);
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
                    if(dest != null) {
                        dataBaseHandler.addFavorite(new Favorite(indx, building, loc, dest));
                    } else {
                        dataBaseHandler.addFavorite(new Favorite(indx, building, loc, "Nearest Bathroom"));
                    }
                    toast.show();
                }
                return true;
            }
        });
    }

    // Draw line on given map image reference from current location to destination via list of nodes
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
        Bitmap destIcon = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier("mapicon2", "mipmap", getPackageName())); //mapicon bitmap creation
        double scale = (2100.0/6998.0); //scale used for pixel conversion
        Canvas canvas = new Canvas(mutableBitmap);

        Node destNode = null;
        Node startNode =null;
        for (Node n: nodes){
            if (startNode == null){
                //set the first node
                destNode = n;
            }
            else{
                canvas.drawLine((int) (startNode.getRelativeX() * scale), (int) (startNode.getRelativeY() * scale) , (int) (n.getRelativeX() * scale), (int) (n.getRelativeY() * scale), paint);
            }
            startNode = n;
        }

        //draw the start icon
        if (startNode != null && destNode != null) {
            canvas.drawBitmap(startIcon, (int) (startNode.getRelativeX() * scale) - 64, (int) (startNode.getRelativeY() * scale) - 120, paint);
            canvas.drawBitmap(destIcon, (int) (destNode.getRelativeX() * scale) - 64, (int) (destNode.getRelativeY() * scale) - 120, paint);
        }

        imageView.setAdjustViewBounds(true);
        imageView.setImageBitmap(mutableBitmap);
    }



}
