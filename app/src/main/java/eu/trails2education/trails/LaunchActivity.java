package eu.trails2education.trails;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;

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

                // TOLE ZAKOMENTIRAJTE
                Intent i = new Intent(LaunchActivity.this, SelectionActivity.class);

                // TOLE ODKOMENTIRAJTE, da vam pozene SearchActivity
                //Intent i = new Intent(LaunchActivity.this, SearchActivity.class);


                startActivity(i);
                finish();
            }
        }, DELAY);

    }

}
