package eu.trails2education.trails.json;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import eu.trails2education.trails.ContentActivity;
import eu.trails2education.trails.database.Content;
import eu.trails2education.trails.database.InterestPoint;
import eu.trails2education.trails.network.SubjectUtils;

/**
 * Created by Žiga on 23. 12. 2017.
 */

public class InterestPointJSON {

    private static String idInterestPointKey = "idInterestPoint";
    private static String latKey = "lat";
    private static String lonKey = "lon";
    private static String altKey = "alt";
    private static String ipnameENKey = "interestPointNameEN";
    private static String ipnameFRKey = "interestPointNameFR";
    private static String ipnamePTKey = "interestPointNamePT";
    private static String ipnameSLKey = "interestPointNameSL";
    private static String ipnameEEKey = "interestPointNameEE";
    private static String ipnameITKey = "interestPointNameIT";
    private static String pointtypeKey = "idPointType";

    public static InterestPoint createInterestPointFromJSON(final Context context, JSONObject jsonObject, int index) throws JSONException {
        InterestPoint p = new InterestPoint();

        p.setIdIP(SafeReader.readInt(jsonObject, idInterestPointKey));
        p.setctype(SafeReader.readInt(jsonObject, pointtypeKey));
        p.setclat(SafeReader.readDouble(jsonObject, latKey));
        p.setclon(SafeReader.readDouble(jsonObject, lonKey));
        p.setcalt(SafeReader.readDouble(jsonObject, altKey));

        p.setNameEN(SafeReader.readString(jsonObject, ipnameENKey));
        p.setNameFR(SafeReader.readString(jsonObject, ipnameFRKey));
        p.setNamePT(SafeReader.readString(jsonObject, ipnamePTKey));
        p.setNameSL(SafeReader.readString(jsonObject, ipnameSLKey));
        p.setNameEE(SafeReader.readString(jsonObject, ipnameEEKey));
        p.setNameIT(SafeReader.readString(jsonObject, ipnameITKey));

        // MAYBE REDO THIS, NOT SURE
        if(jsonObject.has("subjects")){
            final ArrayList<Content> contents = new ArrayList<Content>();
            JSONArray subjects = jsonObject.getJSONArray("subjects");
            for(int i = 0; i < subjects.length();i++){
                SubjectUtils.readSubjectFromNetwork(context, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            contents.add(ContentJSON.createContentFromJSON(context, response.getJSONArray("posts").getJSONObject(0), 0));
                            ((ContentActivity)context).fillViews(); // Update the views TODO REMOVE after db implementation
                        }catch (Exception e){
                            Log.e("CONTENT LOADING ERROR", "ERROR WITH CONTENT");
                        }
                    }
                }, (int)p.getcIdIP(), subjects.getJSONObject(i).getInt("idSubject"));
            }
            p.setContents(contents);
        }
        return p;
    }

    public static ArrayList<InterestPoint> createInterestPointListFromJSON(Context context, JSONArray jsonObject, int pathwayID) throws JSONException{
        ArrayList<InterestPoint> pathList = new ArrayList<InterestPoint>();
        for(int i = 0; i < jsonObject.length(); i++){
            InterestPoint p = createInterestPointFromJSON(context, jsonObject.getJSONObject(i), i);
            p.setPathwayID(pathwayID);
            pathList.add(p);
        }
        return pathList;
    }

}
