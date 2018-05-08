package eu.trails2education.trails.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import eu.trails2education.trails.ContentActivity;
import eu.trails2education.trails.MapsActivity;
import eu.trails2education.trails.R;
import eu.trails2education.trails.database.InterestPoint;
import eu.trails2education.trails.database.Pathway;

/**
 * Created by Žiga on 25. 11. 2017.
 */

public class MyMarker{

    public MarkerOptions markerOptions;
    public InterestPoint interestPoint;
    public Pathway path;

    private Context context;

    // Constructor for start and finish markers
    public MyMarker(Context context, LatLng position, boolean start){
        this.context = context;
        createMarker(position, start ? R.drawable.start : R.drawable.finish, 350);
    }

    // Constructor for interest point markers
    public MyMarker(Context context, InterestPoint interestPoint, Pathway p){
        this.context = context;
        this.interestPoint = interestPoint;
        this.path = p;

        // Select location
        LatLng latLng = new LatLng(interestPoint.getclat(), interestPoint.getclon());

        // Select the drawable that should be on the marker.
        int drawable = 0;
        switch((int)interestPoint.getctype()){
            case 1: drawable = R.drawable.interest_point_fauna;break;
            case 2: drawable = R.drawable.interest_point_flora;break;
            case 3: drawable = R.drawable.interest_point_ruins;break;
            case 4: drawable = R.drawable.interest_point_castle;break;
            case 5: drawable = R.drawable.interest_point_museum;break;
            case 6: drawable = R.drawable.interest_point_observation;break;
            case 7: drawable = R.drawable.interest_point_temple;break;
            case 8: drawable = R.drawable.interest_point_water;break;
            case 9: drawable = R.drawable.interest_point_waypoint;break;

            default: drawable = R.drawable.interest_point_waypoint;break;
        }
        createMarker(latLng, drawable, 130);
    }

    private void createMarker(LatLng pos, int drawable, int size){
        BitmapDrawable bitmapdraw = (BitmapDrawable)context.getResources().getDrawable(drawable);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, size, size, false);

        markerOptions = new MarkerOptions().position(pos).icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
    }

    // Start the content activity
    public void onClick(){
        Intent i = new Intent(context, ContentActivity.class);
        i.putExtra("InterestPointID", interestPoint.getcIdIP()); // Pass the interestPointID
        i.putExtra("PathwayID", path.getId());
        i.putExtra("time", ((MapsActivity)context).read_timer());
        context.startActivity(i);
    }
}
