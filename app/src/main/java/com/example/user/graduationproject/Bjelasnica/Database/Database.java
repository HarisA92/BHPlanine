package com.example.user.graduationproject.Bjelasnica.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.user.graduationproject.Bjelasnica.Database.ReportTable.*;

public class Database extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "Report.db";
    public static final int DATABASE_VERSION = 1;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TABLE = "CREATE TABLE " +
                UserTable.TABLE_NAME + " (" +
                UserTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UserTable.USERNAME + " TEXT NOT NULL, " +
                UserTable.COMMENTBOX + " TEXT NOT NULL, " +
                UserTable.IMAGE + " TEXT NOT NULL, " +
                UserTable.SNOW + " TEXT NOT NULL, " +
                UserTable.SURFACE + " TEXT NOT NULL, " +
                UserTable.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP " +
                ");";
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + UserTable.TABLE_NAME);
        onCreate(db);
    }

    public boolean CheckIsInDBorNot(String name, String commentBox, String image, String snow, String surface) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = " SELECT * FROM " + ReportTable.UserTable.TABLE_NAME + " WHERE " + UserTable.USERNAME + "="
                + "name" + " AND " + UserTable.COMMENTBOX + "=" + "commentBox" + " AND " + UserTable.IMAGE + "="
                + "image" + " AND " + UserTable.SNOW + "=" + "snow" + " AND " + UserTable.SURFACE + "=" + "surface";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public boolean insertDatainDB(String username, String commentBox, String img, String snow, String surface){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserTable.USERNAME, username);
        contentValues.put(UserTable.COMMENTBOX, commentBox);
        contentValues.put(UserTable.IMAGE, String.valueOf(img));
        contentValues.put(UserTable.SNOW, snow);
        contentValues.put(UserTable.SURFACE, surface);
        long result = db.insertWithOnConflict(UserTable.TABLE_NAME,null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        //long result = db.insert(UserTable.TABLE_NAME, null, contentValues);
        if(result == -1) {
            return false;
        }
        else {
            return true;
        }
    }
}
