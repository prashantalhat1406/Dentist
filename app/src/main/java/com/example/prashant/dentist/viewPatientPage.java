package com.example.prashant.dentist;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
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
/*
        Button bDelete = (Button)findViewById(R.id.butViewPatientDelete);
        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {deletePatient(); displayAllExistingPatients(); }
        });

        Button bEdit = (Button)findViewById(R.id.butViewPatientEdit);
        bEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPatient();
            }
        });
*/
        Button bAppointmeent = (Button)findViewById(R.id.butViewPatientAppointment);
        bAppointmeent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoNewAppointmentScreen();
            }
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

        searchname.setText("");

    }



    public void gotoNewAppointmentScreen ()
    {
        Intent i = new Intent(this,addNewAppointment.class);
        startActivity(i);
        //Toast.makeText(getApplicationContext(), "work in progress", Toast.LENGTH_SHORT).show();
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
        if (id == R.id.menuVPEdit) {
            editPatient();
            return true;
        }

        if (id == R.id.menuVPDelete) {
            deletePatient();
            displayAllExistingPatients();
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
                }
                i = i + 1;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void editPatient()
    {
        int i =0;
        TableLayout pt = (TableLayout) findViewById(R.id.patientTable);
        try {
            while (i < pt.getChildCount()) {
                TableRow tr = (TableRow) pt.getChildAt(i);
                CheckBox cb = (CheckBox) tr.getChildAt(0);
                if (cb.isChecked()) {
                    final patientDatabaseHandler pdb = new patientDatabaseHandler(this);

                    final patientInformation pi = pdb.getPatientInfoByID(Integer.parseInt(cb.getText().toString()));

                    final Dialog editDialog = new Dialog(this);
                    editDialog.setContentView(R.layout.editdialoglayout);
                    editDialog.setTitle("Edit Patient");

                    final EditText name = (EditText)editDialog.findViewById(R.id.txtEditPatientName);
                    name.setText(pi.getName());

                    final EditText phone = (EditText)editDialog.findViewById(R.id.txtEditPatientPhone);
                    phone.setText(pi.getPhone());

                    final EditText address = (EditText)editDialog.findViewById(R.id.txtEditPatientAddress);
                    address.setText(pi.getAddress());

                    final RadioButton sexM = (RadioButton)editDialog.findViewById(R.id.rdbEditMale);
                    final RadioButton sexF = (RadioButton)editDialog.findViewById(R.id.rdbEditFemale);
                    if (pi.getSex() == "M")
                        sexM.setSelected(true);
                    else
                        sexF.setSelected(true);


                    final EditText age = (EditText)editDialog.findViewById(R.id.txtEditPatientAge);
                    age.setText(pi.getAge());

                    Button bSave = (Button) editDialog.findViewById(R.id.butEditPatient);
                    bSave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pi.setName(name.getText().toString());
                            pi.setPhone(phone.getText().toString());
                            pi.setAddress(address.getText().toString());
                            pi.setAge(age.getText().toString());
                            if (sexM.isSelected())
                                pi.setSex(sexM.getText().toString());
                            else
                                pi.setSex(sexF.getText().toString());

                            pdb.updatePatientInfo(pi);
                            editDialog.dismiss();
                            displayAllExistingPatients();
                        }
                    });

                    editDialog.show();
                    pdb.close();
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
