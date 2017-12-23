package eu.trails2education.trails.json;

import android.util.Log;

import org.json.JSONObject;

/**
 * Created by Ziga on 19-Dec-17.
 */

public class SafeReader {

    /**
     * Safely reads the integer at @key in the provided object. If it does not exist or is null
     * it returns the provided default value.
     * */
    public static int readInt(JSONObject object, String key, int defaultValue) {
        if(!object.has(key) || object.isNull(key)){
            Log.e("JSON int EXCEPTION", "Value not found at key: " + key);
            return defaultValue;
        }
        try{
            int value = object.getInt(key);
            return value;
        }catch (Exception e){
            Log.e("JSON int EXCEPTION", "A JSON int READING EXCETPTION at key: " + key);
            return defaultValue;
        }
    }

    /** Safe integer read with default value 0. */
    public static int readInt(JSONObject object, String key){
        return readInt(object, key, 0);
    }

    /**
     * Safely reads the String at @key in the provided object. If it does not exist or is null
     * it returns the provided default value.
     * */
    public static String readString(JSONObject object, String key, String defaultValue) {
        if(!object.has(key) || object.isNull(key)) {
            Log.e("JSON int EXCEPTION", "Value not found at key: " + key);
            return defaultValue;
        }
        try{
            String value = object.getString(key);
            return value;
        }catch (Exception e){
            Log.e("JSON String EXCEPTION", "A JSON String READING EXCETPTION at key: " + key);
            return defaultValue;
        }
    }

    /** Safe String read with default value of an empty string. */
    public static String readString(JSONObject object, String key){
        return readString(object, key, "");
    }



}
