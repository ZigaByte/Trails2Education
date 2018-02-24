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

import java.util.Timer;
import java.util.TimerTask;

import eu.trails2education.trails.database.Content;
import eu.trails2education.trails.database.InterestPoint;
import eu.trails2education.trails.database.InterestPointDAO;
import eu.trails2education.trails.database.Pathway;
import eu.trails2education.trails.json.InterestPointJSON;
import eu.trails2education.trails.json.PathwayJSON;
import eu.trails2education.trails.network.InterestPointUtils;
import eu.trails2education.trails.network.PathUtils;
import eu.trails2education.trails.views.ContentSelectionAdapter;

import static android.R.attr.name;

public class ContentActivity extends AppCompatActivity {

    RecyclerView contentList;

    private InterestPointDAO interestPointDAO;

    private InterestPoint interestPoint;
    private Pathway path;

    // Timer
    public int seconds = 0;
    public int minutes = 0;
    public int hours = 0;

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

        interestPointDAO = new InterestPointDAO(this);

        final int interestPointID = (int)getIntent().getExtras().getLong("InterestPointID");
        readInterestPointFromDatabase(interestPointID);
        readInterestPointFromNetwork(interestPointID);

        String time = getIntent().getExtras().getString("time");//((TextView)findViewById(R.id.timeText)).getText().toString();
        int colonStart=time.indexOf(":");

        seconds = Integer.parseInt(time.substring(colonStart+4));//Integer.parseInt(String.valueOf(((TextView)findViewById(R.id.timeText)).getText().subSequence(colonStart+4,colonStart+5)));
        minutes = Integer.parseInt(time.substring(colonStart+1,colonStart+2));//Integer.parseInt(String.valueOf(((TextView)findViewById(R.id.timeText)).getText().subSequence(colonStart+1,colonStart+2)));
        hours = Integer.parseInt(time.substring(0,colonStart));//Integer.parseInt(String.valueOf(((TextView)findViewById(R.id.timeText)).getText().subSequence(0,colonStart)));

        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        seconds++;
                        if(seconds >= 60){
                            seconds -= 60;
                            minutes++;
                            if(minutes >= 60){
                                minutes-= 60;
                                hours++;
                            }
                        }
                        ((TextView)findViewById(R.id.timeText)).setText(hours + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
                    }
                });
            }
        }, 0, 1000);

    }

    private void readInterestPointFromDatabase(int interestPointID){
        interestPoint = interestPointDAO.getInterestPointsById(interestPointID);
        fillViews();
    }

    private void readInterestPointFromNetwork(final int interestPointID){
        InterestPointUtils.readInterestPointFromNetwork(this, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                InterestPoint newInterestPoint = null;
                try {
                    newInterestPoint = InterestPointJSON.createInterestPointFromJSON(ContentActivity.this, response.getJSONArray("posts").getJSONObject(0), 0);
                }catch(Exception e){
                    Log.e("INTEREST POINT LOADING ", "ERROR at id: " + interestPointID);
                }
                interestPointDAO.createInterestPoint(newInterestPoint, InterestPointDAO.INSERT_TYPE_DATA);
                readInterestPointFromDatabase(interestPointID);
            }
        }, interestPointID);
    }

    /**
     * Updates the views. Network or DB updates may change data in interestPoint.
     * */
    public void fillViews(){
        Log.e("CONTENT", ""+ interestPoint.getContents().size());
        if(interestPoint.getContents().size() > 0){
            contentList = (RecyclerView)findViewById(R.id.recyclerView);
            contentList.setAdapter(new ContentSelectionAdapter(this, interestPoint.getContents())); // Pass the ids for the icons
            contentList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

            // Get first subject to populate views
            Content first = interestPoint.getContents().get(0);

            ((TextView)findViewById(R.id.subject_title)).setText(first.gettitEN());
            ((TextView)findViewById(R.id.subject_content)).setText(first.getdesEN());

            final int pathID = (int)getIntent().getExtras().getLong("PathwayID");
            // Read the path from the network
            PathUtils.readPathFromNetwork(this, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        path = PathwayJSON.createFullPathFromJSON(ContentActivity.this, response);
                    }catch(Exception e){
                        Log.e("PATH LOADING ERROR", "Failed loading the path");
                    }

                    String name = path.getNameEN();
                    if(name.length() > 12)
                        name = name.substring(0, 8) + " ..";

                    ((TextView)findViewById(R.id.textView2)).setText(name);  // Svetlana: 17.1.2018
                    ((TextView)findViewById(R.id.textView3)).setText(String.valueOf(path.getcouEN()));

                }
            }, pathID);




        }else{
        }
    }

}
