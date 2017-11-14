package eu.trails2education.trails;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import eu.trails2education.trails.network.RequestManager;

/**
 * Activity with launch screen.
 *
 * Switches to another activity after private variable DELAY milliseconds.
 *
 * */
public class LaunchActivity extends FragmentActivity {

    private final int DELAY = 2000; // In milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

/*     // This is how we download data. Using Volley
        TextView mTxtDisplay;
        ImageView mImageView;
        String url = "https://www.trails2education.eu/json1.php";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("respois ds", "Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsObjRequest);
        queue.start();
*/

        // Set up the basic RequestQueue
        //RequestManager.getInstance(this);

        // Switch after DELAY
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(LaunchActivity.this, SelectionActivity.class);
                startActivity(i);
                finish();
            }
        }, DELAY);
    }

}
