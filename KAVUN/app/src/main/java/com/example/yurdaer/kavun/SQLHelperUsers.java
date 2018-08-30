package com.example.yurdaer.kavun;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.Serializable;

/**
 * Created by YURDAER on 2017-10-08.
 */

public class SQLHelperUsers extends SQLiteOpenHelper{
    public static final String TABLE_NAME = "Users";
    public static final String COLUMN_ID = "Id";
    public static final String COLUMN_USERS = "Username";
    public static final String COLUMN_PASSWORD = "Password";

    private static final String DATABASE_NAME = "users.dp";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE = "create table " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + COLUMN_USERS + " text not null, " + COLUMN_PASSWORD + " text not null);";


    public SQLHelperUsers(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLiteOpenHelper.class.getName(), "Upgrading database from version " + oldVersion + "to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SQLHelperUsers.COLUMN_USERS, username);
        values.put(SQLHelperUsers.COLUMN_PASSWORD, password);
        db.insert(SQLHelperUsers.TABLE_NAME, "", values);
    }

    public void updateUser(int id, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SQLHelperUsers.COLUMN_ID, id);
        values.put(SQLHelperUsers.COLUMN_USERS, username);
        values.put(SQLHelperUsers.COLUMN_PASSWORD, password);
        db.update(SQLHelperUsers.TABLE_NAME, values, SQLHelperUsers.COLUMN_ID + "=" + id + "", null);
    }

    public boolean checkUserInfo(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = "SELECT * FROM " + SQLHelperUsers.TABLE_NAME + " WHERE " + SQLHelperUsers.COLUMN_USERS + " = ?" + " AND " + SQLHelperUsers.COLUMN_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(selection, new String[]{username, password});
        if ((cursor != null) && cursor.getCount() > 0) {
            return true;
        } else return false;
    }

    public boolean isUserNameExist(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = "SELECT * FROM " + SQLHelperUsers.TABLE_NAME + " WHERE " + SQLHelperUsers.COLUMN_USERS + " = ?";
        Cursor cursor = db.rawQuery(selection, new String[]{username});
        boolean exist = (cursor.getCount() > 0);
        cursor.close();
        return exist;

    }



}
