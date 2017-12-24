package eu.trails2education.trails.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by zvene on 29-Nov-17.
 */

public class InterestPointUtils {
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
