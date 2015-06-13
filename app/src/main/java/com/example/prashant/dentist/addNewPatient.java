package com.example.prashant.dentist;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


public class addNewPatient extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_patient);
/*
        Button saveAndAppoint = (Button)findViewById(R.id.butsaveAndOpenAppointmentActivity);
        saveAndAppoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {addNewPatientToDatabase(v);

            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_new_patient, menu);
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

    public void addNewPatientToDatabase(View view) {
        String strDataToAdd, sex;
        EditText name, phone, address, age;
        RadioButton sexM, sexF;


        name = (EditText) findViewById(R.id.txtPatientName);
        phone = (EditText) findViewById(R.id.txtPatientPhone);
        address = (EditText) findViewById(R.id.txtPatientAddress);
        age = (EditText) findViewById(R.id.txtPatientAge);
        sexM = (RadioButton) findViewById(R.id.rdbmale);
        sexF = (RadioButton) findViewById(R.id.rdbfemale);

        patientDatabaseHandler db = new patientDatabaseHandler(this);
        int recordNumber = db.getLastRecordID() + 1;
        if (sexM.isChecked()){sex="M";
            sexM.setChecked(false);
        }else{sex="F";sexF.setChecked(false);
        }

        if (name.getText().length() != 0) {
            if (phone.getText().length() == 10) {
                if (address.getText().length() != 0) {
                    if (Integer.parseInt(age.getText().toString()) > 0) {
                        patientInformation pi = new patientInformation(recordNumber,name.getText().toString(),phone.getText().toString(),address.getText().toString(),sex,age.getText().toString());
                        db.addPatientInfo(pi);
                        db.close();
                        name.setText("");
                        phone.setText("");
                        address.setText("");
                        age.setText("");
                        Toast.makeText(getApplicationContext(), "Record Added", Toast.LENGTH_SHORT).show();
                        this.finish();
                    } else
                        Toast.makeText(getApplicationContext(), "Please enter correct Age", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), "Please enter Address", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(getApplicationContext(), "Please enter 10 digit phone", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(getApplicationContext(), "Please enter Name", Toast.LENGTH_SHORT).show();
    }


    public void saveAndOpenAppointmentActivity()
    {
        Toast.makeText(getApplicationContext(), "work in progress", Toast.LENGTH_SHORT).show();
    }
}
