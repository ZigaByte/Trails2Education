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
 * Created by svetl on 4. 12. 2017.
 */

public class PathwaysDAO {
    public static final String TAG = "PathwayDAO";

    // Database fields
    private SQLiteDatabase mDatabase;
    private DatabaseHelper mDbHelper;
    private Context mContext;
    private String[] mAllColumns = { DatabaseHelper.COL_1_1, DatabaseHelper.COL_1_2, DatabaseHelper.COL_1_3,
            DatabaseHelper.COL_1_4, DatabaseHelper.COL_1_5, DatabaseHelper.COL_1_6,
            DatabaseHelper.COL_1_7, DatabaseHelper.COL_1_8, DatabaseHelper.COL_1_9,
            DatabaseHelper.COL_1_10, DatabaseHelper.COL_1_11, DatabaseHelper.COL_1_12,
            DatabaseHelper.COL_1_13, DatabaseHelper.COL_1_14, DatabaseHelper.COL_1_15,
            DatabaseHelper.COL_1_16, DatabaseHelper.COL_1_17, DatabaseHelper.COL_1_18,
            DatabaseHelper.COL_1_19, DatabaseHelper.COL_1_20, DatabaseHelper.COL_1_21,
            DatabaseHelper.COL_1_22, DatabaseHelper.COL_1_23, DatabaseHelper.COL_1_24,
            DatabaseHelper.COL_1_25, DatabaseHelper.COL_1_26, DatabaseHelper.COL_1_27,
            DatabaseHelper.COL_1_28, DatabaseHelper.COL_1_29, DatabaseHelper.COL_1_30,
            DatabaseHelper.COL_1_31, DatabaseHelper.COL_1_32, DatabaseHelper.COL_1_33
    };

    public PathwaysDAO(Context context) {
        this.mContext = context;
        mDbHelper = new DatabaseHelper(context);
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

    /**
     * Accepts a Pathway object and adds it to the database
     * */
    public void createPathway(Pathway p){
        createPathway(p.getNameEN(), p.getNameFR(), p.getNamePT(), p.getNameSL(), p.getNameEE(), p.getNameIT(), p.getregDate(),
                p.gettotM(), p.getestCal(), p.getestTime(), p.getestStep(), p.getavgHB(),
                p.getvehEN(), p.getvehFR(), p.getvehPT(), p.getvehSL(), p.getvehEE(), p.getvehIT(),
                p.getcouEN(), p.getcouFR(), p.getcouPT(), p.getcouSL(), p.getcouEE(), p.getcouIT(),
                p.getreg(), p.getar(),
                p.getdifEN(), p.getdifFR(), p.getdifPT(), p.getdifSL(), p.getdifEE(), p.getdifIT(), p.getId());
    }

    /**
     * Accepts all the data about a pathway and adds it to the database
     * */
    public void createPathway(String nameEN, String nameFR, String namePT, String nameSL, String nameEE, String nameIT, String regDate,
                                 long totM, long estCal, long estTime, long estStep, String avgHB,
                                 String vehEN, String vehFR, String vehPT, String vehSL, String vehEE, String vehIT,
                                 String couEN, String couFR, String couPT, String couSL, String couEE, String couIT,
                                 String reg, String ar,
                                 String difEN, String difFR, String difPT, String difSL, String difEE, String difIT, long id) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_1_2, nameEN);
        values.put(DatabaseHelper.COL_1_3, nameFR);
        values.put(DatabaseHelper.COL_1_4, namePT);
        values.put(DatabaseHelper.COL_1_5, nameSL);
        values.put(DatabaseHelper.COL_1_6, nameEE);
        values.put(DatabaseHelper.COL_1_7, nameIT);
        values.put(DatabaseHelper.COL_1_8, regDate);
        values.put(DatabaseHelper.COL_1_9, totM);
        values.put(DatabaseHelper.COL_1_10, estCal);
        values.put(DatabaseHelper.COL_1_11, estTime);
        values.put(DatabaseHelper.COL_1_12, estStep);
        values.put(DatabaseHelper.COL_1_13, avgHB);
        values.put(DatabaseHelper.COL_1_14, vehEN);
        values.put(DatabaseHelper.COL_1_15, vehFR);
        values.put(DatabaseHelper.COL_1_16, vehPT);
        values.put(DatabaseHelper.COL_1_17, vehSL);
        values.put(DatabaseHelper.COL_1_18, vehEE);
        values.put(DatabaseHelper.COL_1_19, vehIT);
        values.put(DatabaseHelper.COL_1_20, couEN);
        values.put(DatabaseHelper.COL_1_21, couFR);
        values.put(DatabaseHelper.COL_1_22, couPT);
        values.put(DatabaseHelper.COL_1_23, couSL);
        values.put(DatabaseHelper.COL_1_24, couEE);
        values.put(DatabaseHelper.COL_1_25, couIT);
        values.put(DatabaseHelper.COL_1_26, reg);
        values.put(DatabaseHelper.COL_1_27, ar);
        values.put(DatabaseHelper.COL_1_28, difEN);
        values.put(DatabaseHelper.COL_1_29, difFR);
        values.put(DatabaseHelper.COL_1_30, difPT);
        values.put(DatabaseHelper.COL_1_31, difSL);
        values.put(DatabaseHelper.COL_1_32, difEE);
        values.put(DatabaseHelper.COL_1_33, difIT);

