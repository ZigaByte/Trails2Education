package eu.trails2education.trails;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import eu.trails2education.trails.views.SelectionAdapter;

/**
 * Activity that allows the user to select one of the paths.
 * */
public class SelectionActivity extends AppCompatActivity {

    ListView list;
    SelectionAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        list = (ListView)findViewById(R.id.selectionList);

        // Getting adapter by passing xml data ArrayList
        adapter = new SelectionAdapter(this);
        list.setAdapter(adapter);

        // Click event for single list row
        list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Start the map activity
                Intent i = new Intent(SelectionActivity.this, MapsActivity.class);
                startActivity(i);

            }
        });
    }
}
