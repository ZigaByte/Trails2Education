package eu.trails2education.trails.path;

import android.content.Context;
import android.content.res.Resources;
import android.os.Debug;
import android.renderscript.ScriptGroup;
import android.support.v4.content.res.ResourcesCompat;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Scanner;

import eu.trails2education.trails.R;

/**
 * Created by Žiga on 14. 09. 2017.
 */

public class PathUtils {

    /**
     * Extracts the Path data from the JSON String
     * @param json JSON String to extract from
     * */
    private static Path createPathFromJSON(String json) throws JSONException{
        JSONObject jsonObject = new JSONObject(json);
        jsonObject = jsonObject.getJSONArray("posts").getJSONObject(0);

        Path p = new Path();

        // Get the base data of Path
        p.ID = jsonObject.getInt("idPathway");
        Log.e("id", ""+p.ID);
        p.name = jsonObject.getString("pathwayNameEN");
        p.region = jsonObject.getString("region");
        p.date = jsonObject.getString("registerDate");
        p.totalMeters = jsonObject.getInt("totalMeters");
        p.estimatedCalories = jsonObject.getInt("estimatedCalories");
        p.estimatedTime = jsonObject.getInt("estimatedTime");
        p.estimatedSteps = jsonObject.getInt("estimatedStepsRotat");
        p.averageHeartBeatRate = jsonObject.getInt("averageHeartBitRate");
        p.vehicle = jsonObject.getString("vehicleEN");
        p.area = jsonObject.getString("area");

        // Read the coordiantes array
        JSONArray coordinates = jsonObject.getJSONArray("coordenadas");
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
     * Reads a File from local storage and deserializes it from JSON to a Path object
     * @param context Context of the app
     * @param fileName Location of file in storage
     * */
    public static Path deserialize(Context context, String fileName){
        InputStream input = context.getResources().openRawResource(R.raw.path1);

        StringBuilder stringBuilder = new StringBuilder();
        Scanner scanner  = new Scanner(input);
        while(scanner.hasNext()){
            stringBuilder.append(scanner.next());
        }

        String json = stringBuilder.toString();
        Path p = null;

        try{
            p = createPathFromJSON(json);
        }catch (Exception e){
            Log.e("JSON", "JSON Conversion failed");
        }
        return p;
    }


}
