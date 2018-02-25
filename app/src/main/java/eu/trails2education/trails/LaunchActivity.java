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

    private final int DELAY = 500; // In milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

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
