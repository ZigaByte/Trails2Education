package eu.trails2education.trails.json;

import android.content.Context;

import org.json.JSONObject;

import eu.trails2education.trails.database.Multimedia;

/**
 * Created by Ziga on 24-Apr-18.
 */

public class MultimediaJSON {

    private static String idMultElement = "idMultElement";
    private static String multimediaTypeEN = "multimediaTypeEN";
    private static String multimediaTypeFR = "multimediaTypeFR";
    private static String multimediaTypePT = "multimediaTypePT";
    private static String multimediaTypeSL = "multimediaTypeSL";
    private static String multimediaTypeEE = "multimediaTypeEE";
    private static String multimediaTypeIT = "multimediaTypeIT";
    private static String idMultimediaType = "idMultimediaType";
    private static String elementURL = "elementURL";

    public static Multimedia createMultimediaFromJSON(Context context, JSONObject jsonObject){
        Multimedia m = new Multimedia();

        m.setId(SafeReader.readInt(jsonObject, idMultElement));
        m.setmulEN(SafeReader.readString(jsonObject, multimediaTypeEN));
        m.setmulFR(SafeReader.readString(jsonObject, multimediaTypeFR));
        m.setmulPT(SafeReader.readString(jsonObject, multimediaTypePT));
        m.setmulSL(SafeReader.readString(jsonObject, multimediaTypeSL));
        m.setmulEE(SafeReader.readString(jsonObject, multimediaTypeEE));
        m.setmulIT(SafeReader.readString(jsonObject, multimediaTypeIT));
        m.setctype(SafeReader.readInt(jsonObject, idMultimediaType));
        m.seteURL(SafeReader.readString(jsonObject, elementURL));

        return m;
    }

}
