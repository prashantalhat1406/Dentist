package com.example.prashant.dentist;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class addNewAppointment extends ActionBarActivity {

    private Calendar cal;
    int y, m, d,h,mi;
    Button bd,bt;
    EditText e,t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_appointment);
        populatePatientNameForAutoComplete();

        Button addButton = (Button)findViewById(R.id.butAddAppointment);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {addAppointmentToDatabase();}
        });

        cal = Calendar.getInstance();
        y=cal.get(Calendar.YEAR);
        m=cal.get(Calendar.MONTH);
        d=cal.get(Calendar.DAY_OF_MONTH);
        h=cal.get(Calendar.HOUR_OF_DAY);
        mi=cal.get(Calendar.MINUTE);

        e= (EditText)findViewById(R.id.txtDate);
        t=(EditText)findViewById(R.id.txtTime);
        bd= (Button)findViewById(R.id.showDateDialog);

        bd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {showDialog(0);
            }
        });

        bt = (Button)findViewById(R.id.showTimeDialog);
        bt.setOnClickListener(new View.OnClickListener() {
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
                    try {
                        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                        Date dObj = df.parse(dayOfMonth+"/" + (monthOfYear+1) + "/" + year);
                        Calendar myCal = Calendar.getInstance();
                        myCal.setTime(dObj);
                        e.setText(df.format(myCal.getTime()));
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
                    t.setText(hourOfDay + "::" + minute);
                }
            },h,mi,false);
        }
    }

    public void populatePatientNameForAutoComplete(){
        patientDatabaseHandler pdb = new patientDatabaseHandler(this);
        List<patientInformation> patientList = pdb.getAllPatientInfo();

        String [] patientNames = new String[pdb.getPatientCount()];
        int i=0;

        for(patientInformation pi : patientList){
            patientNames[i]=pi.getName() + "-" + pi.getID() ;
            i=i+1;
        }

        AutoCompleteTextView actv = (AutoCompleteTextView)findViewById(R.id.autotxtPatient);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,patientNames);
        actv.setAdapter(adapter);
    }


    public void addAppointmentToDatabase()
    {
        EditText name = (EditText)findViewById(R.id.autotxtPatient);
        EditText dat = (EditText)findViewById(R.id.txtDate);
        EditText tim = (EditText)findViewById(R.id.txtTime);
        EditText pt = (EditText)findViewById(R.id.txtpt);
        EditText td = (EditText)findViewById(R.id.txttd);

        String pid = name.getText().toString().split("-")[1];

        //patientDatabaseHandler pdb = new patientDatabaseHandler(this);
        //patientInformation pi = pdb.getPatientInfoByID(Integer.parseInt(i));
        appointmentDatabaseHandler adb;
        int lastApptID=1;

        try {
            adb = new appointmentDatabaseHandler(this);
            lastApptID = adb.getLastAppointmentID()+1;
            appointmentInformation ai = new appointmentInformation(lastApptID,Integer.parseInt(pid),dat.getText().toString(),tim.getText().toString(),pt.getText().toString(),td.getText().toString());
            adb.addAppointmentInfo(ai);
            adb.close();
            Toast.makeText(getApplicationContext(), "Record Added", Toast.LENGTH_SHORT).show();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        name.setText("");
        dat.setText("");
        tim.setText("");
        pt.setText("");
        td.setText("");
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_new_appointment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
