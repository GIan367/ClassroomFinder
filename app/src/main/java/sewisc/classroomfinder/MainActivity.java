package sewisc.classroomfinder;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.gesture.Gesture;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TabHost;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import com.vstechlab.easyfonts.EasyFonts;
import android.widget.TextView;
import android.widget.Toast;

import mehdi.sakout.fancybuttons.FancyButton;

import org.xmlpull.v1.XmlPullParserException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    DataBaseHandler dataBaseHandler;
    List<Favorite> favoriteList;
    List<String> favoritesArray;
    SearchableSpinner buildingSpinner1;
    SearchableSpinner curLocSpinner1;
    SearchableSpinner destSpinner1;
    SearchableSpinner buildingSpinner2;
    SearchableSpinner buildingSpinner3;
    SearchableSpinner curLocSpinner3;
    View room_CurrLoc;
    View room_SpinCurrLoc;
    View room_Building;
    View bath_Building;
    View bath_CurrLoc;
    View gal_Building;
    TextView rb;
    View room_SpinBuilding;
    View room_Dest;
    View room_SpinDest;
    GridView floors;
    GridView favorites;
    FancyButton find1;
    FancyButton find2;

    boolean firstPassIsDone = false;


    TabHost host;
    boolean curLocSpinner1Valid;
    boolean destSpinner1Valid;

    TextView tvCapture;

    public static final String EXTRA_BUILDING = "sewisc.classroomfinder.BUILDING";
    public static final String EXTRA_LOC = "sewisc.classroomfinder.LOC";
    public static final String EXTRA_DEST = "sewisc.classroomfinder.DEST";
    public static final String EXTRA_FLOOR = "sewisc.classroomfinder.FLOOR";

    ArrayAdapter<String> buildingAdapter;
    ArrayAdapter<String> eastTowneAdapter;
    ArrayAdapter<String> compSciAdapter;
    ImageAdapter floorsAdapter;
    GridAdapter favoritesAdapter;

    private float x1,x2,y1,y2,deltaX,deltaY;
    static final int MIN_DISTANCE = 150;

    boolean avail;

    final Handler handler = new Handler();

    Timer timer;
    Spinner currSpinnerShaking;
    TimerTask task = new TimerTask() {
        @Override
        public void run() {

            handler.post(new Runnable() {
                public void run() {
                    Spinner spin = (Spinner) currSpinnerShaking;
                    Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                    spin.startAnimation(shake);
                }

            });

        }
    };

    Integer[] eastTowneFloors = {
            R.mipmap.east_towne1
    };

    //cs101
    Integer[] csFloors = {
            R.mipmap.cs
    };

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        for ( int i = 0; i < host.getTabWidget().getTabCount(); i++) {
            TextView tv = (TextView) host.getTabWidget().getChildTabViewAt(i).findViewById(android.R.id.title);
            tv.setTextColor(Color.parseColor("#ffffff"));
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();
                deltaX = x2 - x1;
                deltaY = y2 - y1;
                if ((Math.abs(deltaX) > MIN_DISTANCE) && (Math.abs(deltaX) > Math.abs(deltaY)))
                {
                    if (x2 > x1)
                    {
                        int curr = host.getCurrentTab();
                        if(curr == 1) {
                            host.setCurrentTab(0);
                        }
                        else if(curr == 2) {
                            host.setCurrentTab(1);
                        }
                        // Handled by the favorites GridView
                        /*else if(curr == 3) {
                            host.setCurrentTab(2);
                        }*/
                    }

                    // Right to left swipe action
                    else
                    {
                        int curr = host.getCurrentTab();
                        if(curr == 0) {
                            host.setCurrentTab(1);
                        }
                        else if(curr == 1) {
                            host.setCurrentTab(2);
                        }
                        else if(curr == 2) {
                            host.setCurrentTab(3);
                        }
                    }
                }
                else
                {
                    // consider as something else - a screen tap for example
                }
                break;
        }
        return super.onTouchEvent(event);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        room_Building = findViewById(R.id.textView3_r);
        rb = (TextView) room_Building;
        rb.animate().scaleX(1.5f);
        rb.animate().scaleY(1.5f);
        room_Building.animate().translationY(200);
        room_SpinBuilding = findViewById(R.id.spinner1_r);



        //spin.animate().scaleY(1.5f);
        room_SpinBuilding.animate().translationY(200);

        room_Dest = findViewById(R.id.textView_r);
        room_Dest.animate().alpha(0.0f);
        room_SpinDest = findViewById(R.id.spinner3_r);
        room_SpinDest.animate().alpha(0.0f);

        room_CurrLoc = findViewById(R.id.textView2_r);
        room_CurrLoc.animate().alpha(0.0f);

        room_SpinCurrLoc = findViewById(R.id.spinner2_r);
        room_SpinCurrLoc.animate().alpha(0.0f);

        bath_Building = findViewById(R.id.textView3_b);
        bath_CurrLoc = findViewById(R.id.textView_b);

        gal_Building = findViewById(R.id.textView3_g);


        tvCapture = (TextView) room_Building;
        tvCapture.setTypeface(EasyFonts.captureIt(this));
        tvCapture = (TextView) room_CurrLoc;
        tvCapture.setTypeface(EasyFonts.captureIt(this));
        tvCapture = (TextView) room_Dest;
        tvCapture.setTypeface(EasyFonts.captureIt(this));

        tvCapture = (TextView) bath_Building;
        tvCapture.setTypeface(EasyFonts.captureIt(this));
        tvCapture = (TextView) bath_CurrLoc;
        tvCapture.setTypeface(EasyFonts.captureIt(this));
        tvCapture = (TextView) gal_Building;
        tvCapture.setTypeface(EasyFonts.captureIt(this));



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
            System.out.println("East Towne XML file not found");
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
        InputStream streamCS = null;
        XMLParser xmlParserCS = new XMLParser();
        List<Node> entriesCS = null;
        try {
            streamCS = getAssets().open("computersciences.xml");
            entriesCS = xmlParserCS.parse(streamCS);
        }
        catch (FileNotFoundException e) {
            System.out.println("Computer Sciences XML file not found");
        } catch (XmlPullParserException e) {
            System.out.println("parser not working");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (streamCS != null) {
            try {
                streamCS.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /** for(Node entry: entries) {
         System.out.println("Name: " + entry.getName() + " Type " +  entry.getType() + " X: "
         + entry.getRelativeX() + " Y: " + entry.getRelativeY() + " Z: " + entry.getFloor());
         }
        for(Node entryCS: entriesCS) {
         System.out.println("Name: " + entryCS.getName() + " Type " +  entryCS.getType() + " X: "
         + entryCS.getRelativeX() + " Y: " + entryCS.getRelativeY() + " Z: " + entryCS.getFloor());
         } **/

        // Set up tabs
        host = (TabHost) findViewById(R.id.tabHost);
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
        spec.setIndicator("Bathrm Finder");

        spec.setContent(R.id.bathroom);
        host.addTab(spec);

        //Tab 4
        spec = host.newTabSpec("Favorites");
        spec.setIndicator("Saved Paths");
        spec.setContent(R.id.favorite);
        host.addTab(spec);

        // Replace with reading info from XML files?
        List<String> buildingArray = new ArrayList<String>();
        buildingArray.add("East Towne Mall");
        buildingArray.add("Computer Sciences");

        List<String> eastTowneArray = new ArrayList<String>();
        for(Node entry: entries) {
            if(entry.getType().equals(NodeType.normal)) eastTowneArray.add(entry.getName());
        }
        List<String> compSciArray = new ArrayList<String>();
        for(Node entryCS: entriesCS) {
            if(entryCS.getType().equals(NodeType.normal)) compSciArray.add(entryCS.getName());
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

        compSciAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, compSciArray);
        compSciAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        timer = new Timer("shakeAndBake");
        timer.schedule(task,5000, 5000);

        buildingSpinner1.setAdapter(buildingAdapter);
        buildingSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currSpinnerShaking = (Spinner) room_SpinCurrLoc;

                //timer.cancel();
                curLocSpinner1.setEnabled(true);

                //destSpinner1.setEnabled(true);
                rb.animate().scaleX(1.0f);
                rb.animate().scaleY(1.0f);
                room_CurrLoc.animate().alpha(1.0f).setDuration(1000);
                if(!firstPassIsDone) {
                    room_CurrLoc.animate().scaleY(1.2f);
                    room_CurrLoc.animate().scaleX(1.2f);
                }

                room_SpinCurrLoc.animate().alpha(1.0f).setDuration(1000);
                //room_Dest.animate().alpha(1.0f).setDuration(1000);
                //room_SpinDest.animate().alpha(1.0f).setDuration(1000);
                room_Building.animate().translationY(0).setDuration(500);
                room_SpinBuilding.animate().translationY(0).setDuration(500);
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
                //cs101
                floorsAdapter.notifyDataSetChanged();


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
                    find1.setVisibility(View.VISIBLE);
                    View findButton = (View) find1;
                    int btWidth = find1.getMeasuredWidth();
                    DisplayMetrics metrics = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(metrics);
                    int width = metrics.widthPixels;
                    ObjectAnimator outAnim = ObjectAnimator.ofFloat(findButton, "x", width,
                            (width/2f) - (btWidth/2f));
                    outAnim.setDuration(300);
                    outAnim.start();
                }
                room_CurrLoc.animate().scaleY(1.0f);
                room_CurrLoc.animate().scaleX(1.0f);
                destSpinner1.setEnabled(true);
                if(!firstPassIsDone) {
                    room_Dest.animate().scaleX(1.3f);
                    room_Dest.animate().scaleY(1.3f);
                }

                room_Dest.animate().alpha(1.0f).setDuration(1000);
                room_SpinDest.animate().alpha(1.0f).setDuration(1000);

                currSpinnerShaking = (Spinner) room_SpinDest;
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
                timer.cancel();
                destSpinner1Valid = true;
                firstPassIsDone = true;
                if(curLocSpinner1Valid) {


                    //resize all text views back
                    room_Dest.animate().scaleX(1.0f);
                    room_Dest.animate().scaleY(1.0f);



                    find1.setEnabled(true);
                    find1.setVisibility(View.VISIBLE);
                    int btWidth = find1.getMeasuredWidth();
                    View findButton = (View) find1;
                    DisplayMetrics metrics = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(metrics);
                    int width = metrics.widthPixels;
                    ObjectAnimator outAnim = ObjectAnimator.ofFloat(findButton, "x", width,
                            (width/2f) - (btWidth/2f));
                    outAnim.setDuration(300);
                    outAnim.start();
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
        find1 = (FancyButton) findViewById(R.id.button_r);
        find1.setVisibility(View.INVISIBLE);

        find2 = (FancyButton) findViewById(R.id.button_b);

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
        floors.setOnTouchListener(new AdapterView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, final MotionEvent event){
                switch(event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        y1 = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = event.getX();
                        y2 = event.getY();
                        deltaX = x2 - x1;
                        deltaY = y2 - y1;
                        if ((Math.abs(deltaX) > MIN_DISTANCE) && (Math.abs(deltaX) > Math.abs(deltaY))) {
                            if (x2 > x1) {
                                host.setCurrentTab(0);
                            } else {
                                host.setCurrentTab(2);
                            }
                        } else if((Math.abs(deltaY) > MIN_DISTANCE) && (Math.abs(deltaY) > Math.abs(deltaX))) {
                                floors.smoothScrollBy((int) -deltaY, 2 * (int) Math.abs(deltaY));
                        } else {
                            GestureDetector gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {});
                            return gestureDetector.onTouchEvent(event);
                        }
                        break;
                }
                return true;
            }
        });

        favoritesArray = new ArrayList<String>();
        favorites = (GridView) findViewById(R.id.gridView1_f);
        favorites.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
        updateFavorites();
        avail = true;
        favorites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if(avail == true){
                    final Favorite f = favoriteList.get(position);
                    displayMap(getCurrentFocus(), f.getBuildingName(), f.getStartLocation(), f.getDestination());
                }
            }
        });
        final Handler handler = new Handler();
        final Runnable mLongPressed = new Runnable() {
            public void run() {
                avail = false;
                int position = favorites.pointToPosition((int) x1, (int) y1);
                if(position!=GridView.INVALID_POSITION){
                    final Favorite f = favoriteList.get(position);
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Delete entry")
                            .setMessage("Delete path " + f.getBuildingName() + ": " + f.getStartLocation() + " to " + f.getDestination() + "?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dataBaseHandler.deleteFavorite(f);
                                    updateFavorites();
                                    avail = true;
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    avail = true;
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        };
        favorites.setOnTouchListener(new AdapterView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, final MotionEvent event){
                switch(event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        y1 = event.getY();
                        handler.postDelayed(mLongPressed, 1000);
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = event.getX();
                        y2 = event.getY();
                        handler.removeCallbacks(mLongPressed);
                        deltaX = x2 - x1;
                        deltaY = y2 - y1;
                        if ((Math.abs(deltaX) > MIN_DISTANCE) && (Math.abs(deltaX) > Math.abs(deltaY))) {
                            if (x2 > x1) {
                                host.setCurrentTab(2);
                            }
                        } else if((Math.abs(deltaY) > MIN_DISTANCE) && (Math.abs(deltaY) > Math.abs(deltaX))) {
                            if(avail == true) {
                                favorites.smoothScrollBy((int) -deltaY, 2 * (int) Math.abs(deltaY));
                            }
                        } else {
                            GestureDetector gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {});
                            return gestureDetector.onTouchEvent(event);
                        }
                        break;
                }
                return true;
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

    public void populateSpinners(int tab, Object selectedBuilding) {
        String buildingName = selectedBuilding.toString();
        if(buildingName.equals("East Towne Mall")) {
            if(tab == 1) {
                curLocSpinner1.setAdapter(eastTowneAdapter);
                destSpinner1.setAdapter(eastTowneAdapter);
            } else {
                curLocSpinner3.setAdapter(eastTowneAdapter);
            }
        } else if (buildingName.equals("Computer Sciences")) {
            if (tab == 1) {
                curLocSpinner1.setAdapter(compSciAdapter);
                destSpinner1.setAdapter(compSciAdapter);
            } else {
                curLocSpinner3.setAdapter(compSciAdapter);
            }
        }
    }

    public void populateGallery(Object selectedBuilding) {
        floorsAdapter.setmThumbIds(null);
        String buildingName = selectedBuilding.toString();
        if(buildingName.equals("East Towne Mall")) {
            floorsAdapter.setmThumbIds(eastTowneFloors);
        } else if(buildingName.equals("Computer Sciences")) {
            floorsAdapter.setmThumbIds(csFloors);
        }
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
}
