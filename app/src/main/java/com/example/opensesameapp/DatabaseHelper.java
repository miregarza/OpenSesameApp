package com.example.opensesameapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.SimpleCursorAdapter;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "userlist.db";
    public static final String TABLE_NAME = "listTable";
    public static final String COL1 = "ID";
    public static final String COL2 = "PLATFORM";
    public static final String COL3 = "PASS";




    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, PLATFORM TEXT, PASS TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP IF TABLE EXISTS" + TABLE_NAME);
    }

    public boolean addData (String item1, String item2){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, item1);
        contentValues.put(COL3,item2);
        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public void deleteData (String PlatformText){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + COL2 + " = \'" + PlatformText + "\'"); //DELETES FROM SPECIFIED PLATFORM STRING
    }

    public Cursor getListContents(){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME,null);
        return data;
    }



}
