package com.as.photoapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String DATABASE_NAME = "users.db";

    public static final int DATABASE_VERSION = 3;

    public static final String TABLE_USERS = "users_app";
    public static final String ID = "ID";
    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String EMAIL = "email";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    private static final String SQL_CREATE_TABLE_USERS =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    ID + " INTEGER PRIMARY KEY," +
                    NAME + " TEXT," +
                    SURNAME + " TEXT," +
                    EMAIL + " TEXT," +
                    USERNAME + " TEXT," +
                    PASSWORD + " TEXT)";


    private static final String SQL_DELETE_USERS =
            "DROP TABLE IF EXISTS " + TABLE_USERS;


    public static final String TABLE_PHOTOS = "users_photo";
    public static final String ID_PHOTO = "ID_PHOTO";
    public static final String USER_NAME = "USER_NAME";
    public static final String PHOTO = "PHOTO";
    public static final String DESCRIPTION = "DESCRIPTION";

    private static final String SQL_CREATE_TABLE_PHOTOS =
            "CREATE TABLE " +  TABLE_PHOTOS + " (" +
                    ID_PHOTO + " INTEGER PRIMARY KEY," +
                    USER_NAME + " TEXT," +
                    PHOTO + " TEXT," +
                    DESCRIPTION + " TEXT)";

    private static final String SQL_DELETE_PHOTOS =
            "DROP TABLE IF EXISTS " + TABLE_PHOTOS;


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_USERS);
        db.execSQL(SQL_CREATE_TABLE_PHOTOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_USERS);
        db.execSQL(SQL_DELETE_PHOTOS);
        onCreate(db);
    }
}
