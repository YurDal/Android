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

public class SQLHelperIncome extends SQLiteOpenHelper {
    public static final String TABLE_NAME = Controller.user + "Income";
    public static final String COLUMN_ID = "Id";
    public static final String COLUMN_DATE = "Date";
    public static final String COLUMN_TITLE = "Title";
    public static final String COLUMN_CATEGORY = "Category";
    public static final String COLUMN_AMOUNT = "Amount";

    private static final String DATABASE_NAME = Controller.user + "Inc.dp";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + COLUMN_DATE + " DATE NOT NULL, " + COLUMN_TITLE + " text NOT NULL, " + COLUMN_CATEGORY + " text NOT NULL, " + COLUMN_AMOUNT + " int NOT NULL);";

    public SQLHelperIncome(Context context, String userName) {
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

    public int getTotalIncome() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_AMOUNT + ") FROM " + TABLE_NAME, null);
        cursor.moveToFirst();
        int i = cursor.getInt(0);
        cursor.close();
        return i;
    }

    public int getTotalSalary() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_AMOUNT + ") FROM " + TABLE_NAME + " WHERE " + COLUMN_CATEGORY + " = ?;", new String[]{"Salary"});
        cursor.moveToFirst();
        int i = cursor.getInt(0);
        cursor.close();
        return i;
    }


    public int getTotalInterest() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_AMOUNT + ") FROM " + TABLE_NAME + " WHERE " + COLUMN_CATEGORY + " = ?;", new String[]{"Interest"});
        cursor.moveToFirst();
        int i = cursor.getInt(0);
        cursor.close();
        return i;
    }

    public int getTotalExchange() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_AMOUNT + ") FROM " + TABLE_NAME + " WHERE " + COLUMN_CATEGORY + " = ?;", new String[]{"Exchange"});
        cursor.moveToFirst();
        int i = cursor.getInt(0);
        cursor.close();
        return i;
    }

    public int getTotalOther() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_AMOUNT + ") FROM " + TABLE_NAME + " WHERE " + COLUMN_CATEGORY + " = ?;", new String[]{"Other"});
        cursor.moveToFirst();
        int i = cursor.getInt(0);
        cursor.close();
        return i;
    }


    public void addIncome(String date, String tittle, String category, int amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DATE, date);
        contentValues.put(COLUMN_TITLE, tittle);
        contentValues.put(COLUMN_CATEGORY, category);
        contentValues.put(COLUMN_AMOUNT, amount);
        db.insert(TABLE_NAME, null, contentValues);
    }

    public void updateIncome(int id, String date, String tittle, String category, int amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DATE, date);
        contentValues.put(COLUMN_TITLE, tittle);
        contentValues.put(COLUMN_CATEGORY, category);
        contentValues.put(COLUMN_AMOUNT, amount);
        db.update(TABLE_NAME, contentValues, COLUMN_ID + " = ?", new String[]{Integer.toString(id)});
    }

    public Income[] getAllIncome() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY DATE(" + COLUMN_DATE + ") DESC", null);
        Income[] incomes = new Income[cursor.getCount()];
        cursor.moveToFirst();
        int index = 0;
        while (!cursor.isAfterLast()) {
            incomes[index] = new Income(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)), cursor.getString(cursor.getColumnIndex(COLUMN_DATE)), cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)), cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY)), cursor.getInt(cursor.getColumnIndex(COLUMN_AMOUNT)));
            cursor.moveToNext();
            index++;
        }
        return incomes;
    }

    public Income[] getAllIncomeBetween(String start, String finish) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE DATE ("+ COLUMN_DATE +  ") BETWEEN ? AND ?" + " ORDER BY DATE(" + COLUMN_DATE + ") DESC", new String[]{start, finish});
        Income[] incomes = new Income[cursor.getCount()];
        cursor.moveToFirst();
        int index = 0;
        while (!cursor.isAfterLast()) {
            incomes[index] = new Income(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)), cursor.getString(cursor.getColumnIndex(COLUMN_DATE)), cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)), cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY)), cursor.getInt(cursor.getColumnIndex(COLUMN_AMOUNT)));
            cursor.moveToNext();
            index++;
        }
        return incomes;
    }


}
