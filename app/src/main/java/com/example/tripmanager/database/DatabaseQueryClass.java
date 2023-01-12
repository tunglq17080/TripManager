package com.example.tripmanager.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import com.example.tripmanager.model.Expenses;
import com.example.tripmanager.model.Trip;
import com.example.tripmanager.util.Constant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class DatabaseQueryClass {

    private Context context;

    public DatabaseQueryClass(Context context) {
        this.context = context;
    }


    public int insertTrip(Trip trip) {
        int require;
        if (trip.isRequireRisks()) {
            require = 0;
        } else {
            require = 1;
        }

        int id = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Constant.COLUMN_TRIP_NAME, trip.getName());
        contentValues.put(Constant.COLUMN_TRIP_DESTINATION, trip.getDestination());
        contentValues.put(Constant.COLUMN_TRIP_DATE_OF_TRIP, trip.getDateOfTrip());
        contentValues.put(Constant.COLUMN_TRIP_REQUIRE_RISKS, require);
        contentValues.put(Constant.COLUMN_TRIP_DESCRIPTION, trip.getDescription());

        try {
            id = (int) sqLiteDatabase.insertOrThrow(Constant.TABLE_TRIP, null, contentValues);
        } catch (SQLiteException e) {
            Toast.makeText(context, "Operation failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return id;
    }


    public List<Trip> searchTrip(String s) {
        List<Trip> product = new ArrayList<>();
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sQLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = sQLiteDatabase.rawQuery("SELECT * FROM trip WHERE name LIKE '%" + s + "%' OR destination LIKE '%" + s + "%' OR date_of_trip LIKE '%" + s + "%' ORDER BY _id DESC", null);
        if (cursor.moveToFirst()) {
            do {
                Trip map = new Trip();
                map.setId(cursor.getInt(0));
                map.setName(cursor.getString(1));
                map.setDestination(cursor.getString(2));
                map.setDateOfTrip(cursor.getString(3));
                product.add(map);
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return product;
    }

    public void close() {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sQLiteDatabase = databaseHelper.getReadableDatabase();
        if (sQLiteDatabase != null) {
            sQLiteDatabase.close();
        }
    }


    @SuppressLint("Range")
    public List<Trip> getAllTrip() {

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.query(Constant.TABLE_TRIP, null, null, null, null, null, null, null);
            if (cursor != null)
                if (cursor.moveToFirst()) {
                    List<Trip> tripList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(Constant.COLUMN_TRIP_ID));
                        String name = cursor.getString(cursor.getColumnIndex(Constant.COLUMN_TRIP_NAME));
                        String destination = cursor.getString(cursor.getColumnIndex(Constant.COLUMN_TRIP_DESTINATION));
                        String date = cursor.getString(cursor.getColumnIndex(Constant.COLUMN_TRIP_DATE_OF_TRIP));
                        int require = cursor.getInt(cursor.getColumnIndex(Constant.COLUMN_TRIP_REQUIRE_RISKS));
                        String description = cursor.getString(cursor.getColumnIndex(Constant.COLUMN_TRIP_DESCRIPTION));

                        tripList.add(new Trip(id, name, destination, date, require == 0, description));
                    } while (cursor.moveToNext());

                    return tripList;
                }
        } catch (Exception e) {
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if (cursor != null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return Collections.emptyList();
    }


    public long updateTripInfo(Trip trip) {
        int require;
        if (trip.isRequireRisks()) {
            require = 0;
        } else {
            require = 1;
        }
        long rowCount = 0;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Constant.COLUMN_TRIP_NAME, trip.getName());
        contentValues.put(Constant.COLUMN_TRIP_DESTINATION, trip.getDestination());
        contentValues.put(Constant.COLUMN_TRIP_DATE_OF_TRIP, trip.getDateOfTrip());
        contentValues.put(Constant.COLUMN_TRIP_REQUIRE_RISKS, require);
        contentValues.put(Constant.COLUMN_TRIP_DESCRIPTION, trip.getDescription());

        try {
            rowCount = sqLiteDatabase.update(Constant.TABLE_TRIP, contentValues,
                    Constant.COLUMN_TRIP_ID + " = ? ",
                    new String[]{String.valueOf(trip.getId())});
        } catch (SQLiteException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return rowCount;
    }


    public long deleteTrip(int id) {
        int deletedRowCount = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            deletedRowCount = sqLiteDatabase.delete(Constant.TABLE_TRIP,
                    Constant.COLUMN_TRIP_ID + " = ? ",
                    new String[]{String.valueOf(id)});
        } catch (SQLiteException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return deletedRowCount;
    }


    public boolean deleteAllTrips() {
        boolean deleteStatus = false;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            sqLiteDatabase.delete(Constant.TABLE_TRIP, null, null);

            long count = DatabaseUtils.queryNumEntries(sqLiteDatabase, Constant.TABLE_TRIP);

            if (count == 0)
                deleteStatus = true;

        } catch (SQLiteException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return deleteStatus;
    }



    public int insertExpenses(Expenses expenses, long registrationNo) {
        int rowId = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Constant.COLUMN_EXPENSES_TYPE, expenses.getType());
        contentValues.put(Constant.COLUMN_EXPENSES_AMOUNT, expenses.getAmount());
        contentValues.put(Constant.COLUMN_EXPENSES_TIME, expenses.getTime());
        contentValues.put(Constant.COLUMN_REGISTRATION_NUMBER, registrationNo);

        try {
            rowId = (int) sqLiteDatabase.insertOrThrow(Constant.TABLE_EXPENSES, null, contentValues);
        } catch (SQLiteException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return rowId;
    }


    @SuppressLint("Range")
    public List<Expenses> getAllExpensesByRegNo(int registrationNo) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        List<Expenses> expensesList = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.query(Constant.TABLE_EXPENSES,
                    new String[]{Constant.COLUMN_EXPENSES_ID, Constant.COLUMN_EXPENSES_TYPE, Constant.COLUMN_EXPENSES_AMOUNT, Constant.COLUMN_EXPENSES_TIME},
                    Constant.COLUMN_REGISTRATION_NUMBER + " = ? ",
                    new String[]{String.valueOf(registrationNo)},
                    null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                expensesList = new ArrayList<>();
                do {
                    int id = cursor.getInt(cursor.getColumnIndex(Constant.COLUMN_EXPENSES_ID));
                    String type = cursor.getString(cursor.getColumnIndex(Constant.COLUMN_EXPENSES_TYPE));
                    String amount = cursor.getString(cursor.getColumnIndex(Constant.COLUMN_EXPENSES_AMOUNT));
                    String time = cursor.getString(cursor.getColumnIndex(Constant.COLUMN_EXPENSES_TIME));

                    expensesList.add(new Expenses(id, type, amount, time));
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if (cursor != null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return expensesList;
    }


}