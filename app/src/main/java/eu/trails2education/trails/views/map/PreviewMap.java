package eu.trails2education.trails.views.map;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import eu.trails2education.trails.database.Pathway;

/**
 * Created by Ziga on 07-Jun-18.
 */

public class PreviewMap extends MyMap{

    public PreviewMap(Context context, int map){
        super(context, map);

        hasMarkers = false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        super.onMapReady(googleMap);

        googleMap.getUiSettings().setAllGesturesEnabled(false);
        googleMap.getUiSettings().setCompassEnabled(false);
    }

}
