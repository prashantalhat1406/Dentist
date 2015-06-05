package com.example.prashant.dentist;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class viewPatientPage extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient_page);
        displayAllExistingPatients();

        Button b = (Button)findViewById(R.id.butPatDelete);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {deletePatient(); displayAllExistingPatients(); }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayAllExistingPatients();
    }

    public void searchPatientByName(View view) {
        EditText searchname = (EditText) findViewById(R.id.txtSearchName);
        String nameToSearch = searchname.getText().toString().toUpperCase();

        TableLayout pt = (TableLayout) findViewById(R.id.patientTable);
        pt.removeAllViews();

        patientDatabaseHandler db = new patientDatabaseHandler(this);
        List<patientInformation> patientList = db.getPatientInfoByName(searchname.getText().toString());

        LayoutInflater inflat = getLayoutInflater();

        for (patientInformation pi : patientList) {
            TableRow row = (TableRow) inflat.inflate(R.layout.tablerowforpatient, pt, false);
            CheckBox recnum = (CheckBox) row.findViewById(R.id.rownumber);
            recnum.setText(Integer.toString(pi.getID()));
            TextView name = (TextView) row.findViewById(R.id.name);
            name.setText(pi.getName());
            TextView phone = (TextView) row.findViewById(R.id.phone);
            phone.setText(pi.getPhone());
            pt.addView(row);
        }
    }



    public void openAppointmentActivity ()
    {
        Toast.makeText(getApplicationContext(), "work in progress", Toast.LENGTH_SHORT).show();
    }

    public void openReportsActivity()
    {
        Toast.makeText(getApplicationContext(), "work in progress", Toast.LENGTH_SHORT).show();
    }


    public void displayAllExistingPatients() {
        TableLayout pt = (TableLayout) findViewById(R.id.patientTable);
        pt.removeAllViews();

        patientDatabaseHandler db = new patientDatabaseHandler(this);
        List<patientInformation> patientList = db.getAllPatientInfo();

        LayoutInflater inflat = getLayoutInflater();

        for(patientInformation pi : patientList){
            TableRow row = (TableRow) inflat.inflate(R.layout.tablerowforpatient ,pt,false);
            CheckBox recnum = (CheckBox)row.findViewById(R.id.rownumber);
            recnum.setText(Integer.toString( pi.getID()));
            TextView name = (TextView)row.findViewById(R.id.name) ;
            name.setText(pi.getName());
            TextView phone = (TextView)row.findViewById(R.id.phone);
            phone.setText(pi.getPhone());
            pt.addView(row);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_patient_page, menu);
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

    public int getSelectedPatientID(){
        int patientIDs=-1;

        TableLayout pt = (TableLayout) findViewById(R.id.patientTable);
        int i =0;
        try {
            while (i < pt.getChildCount()) {
                TableRow tr = (TableRow) pt.getChildAt(i);
                CheckBox cb = (CheckBox) tr.getChildAt(0);
                if (cb.isChecked()) {
                    patientIDs = Integer.parseInt(cb.getText().toString());
                    i = i + 1;
                    break;
                }
                i = i + 1;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return patientIDs;
    }

    public void deletePatient()
    {
        TableLayout pt = (TableLayout) findViewById(R.id.patientTable);
        int i =0;
        try {
            while (i < pt.getChildCount()) {
                TableRow tr = (TableRow) pt.getChildAt(i);
                CheckBox cb = (CheckBox) tr.getChildAt(0);
                if (cb.isChecked()) {
                    patientDatabaseHandler db = new patientDatabaseHandler(this);
                    db.deletePatientInfo(Integer.parseInt(cb.getText().toString()));
                    db.close();
                    i = i + 1;
                }
                i = i + 1;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
