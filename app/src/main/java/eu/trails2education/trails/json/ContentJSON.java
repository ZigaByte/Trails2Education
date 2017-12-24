package eu.trails2education.trails.json;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import eu.trails2education.trails.database.Content;

/**
 * Created by Žiga on 23. 12. 2017.
 */

public class ContentJSON {

    private static String idContentKey = "idContent";
    private static String pointtypeKey = "idPointType";
    private static String subjectENKey = "subjectEN";
    private static String subjectFRKey = "subjectFR";
    private static String subjectPTKey = "subjectPT";
    private static String subjectSLKey = "subjectSL";
    private static String subjectEEKey = "subjectEE";
    private static String subjectITKey = "subjectIT";
    private static String titleENKey = "titleEN";
    private static String titleFRKey = "titleFR";
    private static String titlePTKey = "titlePT";
    private static String titleSLKey = "titleSL";
    private static String titleEEKey = "titleEE";
    private static String titleITKey = "titleIT";
    private static String descriptionENKey = "descriptionEN";
    private static String descriptionFRKey = "descriptionFR";
    private static String descriptionPTKey = "descriptionPT";
    private static String descriptionSLKey = "descriptionSL";
    private static String descriptionEEKey = "descriptionEE";
    private static String descriptionITKey = "descriptionIT";

    public static Content createContentFromJSON(Context context, JSONObject jsonObject, int index) throws JSONException {
        Content p = new Content();

        p.setIdC(SafeReader.readInt(jsonObject, idContentKey));
        p.setctype(SafeReader.readInt(jsonObject, pointtypeKey));

        p.setsubEN(SafeReader.readString(jsonObject, subjectENKey));
        p.setsubFR(SafeReader.readString(jsonObject, subjectFRKey));
        p.setsubPT(SafeReader.readString(jsonObject, subjectPTKey));
        p.setsubSL(SafeReader.readString(jsonObject, subjectSLKey));
        p.setsubEE(SafeReader.readString(jsonObject, subjectEEKey));
        p.setsubIT(SafeReader.readString(jsonObject, subjectITKey));

        p.settitEN(SafeReader.readString(jsonObject, titleENKey));
        p.settitFR(SafeReader.readString(jsonObject, titleFRKey));
        p.settitPT(SafeReader.readString(jsonObject, titlePTKey));
        p.settitSL(SafeReader.readString(jsonObject, titleSLKey));
        p.settitEE(SafeReader.readString(jsonObject, titleEEKey));
        p.settitIT(SafeReader.readString(jsonObject, titleITKey));

        p.setdesEN(SafeReader.readString(jsonObject, descriptionENKey));
        p.setdesFR(SafeReader.readString(jsonObject, descriptionFRKey));
        p.setdesPT(SafeReader.readString(jsonObject, descriptionPTKey));
        p.setdesSL(SafeReader.readString(jsonObject, descriptionSLKey));
        p.setdesEE(SafeReader.readString(jsonObject, descriptionEEKey));
        p.setdesIT(SafeReader.readString(jsonObject, descriptionITKey));

        return p;
    }

}
