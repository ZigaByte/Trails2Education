package eu.trails2education.trails.path;

import android.content.Context;
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

/**
 * Created by zvene on 29-Nov-17.
 */

public class InterestPointUtils {

    public static InterestPoint createInterestPointFromJSON(JSONObject jsonObject) throws JSONException{
        jsonObject = jsonObject.getJSONArray("posts").getJSONObject(0);
        InterestPoint p = new InterestPoint(); // New path to populate
        Log.e("loading the " , "haha, not funny");

        p.name = jsonObject.getString("interestPointNameEN");
        p.ID = jsonObject.getInt("idInterestPoint");
        p.type = jsonObject.getInt("idPointType");

        if(!jsonObject.has("subjects") || jsonObject.isNull("subjects")){
            p.subjectIDs = new int[1];
            p.subjectIDs[0] = -1;
            return p;
        }
        // Read all the subject IDs

        JSONArray subjects = jsonObject.getJSONArray("subjects");
        p.subjectIDs = new int[subjects.length()];
        for(int i = 0; i < subjects.length(); i++){
            p.subjectIDs[i] = subjects.getJSONObject(i).getInt("idSubject");
        }

        return p;
    }

    /**
     * Read the JSON object of the InterestPoint from the network
     * @param context Context of the application. The activity
     * @param listener The listener that will handle the returned JSONObject
     * */
    public static void readInterestPointFromNetwork(Context context, Response.Listener<JSONObject> listener, int id){
        String url = "http://trails2education.eu/interestPointJson.php?idInterestPoint=" + id;
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsObjRequest);
        queue.start();
    }


}
