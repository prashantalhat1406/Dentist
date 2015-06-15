package com.example.prashant.dentist;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class editAppointment extends ActionBarActivity {

    private String aidToEdit;
    EditText  dat, tim;
    private Calendar cal;
    int y, m, d,h,mi;
    Spinner sP;
    boolean currentDateFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_appointment);

        Intent i = getIntent();
        aidToEdit = i.getExtras().getString("aid");

        cal = Calendar.getInstance();
        y=cal.get(Calendar.YEAR);
        m=cal.get(Calendar.MONTH);
        d=cal.get(Calendar.DAY_OF_MONTH);
        h=cal.get(Calendar.HOUR_OF_DAY);
        mi=cal.get(Calendar.MINUTE);


        appointmentDatabaseHandler adb = new appointmentDatabaseHandler(this);
        appointmentInformation ai = adb.getAppointmentInfoByID(Integer.parseInt(aidToEdit));

        patientDatabaseHandler pdb = new patientDatabaseHandler(this);
        patientInformation pi = pdb.getPatientInfoByID(ai.getPID());

        TextView name = (TextView)findViewById(R.id.txtEAPatientName);
        dat = (EditText)findViewById(R.id.txtEADate);
        tim = (EditText)findViewById(R.id.txtEATime);
        //EditText pt = (EditText)findViewById(R.id.txtEApt);
        EditText td = (EditText)findViewById(R.id.txtEAtd);

        sP=(Spinner)findViewById(R.id.spnEAProposedTreatment);
        ArrayAdapter<CharSequence> adapterpt = ArrayAdapter.createFromResource(this,R.array.ProposedTreatment, android.R.layout.simple_spinner_item);
        sP.setAdapter(adapterpt);


        sP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        name.setText(pi.getName());
        dat.setText(ai.getaDate());
        tim.setText(ai.getaTime());
        //pt.setText(ai.getProposedTreatment());
        int spinerValue = adapterpt.getPosition(ai.getProposedTreatment());
        sP.setSelection(spinerValue);
        spinerValue=0;
        td.setText(ai.getToothDetails());

        Button saveButton = (Button) findViewById(R.id.butEAEditAppointment);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAppointmentDetailsToDatabase();
            }
        });

        Button dDialog = (Button)findViewById(R.id.showEADateDialog);
        dDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {showDialog(0);
            }
        });

        Button tDialog = (Button)findViewById(R.id.showEATimeDialog);
        tDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {showDialog(1);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id==0){
            return new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    //e.setText(dayOfMonth+"/" + (monthOfYear+1) + "/" + year);

                    /*
                    try {
                        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                        Date dObj = df.parse(dayOfMonth+"/" + (monthOfYear+1) + "/" + year);
                        Date cDate = new Date();
                        if (dObj.compareTo(cDate)>=0) {
                            Calendar myCal = Calendar.getInstance();
                            myCal.setTime(dObj);
                            dat.setText(df.format(myCal.getTime()));
                        }else{
                            Toast.makeText(getApplicationContext(), "Entered Date should not be past date", Toast.LENGTH_SHORT).show();
                            dat.setText("");
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }*/
                    try {
                        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                        Date dObj = df.parse(dayOfMonth+"/" + (monthOfYear+1) + "/" + year);
                        /*Date cDate = new Date();
                        cDate.setHours(0);
                        cDate.setMinutes(0);
                        cDate.setSeconds(0);*/

                        Calendar tempCal = Calendar.getInstance();
                        //tempCal.setTime(new Date());
                        tempCal.set(Calendar.HOUR_OF_DAY, 0);
                        tempCal.set(Calendar.MINUTE,0);
                        tempCal.set(Calendar.SECOND,0);
                        tempCal.set(Calendar.MILLISECOND, 0);
                        //tempCal.add(Calendar.DATE, -1);

                        if (!dObj.before(tempCal.getTime())) {
                            //tempCal.add(Calendar.DATE, 1);
                            currentDateFlag = dObj.equals(tempCal.getTime());
                            Calendar myCal = Calendar.getInstance();
                            myCal.setTime(dObj);
                            dat.setText(df.format(myCal.getTime()));
                        }else{
                            Toast.makeText(getApplicationContext(), "Entered Date should not be past date", Toast.LENGTH_SHORT).show();
                            dat.setText("");
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            },y,m,d);
        }
        else {
            return new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    /*try{
                        Calendar timeCheck = Calendar.getInstance();
                        timeCheck.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        timeCheck.set(Calendar.MINUTE, minute);
                        if(Calendar.getInstance().after(timeCheck)) {
                            Toast.makeText(getApplicationContext(), "Entered Time should be after current Time", Toast.LENGTH_SHORT).show();
                            tim.setText("");
                        }else{
                            tim.setText(hourOfDay + "::" + minute);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }*/
                    try{
                        if(hourOfDay >= 10 && hourOfDay <= 20) {
                            Calendar timeCheck = Calendar.getInstance();
                            timeCheck.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            timeCheck.set(Calendar.MINUTE, minute);

                            if (dat.getText().length() == 0) {
                                Toast.makeText(getApplicationContext(), "Please enter Date first", Toast.LENGTH_SHORT).show();
                                tim.setText("");
                            } else {
                                if (currentDateFlag) {
                                    if (Calendar.getInstance().after(timeCheck)) {
                                        Toast.makeText(getApplicationContext(), "Entered Time should be after current Time", Toast.LENGTH_SHORT).show();
                                        tim.setText("");
                                    } else {
                                        if(hourOfDay<=12){
                                            tim.setText(hourOfDay + ":" + minute);
                                        }
                                        if(hourOfDay>12){
                                            hourOfDay=hourOfDay-12;
                                            tim.setText(hourOfDay + ":" + minute);
                                        }
                                    }
                                } else{
                                    if(hourOfDay<=12){
                                        tim.setText(hourOfDay + ":" + minute);
                                    }
                                    if(hourOfDay>12){
                                        hourOfDay=hourOfDay-12;
                                        tim.setText(hourOfDay + ":" + minute);
                                    }

                                }

                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Clinic working hours 10am-9pm", Toast.LENGTH_SHORT).show();
                            tim.setText("");
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }
            },h,mi,false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_appointment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    public void saveAppointmentDetailsToDatabase(){
        appointmentDatabaseHandler adb = new appointmentDatabaseHandler(this);
        appointmentInformation ai = adb.getAppointmentInfoByID(Integer.parseInt(aidToEdit));

        dat = (EditText)findViewById(R.id.txtEADate);
        tim = (EditText)findViewById(R.id.txtEATime);
        //EditText pt = (EditText)findViewById(R.id.txtEApt);
        Spinner pt = (Spinner)findViewById(R.id.spnEAProposedTreatment);
        EditText td = (EditText)findViewById(R.id.txtEAtd);


        if(dat.getText().length() == 0){
            Toast.makeText(getApplicationContext(), "Enter Date", Toast.LENGTH_SHORT).show();
        }else{
            if(tim.getText().length() ==0 ){
                Toast.makeText(getApplicationContext(), "Enter Time", Toast.LENGTH_SHORT).show();
            }else{
                /*if (pt.getText().length() == 0 ){
                    Toast.makeText(getApplicationContext(), "Enter Proposed Treatment", Toast.LENGTH_SHORT).show();
                }else{*/
                    if(td.getText().length() == 0 ){
                        Toast.makeText(getApplicationContext(), "Enter ToothDetails", Toast.LENGTH_SHORT).show();
                    }else{
                        ai = new appointmentInformation(ai.getAID(),ai.getPID(),dat.getText().toString(),tim.getText().toString(),pt.getSelectedItem().toString(),td.getText().toString());
                        adb.updateAppointmentInfo(ai);
                        Toast.makeText(getApplicationContext(), "Record Edited", Toast.LENGTH_SHORT).show();
                        this.finish();
                    }
                /*}*/
            }

        }
    }
}
