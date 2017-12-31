package eu.trails2education.trails.json;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import eu.trails2education.trails.database.Coordinates;
import eu.trails2education.trails.database.Pathway;

/**
 * Created by Žiga on 23. 12. 2017.
 */

public class CoordinateJSON {

    private static String latKey = "lat";
    private static String lonKey = "lon";
    private static String altKey = "alt";
    private static String sortKey = "sort ";

    public static Coordinates createPathFromJSON(Context context, JSONObject jsonObject, int index) throws JSONException {
        Coordinates p = new Coordinates();

        p.setclat(SafeReader.readDouble(jsonObject, latKey));
        p.setclon(SafeReader.readDouble(jsonObject, lonKey));
        p.setcalt(SafeReader.readDouble(jsonObject, altKey));
        p.setcsort(SafeReader.readInt(jsonObject, sortKey));

        return p;
    }

    public static ArrayList<Coordinates> createCoordinateListFromJSON(Context context, JSONArray jsonObject, int pathwayID) throws JSONException{
        ArrayList<Coordinates> pathList = new ArrayList<Coordinates>();
        for(int i = 0; i < jsonObject.length(); i++){
            Coordinates p = createPathFromJSON(context, jsonObject.getJSONObject(i), i);
            p.setPathwayID(pathwayID);
            pathList.add(p);
        }
        return pathList;
    }
}
