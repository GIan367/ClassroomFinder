package sewisc.classroomfinder;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ListAdapter;
import android.widget.SearchView;
import android.widget.TextView;

/**
 * Created by Zak on 3/13/2017.
 */

public class LocationSearch extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        //setContentView(R.layout.activity_main);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();

        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText("Testing");

        /*if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }*/
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

        return true;
    }

    // TODO: Interface with database
    public void doMySearch(String query) {
        // Return SQLite search results with an adapter, I guess? -- Zak
        // https://developer.android.com/guide/topics/search/search-dialog.html#SearchingYourData
        ListAdapter adapter = null;

        setListAdapter(adapter);
    }*/
}
