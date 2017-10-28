package eu.trails2education.trails;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

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
