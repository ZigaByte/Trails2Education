package eu.trails2education.trails;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Icon;
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
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

import eu.trails2education.trails.path.Coordinate;
import eu.trails2education.trails.path.InterestPoint;
import eu.trails2education.trails.path.PathUtils;
import eu.trails2education.trails.path.Path;
import eu.trails2education.trails.views.MyMarker;

import static android.R.attr.width;
import static eu.trails2education.trails.R.id.map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Path path;

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

        //path = PathUtils.deserialize(this.getApplicationContext(), R.raw.track_coords);
        //path = PathUtils.createPath(this, R.raw.track_coords);

        // Read the path from the network
        PathUtils.readPathFromNetwork(this, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    path = PathUtils.createPathFromJSON(response,0);
                }catch(Exception e){
                    Log.e("PATH LOADING ERROR", "Failed loading the path");
                }

                fillViews(path);
                pathReady = true;
                if(mapReady)
                    populateMap();
            }
        }, 27);


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

    private void fillViews(Path path){
        Log.e("Path" , path + " ");
        Log.e("Path" ,  path.toString());
        ((TextView)findViewById(R.id.distanceTextContent)).setText(String.valueOf(path.totalMeters));
        ((TextView)findViewById(R.id.heartRateTextContent)).setText(String.valueOf(path.averageHeartBeatRate));
        ((TextView)findViewById(R.id.caloriesTextContent)).setText(String.valueOf(path.estimatedCalories));
    }

    /**
     * This callback is triggered when the map is ready to be used.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
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
        if(path.coordinates.size() == 0)
            return;
        else{
            LatLng latLng = new LatLng(path.coordinates.get(0).lat, path.coordinates.get(0).lon);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
        }

        // Create the path line
        PolylineOptions options = new PolylineOptions().clickable(false);
        for(Coordinate coordinate : path.coordinates){
            LatLng latLng = new LatLng(coordinate.lat, coordinate.lon);
            options.add(latLng);
        }
        Polyline line = mMap.addPolyline(options);

        // Add the
        for(InterestPoint interestPoint : path.interestPoints){
            MyMarker marker = new MyMarker(this, interestPoint);
            mMap.addMarker(marker.marker);
        }
    }
}
