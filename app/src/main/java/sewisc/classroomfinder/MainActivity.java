package sewisc.classroomfinder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
}