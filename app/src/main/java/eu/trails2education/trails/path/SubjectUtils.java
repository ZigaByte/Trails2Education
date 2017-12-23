package eu.trails2education.trails.path;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Žiga on 2. 12. 2017.
 */

public class SubjectUtils {
    /**
     * Read the JSON object of the Subject from the network
     * @param context Context of the application. The activity
     * @param listener The listener that will handle the returned JSONObject
     * @param interestPointId The id of the interest point that the subject belongs to
     * @param subjectId The id of the subject
     * */
    public static void readSubjectFromNetwork(Context context, Response.Listener<JSONObject> listener, int interestPointId, int subjectId){
        String url = "http://trails2education.eu/subjectContentJson.php?idInterestPoint=" + interestPointId + "&idSubject=" + subjectId;
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsObjRequest);
        queue.start();
    }

}
