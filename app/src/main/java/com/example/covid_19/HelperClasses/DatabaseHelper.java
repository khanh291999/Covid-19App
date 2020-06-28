package com.example.covid_19.HelperClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Covid.db";
    public static final String USER_TABLE = "User";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "FULL_NAME";
    public static final String COL_3 = "USERNAME";
    public static final String COL_4 = "EMAIL";
    public static final String COL_5 = "PASSWORD";
    public static final String COL_6 = "ADDRESS";
    public static final String COL_7 = "TYPE";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + USER_TABLE +" (ID INTEGER PRIMARY KEY,FULL_NAME TEXT,USERNAME TEXT,EMAIL INTEGER, PASSWORD TEXT, ADDRESS TEXT, TYPE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+USER_TABLE);
        onCreate(db);
    }

    public boolean insertUser(String username,String email,String password, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_3,username);
        contentValues.put(COL_4,email);
        contentValues.put(COL_5,password);
        contentValues.put(COL_6,address);

        long result = db.insert(USER_TABLE,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+USER_TABLE,null);
        return res;
    }
//
//    public boolean updateUser(String id,String name,String surname,String marks) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(COL_1,id);
//        contentValues.put(COL_2,name);
//        contentValues.put(COL_3,surname);
//        contentValues.put(COL_4,marks);
//        db.update(USER_TABLE, contentValues, "ID = ?",new String[] { id });
//        return true;
//    }
//
//    public Integer deleteData (String id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        return db.delete(USER_TABLE, "ID = ?",new String[] {id});
//    }
}