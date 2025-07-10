package com.example.airlines;

import android.content.Context;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.airlines.models.Booking;
import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "AirlinesDB";
    private static final int DATABASE_VERSION = 1;
    private final Context context;

    // Tables
    private static final String TABLE_USERS = "users";
    private static final String TABLE_BOOKINGS = "bookings";

    // User columns
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    // Booking columns
    private static final String COLUMN_FROM = "from_city";
    private static final String COLUMN_TO = "to_city";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_FLIGHT_NUMBER = "flight_number";
    private static final String COLUMN_USER_ID = "user_id";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERNAME + " TEXT UNIQUE,"
                + COLUMN_PASSWORD + " TEXT)";

        String CREATE_BOOKINGS_TABLE = "CREATE TABLE " + TABLE_BOOKINGS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USER_ID + " INTEGER,"
                + COLUMN_FROM + " TEXT,"
                + COLUMN_TO + " TEXT,"
                + COLUMN_DATE + " TEXT,"
                + COLUMN_TIME + " TEXT,"
                + COLUMN_FLIGHT_NUMBER + " TEXT,"
                + "FOREIGN KEY(" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_ID + "))";

        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_BOOKINGS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKINGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public boolean insertUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);

        try {
            db.insertOrThrow(TABLE_USERS, null, values);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{COLUMN_ID, COLUMN_USERNAME},
                COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{username, password},
                null, null, null);

        boolean exists = cursor.moveToFirst();
        if (exists) {
            // Save logged in user
            SharedPreferences prefs = context.getSharedPreferences("Airlines", Context.MODE_PRIVATE);
            prefs.edit()
                .putInt("user_id", cursor.getInt(0))
                .putString("username", cursor.getString(1))
                .apply();
        }

        cursor.close();
        return exists;
    }

    public void logout() {
        SharedPreferences prefs = context.getSharedPreferences("Airlines", Context.MODE_PRIVATE);
        prefs.edit().clear().apply();
    }

    public String getCurrentUser() {
        SharedPreferences prefs = context.getSharedPreferences("Airlines", Context.MODE_PRIVATE);
        return prefs.getString("username", "");
    }

    public int getCurrentUserId() {
        SharedPreferences prefs = context.getSharedPreferences("Airlines", Context.MODE_PRIVATE);
        return prefs.getInt("user_id", -1);
    }

    public void addBooking(String from, String to, String date, String time, String flightNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, getCurrentUserId());
        values.put(COLUMN_FROM, from);
        values.put(COLUMN_TO, to);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_TIME, time);
        values.put(COLUMN_FLIGHT_NUMBER, flightNumber);
        db.insert(TABLE_BOOKINGS, null, values);
    }

    public List<Booking> getUserBookings() {
        List<Booking> bookings = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.query(TABLE_BOOKINGS,
                null,
                COLUMN_USER_ID + "=?",
                new String[]{String.valueOf(getCurrentUserId())},
                null, null, COLUMN_DATE + " DESC");

        if (cursor.moveToFirst()) {
            do {
                bookings.add(new Booking(
                    cursor.getString(cursor.getColumnIndex(COLUMN_FROM)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TO)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_DATE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TIME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_FLIGHT_NUMBER))
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return bookings;
    }
}
