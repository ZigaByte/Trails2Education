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
    public Subject[] subjects;

    public boolean interestPointReady = false;

    public InterestPoint(final Context context, Coordinate coordinate, final int ID, final int type){
        this.coordinate = coordinate;
        this.ID = ID;
        this.type = type;

        InterestPointUtils.readInterestPointFromNetwork(context, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    InterestPoint data = InterestPointUtils.createInterestPointFromJSON(response);

                    InterestPoint.this.type = data.type;
                    InterestPoint.this.name = data.name;
                    InterestPoint.this.subjectIDs = data.subjectIDs;

                    Log.e("LODADED AN INTEREST p.", "" + type + " " + name +
                     " "+ Arrays.toString(InterestPoint.this.subjectIDs));

                    interestPointReady =  true;

                    subjects = new Subject[subjectIDs.length];
                    for(int i = 0; i < subjectIDs.length; i++){
                        subjects[i] = new Subject(context, ID, subjectIDs[i]);
                    }

                }catch (Exception e){
                    Log.e("INTEREST POINT ERROR", "Loading InterestPoint failed");
                }
            }
        }, ID);
    }

    public InterestPoint(){}

    public int getSubjectCount(){
        return subjectIDs.length;
    }

}
