package com.example.tripmanager.util;

public class Constant {

    public static final String DATABASE_NAME = "trip-db";

    //column names of trip table
    public static final String TABLE_TRIP = "trip";
    public static final String COLUMN_TRIP_ID = "_id";
    public static final String COLUMN_TRIP_NAME = "name";
    public static final String COLUMN_TRIP_DESTINATION = "destination";
    public static final String COLUMN_TRIP_DATE_OF_TRIP = "date_of_trip";
    public static final String COLUMN_TRIP_REQUIRE_RISKS = "require_risks";
    public static final String COLUMN_TRIP_DESCRIPTION = "description";

    //column names of expenses table
    public static final String TABLE_EXPENSES = "expenses";
    public static final String COLUMN_EXPENSES_ID = "_id";
    public static final String COLUMN_REGISTRATION_NUMBER = "fk_registration_no";
    public static final String COLUMN_EXPENSES_TYPE = "type";
    public static final String COLUMN_EXPENSES_AMOUNT = "amount";
    public static final String COLUMN_EXPENSES_TIME = "time";

    public static final String TRIP_REGISTRATION = "trip_registration";

    // KEY
    public static final String KEY_TRIP = "key_trip";

}
