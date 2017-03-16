package sewisc.classroomfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TabHost;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
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

    // Temporary declarations and test data before database integration
    ArrayAdapter<String> buildingAdapter;
    ArrayAdapter<String> eastTowneAdapter;
    ArrayAdapter<String> hogwartsAdapter;
    ImageAdapter floorsAdapter;
    GridAdapter favoritesAdapter;

    Integer[] eastTowneFloors = {
            R.drawable.map
    };
    Integer[] hogwartsFloors = {
            R.drawable.map, R.drawable.map, R.drawable.map, R.drawable.map,
            R.drawable.map, R.drawable.map, R.drawable.map, R.drawable.map,
            R.drawable.map, R.drawable.map, R.drawable.map, R.drawable.map,
            R.drawable.map, R.drawable.map, R.drawable.map, R.drawable.map,
            R.drawable.map, R.drawable.map, R.drawable.map, R.drawable.map,
            R.drawable.map, R.drawable.map, R.drawable.map, R.drawable.map,
            R.drawable.map, R.drawable.map, R.drawable.map, R.drawable.map,
            R.drawable.map, R.drawable.map, R.drawable.map, R.drawable.map
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        // Generic test info; will later be replaced with database integration code
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

        List<String> favoritesArray = new ArrayList<String>();
        favoritesArray.add("Hogwarts School of Witchcraft and Wizardry: Headmaster's Office to Quidditch Pitch");
        favoritesArray.add("East Towne Mall: The Gap to Food Court");

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

        hogwartsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, hogwartsArray);
        hogwartsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

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
                if(destSpinner1Valid == true) {
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
                if(curLocSpinner1Valid == true) {
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
                displayMap(getCurrentFocus());
            }
        });

        favorites = (GridView) findViewById(R.id.gridView1_f);
        favoritesAdapter = new GridAdapter(favoritesArray);
        favorites.setAdapter(favoritesAdapter);
        favorites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                displayMap(getCurrentFocus());
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
        } else if (buildingName.equals("Hogwarts School of Witchcraft and Wizardry")) {
            if(tab == 1) {
                curLocSpinner1.setAdapter(hogwartsAdapter);
                destSpinner1.setAdapter(hogwartsAdapter);
            } else {
                curLocSpinner3.setAdapter(hogwartsAdapter);
            }
        }
    }

    // Currently chooses from test data; will of course work with database later
    public void populateGallery(Object selectedBuilding) {
        String buildingName = selectedBuilding.toString();
        if(buildingName.equals("East Towne Mall")) {
            floorsAdapter.setmThumbIds(eastTowneFloors);
        } else if (buildingName.equals("Hogwarts School of Witchcraft and Wizardry")) {
            floorsAdapter.setmThumbIds(hogwartsFloors);
        }
    }

    // Currently displays the same test map no matter where it's called from
    public void displayMap(View view) {
        Intent intent = new Intent(this, MapView.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}