        // preveri, ali pathway obstaja
        Cursor cursor = mDatabase.query(DatabaseHelper.TABLE_1_NAME, mAllColumns,DatabaseHelper.COL_1_1 + " = " + id, null, null,null, null);

        if(cursor.getCount() > 0){
            // Update the existing db entry.
            mDatabase.update(DatabaseHelper.TABLE_1_NAME, values, DatabaseHelper.COL_1_1 +"=?", new String[]{Long.toString(id)});
        }
        else {
            // Create new db entry. Makes sure to also include the id provided from the network.
            values.put(DatabaseHelper.COL_1_1, id);
            mDatabase.insert(DatabaseHelper.TABLE_1_NAME, null, values);
        }
    }

    /**
     * Deletes the pathway from database.
     * */
    public void deletePathway(Pathway pathway) {
        long id = pathway.getId();

        // delete all interest points and coordinates point of this pathway
        InterestPointDAO interestPointDao = new InterestPointDAO(mContext);
        List<InterestPoint> listInterestPoints = interestPointDao.getInterestPointsOfPathway(id);
        if (listInterestPoints != null && !listInterestPoints.isEmpty()) {
            for (InterestPoint i : listInterestPoints) {
                interestPointDao.deleteInterestPoint(i);
            }
        }

        // deleting coordinates
        CoordinatesDAO coordinatesDao = new CoordinatesDAO(mContext);
        List<Coordinates> listCoordinates = coordinatesDao.getCoordinatsOfPathway(id);
        if (listCoordinates != null && !listCoordinates.isEmpty())
            for (Coordinates c : listCoordinates) {
                coordinatesDao.deleteCoordinates(c);
            }

        // deleting pathway
        System.out.println("the deleted pathway has the id: " + id);
        mDatabase.delete(DatabaseHelper.TABLE_1_NAME, DatabaseHelper.COL_1_1
                + " = " + id, null);
    }

    /**
     * Retrieves all pathways from the database.
     * */
    public ArrayList<Pathway> getAllPathways() {
        ArrayList<Pathway> listPathways = new ArrayList<>();

        Cursor cursor = mDatabase.query(DatabaseHelper.TABLE_1_NAME, mAllColumns,
                null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Pathway pathway = cursorToPathway(cursor);
                listPathways.add(pathway);
                cursor.moveToNext();
            }

            // make sure to close the cursor
            cursor.close();
        }
        return listPathways;
    }

    /**
     * Retrieves the pathway.
     * @param id the ID of the Pathway.
     * */
    public Pathway getPathwayById(long id) {
        Cursor cursor = mDatabase.query(DatabaseHelper.TABLE_1_NAME, mAllColumns,
                DatabaseHelper.COL_1_1 + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Pathway pathway = cursorToPathway(cursor);
        return pathway;
    }

    protected Pathway cursorToPathway(Cursor cursor) {
        Pathway pathway = new Pathway();
        //pathway.setId(cursor.getLong(0));
        pathway.setId(cursor.getInt(0));
        pathway.setNameEN(cursor.getString(1));
        pathway.setNameFR(cursor.getString(2));
        pathway.setNamePT(cursor.getString(3));
        pathway.setNameSL(cursor.getString(4));
        pathway.setNameEE(cursor.getString(5));
        pathway.setNameIT(cursor.getString(6));
        pathway.setregDate(cursor.getString(7));
        pathway.settotM(cursor.getLong(8));
        pathway.setestCal(cursor.getLong(9));
        pathway.setestTime(cursor.getLong(10));
        pathway.setestStep(cursor.getLong(11));
        pathway.setavgHB(cursor.getString(12));
        pathway.setvehEN(cursor.getString(13));
        pathway.setvehFR(cursor.getString(14));
        pathway.setvehPT(cursor.getString(15));
        pathway.setvehSL(cursor.getString(16));
        pathway.setvehEE(cursor.getString(17));
        pathway.setvehIT(cursor.getString(18));
        pathway.setcouEN(cursor.getString(19));
        pathway.setcouFR(cursor.getString(20));
        pathway.setcouPT(cursor.getString(21));
        pathway.setcouSL(cursor.getString(22));
        pathway.setcouEE(cursor.getString(23));
        pathway.setcouIT(cursor.getString(24));
        pathway.setreg(cursor.getString(25));
        pathway.setar(cursor.getString(26));
        pathway.setdifEN(cursor.getString(27));
        pathway.setdifFR(cursor.getString(28));
        pathway.setdifPT(cursor.getString(29));
        pathway.setdifSL(cursor.getString(30));
        pathway.setdifEE(cursor.getString(31));
        pathway.setdifIT(cursor.getString(32));

        return pathway;
    }

}
