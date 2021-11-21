package com.app.sqliteassignment.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB = "RememberTheLocation.db";

    //USERS TABLE
    public static final String USERS_TABLE = "Users";
    public static final String COL_USER_ID = "id";
    public static final String COL_USER_USERNAME ="username";
    public static final String COL_USER_FIRST_NAME = "first_name";
    public static final String COL_USER_LAST_NAME = "last_name";
    public static final String COL_USER_PASSWORD = "password";

    //IMAGES TABLE
    public static final String IMAGES_TABLE = "Images";
    public static final String COL_IMAGE_ID = "id";
    public static final String COL_IMAGE_PATH = "path";
    public static final String COL_IMAGE_LOCATION_DESCRIPTION = "locationDescription";
    public static final String COL_LOCATION_NAME = "locationName";
    public static final String COL_IMAGE_TABLE_USER_ID = "userID";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_USER_TABLE =
                "CREATE TABLE " + USERS_TABLE + "("
                        + COL_USER_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COL_USER_FIRST_NAME +" TEXT NOT NULL, "
                        + COL_USER_LAST_NAME +" TEXT NOT NULL, "
                        + COL_USER_USERNAME + " TEXT NOT NULL, "
                        + COL_USER_PASSWORD + " TEXT NOT NULL)";

        String CREATE_IMAGES_TABLE =
                "CREATE TABLE " + IMAGES_TABLE + "("
                        + COL_IMAGE_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COL_LOCATION_NAME +" TEXT NOT NULL, "
                        + COL_IMAGE_LOCATION_DESCRIPTION + "TEXT NOT NULL, "
                        + COL_IMAGE_PATH + "TEXT NOT NULL, "
                        + COL_IMAGE_TABLE_USER_ID + "INT NOT NULL)";

        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(CREATE_IMAGES_TABLE);
    }

    public Cursor fetchAllData(SQLiteDatabase sqLiteDatabase, int UserID) {
        return sqLiteDatabase.rawQuery(
                "SELECT * FROM " + IMAGES_TABLE + " WHERE "+ COL_IMAGE_TABLE_USER_ID+"="+UserID,
                null);
    }

    public void addUser(SQLiteDatabase sqLiteDatabase, String firstName, String lastName, String email, String password) {
        ContentValues contentValues =new ContentValues();
        contentValues.put(COL_USER_FIRST_NAME, firstName);
        contentValues.put(COL_USER_LAST_NAME, lastName);
        contentValues.put(COL_USER_USERNAME,email);
        contentValues.put(COL_USER_PASSWORD,password);

        sqLiteDatabase.insert(USERS_TABLE,null,contentValues);
    }

    public Cursor fetchUser(SQLiteDatabase sqLiteDatabase, String username, String password) {
        return sqLiteDatabase.rawQuery(
                "SELECT * FROM " + USERS_TABLE + " WHERE " + COL_USER_USERNAME + "=?"+ " AND " + COL_USER_PASSWORD + "=?"
                ,new String[] {username, password});
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + IMAGES_TABLE);
        onCreate(sqLiteDatabase);
    }
}
