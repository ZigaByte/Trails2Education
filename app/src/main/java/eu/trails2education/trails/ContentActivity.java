package eu.trails2education.trails;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Response;

import org.json.JSONObject;

import eu.trails2education.trails.database.Content;
import eu.trails2education.trails.database.InterestPoint;
import eu.trails2education.trails.json.InterestPointJSON;
import eu.trails2education.trails.network.InterestPointUtils;
import eu.trails2education.trails.views.ContentSelectionAdapter;

public class ContentActivity extends AppCompatActivity {

    RecyclerView contentList;

    private InterestPoint interestPoint;

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

        final int interestPointID = (int)getIntent().getExtras().getLong("InterestPointID");
        InterestPointUtils.readInterestPointFromNetwork(this, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    interestPoint = InterestPointJSON.createInterestPointFromJSON(ContentActivity.this, response.getJSONArray("posts").getJSONObject(0), 0);
                    fillViews();

                }catch(Exception e){
                    Log.e("INTEREST POINT LOADING ", "ERROR at id: " + interestPointID);
                }
            }
        }, interestPointID);


    }

    /**
     * Updates the views. Network or DB updates may change data in interestPoint.
     * */
    public void fillViews(){
        if(interestPoint.getContents().size() > 0){
            contentList = (RecyclerView)findViewById(R.id.recyclerView);
            contentList.setAdapter(new ContentSelectionAdapter(interestPoint.getContents())); // Pass the ids for the icons
            contentList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

            // Get first subject to populate views
            Content first = interestPoint.getContents().get(0);

            ((TextView)findViewById(R.id.subject_title)).setText(first.gettitEN());
            ((TextView)findViewById(R.id.subject_content)).setText(first.getdesEN());
        }else{
        }
    }

}
