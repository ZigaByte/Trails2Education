package eu.trails2education.trails.path;

import android.content.Context;
import android.content.res.Resources;
import android.os.Debug;
import android.renderscript.ScriptGroup;
import android.support.v4.content.res.ResourcesCompat;
import android.util.JsonReader;
import android.util.Log;

import com.android.volley.toolbox.JsonObjectRequest;

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
    private static ArrayList<Path> createPathListFromJSON(JSONObject jsonObject) throws JSONException{
        ArrayList<Path> pathList = new ArrayList<Path>();
        for(int i = 0; i < jsonObject.getJSONArray("posts").length(); i++){
            Path p = createPathFromJSON(jsonObject, i);
            pathList.add(p);
        }
        return pathList;
    }

    /**
     * Extracts the Path data from the JSON String
     * @param jsonObject JSONObject to extract from
     * @param index Index of the path to read
     * */
    private static Path createPathFromJSON(JSONObject jsonObject, int index) throws JSONException{
        jsonObject = jsonObject.getJSONArray("posts").getJSONObject(index);

        Log.e("Going strong", " zeshh");
        Path p = new Path();

        // Get the base data of Path
        p.ID = jsonObject.getInt("idPathway");
        //Log.e("id", "" + p.ID);
        p.name = jsonObject.getString("pathwayNameEN");
        Log.e("Going strong", " zeshh1");

        p.region = jsonObject.getString("region");
        Log.e("Going strong", " zeshh2");

        p.date = jsonObject.getString("registerDate");
        Log.e("Going strong", " zeshh3");

        p.totalMeters = jsonObject.getInt("totalMeters"); // This is an int. It has to be provided or the app might crash
        Log.e("Going strong", " zeshh4");

        p.estimatedCalories = jsonObject.getString("estimatedCalories");
        Log.e("Going strong", " zeshh5");

        p.estimatedTime = jsonObject.getString("estimatedTime");
        Log.e("Going strong", " zeshh6");

        //p.estimatedSteps = jsonObject.getString("estimatedStepsRotat");
        p.averageHeartBeatRate = jsonObject.getString("averageHeartBeatRate");
        Log.e("Going strong", " zeshh7");

        // NAMING SHOULD BE THE SAME FOR VEHICLE AND VEHICLE EN
        p.vehicle = "Please fix";
        //p.vehicle = jsonObject.getString("vehicleEN");
        Log.e("Going strong", " zeshh8");

        p.area = jsonObject.getString("area");
        Log.e("Going strong", " zeshh9");


        // Read the coordiantes array
        Log.e("Let's read coords", "heas2222");

        // Return if the coordinates array is not present
        if(!jsonObject.has("coordenates")) return p;
        JSONArray coordinates = jsonObject.getJSONArray("coordenates");
        for(int i = 0; i < coordinates.length(); i++){
            JSONObject currentCoordinate = coordinates.getJSONObject(i);
            double lat = currentCoordinate.getDouble("lat");
            double lon = currentCoordinate.getDouble("lon");
            int id = currentCoordinate.getInt("sort");

            Path.Coordinate newCoordinate = p.new Coordinate(lat, lon, id);
            p.coordinates.add(newCoordinate);
        }

        return p;
    }

    /**
     * Reads a File from local storage and deserializes it to s JSONObject
     * @param context Context of the app
     * @param resource R.raw.something of the file to read
     * */
    public static JSONObject deserialize(Context context, int resource){
        InputStream input = context.getResources().openRawResource(resource);

        StringBuilder stringBuilder = new StringBuilder();
        Scanner scanner  = new Scanner(input);
        while(scanner.hasNext()){
            stringBuilder.append(scanner.next());
        }

        String json = stringBuilder.toString();
        JSONObject jsonObject = null;
        try {
             jsonObject = new JSONObject(json);
        }catch (JSONException exception){
            Log.e("JSON Exception", "Unable to read from the resource!");
        }

        return jsonObject;
    }

    // Temporary method that generates the path from the file. This will be replaced by network stream
    public static Path createPath(Context context, int resource){
        JSONObject object = deserialize(context, resource);
        Path p = null;
        try{
            p = createPathFromJSON(object, 0);
        }catch(Exception e){
            Log.e("Path creation Exception", "Unable to create path!");
        }
        return p;
    }

    // Temporary method that generates the path from the file. This will be replaced by network stream
    public static ArrayList<Path> createPathList(Context context, int resource){
        JSONObject object = deserialize(context, resource);
        ArrayList<Path> p = null;
        try{
            p = createPathListFromJSON(object);
        }catch(Exception e){
            Log.e("Path creation Exception", "Unable to create path list!");
        }
        return p;
    }



}
