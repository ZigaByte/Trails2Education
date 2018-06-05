package eu.trails2education.trails.json;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import eu.trails2education.trails.database.Pathway;

/**
 * Created by Žiga on 23. 12. 2017.
 */

public class PathwayJSON {

    private static String idPathwayKey = "idPathway";
    private static String pathwayNameENKey = "pathwayNameEN";
    private static String pathwayNameFRKey = "pathwayNameFR";
    private static String pathwayNamePTKey = "pathwayNamePT";
    private static String pathwayNameSLKey = "pathwayNameSL";
    private static String pathwayNameEEKey = "pathwayNameEE";
    private static String pathwayNameITKey = "pathwayNameIT";
    private static String registerDateKey = "registerDate";
    private static String updatedDateKey = "lastUpdated";
    private static String totalMetersKey = "totalMeters";
    private static String estimatedCaloriesKey = "estimatedCalories";
    private static String estimatedTimeKey = "estimatedTime";
    private static String estimatedStepsRotatKey = "estimatedStepsRotat";
    private static String averageHeartBeatRateKey = "averageHeartBeatRate";
    private static String vehicleENKey = "vehicleEN";
    private static String vehicleFRKey = "vehicleFR";
    private static String vehiclePTKey = "vehiclePT";
    private static String vehicleSLKey = "vehicleSL";
    private static String vehicleEEKey = "vehicleEE";
    private static String vehicleITKey = "vehicleIT";
    private static String countryENKey = "countryEN";
    private static String countryFRKey = "countryFR";
    private static String countryPTKey = "countryPT";
    private static String countrySLKey = "countrySL";
    private static String countryEEKey = "countryEE";
    private static String countryITKey = "countryIT";
    private static String regionKey = "region";
    private static String areaKey = "area";
    private static String difficultyENKey = "difficultyEN";
    private static String difficultyFRKey = "difficultyFR";
    private static String difficultyPTKey = "difficultyPT";
    private static String difficultySLKey = "difficultySL";
    private static String difficultyEEKey = "difficultyEE";
    private static String difficultyITKey = "difficultyIT";

    public static Pathway createPathFromJSON(Context context, JSONObject jsonObject, int index) throws JSONException {
        jsonObject = jsonObject.getJSONArray("posts").getJSONObject(index);

        Pathway p = new Pathway();

        p.setId(SafeReader.readInt(jsonObject, idPathwayKey));
        p.setNameEN(SafeReader.readString(jsonObject, pathwayNameENKey));
        p.setNameFR(SafeReader.readString(jsonObject, pathwayNameFRKey));
        p.setNamePT(SafeReader.readString(jsonObject, pathwayNamePTKey));
        p.setNameSL(SafeReader.readString(jsonObject, pathwayNameSLKey));
        p.setNameEE(SafeReader.readString(jsonObject, pathwayNameEEKey));
        p.setNameIT(SafeReader.readString(jsonObject, pathwayNameITKey));
        p.setregDate(SafeReader.readString(jsonObject, registerDateKey));
        p.setupdDate(SafeReader.readString(jsonObject, updatedDateKey));
        p.settotM(SafeReader.readInt(jsonObject, totalMetersKey));
        p.setestCal(SafeReader.readInt(jsonObject, estimatedCaloriesKey));
        p.setestTime(SafeReader.readInt(jsonObject, estimatedTimeKey));
        p.setestStep(SafeReader.readInt(jsonObject, estimatedStepsRotatKey));
        p.setavgHB(SafeReader.readString(jsonObject, averageHeartBeatRateKey));
        p.setvehEN(SafeReader.readString(jsonObject, vehicleENKey));
        p.setvehFR(SafeReader.readString(jsonObject, vehicleFRKey));
        p.setvehPT(SafeReader.readString(jsonObject, vehiclePTKey));
        p.setvehSL(SafeReader.readString(jsonObject, vehicleSLKey));
        p.setvehEE(SafeReader.readString(jsonObject, vehicleEEKey));
        p.setvehIT(SafeReader.readString(jsonObject, vehicleITKey));
        p.setcouEN(SafeReader.readString(jsonObject, countryENKey));
        p.setcouFR(SafeReader.readString(jsonObject, countryFRKey));
        p.setcouPT(SafeReader.readString(jsonObject, countryPTKey));
        p.setcouSL(SafeReader.readString(jsonObject, countrySLKey));
        p.setcouEE(SafeReader.readString(jsonObject, countryEEKey));
        p.setcouIT(SafeReader.readString(jsonObject, countryITKey));
        p.setreg(SafeReader.readString(jsonObject, regionKey));
        p.setar(SafeReader.readString(jsonObject, areaKey));
        p.setdifEN(SafeReader.readString(jsonObject, difficultyENKey));
        p.setdifFR(SafeReader.readString(jsonObject, difficultyFRKey));
        p.setdifPT(SafeReader.readString(jsonObject, difficultyPTKey));
        p.setdifSL(SafeReader.readString(jsonObject, difficultySLKey));
        p.setdifEE(SafeReader.readString(jsonObject, difficultyEEKey));
        p.setdifIT(SafeReader.readString(jsonObject, difficultyITKey));

        return p;
    }

    public static ArrayList<Pathway> createPathListFromJSON(Context context, JSONObject jsonObject) throws JSONException{
        ArrayList<Pathway> pathList = new ArrayList<Pathway>();
        for(int i = 0; i < jsonObject.getJSONArray("posts").length(); i++){
            Pathway p = createPathFromJSON(context, jsonObject, i);
            pathList.add(p);
        }
        return pathList;
    }

    public static Pathway createFullPathFromJSON(Context context, JSONObject jsonObject) throws JSONException{
        Pathway p = createPathFromJSON(context, jsonObject, 0);
        p.setCoorinates(CoordinateJSON.createCoordinateListFromJSON(context, jsonObject.getJSONArray("posts").getJSONObject(0).getJSONArray("coordinates"), (int)p.getId()));
        p.setInterestPoints(InterestPointJSON.createInterestPointListFromJSON(context, jsonObject.getJSONArray("posts").getJSONObject(0).getJSONArray("interestPoints"), (int)p.getId()));
        return p;
    }
}
