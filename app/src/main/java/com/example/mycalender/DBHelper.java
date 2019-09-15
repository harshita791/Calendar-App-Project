package com.example.mycalender;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Mydatabase.db";
    public static final String TABLE_NAME = "eventcalender";
    public static final String COL_1 = "Id";
    public static final String COL_2 = "Date";
    public static final String COL_3 = "Event";
    public static final String COL_4 = "EventType";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        //SQLiteDatabase db=this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(Id integer primary key autoincrement,Date text,Event text,EventType text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertdata(String Date, String Event,String EventType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Date", Date);
        contentValues.put("Event", Event);
        contentValues.put("EventType", EventType);
        db.insert(TABLE_NAME, null, contentValues);
        return true;

    }

    public Cursor getdata() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res =db.rawQuery("select * from eventcalender",null);
        return res;
    }

    public Cursor getAlldata(String Date){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res =db.rawQuery("select * from eventcalender where Date is "+Date+"",null);
        return res;
    }

}
