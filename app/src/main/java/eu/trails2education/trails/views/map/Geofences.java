package eu.trails2education.trails.views.map;

import android.app.PendingIntent;
import android.content.Intent;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

import eu.trails2education.trails.GeofenceTransitionsIntentService;

/**
 * Created by Ziga on 19-Sep-18.
 */

public interface Geofences {
    ArrayList<Geofence> mGeofenceList = new ArrayList<Geofence>();
    // Test


}
