package sewisc.classroomfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TabHost;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TabHost tabHost;

    SearchableSpinner buildingSpinner;
    SearchableSpinner curLocSpinner;
    SearchableSpinner destSpinner;

    // Temporary declarations before database integration
    ArrayAdapter<String> buildingAdapter;
    ArrayAdapter<String> eastTowneAdapter;
    ArrayAdapter<String> hogwartsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost host = (TabHost) findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Tab One");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Tab Two");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("Tab Three");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Tab Three");
        host.addTab(spec);

	    //Tab 4
        spec = host.newTabSpec("Tab Four");
        spec.setContent(R.id.tab4);
        spec.setIndicator("Tab Four");
        host.addTab(spec);


        SQLTest();

        // SearchableSpinners
        // All populated with generic test info at the moment; will later be linked to database
        buildingSpinner = (SearchableSpinner) findViewById(R.id.spinner1);
        curLocSpinner = (SearchableSpinner) findViewById(R.id.spinner2);
        destSpinner = (SearchableSpinner) findViewById(R.id.spinner3);

        List<String> buildingArray = new ArrayList<String>();
        buildingArray.add("East Towne Mall");
        buildingArray.add("Hogwarts School of Witchcraft and Wizardry");

        List<String> eastTowneArray = new ArrayList<String>();
        eastTowneArray.add("The Gap");
        eastTowneArray.add("Food Court");
        eastTowneArray.add("Radio Shack");

        List<String> hogwartsArray = new ArrayList<String>();
        hogwartsArray.add("Headmaster's Office");
        hogwartsArray.add("The Great Hall");
        hogwartsArray.add("Gryffindor Tower");
        hogwartsArray.add("Quidditch Pitch");

        buildingAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, buildingArray);
        buildingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        eastTowneAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, eastTowneArray);
        eastTowneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        hogwartsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, hogwartsArray);
        hogwartsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        buildingSpinner.setAdapter(buildingAdapter);
        buildingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                curLocSpinner.setEnabled(true);
                destSpinner.setEnabled(true);
                populateSpinners(buildingSpinner.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                curLocSpinner.setEnabled(false);
                destSpinner.setEnabled(false);
            }
        });
    }

    // Currently chooses from test data; will of course work with database later
    public void populateSpinners(Object selectedBuilding) {
        String buildingName = selectedBuilding.toString();
        if(buildingName.equals("East Towne Mall")) {
            curLocSpinner.setAdapter(eastTowneAdapter);
            destSpinner.setAdapter(eastTowneAdapter);
        } else if (buildingName.equals("Hogwarts School of Witchcraft and Wizardry")) {
            curLocSpinner.setAdapter(hogwartsAdapter);
            destSpinner.setAdapter(hogwartsAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // Got rid of settings action bar item because we don't need it.
    // Also commented out relevant stuff in menu_main.xml -- Zak
    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/



    //not a very intensive test - only tests insert; shows output in debugger
    public void SQLTest(){
        DataBaseHandler dataBaseHandler = new DataBaseHandler(this);
        dataBaseHandler.clearAllData(); //resets data

        List<Favorite> testFavorites = new ArrayList<Favorite>();
        for (int i = 0;i < 4; i++){
            testFavorites.add(new Favorite(i, "Test Building Name " + i, "Test Start Location " + i, "Test Destination " + i));
        }

        List<Building> testBuildings = new ArrayList<Building>();
        for (int i = 0;i < 4; i++){
            testBuildings.add(new Building(i, "Test Name(Building) " + i));
        }

        List<Location> testLocations = new ArrayList<Location>();
        for (int i = 0;i < 4; i++){
            testLocations.add(new Location(i, "Test Name(Location) " + i, i, i));
        }

        for (Favorite f: testFavorites){
            Log.d("Insert:", "Inserting "+f.getBuildingName());
            dataBaseHandler.addFavorite(f);
        }

        for (Building b: testBuildings){
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

        List<Building> resultBuildings = dataBaseHandler.getAllBuildings();
        Log.d("Reading:", "Buildings");
        for (Building b: resultBuildings){
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