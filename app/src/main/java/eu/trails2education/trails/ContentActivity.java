package eu.trails2education.trails;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import eu.trails2education.trails.database.Content;
import eu.trails2education.trails.database.ContentDAO;
import eu.trails2education.trails.database.InterestPoint;
import eu.trails2education.trails.database.InterestPointDAO;
import eu.trails2education.trails.database.Multimedia;
import eu.trails2education.trails.database.MultimediaDAO;
import eu.trails2education.trails.database.Pathway;
import eu.trails2education.trails.json.ContentJSON;
import eu.trails2education.trails.json.InterestPointJSON;
import eu.trails2education.trails.json.PathwayJSON;
import eu.trails2education.trails.network.ContentUtils;
import eu.trails2education.trails.network.InterestPointUtils;
import eu.trails2education.trails.network.PathUtils;
import eu.trails2education.trails.network.RequestManager;
import eu.trails2education.trails.views.ContentSelectionAdapter;
import eu.trails2education.trails.views.MyTimer;

public class ContentActivity extends AppCompatActivity {

    RecyclerView contentList;

    private InterestPointDAO interestPointDAO;
    private ContentDAO contentDAO;
    private MultimediaDAO multimediaDAO;

    private InterestPoint interestPoint;
    private Pathway path;

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
        contentDAO = new ContentDAO(this);
        multimediaDAO = new MultimediaDAO(this);

        final int interestPointID = (int)getIntent().getExtras().getLong("InterestPointID");
        readInterestPointFromDatabase(interestPointID);
        readInterestPointFromNetwork(interestPointID);

        String time = getIntent().getExtras().getString("time");//((TextView)findViewById(R.id.timeText)).getText().toString();
        ((MyTimer)findViewById(R.id.timeText)).setTime(time);

    }

    private void readInterestPointFromDatabase(int interestPointID){
        interestPoint = interestPointDAO.getInterestPointsById(interestPointID);
        interestPoint.setContents(contentDAO.getContentsOfInterestPoint(interestPointID));
        for(Content c: interestPoint.getContents()){
            c.setMultimedia(multimediaDAO.getMultimediaOfContent(c.getcIdC()));

            /*for(Multimedia m : c.getMultimedia()){
                Log.e("Multimedia", "a " + m.getId() + " " + m.geteURL());
            }*/
        }
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
                    return;
                }

                readContentFromNetwork((int)newInterestPoint.getcIdIP(), newInterestPoint.getSubjectIds());

                interestPointDAO.createInterestPoint(newInterestPoint, InterestPointDAO.INSERT_TYPE_DATA);
                readInterestPointFromDatabase(interestPointID);
            }
        }, interestPointID);
    }

    private void readContentFromNetwork(final int interestPointID, ArrayList<Integer> subjectIds){
        final ArrayList<Content> contents = new ArrayList<Content>();
        final Context context = this;
        for(Integer i : subjectIds){
            final int ii = i; // ok.
            ContentUtils.readContentFromNetwork(this, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Content newContent = null;
                    try {
                        newContent = ContentJSON.createContentFromJSON(context, response.getJSONArray("posts").getJSONObject(0), 0);
                    }catch (Exception e){
                        Log.e("CONTENT LOADING ERROR", "ERROR WITH CONTENT");
                    }
                    newContent.setIpId(interestPointID);
                    //newContent.setstype(ii);

                    contentDAO.createContent(newContent, ContentDAO.INSERT_TYPE_DATA);
                    for(Multimedia m : newContent.getMultimedia()){
                        m.setCId(newContent.getcIdC());
                        multimediaDAO.createMultimedia(m);
                    }

                    readInterestPointFromDatabase(interestPointID);
                }
            }, interestPointID, i);
        }
    }

    /**
     * Updates the views. Network or DB updates may change data in interestPoint.
     * */
    public void fillViews(){
        if(interestPoint.getContents().size() > 0){
            contentList = (RecyclerView)findViewById(R.id.recyclerView);
            contentList.setAdapter(new ContentSelectionAdapter(this, interestPoint.getContents())); // Pass the ids for the icons
            contentList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

            // Get first subject to populate views
            Content first = interestPoint.getContents().get(0);

            ((TextView)findViewById(R.id.subject_title)).setText(first.gettitEN());
            ((TextView)findViewById(R.id.subject_content)).setText(first.getdesEN());

            // Load multimedia
            LayoutInflater inflater = getLayoutInflater();
            LinearLayout linearLayout = ((LinearLayout)findViewById(R.id.content_container));
            linearLayout.removeAllViewsInLayout();
            for(Multimedia m : first.getMultimedia()){
                NetworkImageView imageView = (NetworkImageView) inflater.inflate(R.layout.content_image, null);
                String url = "http://" + m.geteURL();
                ImageLoader loader = RequestManager.getInstance(getApplicationContext()).getImageLoader();
                loader.get(url, loader.getImageListener(imageView, R.mipmap.ic_launcher, R.mipmap.ic_launcher_round));
                imageView.setImageUrl(url, loader);

                linearLayout.addView(imageView);
            }

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
        }
    }

}
