package com.example.prashant.dentist;

import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class homePatient extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_patient);

        Button bt  =(Button) findViewById(R.id.butViewAppiontment);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoViewAppointmentPage();
            }
        });
    }

    public void displayAddNewPatientActivity(View view){
        Intent i = new Intent(this,addNewPatient.class);
        startActivity(i);
    }

    public void displayExistingPatientActivity(View view)
    {
        Intent i = new Intent(this,viewPatientPage.class);
        startActivity(i);
    }

    public void gotoAddNewAppointmentPage(View view){
        Intent i = new Intent(this,addNewAppointment.class);
        startActivity(i);
    }

    public void gotoViewAppointmentPage(){
        Intent i = new Intent(this,viewAppiontmentPage.class);
        startActivity(i);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_patient, menu);
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
