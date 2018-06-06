package eu.trails2education.trails.views;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.nio.file.Path;
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

    private Pathway pathway;
    private Marker locationMarker;

    private boolean mapReady = false;

    public MyMap(Context context, int map){
        this.context = context;

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) ((FragmentActivity)context).getSupportFragmentManager().findFragmentById(map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Disable map things we don't need.
        mMap.getUiSettings().setMapToolbarEnabled(false);
        //mMap.getUiSettings().setAllGesturesEnabled(false);
        //mMap.getUiSettings().setCompassEnabled(false);

        mapReady = true;

        createPath(pathway);
    }

    public void createPath(Pathway pathway){
        if(pathway == null)
            return;

        this.pathway = pathway;
        if(!mapReady){
            return;
        }

        mMap.clear();

        // Move to the first point of the path
        if(pathway.getCoordinates().size() == 0)
            return;
        else{
            ArrayList<Coordinates> coordinates = pathway.getCoordinates();
            LatLng latLng = new LatLng(coordinates.get(0).getclat(), coordinates.get(0).getclon());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
        }

        LatLng maxLat = null, maxLon= null, minLat= null, minLon= null;
        // Create the path line
        PolylineOptions options = new PolylineOptions().clickable(false);
        for(Coordinates coordinate : pathway.getCoordinates()){
            LatLng latLng = new LatLng(coordinate.getclat(), coordinate.getclon());
            options.add(latLng);

            if(maxLat == null){
                maxLat = maxLon = minLon = minLat = latLng;
            } else{
                if(latLng.latitude > maxLat.latitude) maxLat = latLng;
                if(latLng.latitude < minLat.latitude) minLat = latLng;
                if(latLng.longitude > maxLon.longitude) maxLon = latLng;
                if(latLng.longitude < minLon.longitude) minLon = latLng;
            }
        }
        Polyline line = mMap.addPolyline(options);

        Log.e("Bounds", maxLat + " " +  minLat+ " " + maxLon + " " + minLon);
        final LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(maxLat);
        builder.include(minLat);
        builder.include(maxLon);
        builder.include(minLon);
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 40));
            }
        });

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

    }

    public void updateLastLocation(LatLng lastLocation){
        if(!mapReady || lastLocation == null){
            return;
        }

        if(locationMarker == null){
            MarkerOptions markerOptions = new MarkerOptions().position(lastLocation).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            locationMarker = mMap.addMarker(markerOptions);
            locationMarker.setTitle("Current location");
        }else{
            locationMarker.setPosition(lastLocation);
        }
    }


    public Pathway getPathway(){
        return pathway;
    }


}
