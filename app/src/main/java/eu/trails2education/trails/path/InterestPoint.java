package eu.trails2education.trails.path;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;

import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by Žiga on 25. 11. 2017.
 */

public class InterestPoint {

    private Context context;

    public Coordinate coordinate;
    public int ID;
    public int type;

    public String name;
    public int subjectIDs[];

    public boolean interestPointReady = false;

    public InterestPoint(Context context, Coordinate coordinate, int ID, final int type){
        this.coordinate = coordinate;
        this.ID = ID;
        this.type = type;

        InterestPointUtils.readInterestPointFromNetwork(context, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                interestPointReady = true;
                try{
                    InterestPoint data = InterestPointUtils.createInterestPointFromJSON(response);

                    InterestPoint.this.type = data.type;
                    InterestPoint.this.name = data.name;
                    InterestPoint.this.subjectIDs = data.subjectIDs;

                    Log.e("LODADED AN INTEREST p.", "" + type + " " + name +
                     " "+ Arrays.toString(InterestPoint.this.subjectIDs));

                    interestPointReady =  true;

                    // TODO load all the subjects or something

                }catch (Exception e){
                    Log.e("INTEREST POINT ERROR", "Loading InterestPoint failed");
                }
            }
        }, ID);
    }

    public InterestPoint(){}

}
