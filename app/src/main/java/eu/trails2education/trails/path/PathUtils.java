package eu.trails2education.trails.path;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by Žiga on 14. 09. 2017.
 */

public class PathUtils {
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
