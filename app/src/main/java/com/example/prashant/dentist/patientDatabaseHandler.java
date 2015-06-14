package com.example.prashant.dentist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sinprl on 6/2/2015.
 */
public class patientDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="clinicManager";
    private static final String TABLE_NAME ="patientInfo";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_SEX = "sex";
    private static final String KEY_AGE = "age";

    public patientDatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PATIENT_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_PHONE + " TEXT,"
                + KEY_ADDRESS + " TEXT,"
                + KEY_SEX + " TEXT,"
                + KEY_AGE + " TEXT"
                + ")";

        db.execSQL(CREATE_PATIENT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addPatientInfo(patientInformation pi)    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID,pi.getID());
        values.put(KEY_NAME,pi.getName());
        values.put(KEY_PHONE,pi.getPhone());
        values.put(KEY_ADDRESS,pi.getAddress());
        values.put(KEY_SEX,pi.getSex());
        values.put(KEY_AGE,pi.getAge());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public patientInformation getPatientInfoByID(int id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID,KEY_NAME,KEY_PHONE,KEY_ADDRESS,KEY_SEX,KEY_AGE}, KEY_ID + "=?", new String[]{String.valueOf(id)},null,null,null,null );
        if (cursor != null)
            cursor.moveToFirst();
        patientInformation pi = new patientInformation(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
        cursor.close();
        db.close();
        return  pi;
    }

    public List<patientInformation> getPatientInfoByName(String name){
        List<patientInformation> patientList = new ArrayList<patientInformation>();


        //Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID, KEY_NAME, KEY_PHONE, KEY_ADDRESS, KEY_SEX, KEY_AGE}, KEY_NAME + " LIKE ? ", new String[]{"%" + name + "%"}, null, null, null, null);

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_NAME + " LIKE '%" + name + "%' ORDER BY " + KEY_NAME + " ASC" ;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);


        if(cursor.moveToFirst()){
            do{
                patientInformation pi = new patientInformation();
                pi.setID(Integer.parseInt(cursor.getString(0)));
                pi.setName(cursor.getString(1));
                pi.setPhone(cursor.getString(2));
                pi.setAddress(cursor.getString(3));
                pi.setSex(cursor.getString(4));
                pi.setAge(cursor.getString(5));
                patientList.add(pi);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return  patientList;
    }

    public List<patientInformation> getAllPatientInfo(){
        List<patientInformation> patientList = new ArrayList<patientInformation>();

        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + KEY_NAME + " ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                patientInformation pi = new patientInformation();
                pi.setID(Integer.parseInt(cursor.getString(0)));
                pi.setName(cursor.getString(1));
                pi.setPhone(cursor.getString(2));
                pi.setAddress(cursor.getString(3));
                pi.setSex(cursor.getString(4));
                pi.setAge(cursor.getString(5));
                patientList.add(pi);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return patientList;
    }

    public int getPatientCount(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int rowCount = cursor.getCount();
        cursor.close();
        db.close();

        return rowCount ;
    }

    public int updatePatientInfo(patientInformation pi){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID,pi.getID());
        values.put(KEY_NAME,pi.getName());
        values.put(KEY_PHONE,pi.getPhone());
        values.put(KEY_ADDRESS,pi.getAddress());
        values.put(KEY_SEX, pi.getSex());
        values.put(KEY_AGE, pi.getAge());

        int returnCode =db.update(TABLE_NAME, values, KEY_ID + "=?", new String[]{String.valueOf(pi.getID())});

        db.close();

        return returnCode;
    }

    public void  deletePatientInfo(int patientID){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + "=?", new String[] {String.valueOf(patientID)});
        db.close();
    }

    public int getLastRecordID(){
        int lastRecordID;
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.getCount() != 0) {
            cursor.moveToLast();
            lastRecordID = Integer.parseInt(cursor.getString(0));
            cursor.close();
            db.close();
        }else{
            lastRecordID = 0;
        }

        return  lastRecordID;
    }
}


