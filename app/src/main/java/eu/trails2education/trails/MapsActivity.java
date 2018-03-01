package eu.trails2education.trails;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import eu.trails2education.trails.database.Coordinates;
import eu.trails2education.trails.database.CoordinatesDAO;
import eu.trails2education.trails.database.InterestPoint;
import eu.trails2education.trails.database.InterestPointDAO;
import eu.trails2education.trails.database.Pathway;
import eu.trails2education.trails.database.PathwaysDAO;
import eu.trails2education.trails.json.PathwayJSON;
import eu.trails2education.trails.network.PathUtils;
import eu.trails2education.trails.views.MyMarker;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Pathway pathway;
    private LatLng lastLocation;

    private FusedLocationProviderClient mFusedLocationClient;

    private PathwaysDAO pathwaysDAO;
    private CoordinatesDAO coordinatesDAO;
    private InterestPointDAO interestPointDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Start setting up the location listener
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);
        }else{
            requestLocation();
        }

        // Database connections
        pathwaysDAO = new PathwaysDAO(this);
        coordinatesDAO = new CoordinatesDAO(this);
        interestPointDAO = new InterestPointDAO(this);

        int pathID = (int)getIntent().getExtras().getLong("PathID"); // Get the selected path ID
        readPathwayFromDatabase(pathID);
        readPathwayFromNetwork(pathID);

        final Context context = this;
        // Set up the back button. TODO: Change to finish path thing.
        findViewById(R.id.button_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                finish();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Finish the path?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });
    }

    private void readPathwayFromDatabase(int pathwayId) {
        pathway = pathwaysDAO.getPathwayById(pathwayId);
        pathway.setCoorinates(coordinatesDAO.getCoordinatesOfPathway(pathwayId));
        pathway.setInterestPoints(interestPointDAO.getInterestPointsOfPathway(pathwayId));

        fillViews(pathway);
        pathReady = true;
        if(mapReady)
            populateMap();
    }

    private void readPathwayFromNetwork(final int pathwayId){
        // Read the path from the network
        PathUtils.readPathFromNetwork(this, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Pathway newPathway = null;
                try {
                    newPathway = PathwayJSON.createFullPathFromJSON(MapsActivity.this, response);
                }catch(Exception e){
                    Log.e("PATH LOADING ERROR", "Failed loading the path");
                }
                // Insert Pathway into the database
                pathwaysDAO.createPathway(newPathway);

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
                }

                readPathwayFromDatabase(pathwayId);
            }
        }, pathwayId);
    }

    private void fillViews(Pathway path){
        ((TextView)findViewById(R.id.distanceTextContent)).setText(String.valueOf(path.gettotM()));
        ((TextView)findViewById(R.id.heartRateTextContent)).setText(String.valueOf(path.getavgHB()));
        ((TextView)findViewById(R.id.caloriesTextContent)).setText(String.valueOf(path.getestCal()));

        // Skrajsa ime, good for now ..
        String name = path.getNameEN();
        if(name.length() > 12)
            name = name.substring(0, 8) + " ..";

        //((TextView)findViewById(R.id.textView2)).setText(name);
        ((TextView)findViewById(R.id.textView2)).setText(String.valueOf(path.getreg()));
        ((TextView)findViewById(R.id.textView3)).setText(String.valueOf(path.getcouEN()));
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
    boolean locationReady = false;

    /**
     * Populate the map with the loaded path
     */
    public void populateMap(){
        boolean first = true;
        // Move to the first point of the path
        if(pathway.getCoordinates().size() == 0)
            return;
        else{
            ArrayList<Coordinates> coordinates = pathway.getCoordinates();
            LatLng latLng = new LatLng(coordinates.get(0).getclat(), coordinates.get(0).getclon());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
        }

        // Create the path line
        PolylineOptions options = new PolylineOptions().clickable(false);
        for(Coordinates coordinate : pathway.getCoordinates()){
            LatLng latLng = new LatLng(coordinate.getclat(), coordinate.getclon());
            options.add(latLng);
        }
        Polyline line = mMap.addPolyline(options);

        // Add the
        for(InterestPoint interestPoint : pathway.getInterestPoints()){
            MyMarker myMarker = new MyMarker(this, interestPoint, pathway);
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

        // Add last location if ready
        if(locationReady)
            addLastLocation();
    }

    private void addLastLocation(){
        MarkerOptions markerOptions = new MarkerOptions().position(lastLocation).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mMap.addMarker(markerOptions);
    }

    private void requestLocation(){
        if(checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        lastLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        locationReady = true;
                        if (mapReady)
                            addLastLocation();
                    }
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    requestLocation();
                }
                return;
            }
        }
    }

    public String read_timer(){
        String beri="";
        try{
            beri = ((TextView)this.findViewById(R.id.timeText)).getText().toString();
        }
        catch (Exception ex){
            Log.d("Exception","Exception of type"+ex.getMessage());
        }

        return beri;
    }
}
