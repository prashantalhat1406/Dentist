package com.example.prashant.dentist;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;


public class viewAppiontmentPage extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appiontment_page);
/*
        Spinner year = (Spinner)findViewById(R.id.spYear);
        ArrayAdapter<CharSequence> adaptery = ArrayAdapter.createFromResource(this,R.array.Years, android.R.layout.simple_spinner_item);
        year.setAdapter(adaptery);


        Spinner month = (Spinner)findViewById(R.id.spMonth);
        ArrayAdapter<CharSequence> adapterm = ArrayAdapter.createFromResource(this,R.array.Months, android.R.layout.simple_spinner_item);
        month.setAdapter(adapterm);

        Spinner datel = (Spinner)findViewById(R.id.spDate);
        ArrayAdapter<CharSequence> adapterd = ArrayAdapter.createFromResource(this,R.array.Dates, android.R.layout.simple_spinner_item);
        datel.setAdapter(adapterd);
*/

        displayAllAppointments();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_appiontment_page, menu);
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

    public void displayAllAppointments(){
        try {
            TableLayout aptTable = (TableLayout) findViewById(R.id.apptTable);
            aptTable.removeAllViews();

            appointmentDatabaseHandler adb = new appointmentDatabaseHandler(this);
            List<appointmentInformation> appointmentList = adb.getAllAppointmentInfo();

            LayoutInflater inflater = getLayoutInflater();

            patientDatabaseHandler pdb = new patientDatabaseHandler(this);

            for (appointmentInformation ai : appointmentList) {
                TableRow tr = (TableRow) inflater.inflate(R.layout.tablerowforappointment, aptTable, false);
                CheckBox cb = (CheckBox) tr.findViewById(R.id.aptcheckbox);
                //cb.setText(String.valueOf( ai.getAID()));

                TextView aptID = (TextView) tr.findViewById(R.id.aptAptID);
                aptID.setText(String.valueOf(ai.getAID()));

                patientInformation pi = pdb.getPatientInfoByID(ai.getPID());
                TextView name = (TextView) tr.findViewById(R.id.aptpatientName);
                name.setText(pi.getName());

                TextView dt = (TextView) tr.findViewById(R.id.aptdateTime);
                dt.setText(ai.getaDate() + " - " + ai.getaTime());

                TextView pt = (TextView) tr.findViewById(R.id.aptproposedaction);
                pt.setText(ai.getProposedTreatment());

                aptTable.addView(tr);
            }

            pdb.close();
            adb.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
