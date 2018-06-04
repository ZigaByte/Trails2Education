package eu.trails2education.trails.views;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

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

import java.util.ArrayList;

import eu.trails2education.trails.R;
import eu.trails2education.trails.database.Coordinates;
import eu.trails2education.trails.database.InterestPoint;
import eu.trails2education.trails.database.Pathway;

/**
 * Created by Ziga on 04-Jun-18.
 */

public class MyMap implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Context context;

    public Pathway pathway;
    public LatLng lastLocation;
    public Marker locationMarker;

    public MyMap(Context context){
        this.context = context;

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) ((FragmentActivity)context).getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    // Make sure both are ready before populating to avoid Null Pointers
    public boolean pathReady = false;
    public boolean mapReady = false;
    public boolean locationReady = false;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Disable map things we don't need.
        mMap.getUiSettings().setMapToolbarEnabled(false);

        mapReady = true;

        if(pathReady)
            populateMap();
    }

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

        // Add the markers for the individual interest points
        for(InterestPoint interestPoint : pathway.getInterestPoints()){
            MyMarker myMarker = new MyMarker(context, interestPoint, pathway);
            Marker marker = mMap.addMarker(myMarker.markerOptions);

            marker.setTag(myMarker);
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if(marker.getTag() == null)return false;
                // Call the on click method of the marker
                ((MyMarker)marker.getTag()).onClick();
                return true;
            }
        });

        // Add the start and finish markers.
        if(pathway.getCoordinates().size() > 0){
            Coordinates startCoordinate = pathway.getCoordinates().get(0);
            Coordinates finishCoordinate = pathway.getCoordinates().get(pathway.getCoordinates().size() - 1);
            MyMarker start = new MyMarker(context, new LatLng(startCoordinate.getclat(), startCoordinate.getclon()), true);
            MyMarker finish = new MyMarker(context, new LatLng(finishCoordinate.getclat(), finishCoordinate.getclon()), false);
            mMap.addMarker(start.markerOptions);
            mMap.addMarker(finish.markerOptions);
        }

        // Add last location if ready
        if(locationReady)
            addLastLocation();
    }

    public void addLastLocation(){
        if(locationMarker == null){
            MarkerOptions markerOptions = new MarkerOptions().position(lastLocation).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            locationMarker = mMap.addMarker(markerOptions);
            locationMarker.setTitle("Current location");
        }else{
            locationMarker.setPosition(lastLocation);
        }
    }


}
