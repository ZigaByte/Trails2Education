package eu.trails2education.trails;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import eu.trails2education.trails.database.Content;
import eu.trails2education.trails.database.ContentDAO;
import eu.trails2education.trails.database.Coordinates;
import eu.trails2education.trails.database.CoordinatesDAO;
import eu.trails2education.trails.database.InterestPoint;
import eu.trails2education.trails.database.InterestPointDAO;
import eu.trails2education.trails.database.Multimedia;
import eu.trails2education.trails.database.MultimediaDAO;
import eu.trails2education.trails.database.Pathway;
import eu.trails2education.trails.database.PathwaysDAO;
import eu.trails2education.trails.json.ContentJSON;
import eu.trails2education.trails.json.InterestPointJSON;
import eu.trails2education.trails.json.PathwayJSON;
import eu.trails2education.trails.network.ContentUtils;
import eu.trails2education.trails.network.InterestPointUtils;
import eu.trails2education.trails.network.PathUtils;
import eu.trails2education.trails.views.map.MyMap;
import eu.trails2education.trails.views.map.PreviewMap;

/**
 * Created by Ziga on 06-Jun-18.
 */

public class PathPreviewActivity extends FragmentActivity {

    private PreviewMap myMap;

    private PathwaysDAO pathwaysDAO;
    private CoordinatesDAO coordinatesDAO;
    private InterestPointDAO interestPointDAO;
    private ContentDAO contentDAO;
    private MultimediaDAO multimediaDAO;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_preview);

        myMap = new PreviewMap(this, R.id.preview_map);

        // Database connections
        pathwaysDAO = new PathwaysDAO(this);
        coordinatesDAO = new CoordinatesDAO(this);
        interestPointDAO = new InterestPointDAO(this);
        contentDAO = new ContentDAO(this);
        multimediaDAO = new MultimediaDAO(this);

        final int pathID = (int)getIntent().getExtras().getLong("PathID"); // Get the selected path ID
        readPathwayFromDatabase(pathID);
        readPathwayFromNetwork(pathID);

        findViewById(R.id.button_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PathPreviewActivity.this, MapsActivity.class);
                i.putExtra("PathID", (long)pathID);
                startActivity(i);
            }
        });


        // Notification test
        /*createNotificationChannel();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "A")
                .setSmallIcon(R.drawable.interest_point_museum)
                .setContentTitle("Trails2Education")
                .setContentText("This is a notification with some long text that will hopefully be in 2 layers")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(123456, mBuilder.build());*/
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notifications";
            String description = "Basic notifications for the application";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("A", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void readPathwayFromNetwork(final int pathwayId){
        // Read the path from the network
        PathUtils.readPathFromNetwork(this, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Pathway newPathway = null;
                try {
                    newPathway = PathwayJSON.createFullPathFromJSON(PathPreviewActivity.this, response);
                }catch(Exception e){
                    Log.e("PATH LOADING ERROR", "Failed loading the path");
                }
                Log.e("PATH", newPathway + " ");

                // Only update path if there is a new version. Return otherwise
                if(newPathway.getupdDate().equals(myMap.getPathway().getupdDate())){
                    return;
                }

                // Insert Pathway into the database
                pathwaysDAO.createPathway(newPathway);
                pathwaysDAO.updateLastUpdated(newPathway.getId(), newPathway.getupdDate());

                // Insert the cooridnates into the database
                coordinatesDAO.deleteCoordinatesOfPathway((int)newPathway.getId());
                for(Coordinates c : newPathway.getCoordinates()){
                    coordinatesDAO.createCoordinates(c);
                }
                // Insert the interest points into the database
                interestPointDAO.deleteInterestPointsOfPathway((int)newPathway.getId());
                for(InterestPoint ip : newPathway.getInterestPoints()){
                    ip.setPathwayID(newPathway.getId()); // Set the pathway ID as it is not present in the JSON TODO: Tell Duarte to include it
                    interestPointDAO.createInterestPoint(ip, InterestPointDAO.INSERT_TYPE_COORDINATES);

                    readInterestPointFromNetwork((int)ip.getcIdIP());
                }

                // Read the database again and update the views if there was a change
                readPathwayFromDatabase(pathwayId);
            }
        }, pathwayId);
    }

    private void readInterestPointFromNetwork(final int interestPointID){
        InterestPointUtils.readInterestPointFromNetwork(this, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                InterestPoint newInterestPoint = null;
                try {
                    newInterestPoint = InterestPointJSON.createInterestPointFromJSON(PathPreviewActivity.this, response.getJSONArray("posts").getJSONObject(0), 0);
                }catch(Exception e){
                    Log.e("INTEREST POINT LOADING ", "ERROR at id: " + interestPointID);
                    return;
                }

                readContentFromNetwork((int)newInterestPoint.getcIdIP(), newInterestPoint.getSubjectIds());

                interestPointDAO.createInterestPoint(newInterestPoint, InterestPointDAO.INSERT_TYPE_DATA);
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

                    contentDAO.createContent(newContent, ContentDAO.INSERT_TYPE_DATA);

                    // Do new multimedia
                    multimediaDAO.deleteMultimediaOfContent((int)newContent.getcIdC());
                    for(Multimedia m : newContent.getMultimedia()){
                        m.setCId(newContent.getcIdC());
                        multimediaDAO.createMultimedia(m);
                    }
                }
            }, interestPointID, i);
        }
    }

    private void readPathwayFromDatabase(int pathwayId) {
        Pathway pathway = pathwaysDAO.getPathwayById(pathwayId);
        pathway.setCoorinates(coordinatesDAO.getCoordinatesOfPathway(pathwayId));
        pathway.setInterestPoints(interestPointDAO.getInterestPointsOfPathway(pathwayId));

        fillViews(pathway);
        myMap.createPathProtected(pathway);
    }

    private void fillViews(Pathway path){
        // Fill all the views
        Pathway.DisplayValues dv = path.getDisplayValues(Locale.getDefault());

        ((TextView)findViewById(R.id.pathway_title)).setText(dv.pathwayName);
        ((TextView)findViewById(R.id.pathway_subtitle)).setText(path.getreg() + ", "  + dv.country);

        ((TextView)findViewById(R.id.distance_text)).setText(path.gettotM() + "");
        ((TextView)findViewById(R.id.time_text)).setText(path.getestTime() + "");
        ((TextView)findViewById(R.id.heart_rate_text)).setText(path.getavgHB());
        ((TextView)findViewById(R.id.calories_text)).setText(path.getestCal()+"");
        ((TextView)findViewById(R.id.vehicle_text)).setText(dv.vehicle);
    }

}
