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
 * Created by svetl on 6. 12. 2017.
 */

public class InterestPointDAO {

    public static final String TAG = "InterestPointDAO";

    private Context mContext;

    // Database fields
    private SQLiteDatabase mDatabase;
    private DatabaseHelper mDbHelper;
    private String[] mAllColumns = { DatabaseHelper.COL_3_1,
            DatabaseHelper.COL_3_2,
            DatabaseHelper.COL_3_3,
            DatabaseHelper.COL_3_4,
            DatabaseHelper.COL_3_5,
            DatabaseHelper.COL_3_6,
            DatabaseHelper.COL_3_7,
            DatabaseHelper.COL_3_8,
            DatabaseHelper.COL_3_9,
            DatabaseHelper.COL_3_10,
            DatabaseHelper.COL_3_11,
            DatabaseHelper.COL_3_12};    // Svetlana: 13.1.2018 dodala koordinate

    public InterestPointDAO(Context context) {
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

    public InterestPoint createInterestPoint(String nameEN, String nameFR, String namePT, String nameSL, String nameEE, String nameIT,
                                             long ctype, long pathwayId, double clat, double clon, double calt) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_3_1, pathwayId);
        values.put(DatabaseHelper.COL_3_3, nameEN);
        values.put(DatabaseHelper.COL_3_4, nameFR);
        values.put(DatabaseHelper.COL_3_5, namePT);
        values.put(DatabaseHelper.COL_3_6, nameSL);
        values.put(DatabaseHelper.COL_3_7, nameEE);
        values.put(DatabaseHelper.COL_3_8, nameIT);
        values.put(DatabaseHelper.COL_3_9, ctype);
        values.put(DatabaseHelper.COL_3_10, clat);    // Svetlana: 13.1.2018 dodala koordinate
        values.put(DatabaseHelper.COL_3_11, clon);
        values.put(DatabaseHelper.COL_3_12, calt);

        long insertId = mDatabase
                .insert(DatabaseHelper.TABLE_3_NAME, null, values);
        Cursor cursor = mDatabase.query(DatabaseHelper.TABLE_3_NAME, mAllColumns,
                DatabaseHelper.COL_3_1 + " = " + insertId, null, null,
                null, null);
        cursor.moveToFirst();
        InterestPoint interestpoint = cursorToInterestPoint(cursor);
        cursor.close();
        return interestpoint;
    }

    public void deleteInterestPoint(InterestPoint interestpoint) {
        long id = interestpoint.getcIdIP();
        System.out.println("the deleted interest point has the id: " + id);

        ContentDAO contentDao = new ContentDAO(mContext);
        List<Content> listContents = contentDao.getContentsOfInterestPoint(id);
        if (listContents != null && !listContents.isEmpty()) {
            for (Content c : listContents) {
                contentDao.deleteContent(c);
            }
        }

        mDatabase.delete(DatabaseHelper.TABLE_3_NAME, DatabaseHelper.COL_3_2
                + " = " + id, null);
    }

    public ArrayList<InterestPoint> getAllInterestPoints() {
        ArrayList<InterestPoint> listInterestPoints = new ArrayList<>();

        Cursor cursor = mDatabase.query(DatabaseHelper.TABLE_3_NAME, mAllColumns,
                null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            InterestPoint interestpoint = cursorToInterestPoint(cursor);
            listInterestPoints.add(interestpoint);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listInterestPoints;
    }

    public ArrayList<InterestPoint> getInterestPointsOfPathway(long pathwayId) {
        ArrayList<InterestPoint> listInterestPoints = new ArrayList<>();

        Cursor cursor = mDatabase.query(DatabaseHelper.TABLE_3_NAME, mAllColumns,
                DatabaseHelper.COL_3_2 + " = ?",
                new String[] { String.valueOf(pathwayId) }, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            InterestPoint interestpoint = cursorToInterestPoint(cursor);
            listInterestPoints.add(interestpoint);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listInterestPoints;
    }

    public InterestPoint getInterestPointsById(long interestpointId) {
        Cursor cursor = mDatabase.query(DatabaseHelper.TABLE_3_NAME, mAllColumns,
                DatabaseHelper.COL_3_2 + " = ?",
                new String[] { String.valueOf(interestpointId) }, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        InterestPoint interestpoint = cursorToInterestPoint(cursor);
        return interestpoint;
    }

    private InterestPoint cursorToInterestPoint(Cursor cursor) {
        InterestPoint interestpoint = new InterestPoint();
        interestpoint.setIdIP(cursor.getLong(1));
        interestpoint.setNameEN(cursor.getString(2));
        interestpoint.setNameFR(cursor.getString(3));
        interestpoint.setNamePT(cursor.getString(4));
        interestpoint.setNameSL(cursor.getString(5));
        interestpoint.setNameEE(cursor.getString(6));
        interestpoint.setNameIT(cursor.getString(7));
        interestpoint.setctype(cursor.getLong(8));
        interestpoint.setclat(cursor.getLong(9));   // Svetlana: 13.1.2018 dodala koordinate
        interestpoint.setclon(cursor.getLong(10));
        interestpoint.setcalt(cursor.getLong(11));

        // get The pathway by id
        long pathwayId = cursor.getLong(0);
        PathwaysDAO dao = new PathwaysDAO(mContext);
        Pathway pathway = dao.getPathwayById(pathwayId);
        if (pathway != null)
            interestpoint.setPathway(pathway);

        return interestpoint;
    }
}
