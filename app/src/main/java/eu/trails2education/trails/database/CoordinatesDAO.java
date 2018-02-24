package eu.trails2education.trails.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by svetl on 5. 12. 2017.
 */

public class CoordinatesDAO {

    public static final String TAG = "CoordinatesDAO";

    private Context mContext;

    // Database fields
    private SQLiteDatabase mDatabase;
    private DatabaseHelper mDbHelper;
    private String[] mAllColumns = { DatabaseHelper.COL_2_1,
            DatabaseHelper.COL_2_2,
            DatabaseHelper.COL_2_3, DatabaseHelper.COL_2_4,
            DatabaseHelper.COL_2_5,
            DatabaseHelper.COL_2_6 };

    public CoordinatesDAO(Context context) {
        mDbHelper = new DatabaseHelper(context);
        this.mContext = context;
        // open the database
        try {
            open();
        } catch (SQLException e) {
            Log.e(TAG, "SQLException on opening database " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void open() throws SQLException {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    public void createCoordinates(Coordinates c){
        createCoordinates(c.getclat(), c.getclon(), c.getcalt(), c.getcsort(), c.getPathwayID());
    }

    public void createCoordinates(double clat, double clon, double calt, long csort, long pathwayId) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_2_3, clat);
        values.put(DatabaseHelper.COL_2_4, clon);
        values.put(DatabaseHelper.COL_2_5, calt);
        values.put(DatabaseHelper.COL_2_6, csort);
        values.put(DatabaseHelper.COL_2_2, pathwayId);
        long insertId = mDatabase.insert(DatabaseHelper.TABLE_2_NAME, null, values);
    }

    public void deleteCoordinates(Coordinates coordinates) {
        long id = coordinates.getIdC();
        mDatabase.delete(DatabaseHelper.TABLE_2_NAME, DatabaseHelper.COL_2_1
                + " = " + id, null);
    }

    /*public ArrayList<Coordinates> getAllCoordinates() {
        ArrayList<Coordinates> listCoordinates = new ArrayList<>();

        Cursor cursor = mDatabase.query(DatabaseHelper.TABLE_2_NAME, mAllColumns,
                null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Coordinates coordinates = cursorToCoordinates(cursor);
            listCoordinates.add(coordinates);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listCoordinates;
    }*/

    public void deleteCoordinatesOfPathway(int pathwayId){
        mDatabase.delete(DatabaseHelper.TABLE_2_NAME, DatabaseHelper.COL_2_2 +  "= ?", new String[]{String.valueOf(pathwayId)});
    }

    public ArrayList<Coordinates> getCoordinatesOfPathway(long pathwayId) {
        ArrayList<Coordinates> listCoordinates = new ArrayList<>();

        Cursor cursor = mDatabase.query(DatabaseHelper.TABLE_2_NAME, mAllColumns,
                DatabaseHelper.COL_2_2 + " = ?",
                new String[] { String.valueOf(pathwayId) }, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Coordinates coordinates = cursorToCoordinates(cursor);

            //Log.e("DATABASE COORDINATES", coordinates.getIdC() +  ", " + coordinates.getclat() + ", " + coordinates.getclon());

            listCoordinates.add(coordinates);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listCoordinates;
    }

    private Coordinates cursorToCoordinates(Cursor cursor) {
        Coordinates coordinates = new Coordinates();
        coordinates.setIdC(cursor.getLong(0));
        coordinates.setPathwayID(cursor.getInt(1));
        coordinates.setclat(cursor.getDouble(2));
        coordinates.setclon(cursor.getDouble(3));
        coordinates.setcalt(cursor.getDouble(4));
        coordinates.setcsort(cursor.getLong(5));

        // get The pathway by id
        /*PathwaysDAO dao = new PathwaysDAO(mContext);
        Pathway pathway = dao.getPathwayById(pathwayId);
        if (pathway != null)
            coordinates.setPathway(pathway);*/

        return coordinates;
    }

}
