package eu.trails2education.trails;

import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

import eu.trails2education.trails.path.PathUtils;
import eu.trails2education.trails.path.Path;

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

        path = PathUtils.deserialize(this.getApplicationContext(), "trackCoords.json");
        fillViews(path);

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
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Disable mapp things we don't need.
        mMap.getUiSettings().setMapToolbarEnabled(false);

        // Add a marker in Sydney and move the camera
        // LatLng sydney = new LatLng(-34, 151);
        // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        // mMap.addMarker(new MarkerOptions().position(new LatLng(10, 10)).title("Hello world"));
        // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10));

        boolean first = true;
        // Read the Path data and create map markers
        for(Path.Coordinate coordinate : path.coordinates){
            LatLng latLng = new LatLng(coordinate.lat, coordinate.lon);
            if(first){
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
                first = false;
            }
        }


        PolylineOptions options = new PolylineOptions().clickable(false);
        int i = 0;
        for(Path.Coordinate coordinate : path.coordinates){
            LatLng latLng = new LatLng(coordinate.lat, coordinate.lon);
            options.add(latLng);
            if(i++ % 170 == 0)
                mMap.addMarker(new MarkerOptions().position(latLng).title("Test Marker"));
        }
        Polyline line = mMap.addPolyline(options);

    }
}
