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

public class MultimediaDAO {

    public static final String TAG = "MultimediaDAO";

    private Context mContext;

    // Database fields
    private SQLiteDatabase mDatabase;
    private DatabaseHelper mDbHelper;
    private String[] mAllColumns = { DatabaseHelper.COL_5_1,
            DatabaseHelper.COL_5_2, DatabaseHelper.COL_5_3, DatabaseHelper.COL_5_4, DatabaseHelper.COL_5_5, DatabaseHelper.COL_5_6, DatabaseHelper.COL_5_7,
            DatabaseHelper.COL_5_8, DatabaseHelper.COL_5_9, DatabaseHelper.COL_5_10 };

    public MultimediaDAO(Context context) {
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

    public void createMultimedia(Multimedia m){
        createMultimedia(m.getCId(), m.getctype(), m.getmulEN(), m.getmulFR(), m.getmulPT(), m.getmulSL(), m.getmulEE(), m.getmulIT(), m.geteURL(), m.getId());
    }

    public void createMultimedia(long contentId, long multimediaType, String mulEN, String mulFR, String mulPT, String mulSL, String mulEE, String mulIT,
                                 String eURL, long multimediaId) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_5_1, contentId);
        //values.put(DatabaseHelper.COL_5_2, multimediaId);
        values.put(DatabaseHelper.COL_5_3, multimediaType);
        values.put(DatabaseHelper.COL_5_4, mulEN);
        values.put(DatabaseHelper.COL_5_5, mulFR);
        values.put(DatabaseHelper.COL_5_6, mulPT);
        values.put(DatabaseHelper.COL_5_7, mulSL);
        values.put(DatabaseHelper.COL_5_8, mulEE);
        values.put(DatabaseHelper.COL_5_9, mulIT);
        values.put(DatabaseHelper.COL_5_10, eURL);

        Log.e("Inserting new media", multimediaId + " " + eURL + " " + contentId);

        // preveri, ali pathway obstaja
        Cursor cursor = mDatabase.query(DatabaseHelper.TABLE_5_NAME, mAllColumns,DatabaseHelper.COL_5_2 + " = " + multimediaId, null, null,null, null);

        if(cursor.getCount() > 0){
            // Update the existing db entry.
            mDatabase.update(DatabaseHelper.TABLE_5_NAME, values, DatabaseHelper.COL_5_2 +"=?", new String[]{Long.toString(multimediaId)});
            Log.e("Updating", "yes");
        }
        else {
            // Create new db entry. Makes sure to also include the id provided from the network.
            Log.e("Inserting", "yes");
            values.put(DatabaseHelper.COL_5_2, multimediaId);
            mDatabase.insert(DatabaseHelper.TABLE_5_NAME, null, values);
        }
    }

    public void deleteMultimedia(Multimedia multimedia) {
        long id = multimedia.getId();
        System.out.println("the deleted multimedia has the id: " + id);

        mDatabase.delete(DatabaseHelper.TABLE_5_NAME, DatabaseHelper.COL_5_2
                + " = " + id, null);
    }

    public List<Multimedia> getAllMultimedia() {
        List<Multimedia> listMultimedia = new ArrayList<>();

        Cursor cursor = mDatabase.query(DatabaseHelper.TABLE_5_NAME, mAllColumns,
                null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Multimedia multimedia = cursorToMultimedia(cursor);
            listMultimedia.add(multimedia);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listMultimedia;
    }

    public ArrayList<Multimedia> getMultimediaOfContent(long contentId) {
        ArrayList<Multimedia> listMultimedia = new ArrayList<>();

        Cursor cursor = mDatabase.query(DatabaseHelper.TABLE_5_NAME, mAllColumns,
                DatabaseHelper.COL_5_1 + " = ?",
                new String[] { String.valueOf(contentId) }, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Multimedia multimedia = cursorToMultimedia(cursor);
            listMultimedia.add(multimedia);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();

        Log.e("Returnign" ,  contentId +", size:" + listMultimedia.size());
        return listMultimedia;
    }

    private Multimedia cursorToMultimedia(Cursor cursor) {
        Multimedia multimedia = new Multimedia();
        multimedia.setCId(cursor.getLong(0));
        multimedia.setId(cursor.getLong(1));
        multimedia.setctype(cursor.getLong(2));
        multimedia.setmulEN(cursor.getString(3));
        multimedia.setmulFR(cursor.getString(4));
        multimedia.setmulPT(cursor.getString(5));
        multimedia.setmulSL(cursor.getString(6));
        multimedia.setmulEE(cursor.getString(7));
        multimedia.setmulIT(cursor.getString(8));
        multimedia.seteURL(cursor.getString(9));
        return multimedia;

    }

}
