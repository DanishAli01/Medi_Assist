package com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.SQLiteMain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Login.Login;

public class SQLitePerson extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "SQLitePerson.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "Details";
    public static final String PERSON_COLUMN_ID = "_id";
    public static final String PERSON_COLUMN_NAME = "name";
    public static final String PERSON_COLUMN_EMAIL = "email";
    public static final String PERSON_COLUMN_TOKEN = "token";


    public SQLitePerson(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "(" +
                PERSON_COLUMN_ID + " TEXT, " +
                PERSON_COLUMN_NAME + " TEXT, " +
                PERSON_COLUMN_EMAIL+ " TEXT, " +
                PERSON_COLUMN_TOKEN + " TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertUser(String id, String name, String email, String token) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PERSON_COLUMN_ID, id);
        contentValues.put(PERSON_COLUMN_NAME, name);
        contentValues.put(PERSON_COLUMN_EMAIL, email);
        contentValues.put(PERSON_COLUMN_TOKEN, token);
        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean updateUser(String id, String name, String email, String token) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PERSON_COLUMN_NAME, name);
        contentValues.put(PERSON_COLUMN_ID, id);
        contentValues.put(PERSON_COLUMN_EMAIL, email);
        contentValues.put(PERSON_COLUMN_TOKEN, token);
        db.update(TABLE_NAME, contentValues, PERSON_COLUMN_ID + " = ? ", new String[] { id } );
        return true;
    }

    public Cursor getUser(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME + " WHERE " +
                PERSON_COLUMN_ID + "=?", new String[] { id } );
        return res;
    }
    public Cursor getUserE(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME + " WHERE " +
                PERSON_COLUMN_EMAIL + "=?", new String[] { name } );
        return res;
    }

    public Cursor getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME, null );

        if(res.getCount() > 0){
            res.moveToFirst();
            return res;
        }
      else return null;
    }

    public Integer deletePerson(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,
                PERSON_COLUMN_ID + " = ? ",
                new String[] { (id) });
    }
}
