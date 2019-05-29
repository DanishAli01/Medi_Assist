package com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *
 * @version 1.0
 *
 * Class to access data from databse
 */
public class LoginLocalDAO extends SQLiteOpenHelper {

    private static final String TRAVELLER_DATABASE_NAME = "Traveller.db";
    private final String TRAVELLER_TABLE_NAME = "Login";
    private final String LOGIN_COLUMN_ID = "id";
    private final String LOGIN_COLUMN_USERNAME = "username";
    private final String LOGIN_COLUMN_TOKEN = "token";
    private final String LOGIN_COLUMN_EMAIL = "email";

    private final String LOGIN_CREATE_TABLE = "create table " + TRAVELLER_TABLE_NAME +"(" +
                                                LOGIN_COLUMN_ID + " text primary key, " +
                                                LOGIN_COLUMN_USERNAME + " text, " +
                                                LOGIN_COLUMN_TOKEN + " text, " +
            LOGIN_COLUMN_EMAIL + " text" +
                                                ")";
    private final String LOGIN_DROP_TABLE = "drop table " + TRAVELLER_TABLE_NAME;

    private final SQLiteDatabase getWritableDB = this.getWritableDatabase();
    private final SQLiteDatabase getReadableDB = this.getReadableDatabase();
    private ContentValues contentValues = new ContentValues();

    /**
     * Constructor
     * @param context
     */
    public LoginLocalDAO(Context context){
        super(context, TRAVELLER_DATABASE_NAME, null, 1);
        createTableIfNotExist();
    }

    /**
     * Method to create login table on databse creation
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(LOGIN_CREATE_TABLE);
    }

    /**
     * Method to be called when database upgraded
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(isTableExist()) {
            db.execSQL(LOGIN_DROP_TABLE);
            db.execSQL(LOGIN_CREATE_TABLE);
        }
    }

    /**
     * Method to add login information in device database
     * @param login
     * @return boolean
     */
    public boolean insertLogin(Login login) {
        createTableIfNotExist();
//        Login loginExists = getLogin();

        try{
//            if(loginExists == null){
                contentValues.put(LOGIN_COLUMN_ID, login.getId());
                contentValues.put(LOGIN_COLUMN_USERNAME, login.getUsername());
                contentValues.put(LOGIN_COLUMN_TOKEN, login.getToken());
                contentValues.put(LOGIN_COLUMN_EMAIL, login.getEmail());
                getWritableDB.insert(TRAVELLER_TABLE_NAME, null, contentValues);
//            } else {
//                resetTable();
//                contentValues.put(LOGIN_COLUMN_ID, login.getId());
//                contentValues.put(LOGIN_COLUMN_USERNAME, login.getUsername());
//                contentValues.put(LOGIN_COLUMN_TOKEN, login.getToken());
//                contentValues.put(LOGIN_COLUMN_EMAIL, login.getEmail());
//                getWritableDB.insert(TRAVELLER_TABLE_NAME, null, contentValues);
//            }
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Method to retrieve login information of current logged in user.
     * @return Login Object
     */
    public Login getLogin() {
        Login login = null;
        Cursor cursor = getReadableDB.rawQuery("SELECT * FROM " + TRAVELLER_TABLE_NAME, null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            login = new Login(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
        }

        return login;
    }

    /**
     * Method to update login information in database
     * @param login
     * @return boolean
     */
    public boolean updateLogin(Login login) {
        contentValues.put(LOGIN_COLUMN_ID, login.getId());
        contentValues.put(LOGIN_COLUMN_USERNAME, login.getUsername());
        contentValues.put(LOGIN_COLUMN_TOKEN, login.getToken());
        getWritableDB.update(TRAVELLER_TABLE_NAME, contentValues, "identity = ? ", new String[] { login.getId() } );
        return true;
    }

    /**
     * Method to delete login information from database
     * @param login
     * @return boolean
     */
    public boolean deleteLogin(Login login) {
        getWritableDB.delete(TRAVELLER_TABLE_NAME, "identity = ? ", new String[] { login.getId() });
        return true;
    }

    private void createTableIfNotExist(){
        if(!isTableExist()){
            getWritableDB.execSQL(LOGIN_CREATE_TABLE);
        }
    }

    private boolean isTableExist(){
        Cursor cursor = getReadableDB.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+TRAVELLER_TABLE_NAME+"'", null);
        if(cursor!=null) {
            if(cursor.getCount()>0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    public void resetTable(){
        getWritableDB.execSQL("delete from "+TRAVELLER_TABLE_NAME);
    }

    /**
     * Method to reset databse
     * @return boolean
     */
    public boolean resetDb(){
        onUpgrade(getReadableDB, 0, 0);
        return true;
    }

}
