package sewisc.classroomfinder;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.TabHost;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TabHost tabHost;

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

    public void openSearch(View view) {
        Intent intent = new Intent(this, LocationSearch.class);
        startActivity(intent);
    }


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