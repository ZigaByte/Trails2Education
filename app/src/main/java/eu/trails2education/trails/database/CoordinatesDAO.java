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

    public Coordinates createCoordinates(double clat, double clon, double calt, long csort, long pathwayId) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_2_3, calt);
        values.put(DatabaseHelper.COL_2_4, clon);
        values.put(DatabaseHelper.COL_2_5, calt);
        values.put(DatabaseHelper.COL_2_6, csort);
        values.put(DatabaseHelper.COL_2_2, pathwayId);
        long insertId = mDatabase
                .insert(DatabaseHelper.TABLE_2_NAME, null, values);
        Cursor cursor = mDatabase.query(DatabaseHelper.TABLE_2_NAME, mAllColumns,
                DatabaseHelper.COL_2_1 + " = " + insertId, null, null,
                null, null);
        cursor.moveToFirst();
        Coordinates newCoordinates = cursorToCoordinates(cursor);
        cursor.close();
        return newCoordinates;
    }

    public void deleteCoordinates(Coordinates coordinates) {
        long id = coordinates.getIdC();
        System.out.println("the deleted coordinates has the id: " + id);
        mDatabase.delete(DatabaseHelper.TABLE_2_NAME, DatabaseHelper.COL_2_1
                + " = " + id, null);
    }

    public List<Coordinates> getAllCoordinates() {
        List<Coordinates> listCoordinates = new ArrayList<>();

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
    }

    public List<Coordinates> getCoordinatsOfPathway(long pathwayId) {
        List<Coordinates> listCoordinates = new ArrayList<>();

        Cursor cursor = mDatabase.query(DatabaseHelper.TABLE_2_NAME, mAllColumns,
                DatabaseHelper.COL_2_2 + " = ?",
                new String[] { String.valueOf(pathwayId) }, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Coordinates coordinates = cursorToCoordinates(cursor);
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
        coordinates.setclat(cursor.getDouble(1));
        coordinates.setclon(cursor.getDouble(2));
        coordinates.setcalt(cursor.getDouble(3));
        coordinates.setcsort(cursor.getLong(4));

        // get The pathway by id
        long pathwayId = cursor.getLong(5);
        PathwaysDAO dao = new PathwaysDAO(mContext);
        Pathway pathway = dao.getPathwayById(pathwayId);
        if (pathway != null)
            coordinates.setPathway(pathway);

        return coordinates;
    }

}
