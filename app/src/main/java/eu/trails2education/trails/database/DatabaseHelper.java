package eu.trails2education.trails.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by svetl on 29. 11. 2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Ce bi prej mislil :D
    // https://stackoverflow.com/questions/36120107/how-to-create-android-sqlite-database-for-many-languages-like-string-values

    public static final String DATABASE_NAME = "trails2education.db";
    public static final String TABLE_1_NAME = "pathways";
    public static final String COL_1_1 = "idPathway";
    public static final String COL_1_2 = "pathwayNameEN";
    public static final String COL_1_3 = "pathwayNameFR";
    public static final String COL_1_4 = "pathwayNamePT";
    public static final String COL_1_5 = "pathwayNameSL";
    public static final String COL_1_6 = "pathwayNameEE";
    public static final String COL_1_7 = "pathwayNameIT";
    public static final String COL_1_8 = "registerDate";
    public static final String COL_1_9 = "totalMeters";
    public static final String COL_1_10 = "estimatedCalories";
    public static final String COL_1_11 = "estimatedTime";
    public static final String COL_1_12 = "estimatedStepsRotat";
    public static final String COL_1_13 = "averageHeartBeatRate";
    public static final String COL_1_14 = "vehicleEN";
    public static final String COL_1_15 = "vehicleFR";
    public static final String COL_1_16 = "vehiclePT";
    public static final String COL_1_17 = "vehicleSL";
    public static final String COL_1_18 = "vehicleEE";
    public static final String COL_1_19 = "vehicleIT";
    public static final String COL_1_20 = "countryEN";
    public static final String COL_1_21 = "countryFR";
    public static final String COL_1_22 = "countryPT";
    public static final String COL_1_23 = "countrySL";
    public static final String COL_1_24 = "countryEE";
    public static final String COL_1_25 = "countryIT";
    public static final String COL_1_26 = "region";
    public static final String COL_1_27 = "area";
    public static final String COL_1_28 = "difficultyEN";
    public static final String COL_1_29 = "difficultyFR";
    public static final String COL_1_30 = "difficultyPT";
    public static final String COL_1_31 = "difficultySL";
    public static final String COL_1_32 = "difficultyEE";
    public static final String COL_1_33 = "difficultyIT";
    public static final String COL_1_34 = "updatedDate";
    public static final String TABLE_2_NAME = "coordinates";
    public static final String COL_2_1 = "id";
    public static final String COL_2_2 = "idPathway";
    public static final String COL_2_3 = "lat";
    public static final String COL_2_4 = "lon";
    public static final String COL_2_5 = "alt";
    public static final String COL_2_6 = "sort";
    public static final String TABLE_3_NAME = "interestpoints";
    public static final String COL_3_1 = "idPathway";
    public static final String COL_3_2 = "idInterestPoint";
    public static final String COL_3_3 = "interestPointNameEN";
    public static final String COL_3_4 = "interestPointNameFR";
    public static final String COL_3_5 = "interestPointNamePT";
    public static final String COL_3_6 = "interestPointNameSL";
    public static final String COL_3_7 = "interestPointNameEE";
    public static final String COL_3_8 = "interestPointNameIT";
    public static final String COL_3_9 = "idPointType";
    public static final String COL_3_10 = "lat";
    public static final String COL_3_11 = "lon";
    public static final String COL_3_12 = "alt";
    public static final String TABLE_4_NAME = "content";
    public static final String COL_4_1 = "idInterestPoint";
    public static final String COL_4_2 = "idContent";
    public static final String COL_4_3 = "idPointType";
    public static final String COL_4_4 = "subjectEN";
    public static final String COL_4_5 = "subjectFR";
    public static final String COL_4_6 = "subjectPT";
    public static final String COL_4_7 = "subjectSL";
    public static final String COL_4_8 = "subjectEE";
    public static final String COL_4_9 = "subjectIT";
    public static final String COL_4_10 = "titleEN";
    public static final String COL_4_11 = "titleFR";
    public static final String COL_4_12 = "titlePT";
    public static final String COL_4_13 = "titleSL";
    public static final String COL_4_14 = "titleEE";
    public static final String COL_4_15 = "titleIT";
    public static final String COL_4_16 = "descriptionEN";
    public static final String COL_4_17 = "descriptionFR";
    public static final String COL_4_18 = "descriptionPT";
    public static final String COL_4_19 = "descriptionSL";
    public static final String COL_4_20 = "descriptionEE";
    public static final String COL_4_21 = "descriptionIT";
    public static final String COL_4_22 = "idSubjectType";
    public static final String TABLE_5_NAME = "multimedia";
    public static final String COL_5_1 = "idContent";
    public static final String COL_5_2 = "idMultElement";
    public static final String COL_5_3 = "idMultimediaType";
    public static final String COL_5_4 = "multimediaTypeEN";
    public static final String COL_5_5 = "multimediaTypeFR";
    public static final String COL_5_6 = "multimediaTypePT";
    public static final String COL_5_7 = "multimediaTypeSL";
    public static final String COL_5_8 = "multimediaTypeEE";
    public static final String COL_5_9 = "multimediaTypeIT";
    public static final String COL_5_10 = "elementURL";

    private static final String SQL_CREATE_TABLE_1 = "CREATE TABLE " + TABLE_1_NAME + "("
            + COL_1_1 + " INTEGER PRIMARY KEY, "
            + COL_1_2 + " TEXT NOT NULL, " + COL_1_3 + " TEXT, " + COL_1_4 + " TEXT, " + COL_1_5 + " TEXT, " + COL_1_6 + " TEXT, " + COL_1_7 + " TEXT, "
            + COL_1_8 + " TEXT, "
            + COL_1_34 + " TEXT, " // last updated field
            + COL_1_9 + " INTEGER, "
            + COL_1_10 + " INTEGER, "
            + COL_1_11 + " INTEGER, "
            + COL_1_12 + " INTEGER, "
            + COL_1_13 + " TEXT, "
            + COL_1_14 + " TEXT, " + COL_1_15 + " TEXT, " + COL_1_16 + " TEXT, " + COL_1_17 + " TEXT, " + COL_1_18 + " TEXT, " + COL_1_19 + " TEXT, "
            + COL_1_20 + " TEXT, " + COL_1_21 + " TEXT, " + COL_1_22 + " TEXT, " + COL_1_23 + " TEXT, " + COL_1_24 + " TEXT, " + COL_1_25 + " TEXT, "
            + COL_1_26 + " TEXT, "
            + COL_1_27 + " TEXT, "
            + COL_1_28 + " TEXT, " + COL_1_29 + " TEXT, " + COL_1_30 + " TEXT, " + COL_1_31 + " TEXT, " + COL_1_32 + " TEXT, " + COL_1_33 + " TEXT "
            + ");"
            ;

    private static final String SQL_CREATE_TABLE_2 = "CREATE TABLE " + TABLE_2_NAME + "("
            + COL_2_1 + " INTEGER PRIMARY KEY, "
            + COL_2_2 + " INTEGER NOT NULL, "
            + COL_2_3 + " REAL NOT NULL, "
            + COL_2_4 + " REAL NOT NULL, "
            + COL_2_5 + " REAL NOT NULL, "
            + COL_2_6 + " INTEGER NOT NULL "
            + ");"
            ;

    private static final String SQL_CREATE_TABLE_3 = "CREATE TABLE " + TABLE_3_NAME + "("
            + COL_3_1 + " INTEGER, "
            + COL_3_2 + " INTEGER PRIMARY KEY, "
            + COL_3_3 + " TEXT, "
            + COL_3_4 + " TEXT, "
            + COL_3_5 + " TEXT, "
            + COL_3_6 + " TEXT, "
            + COL_3_7 + " TEXT, "
            + COL_3_8 + " TEXT, "
            + COL_3_9 + " INTEGER,"
            + COL_3_10 + " REAL, "
            + COL_3_11 + " REAL, "
            + COL_3_12 + " REAL "
            + ");"
            ;    // Svetlana: 13.1.2018 dodala koordinate


    private static final String SQL_CREATE_TABLE_4 = "CREATE TABLE " + TABLE_4_NAME + "("
            + COL_4_1 + " INTEGER, "
            + COL_4_2 + " INTEGER PRIMARY KEY, "
            + COL_4_3 + " INTEGER, "
            + COL_4_4 + " TEXT, " + COL_4_5 + " TEXT, " + COL_4_6 + " TEXT, " + COL_4_7 + " TEXT, " + COL_4_8 + " TEXT, " + COL_4_9 + " TEXT, "
            + COL_4_10 + " TEXT, " + COL_4_11 + " TEXT, " + COL_4_12 + " TEXT, " + COL_4_13 + " TEXT, " + COL_4_14 + " TEXT, " + COL_4_15 + " TEXT, "
            + COL_4_16 + " TEXT, " + COL_4_17 + " TEXT, " + COL_4_18 + " TEXT, " + COL_4_19 + " TEXT, " + COL_4_20 + " TEXT, " + COL_4_21 + " TEXT, "
            + COL_4_22 + " INTEGER "
            + ");"
            ;

    private static final String SQL_CREATE_TABLE_5 = "CREATE TABLE " + TABLE_5_NAME + "("
            + COL_5_1 + " INTEGER NOT NULL, "
            + COL_5_2 + " INTEGER PRIMARY KEY, "
            + COL_5_3 + " INTEGER NOT NULL, "
            + COL_5_4 + " TEXT NOT NULL, " + COL_5_5 + " TEXT, " + COL_5_6 + " TEXT, " + COL_5_7 + " TEXT, " + COL_5_8 + " TEXT, " + COL_5_9 + " TEXT, "
            + COL_5_10 + " TEXT "
            + ");"
            ;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_TABLE_1);
        db.execSQL(SQL_CREATE_TABLE_2);
        db.execSQL(SQL_CREATE_TABLE_3);
        db.execSQL(SQL_CREATE_TABLE_4);
        db.execSQL(SQL_CREATE_TABLE_5);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_5_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_4_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_3_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_2_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_1_NAME);

        onCreate(db);

    }
}
