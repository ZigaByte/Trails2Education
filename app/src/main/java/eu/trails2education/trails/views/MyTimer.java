package eu.trails2education.trails.views;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import eu.trails2education.trails.R;

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
                        //(TextView)((Activity) context).findViewById(R.id.timeText)
                    }
                });
            }
        }, 0, 1000);
    }

    public void setTime(String time){
        int colonStart=time.indexOf(":");

        seconds = Integer.parseInt(time.substring(colonStart+4));//Integer.parseInt(String.valueOf(((TextView)findViewById(R.id.timeText)).getText().subSequence(colonStart+4,colonStart+5)));
        minutes = Integer.parseInt(time.substring(colonStart+1,colonStart+2));//Integer.parseInt(String.valueOf(((TextView)findViewById(R.id.timeText)).getText().subSequence(colonStart+1,colonStart+2)));
        hours = Integer.parseInt(time.substring(0,colonStart));//Integer.parseInt(String.valueOf(((TextView)findViewById(R.id.timeText)).getText().subSequence(0,colonStart)));
    }

}
