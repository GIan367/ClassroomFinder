package sewisc.classroomfinder;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TabHost;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.xmlpull.v1.XmlPullParserException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DataBaseHandler dataBaseHandler;
    List<Favorite> favoriteList;
    List<String> favoritesArray;
    SwipeDetector swipeDetector;
    SearchableSpinner buildingSpinner1;
    SearchableSpinner curLocSpinner1;
    SearchableSpinner destSpinner1;
    SearchableSpinner buildingSpinner2;
    SearchableSpinner buildingSpinner3;
    SearchableSpinner curLocSpinner3;
    GridView floors;
    GridView favorites;
    Button find1;
    Button find2;
    boolean curLocSpinner1Valid;
    boolean destSpinner1Valid;

    public static final String EXTRA_BUILDING = "sewisc.classroomfinder.BUILDING";
    public static final String EXTRA_LOC = "sewisc.classroomfinder.LOC";
    public static final String EXTRA_DEST = "sewisc.classroomfinder.DEST";
    public static final String EXTRA_FLOOR = "sewisc.classroomfinder.FLOOR";

    // Temporary declarations and test data before database integration
    ArrayAdapter<String> buildingAdapter;
    ArrayAdapter<String> eastTowneAdapter;
    ImageAdapter floorsAdapter;
    GridAdapter favoritesAdapter;

    Integer[] eastTowneFloors = {
            R.mipmap.east_towne1
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataBaseHandler = new DataBaseHandler(this);

        /*
        // Parse XML file.
        // An entry is defined in Node.java and currently consists of name, type, x, y, z, and neighbor nodes.
        // If XML format should change, this can easily be updated and accounted for in XMLParser.java
         */
        InputStream stream = null;
        XMLParser xmlParser = new XMLParser();
        List<Node> entries = null;
        try {
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

        /** for(Node entry: entries) {
            System.out.println("Name: " + entry.getName() + " Type " +  entry.getType() + " X: "
                + entry.getRelativeX() + " Y: " + entry.getRelativeY() + " Z: " + entry.getFloor());
        } **/

        // Set up tabs
        TabHost host = (TabHost) findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Room Finder");
        spec.setIndicator("Room Finder");
        spec.setContent(R.id.room_finder);
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Floor Gallery");
        spec.setIndicator("Floor Gallery");
        spec.setContent(R.id.floor_gallery);
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("Bathroom");
        spec.setIndicator("Bathroom");
        spec.setContent(R.id.bathroom);
        host.addTab(spec);

        //Tab 4
        spec = host.newTabSpec("Favorites");
        spec.setIndicator("Favorites");
        spec.setContent(R.id.favorite);
        host.addTab(spec);

        //SQLTest();
        //dataBaseHandler.clearAllData();

        // Replace with reading info from XML files?
        List<String> buildingArray = new ArrayList<String>();
        buildingArray.add("East Towne Mall");

        List<String> eastTowneArray = new ArrayList<String>();
        for(Node entry: entries) {
            if(entry.getType().equals(NodeType.normal)) eastTowneArray.add(entry.getName());
        }

        // SearchableSpinners
        buildingSpinner1 = (SearchableSpinner) findViewById(R.id.spinner1_r);
        curLocSpinner1 = (SearchableSpinner) findViewById(R.id.spinner2_r);
        destSpinner1 = (SearchableSpinner) findViewById(R.id.spinner3_r);
        buildingSpinner2 = (SearchableSpinner) findViewById(R.id.spinner1_g);
        buildingSpinner3 = (SearchableSpinner) findViewById(R.id.spinner1_b);
        curLocSpinner3 = (SearchableSpinner) findViewById(R.id.spinner2_b);

        buildingAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, buildingArray);
        buildingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        eastTowneAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, eastTowneArray);
        eastTowneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        buildingSpinner1.setAdapter(buildingAdapter);
        buildingSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                curLocSpinner1.setEnabled(true);
                destSpinner1.setEnabled(true);
                populateSpinners(1, buildingSpinner1.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                curLocSpinner1.setEnabled(false);
                destSpinner1.setEnabled(false);
            }
        });

        buildingSpinner2.setAdapter(buildingAdapter);
        buildingSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                floors.setVisibility(View.GONE);
                populateGallery(buildingSpinner2.getSelectedItem());
                floors.setVisibility(View.VISIBLE);
                floors.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                floors.setVisibility(View.GONE);
                floors.setEnabled(false);
            }
        });

        buildingSpinner3.setAdapter(buildingAdapter);
        buildingSpinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                curLocSpinner3.setEnabled(true);
                populateSpinners(3, buildingSpinner3.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                curLocSpinner3.setEnabled(false);
            }
        });

        curLocSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                curLocSpinner1Valid = true;
                if(destSpinner1Valid) {
                    find1.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                curLocSpinner1Valid = false;
                find1.setEnabled(false);
            }
        });

        destSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                destSpinner1Valid = true;
                if(curLocSpinner1Valid) {
                    find1.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                curLocSpinner1Valid = false;
                find1.setEnabled(false);
            }
        });

        curLocSpinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                find2.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                find2.setEnabled(false);
            }
        });

        // Buttons
        find1 = (Button) findViewById(R.id.button_r);
        find2 = (Button) findViewById(R.id.button_b);

        // GridViews
        floors = (GridView) findViewById(R.id.gridView1_g);
        floorsAdapter = new ImageAdapter(this);
        floors.setAdapter(floorsAdapter);
        floors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                displayMap(getCurrentFocus(), floorsAdapter.getItemRef(position));
            }
        });

        favoritesArray = new ArrayList<String>();
        favorites = (GridView) findViewById(R.id.gridView1_f);
        favorites.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
        favorites.setSelector(android.R.color.darker_gray);
        updateFavorites();
        swipeDetector = new SwipeDetector();
        favorites.setOnTouchListener(swipeDetector);
        favorites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                final Favorite f = favoriteList.get(position);
                if(swipeDetector.swipeDetected()) {
                    if(swipeDetector.getAction() == SwipeDetector.Action.RL) {
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Delete entry")
                                .setMessage("Are you sure you want to delete the highlighted entry?")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dataBaseHandler.deleteFavorite(f);
                                        updateFavorites();
                                    }
                                })
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        // do nothing
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                } else {
                    displayMap(getCurrentFocus(), f.getBuildingName(), f.getStartLocation(), f.getDestination());
                }
            }
        });

        // Refresh list of favorites whenever switching to Favorites tab
        host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                if(s.equals("Favorites")) updateFavorites();
            }
        });
    }

    // Currently chooses from test data; will of course work with database later
    public void populateSpinners(int tab, Object selectedBuilding) {
        String buildingName = selectedBuilding.toString();
        if(buildingName.equals("East Towne Mall")) {
            if(tab == 1) {
                curLocSpinner1.setAdapter(eastTowneAdapter);
                destSpinner1.setAdapter(eastTowneAdapter);
            } else {
                curLocSpinner3.setAdapter(eastTowneAdapter);
            }
        } /** else if (buildingName.equals("Hogwarts School of Witchcraft and Wizardry")) {
            if(tab == 1) {
                curLocSpinner1.setAdapter(hogwartsAdapter);
                destSpinner1.setAdapter(hogwartsAdapter);
            } else {
                curLocSpinner3.setAdapter(hogwartsAdapter);
            }
        } **/
    }

    // Currently chooses from test data; will of course work with database later
    public void populateGallery(Object selectedBuilding) {
        floorsAdapter.setmThumbIds(null);
        String buildingName = selectedBuilding.toString();
        if(buildingName.equals("East Towne Mall")) {
            floorsAdapter.setmThumbIds(eastTowneFloors);
        } /** else if (buildingName.equals("Hogwarts School of Witchcraft and Wizardry")) {
            floorsAdapter.setmThumbIds(hogwartsFloors);
        } **/
    }

    // Generates the favorites list from the favorites database and sets the grid adapter
    public void updateFavorites() {
        favoritesArray.clear();
        favoriteList = dataBaseHandler.getAllFavorites();
        for(Favorite f: favoriteList) {
            //System.out.println(f.getIndx() + f.getBuildingName() + f.getStartLocation() + f.getDestination());
            favoritesArray.add(f.getBuildingName() + ": " + f.getStartLocation() + " to " + f.getDestination());
        }
        favoritesAdapter = new GridAdapter(favoritesArray);
        favorites.setAdapter(favoritesAdapter);
    }

    // Handle find button press from RoomFinder view
    public void findRF(View view) {
        displayMap(getCurrentFocus(), buildingSpinner1.getSelectedItem().toString(), curLocSpinner1.getSelectedItem().toString(), destSpinner1.getSelectedItem().toString());
    }

    // Handle find button press from BathroomFinder view
    public void findBF(View view) {
        displayMap(getCurrentFocus(), buildingSpinner3.getSelectedItem().toString(), curLocSpinner3.getSelectedItem().toString(), null);
    }

    // When given a floor from Floor Gallery
    public void displayMap(View view, Integer floor) {
        Intent intent = new Intent(this, MapView.class);
        intent.putExtra(EXTRA_FLOOR, floor);
        startActivity(intent);
    }

    // When given text info
    public void displayMap(View view, String building, String loc, String dest) {
        Intent intent = new Intent(this, MapView.class);
        intent.putExtra(EXTRA_BUILDING, building);
        intent.putExtra(EXTRA_LOC, loc);
        intent.putExtra(EXTRA_DEST, dest);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //not a very intensive test - only tests insert; shows output in debugger
    public void SQLTest(){
        dataBaseHandler.clearAllData(); //resets data

        List<Favorite> testFavorites = new ArrayList<Favorite>();
        for (int i = 0;i < 4; i++){
            testFavorites.add(new Favorite(i, "Test Building Name " + i, "Test Start Location " + i, "Test Destination " + i));
        }

        List<BuildingDB> testBuildings = new ArrayList<BuildingDB>();
        for (int i = 0;i < 4; i++){
            testBuildings.add(new BuildingDB(i, "Test Name(Building) " + i));
        }

        List<Location> testLocations = new ArrayList<Location>();
        for (int i = 0;i < 4; i++){
            testLocations.add(new Location(i, "Test Name(Location) " + i, i, i));
        }

        for (Favorite f: testFavorites){
            Log.d("Insert:", "Inserting "+f.getBuildingName());
            dataBaseHandler.addFavorite(f);
        }

        for (BuildingDB b: testBuildings){
            Log.d("Insert:", "Inserting "+b.getName());
            dataBaseHandler.addBuilding(b);
        }

        for (Location l: testLocations){
            Log.d("Insert:", "Inserting " + l.getName());
            dataBaseHandler.addLocation(l);
        }

        List<Favorite> resultFavorites = dataBaseHandler.getAllFavorites();
        Log.d("Reading:", "Favorites");
        for (Favorite f: resultFavorites){
            Log.d("Read:", "Index: " + f.getIndx() + " Building Name: "
                    + f.getBuildingName() + " Start Location: " + f.getStartLocation()
                    + " Destination: " + f.getDestination());
        }

        List<BuildingDB> resultBuildings = dataBaseHandler.getAllBuildings();
        Log.d("Reading:", "Buildings");
        for (BuildingDB b: resultBuildings){
            Log.d("Read:", "ID: " + b.getID() + " Name: " + b.getName());
        }

        List<Location> resultLocations = dataBaseHandler.getAllLocations();
        Log.d("Reading:", "Favorites");
        for (Location l: resultLocations){
            Log.d("Read:", "ID: " + l.getID() + " Name: "
                    + l.getName() + " Building ID: " + l.getBuildingID()
                    + " Floor Number: " + l.getFloorNumber());
        }

        dataBaseHandler.closeDB();


    }
}
