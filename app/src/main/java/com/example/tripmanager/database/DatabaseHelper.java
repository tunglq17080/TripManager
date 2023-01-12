package com.example.tripmanager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tripmanager.util.Constant;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper databaseHelper;

    // All Static variables
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = Constant.DATABASE_NAME;

    // Constructor
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DatabaseHelper getInstance(Context context) {
        if (databaseHelper == null) {
            synchronized (DatabaseHelper.class) {
                if (databaseHelper == null)
                    databaseHelper = new DatabaseHelper(context);
            }
        }
        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables SQL execution
        String CREATE_TRIP_TABLE = "CREATE TABLE " + Constant.TABLE_TRIP + "("
                + Constant.COLUMN_TRIP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Constant.COLUMN_TRIP_NAME + " TEXT NOT NULL, "
                + Constant.COLUMN_TRIP_DESTINATION + " TEXT NOT NULL, "
                + Constant.COLUMN_TRIP_DATE_OF_TRIP + " TEXT NOT NULL, " //nullable
                + Constant.COLUMN_TRIP_REQUIRE_RISKS + " INTEGER DEFAULT 0," //nullable
                + Constant.COLUMN_TRIP_DESCRIPTION + " TEXT NOT NULL" //nullable
                + ")";

        String CREATE_EXPENSES_TABLE = "CREATE TABLE " + Constant.TABLE_EXPENSES + "("
                + Constant.COLUMN_EXPENSES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Constant.COLUMN_REGISTRATION_NUMBER + " INTEGER NOT NULL, "
                + Constant.COLUMN_EXPENSES_TYPE + " TEXT NOT NULL, "
                + Constant.COLUMN_EXPENSES_AMOUNT + " TEXT NOT NULL, "
                + Constant.COLUMN_EXPENSES_TIME + " TEXT NOT NULL"
                + ")";
        db.execSQL(CREATE_TRIP_TABLE);
        db.execSQL(CREATE_EXPENSES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Constant.TABLE_TRIP);
        db.execSQL("DROP TABLE IF EXISTS " + Constant.TABLE_EXPENSES);

        // Create tables again
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

}
