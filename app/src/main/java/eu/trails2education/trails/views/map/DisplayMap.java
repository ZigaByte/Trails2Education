package eu.trails2education.trails.views.map;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;

import eu.trails2education.trails.database.Pathway;

/**
 * Created by Ziga on 07-Jun-18.
 */

public class DisplayMap extends MyMap{

    public DisplayMap(Context context, int map){
        super(context, map);
    }

    @Override
    protected void createPath(Pathway pathway) {
        super.createPath(pathway);
        createMarkers();
    }
}
