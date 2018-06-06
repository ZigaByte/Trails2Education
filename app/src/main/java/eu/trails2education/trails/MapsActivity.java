package eu.trails2education.trails;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONObject;

import eu.trails2education.trails.database.Coordinates;
import eu.trails2education.trails.database.CoordinatesDAO;
import eu.trails2education.trails.database.InterestPoint;
import eu.trails2education.trails.database.InterestPointDAO;
import eu.trails2education.trails.database.Pathway;
import eu.trails2education.trails.database.PathwaysDAO;
import eu.trails2education.trails.json.PathwayJSON;
import eu.trails2education.trails.network.PathUtils;
import eu.trails2education.trails.views.MyMap;

public class MapsActivity extends FragmentActivity  {

    private MyMap myMap;

    private FusedLocationProviderClient mFusedLocationClient;
    private PathwaysDAO pathwaysDAO;
    private CoordinatesDAO coordinatesDAO;
    private InterestPointDAO interestPointDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        myMap = new MyMap(this, R.id.map);

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
                builder.setMessage(R.string.back_title).setPositiveButton(R.string.back_option_positive, dialogClickListener)
                        .setNegativeButton(R.string.back_option_negative, dialogClickListener).show();
            }
        });
    }

    private void readPathwayFromDatabase(int pathwayId) {
        Pathway pathway = pathwaysDAO.getPathwayById(pathwayId);
        pathway.setCoorinates(coordinatesDAO.getCoordinatesOfPathway(pathwayId));
        pathway.setInterestPoints(interestPointDAO.getInterestPointsOfPathway(pathwayId));

        fillViews(pathway);
        myMap.createPath(pathway);
    }

    private void fillViews(Pathway path){
        ((TextView)findViewById(R.id.distanceTextContent)).setText(String.valueOf(path.gettotM()));
        ((TextView)findViewById(R.id.heartRateTextContent)).setText(String.valueOf(path.getavgHB()));
        ((TextView)findViewById(R.id.caloriesTextContent)).setText(String.valueOf(path.getestCal()));

        ((TextView)findViewById(R.id.regionText)).setText(String.valueOf(path.getreg()));
        ((TextView)findViewById(R.id.countryText)).setText(String.valueOf(path.getcouEN()));
    }

    private void requestLocation(){
        if(checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        LatLng lastLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        myMap.updateLastLocation(lastLocation);
                    }
                }
            });

            LocationCallback locationCallback = new LocationCallback(){
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult == null) {
                        return;
                    }
                    for (Location location : locationResult.getLocations()) {
                        LatLng lastLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        myMap.updateLastLocation(lastLocation);
                    }
                }
            };
            mFusedLocationClient.requestLocationUpdates(new LocationRequest().setInterval(1000).setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY), locationCallback, null);
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
