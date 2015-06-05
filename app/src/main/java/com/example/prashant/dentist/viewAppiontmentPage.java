package com.example.prashant.dentist;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.List;


public class viewAppiontmentPage extends ActionBarActivity {

    private Calendar cal;
    int y, m, d,h,mi;
    Button bd,bt;
    TextView currentDate;

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
        cal = Calendar.getInstance();
        y=cal.get(Calendar.YEAR);
        m=cal.get(Calendar.MONTH);
        d=cal.get(Calendar.DAY_OF_MONTH);
        h=cal.get(Calendar.HOUR_OF_DAY);
        mi=cal.get(Calendar.MINUTE);

        currentDate = (TextView)findViewById(R.id.txtViewAppointmentCurrentDate);
        currentDate.setText(d + "/" + (m + 1) + "/" + y);
        bd = (Button)findViewById(R.id.butViewAppiontmentDateDialog);
        bd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {showDialog(0);
            }
        });

        Button newApt = (Button)findViewById(R.id.butNewAppointment);
        newApt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {gotoNewAppointmentScreen();
            }
        });



    }

    @Override
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                currentDate.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                displayAppointmentForDate(currentDate.getText().toString());
            }
        },y,m,d);
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

    @Override
    protected void onResume() {
        super.onResume();
        displayAllAppointments();
    }

    public void gotoNewAppointmentScreen()
    {
        Intent i = new Intent(this,addNewAppointment.class);
        startActivity(i);
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

    public void displayAppointmentForDate(String currentD)
    {
        try {
            TableLayout aptTable = (TableLayout) findViewById(R.id.apptTable);
            aptTable.removeAllViews();

            appointmentDatabaseHandler adb = new appointmentDatabaseHandler(this);
            List<appointmentInformation> appointmentList = adb.getAppointmentInfoByDate(currentD);

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
