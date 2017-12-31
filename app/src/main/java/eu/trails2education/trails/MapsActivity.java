package eu.trails2education.trails;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import eu.trails2education.trails.database.Coordinates;
import eu.trails2education.trails.database.InterestPoint;
import eu.trails2education.trails.database.Pathway;
import eu.trails2education.trails.json.PathwayJSON;
import eu.trails2education.trails.network.PathUtils;
import eu.trails2education.trails.views.MyMarker;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Pathway path;

    // Timer
    public int seconds = 0;
    public int minutes = 0;
    public int hours = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        int pathID = (int)getIntent().getExtras().getLong("PathID"); // Get the selected path
        Log.e("MapsActivity", "Loading path with ID: " + pathID);

        // Read the path from the network
        PathUtils.readPathFromNetwork(this, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    path = PathwayJSON.createFullPathFromJSON(MapsActivity.this, response);
                }catch(Exception e){
                    Log.e("PATH LOADING ERROR", "Failed loading the path");
                }

                fillViews(path);
                pathReady = true;
                if(mapReady)
                    populateMap();
            }
        }, pathID);


        findViewById(R.id.button_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Create the timer
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

    private void fillViews(Pathway path){
        Log.e("Path" , path + " ");
        Log.e("Path" ,  path.toString());
        ((TextView)findViewById(R.id.distanceTextContent)).setText(String.valueOf(path.gettotM()));
        ((TextView)findViewById(R.id.heartRateTextContent)).setText(String.valueOf(path.getavgHB()));
        ((TextView)findViewById(R.id.caloriesTextContent)).setText(String.valueOf(path.getestCal()));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Disable map things we don't need.
        mMap.getUiSettings().setMapToolbarEnabled(false);

        mapReady = true;

        if(pathReady)
            populateMap();
    }

    // Make sure both are ready before populating to avoid Null Pointers
    boolean pathReady = false;
    boolean mapReady = false;

    /**
     * Populate the map with the loaded path
     */
    public void populateMap(){
        boolean first = true;
        // Move to the first point of the path
        if(path.getCoordinates().size() == 0)
            return;
        else{
            ArrayList<Coordinates> coordinates = path.getCoordinates();
            LatLng latLng = new LatLng(coordinates.get(0).getclat(), coordinates.get(0).getclon());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
        }

        // Create the path line
        PolylineOptions options = new PolylineOptions().clickable(false);
        for(Coordinates coordinate : path.getCoordinates()){
            LatLng latLng = new LatLng(coordinate.getclat(), coordinate.getclon());
            options.add(latLng);
        }
        Polyline line = mMap.addPolyline(options);

        // Add the
        for(InterestPoint interestPoint : path.getInterestPoints()){
            MyMarker myMarker = new MyMarker(this, interestPoint);
            Marker marker = mMap.addMarker(myMarker.markerOptions);

            marker.setTag(myMarker);
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // Call the on click method of the marker
                ((MyMarker)marker.getTag()).onClick();
                return true;
            }
        });
    }
}
