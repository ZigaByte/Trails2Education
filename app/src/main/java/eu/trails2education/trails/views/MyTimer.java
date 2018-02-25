package eu.trails2education.trails.views;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Žiga on 25. 02. 2018.
 */

public class MyTimer extends MyTextViewBold {

    // Timer
    public int seconds = 0;
    public int minutes = 0;
    public int hours = 0;

    public MyTimer(Context context) {
        super(context);
        start(context);
    }

    public MyTimer(Context context, AttributeSet attrs) {
        super(context, attrs);
        start(context);
    }

    public MyTimer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        start(context);
    }

    public void start(final Context context){
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        seconds++;
                        if(seconds >= 60){
                            seconds -= 60;
                            minutes++;
                            if(minutes >= 60){
                                minutes-= 60;
                                hours++;
                            }
                        }
                        setText(hours + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
                    }
                });
            }
        }, 0, 1000);
    }

    public void setTime(String time){
        int colonStart=time.indexOf(":");
        seconds = Integer.parseInt(time.substring(colonStart+4));
        minutes = Integer.parseInt(time.substring(colonStart+1,colonStart+2));
        hours = Integer.parseInt(time.substring(0,colonStart));
    }

}
