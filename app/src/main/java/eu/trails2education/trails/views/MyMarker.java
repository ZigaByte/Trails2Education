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
    private Marker marker;
    public InterestPoint interestPoint;
    public Pathway path;
    private String timer ="";

    private Context context;

    public MyMarker(Context context, InterestPoint interestPoint, Pathway p){
        this.context = context;
        this.interestPoint = interestPoint;
        this.path = p;

        LatLng latLng = new LatLng(interestPoint.getclat(), interestPoint.getclon());

        BitmapDrawable bitmapdraw = (BitmapDrawable)context.getResources().getDrawable(R.drawable.interest_point_castle);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, 130, 130, false);
        markerOptions = new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
    }

    // Start the content activity
    public void onClick(){

        //timer = ((TextView)activity.findViewById(R.id.timeText)).getText().toString();

        Intent i = new Intent(context, ContentActivity.class);
        i.putExtra("InterestPointID", interestPoint.getcIdIP()); // Pass the interestPointID
        i.putExtra("PathwayID", path.getId());
        i.putExtra("time", ((MapsActivity)context).read_timer());
        context.startActivity(i);
    }

    public void setMarker(Marker marker){
        this.marker = marker;
    }
}
