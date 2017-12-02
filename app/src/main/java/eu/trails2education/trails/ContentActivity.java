package eu.trails2education.trails;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.List;

import eu.trails2education.trails.views.ContentSelectionAdapter;
import eu.trails2education.trails.views.SelectionAdapter;

public class ContentActivity extends AppCompatActivity {

    RecyclerView contentList;
    ContentSelectionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        // Set the back button to exit the activity and return to the MapActivity
        ImageButton backButton = (ImageButton) findViewById(R.id.button_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        contentList = (RecyclerView)findViewById(R.id.recyclerView);
        contentList.setAdapter(new ContentSelectionAdapter());
        contentList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Getting adapter by passing xml data ArrayList
//        adapter = new ContentSelectionAdapter(this);
//        contentList.setAdapter(adapter);


        // Click event for single list row
        /*contentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Start the map activity
                Intent i = new Intent(SelectionActivity.this, MapsActivity.class);
                startActivity(i);

            }
        });*/
    }

}
