package eu.trails2education.trails.path;

import android.content.Context;
import android.content.res.Resources;
import android.os.Debug;
import android.renderscript.ScriptGroup;
import android.support.v4.content.res.ResourcesCompat;
import android.util.JsonReader;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import eu.trails2education.trails.R;

/**
 * Created by Žiga on 14. 09. 2017.
 */

public class PathUtils {
    /**
     * Extracts the Path data from the JSON String
     * @param jsonObject JSONObject to extract from
     * */
    public static ArrayList<Path> createPathListFromJSON(Context context, JSONObject jsonObject) throws JSONException{
        ArrayList<Path> pathList = new ArrayList<Path>();
        for(int i = 0; i < jsonObject.getJSONArray("posts").length(); i++){
            Path p = createPathFromJSON(context, jsonObject, i);
            pathList.add(p);
        }
        return pathList;
    }

    /**
     * Extracts the Path data from the JSON String
     * @param jsonObject JSONObject to extract from
     * @param index Index of the path to read
     * */
    public static Path createPathFromJSON(Context context, JSONObject jsonObject, int index) throws JSONException{
        jsonObject = jsonObject.getJSONArray("posts").getJSONObject(index);
        Path p = new Path(); // New path to populate

        // Get the base data of Path
        p.ID = jsonObject.getInt("idPathway");
        p.name = jsonObject.getString("pathwayNameEN");
        p.region = jsonObject.getString("region");
        p.date = jsonObject.getString("registerDate");
        p.totalMeters = jsonObject.getInt("totalMeters"); // This is an int. It has to be provided or the app might crash
        p.estimatedCalories = jsonObject.getString("estimatedCalories");
        p.estimatedTime = jsonObject.getString("estimatedTime");
        //p.estimatedSteps = jsonObject.getString("estimatedStepsRotat");
        p.averageHeartBeatRate = jsonObject.getString("averageHeartBeatRate");
        // NAMING SHOULD BE THE SAME FOR VEHICLE AND VEHICLE EN
        p.vehicle = "Please fix";
        //p.vehicle = jsonObject.getString("vehicleEN");
        p.area = jsonObject.getString("area");


        // Return if the coordinates array is not present
        if(!jsonObject.has("coordinates")) return p;

        // Read all the coordinates
        JSONArray coordinates = jsonObject.getJSONArray("coordinates");
        for(int i = 0; i < coordinates.length(); i++){
            JSONObject currentCoordinate = coordinates.getJSONObject(i);
            double lat = currentCoordinate.getDouble("lat");
            double lon = currentCoordinate.getDouble("lon");
            int id = currentCoordinate.getInt("sort");

            Coordinate newCoordinate = new Coordinate(lat, lon, id);
            p.coordinates.add(newCoordinate);
        }

        // Return if the interestPoints array is not present
        if(!jsonObject.has("interestPoints")) return p;

        // Read all the interestPoints
        JSONArray interestPoints = jsonObject.getJSONArray("interestPoints");
        for(int i = 0; i < interestPoints.length(); i++){
            JSONObject currentInterestPoint = interestPoints.getJSONObject(i);
            double lat = currentInterestPoint.getDouble("lat");
            double lon = currentInterestPoint.getDouble("lon");
            double alt = currentInterestPoint.getDouble("alt");
            int id = currentInterestPoint.getInt("idInterestPoint");
            int type = 0;
            if(!currentInterestPoint.isNull("idPointType"))
                type = currentInterestPoint.getInt("idPointType");

            Coordinate interestPointCoordinate = new Coordinate(lat, lon, id);
            InterestPoint interestPoint = new InterestPoint(context, interestPointCoordinate, id, type);
            p.interestPoints.add(interestPoint);
        }

        return p;
    }

    /**
     * Read the JSON object of the Path list from the network
     * @param context Context of the application. The activity
     * @param listener The listener that will handle the returned JSONObject
     * */
     public static void readPathListFromNetwork(Context context, Response.Listener<JSONObject> listener){
        String url = "https://www.trails2education.eu/trackListJson.php";
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, listener, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsObjRequest);
        queue.start();
    }

    /**
     * Read the JSON object of the Path list from the network
     * @param context Context of the application. The activity
     * @param listener The listener that will handle the returned JSONObject
     * */
    public static void readPathFromNetwork(Context context, Response.Listener<JSONObject> listener, int pathID){
        String url = "https://www.trails2education.eu/trackCoordsJson.php?idPathway=" + pathID;
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsObjRequest);
        queue.start();
    }
}
