package eu.trails2education.trails.path;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;

import org.json.JSONObject;

import java.util.Arrays;

import static android.R.attr.id;
import static android.R.attr.name;
import static android.R.attr.type;

/**
 * Created by Žiga on 2. 12. 2017.
 */

public class Subject {

    public int interestPointID;
    public int ID;
    public int type;

    public boolean subjectReady;

    public String subject;
    public String title;
    public String description;

    public Subject(final Context context, final int interestPointID, final int ID){
        this.ID = ID;
        this.interestPointID = interestPointID;

        SubjectUtils.readSubjectFromNetwork(context, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    Subject data = SubjectUtils.createSubjectFromJSON(response);

                    Subject.this.subject = data.subject;
                    Subject.this.title = data.title;
                    Subject.this.description = data.description;

                    Log.e("LODADED A SUBJECT", "" + ID + " " + interestPointID +
                            " "+ title + " || " + subject + " || " + description);

                    subjectReady =  true;
                }catch (Exception e){
                    Log.e("SUBJECT ERROR", "Loading Subject failed");
                }
            }
        }, interestPointID, ID);
    }

    public Subject(){}

}
