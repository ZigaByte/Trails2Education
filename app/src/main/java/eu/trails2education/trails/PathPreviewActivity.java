package eu.trails2education.trails;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Response;

import org.json.JSONObject;

import eu.trails2education.trails.database.ContentDAO;
import eu.trails2education.trails.database.Coordinates;
import eu.trails2education.trails.database.CoordinatesDAO;
import eu.trails2education.trails.database.InterestPoint;
import eu.trails2education.trails.database.InterestPointDAO;
import eu.trails2education.trails.database.MultimediaDAO;
import eu.trails2education.trails.database.Pathway;
import eu.trails2education.trails.database.PathwaysDAO;
import eu.trails2education.trails.json.PathwayJSON;
import eu.trails2education.trails.network.PathUtils;
import eu.trails2education.trails.views.MyMap;
import eu.trails2education.trails.views.SelectionAdapter;

/**
 * Created by Ziga on 06-Jun-18.
 */

public class PathPreviewActivity extends FragmentActivity {

    private MyMap myMap;

    private PathwaysDAO pathwaysDAO;
    private CoordinatesDAO coordinatesDAO;
    private InterestPointDAO interestPointDAO;
    private ContentDAO contentDAO;
    private MultimediaDAO multimediaDAO;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_preview);

        myMap = new MyMap(this, R.id.preview_map);

        // Database connections
        pathwaysDAO = new PathwaysDAO(this);
        coordinatesDAO = new CoordinatesDAO(this);
        interestPointDAO = new InterestPointDAO(this);

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
                //Log.e("PATHWAY UPDATE COMPARE", newPathway.getupdDate() + ", old: " + myMap.getPathway().getupdDate());

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

                    //TODO LOAD CONTENT
                }

                readPathwayFromDatabase(pathwayId);
            }
        }, pathwayId);
    }

    private void readPathwayFromDatabase(int pathwayId) {
        Pathway pathway = pathwaysDAO.getPathwayById(pathwayId);
        pathway.setCoorinates(coordinatesDAO.getCoordinatesOfPathway(pathwayId));
        pathway.setInterestPoints(interestPointDAO.getInterestPointsOfPathway(pathwayId));

        fillViews(pathway);
        myMap.createPath(pathway);
    }

    private void fillViews(Pathway path){
        // Fill all the views
    }

}
