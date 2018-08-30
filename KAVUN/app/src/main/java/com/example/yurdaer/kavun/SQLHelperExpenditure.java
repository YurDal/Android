package com.example.yurdaer.kavun;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by YURDAER on 2017-10-08.
 */

public class SQLHelperExpenditure extends SQLiteOpenHelper implements Serializable {
    public static final String TABLE_NAME = Controller.user + "Expenditure";
    public static final String COLUMN_ID = "Id";
    public static final String COLUMN_DATE = "Date";
    public static final String COLUMN_TITLE = "Title";
    public static final String COLUMN_CATEGORY = "Category";
    public static final String COLUMN_AMOUNT = "Amount";

    private static final String DATABASE_NAME = Controller.user + "Exp.dp";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + COLUMN_DATE + " DATE NOT NULL, " + COLUMN_TITLE + " text NOT NULL, " + COLUMN_CATEGORY + " text NOT NULL, " + COLUMN_AMOUNT + " int NOT NULL);";

    public SQLHelperExpenditure(Context context, String userName) {
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

    public int getTotalExpence() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM (" + COLUMN_AMOUNT + ") FROM " + TABLE_NAME, null);
        cursor.moveToFirst();
        int i = cursor.getInt(0);
        cursor.close();
        return i;
    }

    public int getTotalFood() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_AMOUNT + ") FROM " + TABLE_NAME + " WHERE " + COLUMN_CATEGORY + " = ?;", new String[]{"Food"});
        cursor.moveToFirst();
        int i = cursor.getInt(0);
        cursor.close();
        return i;
    }

    public int getTotalLeisure() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_AMOUNT + ") FROM " + TABLE_NAME + " WHERE " + COLUMN_CATEGORY + " = ?;", new String[]{"Leisure"});
        cursor.moveToFirst();
        int i = cursor.getInt(0);
        cursor.close();
        return i;
    }

    public int getTotalTravel() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_AMOUNT + ") FROM " + TABLE_NAME + " WHERE " + COLUMN_CATEGORY + " = ?;", new String[]{"Travel"});
        cursor.moveToFirst();
        int i = cursor.getInt(0);
        cursor.close();
        return i;
    }

    public int getTotalAcommodation() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_AMOUNT + ") FROM " + TABLE_NAME + " WHERE " + COLUMN_CATEGORY + " = ?;", new String[]{"Accommodation"});
        cursor.moveToFirst();
        int i = cursor.getInt(0);
        cursor.close();
        return i;
    }

    public int getTotalOther() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_AMOUNT + ") FROM " + TABLE_NAME + " WHERE " + COLUMN_CATEGORY + " = ?", new String[]{"Other"});
        cursor.moveToFirst();
        int i = cursor.getInt(0);
        cursor.close();
        return i;
    }

    public void addExpence(String date, String tittle, String category, int amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DATE, date);
        contentValues.put(COLUMN_TITLE, tittle);
        contentValues.put(COLUMN_CATEGORY, category);
        contentValues.put(COLUMN_AMOUNT, amount);
        db.insert(TABLE_NAME, null, contentValues);
    }

    public void updateExpence(int id, String date, String tittle, String category, int amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DATE, date);
        contentValues.put(COLUMN_TITLE, tittle);
        contentValues.put(COLUMN_CATEGORY, category);
        contentValues.put(COLUMN_AMOUNT, amount);
        db.update(TABLE_NAME, contentValues, COLUMN_ID + " = ?", new String[]{Integer.toString(id)});
    }

    public Expenditure[] getAllExpenditure() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY  DATE ("+ COLUMN_DATE + ") DESC", null);
        Expenditure[] expenditures = new Expenditure[cursor.getCount()];
        cursor.moveToFirst();
        int index = 0;
        while (!cursor.isAfterLast()) {
            expenditures[index] = new Expenditure(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)), cursor.getString(cursor.getColumnIndex(COLUMN_DATE)), cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)), cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY)), cursor.getInt(cursor.getColumnIndex(COLUMN_AMOUNT)));
            cursor.moveToNext();
            index++;
        }
        return expenditures;
    }

    public Expenditure[] getAllExpenditureBetween(String start, String finish) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE DATE ("+ COLUMN_DATE +  ") BETWEEN ? AND ?" + " ORDER BY DATE(" + COLUMN_DATE + ") DESC", new String[]{start, finish});
        Expenditure[] expenditures = new Expenditure[cursor.getCount()];
        cursor.moveToFirst();
        int index = 0;
        while (!cursor.isAfterLast()) {
            expenditures[index] = new Expenditure(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)), cursor.getString(cursor.getColumnIndex(COLUMN_DATE)), cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)), cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY)), cursor.getInt(cursor.getColumnIndex(COLUMN_AMOUNT)));
            cursor.moveToNext();
            index++;
        }
        return expenditures;
    }

}
