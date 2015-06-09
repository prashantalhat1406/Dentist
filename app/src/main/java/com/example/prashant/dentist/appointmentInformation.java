package com.example.prashant.dentist;

import java.sql.Time;
import java.util.Date;

/**
 * Created by sinprl on 6/4/2015.
 */
public class appointmentInformation {

    int _aid;
    int _pid;
    String _adate;
    String _atime;
    String _status;
    int _payment;
    String _proposedTreatment;
    String _actualTreatment;
    String _toothDetails;

    public appointmentInformation(){}

    public appointmentInformation(int aid, int pid, String date, String time, String status, int payment, String proposedTreatment, String actualTreatment, String toothDetails){
        this._aid=aid;
        this._pid=pid;
        this._adate=date;
        this._atime=time;
        this._status=status;
        this._payment=payment;
        this._proposedTreatment=proposedTreatment;
        this._actualTreatment=actualTreatment;
        this._toothDetails=toothDetails;
    }
//use this constructor to create new appointment .. payment details are not added here
    public appointmentInformation(int aid, int pid, String date, String time, String proposedTreatment, String toothDetails){
        this._aid=aid;
        this._pid=pid;
        this._adate=date;
        this._atime=time;
        this._proposedTreatment=proposedTreatment;
        this._toothDetails=toothDetails;
    }

    //use this constructor to add any payment details for patient
    public appointmentInformation(int aid, int pid, String date, String time, String actualTreatment, int payment){
        this._aid=aid;
        this._pid=pid;
        this._adate=date;
        this._atime=time;
        this._actualTreatment=actualTreatment;
        this._payment=payment;
    }

    public int getAID(){return this._aid;}
    public void setAID(int aid){this._aid=aid;}

    public int getPID(){return this._pid;}
    public void setPID(int pid){this._pid=pid;}

    public String getaDate(){return this._adate;}
    public void getaDate(String date){this._adate=date;}

    public String getaTime(){return this._atime;}
    public void setaTime(String time){this._atime=time;}

    public String getStatus(){return this._status;}
    public void setPID(String status){this._status=status;}

    public int getPayment(){return this._payment;}
    public void setPayment(int payment){this._payment=payment;}

    public String getProposedTreatment(){return this._proposedTreatment;}
    public void setProposedTreatment(String proposedTreatment){this._proposedTreatment=proposedTreatment;}

    public String getActualTreatment(){return this._actualTreatment;}
    public void setActualTreatment(String actualTreatment){this._actualTreatment=actualTreatment;}

    public String getToothDetails(){return this._toothDetails;}
    public void setToothDetails(String toothDetails){this._toothDetails=toothDetails;}
}
