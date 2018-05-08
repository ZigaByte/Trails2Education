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

public class ContentDAO {

    public static final String TAG = "ContentDAO";

    private Context mContext;

    // Database fields
    private SQLiteDatabase mDatabase;
    private DatabaseHelper mDbHelper;
    private String[] mAllColumns = { DatabaseHelper.COL_4_1,
            DatabaseHelper.COL_4_2, DatabaseHelper.COL_4_3, DatabaseHelper.COL_4_4, DatabaseHelper.COL_4_5, DatabaseHelper.COL_4_6, DatabaseHelper.COL_4_7,
            DatabaseHelper.COL_4_8, DatabaseHelper.COL_4_9, DatabaseHelper.COL_4_10, DatabaseHelper.COL_4_11, DatabaseHelper.COL_4_12, DatabaseHelper.COL_4_13,
            DatabaseHelper.COL_4_14, DatabaseHelper.COL_4_15, DatabaseHelper.COL_4_16, DatabaseHelper.COL_4_17, DatabaseHelper.COL_4_18, DatabaseHelper.COL_4_19,
            DatabaseHelper.COL_4_20, DatabaseHelper.COL_4_21, DatabaseHelper.COL_4_22 };

    public ContentDAO(Context context) {
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

    public static final int INSERT_TYPE_FULL = 1;
    public static final int INSERT_TYPE_DATA = 2;
    public static final int INSERT_TYPE_SUBJECT = 3;

    public void createContent(Content c, int INSERT_TYPE){
        createContent(c.getcIdC(), c.getctype(), c.getsubEN(), c.getsubFR(), c.getsubPT(), c.getsubSL(), c.getsubEE(), c.getsubIT(),
                c.gettitEN(), c.gettitFR(), c.gettitPT(), c.gettitSL(), c.gettitEE(), c.gettitIT(),
                c.getdesEN(), c.getdesFR(), c.getdesPT(), c.getdesSL(), c.getdesEE(), c.getdesIT(), c.getIpId(), c.getstype(), INSERT_TYPE);
    }

    public void createContent(long cid,long ctype, String subEN, String subFR, String subPT, String subSL, String subEE, String subIT,
                                 String titEN, String titFR, String titPT, String titSL, String titEE, String titIT,
                                 String desEN, String desFR, String desPT, String desSL, String desEE, String desIT, long interestpointId, long subjectType, final int INSERT_TYPE) {
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.COL_4_1, interestpointId);
        values.put(DatabaseHelper.COL_4_2, cid);
        values.put(DatabaseHelper.COL_4_3, ctype);
        values.put(DatabaseHelper.COL_4_22, subjectType);

        if(INSERT_TYPE == INSERT_TYPE_FULL || INSERT_TYPE == INSERT_TYPE_SUBJECT){
        }
        if(INSERT_TYPE == INSERT_TYPE_FULL || INSERT_TYPE == INSERT_TYPE_DATA){
            values.put(DatabaseHelper.COL_4_4, subEN);
            values.put(DatabaseHelper.COL_4_5, subFR);
            values.put(DatabaseHelper.COL_4_6, subPT);
            values.put(DatabaseHelper.COL_4_7, subSL);
            values.put(DatabaseHelper.COL_4_8, subEE);
            values.put(DatabaseHelper.COL_4_9, subIT);
            values.put(DatabaseHelper.COL_4_10, titEN);
            values.put(DatabaseHelper.COL_4_11, titFR);
            values.put(DatabaseHelper.COL_4_12, titPT);
            values.put(DatabaseHelper.COL_4_13, titSL);
            values.put(DatabaseHelper.COL_4_14, titEE);
            values.put(DatabaseHelper.COL_4_15, titIT);
            values.put(DatabaseHelper.COL_4_16, desEN);
            values.put(DatabaseHelper.COL_4_17, desFR);
            values.put(DatabaseHelper.COL_4_18, desPT);
            values.put(DatabaseHelper.COL_4_19, desSL);
            values.put(DatabaseHelper.COL_4_20, desEE);
            values.put(DatabaseHelper.COL_4_21, desIT);
        }

        // preveri, ali content obstaja
        Cursor cursor = mDatabase.query(DatabaseHelper.TABLE_4_NAME, mAllColumns,DatabaseHelper.COL_4_2 + " = " + cid,null, null,null, null);

        if(cursor.getCount() > 0){
            // Update the existing db entry.
            mDatabase.update(DatabaseHelper.TABLE_4_NAME, values, DatabaseHelper.COL_4_2 + " = " + cid, null);
        }
        else {
            //values.put(DatabaseHelper.COL_4_2, cid);
            mDatabase.insert(DatabaseHelper.TABLE_4_NAME, null, values);
        }
    }

    public void deleteContent(Content content) {
        long id = content.getcIdC();

        MultimediaDAO multimediaDao = new MultimediaDAO(mContext);
        List<Multimedia> listMultimedia = multimediaDao.getMultimediaOfContent(id);
        if (listMultimedia != null && !listMultimedia.isEmpty()) {
            for (Multimedia m : listMultimedia) {
                multimediaDao.deleteMultimedia(m);
            }
        }

        mDatabase.delete(DatabaseHelper.TABLE_4_NAME, DatabaseHelper.COL_4_2
                + " = " + id, null);
    }

    public ArrayList<Content> getAllContents() {
        ArrayList<Content> listContents = new ArrayList<>();

        Cursor cursor = mDatabase.query(DatabaseHelper.TABLE_4_NAME, mAllColumns,
                null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Content content = cursorToContent(cursor);
            listContents.add(content);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listContents;
    }

    public ArrayList<Content> getContentsOfInterestPoint(long interestpointId) {
        ArrayList<Content> listContents = new ArrayList<>();

        Cursor cursor = mDatabase.query(DatabaseHelper.TABLE_4_NAME, mAllColumns,
                DatabaseHelper.COL_4_1 + " = ?",
                new String[] { String.valueOf(interestpointId) }, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Content content = cursorToContent(cursor);
            listContents.add(content);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listContents;
    }

    public Content getContentById(long contentId) {
        Cursor cursor = mDatabase.query(DatabaseHelper.TABLE_4_NAME, mAllColumns,
                DatabaseHelper.COL_4_2 + " = ?",
                new String[] { String.valueOf(contentId) }, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        Content content = cursorToContent(cursor);
        return content;
    }

    private Content cursorToContent(Cursor cursor) {
        Content content = new Content();
        content.setIpId(cursor.getLong(0));
        content.setIdC(cursor.getLong(1));
        content.setctype(cursor.getLong(2));
        content.setsubEN(cursor.getString(3));
        content.setsubFR(cursor.getString(4));
        content.setsubPT(cursor.getString(5));
        content.setsubSL(cursor.getString(6));
        content.setsubEE(cursor.getString(7));
        content.setsubIT(cursor.getString(8));
        content.settitEN(cursor.getString(9));
        content.settitFR(cursor.getString(10));
        content.settitPT(cursor.getString(11));
        content.settitSL(cursor.getString(12));
        content.settitEE(cursor.getString(13));
        content.settitIT(cursor.getString(14));
        content.setdesEN(cursor.getString(15));
        content.setdesFR(cursor.getString(16));
        content.setdesPT(cursor.getString(17));
        content.setdesSL(cursor.getString(18));
        content.setdesEE(cursor.getString(19));
        content.setdesIT(cursor.getString(20));
        content.setstype(cursor.getLong(21));
        return content;
    }

}
