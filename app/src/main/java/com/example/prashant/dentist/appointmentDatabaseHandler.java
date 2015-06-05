package com.example.prashant.dentist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prashant on 6/3/2015.
 */
public class appointmentDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="appointmentManager";
    private static final String TABLE_NAME ="apptInfo";

    private static final String KEY_AID = "aid";
    private static final String KEY_PID = "pid";
    private static final String KEY_ADATE = "adate";
    private static final String KEY_ATIME = "atime";
    private static final String KEY_STATUS = "status";
    private static final String KEY_PAYMENT = "payment";
    private static final String KEY_PROPOSEDTREATMENT = "proposedTreatment";
    private static final String KEY_ACTUALTREATMENT = "actualTreatment";
    private static final String KEY_TOOTHDETAILS = "toothDetails";

    public appointmentDatabaseHandler(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PATIENT_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_AID + " INTEGER PRIMARY KEY,"
                + KEY_PID + " TEXT,"
                + KEY_ADATE + " TEXT,"
                + KEY_ATIME+ " TEXT,"
                + KEY_STATUS + " TEXT,"
                + KEY_PAYMENT + " INTEGER,"
                + KEY_PROPOSEDTREATMENT + " TEXT,"
                + KEY_ACTUALTREATMENT + " TEXT,"
                + KEY_TOOTHDETAILS + " TEXT"
                + ")";

        db.execSQL(CREATE_PATIENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addAppointmentInfo(appointmentInformation ai){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_AID,ai.getAID());
        values.put(KEY_PID,ai.getPID());
        values.put(KEY_ADATE,ai.getaDate());
        values.put(KEY_ATIME,ai.getaTime());
        values.put(KEY_STATUS,ai.getStatus());
        values.put(KEY_PAYMENT,ai.getPayment());
        values.put(KEY_PROPOSEDTREATMENT,ai.getProposedTreatment());
        values.put(KEY_ACTUALTREATMENT,ai.getActualTreatment());
        values.put(KEY_TOOTHDETAILS,ai.getToothDetails());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public appointmentInformation getAppointmentInfoByID(int aid){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_AID,KEY_PID,KEY_ADATE,KEY_ATIME,KEY_STATUS,KEY_PAYMENT,KEY_PROPOSEDTREATMENT,KEY_ACTUALTREATMENT,KEY_TOOTHDETAILS}, KEY_AID + "=?", new String[]{String.valueOf(aid)},null,null,null,null );
        if (cursor != null)
            cursor.moveToFirst();
        appointmentInformation ai = new appointmentInformation(Integer.parseInt(cursor.getString(0)),Integer.parseInt(cursor.getString(1)),cursor.getString(2),cursor.getString(3),cursor.getString(4),Integer.parseInt(cursor.getString(5)),cursor.getString(6),cursor.getString(7),cursor.getString(8));
        cursor.close();
        db.close();
        return  ai;
    }

    public List<appointmentInformation> getAppointmentInfoByPaitentID(int pid){
        List<appointmentInformation> appointmentList = new ArrayList<appointmentInformation>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_AID,KEY_PID,KEY_ADATE,KEY_ATIME,KEY_STATUS,KEY_PAYMENT,KEY_PROPOSEDTREATMENT,KEY_ACTUALTREATMENT,KEY_TOOTHDETAILS}, KEY_PID + "=?", new String[]{String.valueOf(pid)},null,null,null,null );

        if(cursor.moveToFirst()) {
            do {
                appointmentInformation ai = new appointmentInformation(Integer.parseInt(cursor.getString(0)),Integer.parseInt(cursor.getString(1)),cursor.getString(2),cursor.getString(3),cursor.getString(4),Integer.parseInt(cursor.getString(5)),cursor.getString(6),cursor.getString(7),cursor.getString(8));
                appointmentList.add(ai);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return  appointmentList;
    }

    public List<appointmentInformation> getAppointmentInfoByDate(String currentDate){
        List<appointmentInformation> appointmentList = new ArrayList<appointmentInformation>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_AID,KEY_PID,KEY_ADATE,KEY_ATIME,KEY_STATUS,KEY_PAYMENT,KEY_PROPOSEDTREATMENT,KEY_ACTUALTREATMENT,KEY_TOOTHDETAILS}, KEY_ADATE + " LIKE ? ", new String[]{"%" + currentDate + "%"},null,null,null,null );

        if(cursor.moveToFirst()) {
            do {
                appointmentInformation ai = new appointmentInformation(Integer.parseInt(cursor.getString(0)),Integer.parseInt(cursor.getString(1)),cursor.getString(2),cursor.getString(3),cursor.getString(4),Integer.parseInt(cursor.getString(5)),cursor.getString(6),cursor.getString(7),cursor.getString(8));
                appointmentList.add(ai);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return  appointmentList;
    }

    public List<appointmentInformation> getAllAppointmentInfo(){
        List<appointmentInformation> appointmentList = new ArrayList<appointmentInformation>();

        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            do {
                appointmentInformation ai = new appointmentInformation(Integer.parseInt(cursor.getString(0)),Integer.parseInt(cursor.getString(1)),cursor.getString(2),cursor.getString(3),cursor.getString(4),Integer.parseInt(cursor.getString(5)),cursor.getString(6),cursor.getString(7),cursor.getString(8));
                appointmentList.add(ai);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return  appointmentList;
    }

    public int getAppointmentCount(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int rowCount = cursor.getCount();
        cursor.close();
        db.close();

        return rowCount ;
    }

    public int updateAppointmentInfo(appointmentInformation ai){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_AID,ai.getAID());
        values.put(KEY_PID,ai.getPID());
        values.put(KEY_ADATE,ai.getaDate());
        values.put(KEY_ATIME,ai.getaTime());
        values.put(KEY_STATUS,ai.getStatus());
        values.put(KEY_PAYMENT,ai.getPayment());
        values.put(KEY_PROPOSEDTREATMENT,ai.getProposedTreatment());
        values.put(KEY_ACTUALTREATMENT,ai.getActualTreatment());
        values.put(KEY_TOOTHDETAILS, ai.getToothDetails());

        int returnCode =db.update(TABLE_NAME, values, KEY_AID + "=?", new String[]{String.valueOf(ai.getAID())});

        db.close();

        return returnCode;
    }

    public void  deleteAppointmentInfo(int aid){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_AID + "=?", new String[] {String.valueOf(aid)});
        db.close();
    }

    public int getLastAppointmentID(){
        int lastAID=0;
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.getCount() != 0) {
                cursor.moveToLast();
                lastAID = Integer.parseInt(cursor.getString(0));
                cursor.close();
                db.close();
            } else {
                lastAID = 0;
            }
        }
        catch(Exception e){e.printStackTrace();
        }

        return  lastAID;
    }
}
