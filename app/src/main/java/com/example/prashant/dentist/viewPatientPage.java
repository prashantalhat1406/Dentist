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
import android.view.WindowManager;
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
    private int noOfPatientSelected=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient_page);
        displayAllExistingPatients();

        Button bNewPatient = (Button)findViewById(R.id.butVPNEW);
        bNewPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewPatientDialog();

            }
        });

        Button bAppointmeent = (Button)findViewById(R.id.butViewPatientAppointment);
        bAppointmeent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoNewAppointmentScreen();
            }
        });

        Button bDetails = (Button)findViewById(R.id.butVPDetails);
        bDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPatientDetails();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayAllExistingPatients();
    }

    public void clearAllSelection(){
        TableLayout pt = (TableLayout) findViewById(R.id.patientTable);
        int i =0;
        try {
            while (i < pt.getChildCount()) {
                TableRow tr = (TableRow) pt.getChildAt(i);
                CheckBox cb = (CheckBox) tr.getChildAt(0);
                if (cb.isChecked()) {cb.setChecked(false);}
                i = i + 1;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void searchPatientByName(View view) {
        EditText searchname = (EditText) findViewById(R.id.txtSearchName);
        TableLayout pt = (TableLayout) findViewById(R.id.patientTable);
        pt.removeAllViews();
        patientDatabaseHandler db = new patientDatabaseHandler(this);
        List<patientInformation> patientList = db.getPatientInfoByName(searchname.getText().toString());
        LayoutInflater inflat = getLayoutInflater();
        boolean color = false;
        for (patientInformation pi : patientList) {
            TableRow row = (TableRow) inflat.inflate(R.layout.tablerowforpatient, pt, false);
            if(color == false){
                color=true;
                row.setBackgroundResource(R.drawable.shapeofpatientrowdark);
            }else{
                color=false;
                row.setBackgroundResource(R.drawable.shapeofpatientrowlight);
            }
            final CheckBox cb = (CheckBox)row.findViewById(R.id.rownumber);
            TextView recnum = (TextView)row.findViewById(R.id.txtPatientRecordID);
            recnum.setText(Integer.toString( pi.getID()));
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
        int pID = getSelectedPatientID();
        if (pID==-1){
            Toast.makeText(getApplicationContext(), "Select Record", Toast.LENGTH_SHORT).show();
        }else {
            Intent i = new Intent(this, addNewAppointment.class);
            i.putExtra("pid", String.valueOf(pID));
            startActivity(i);
        }
    }

    public void viewPatientDetails(){
        int pID = getSelectedPatientID();
        if (pID==-1){
            Toast.makeText(getApplicationContext(), "Select Record", Toast.LENGTH_SHORT).show();
        }else {
            patientDatabaseHandler pdb = new patientDatabaseHandler(this);
            appointmentDatabaseHandler adb = new appointmentDatabaseHandler(this);
            patientInformation pi = pdb.getPatientInfoByID(pID);
            List<appointmentInformation> appointmentList = adb.getAppointmentInfoByPaitentID(pID);
            Dialog patientDetails = new Dialog(this);
            patientDetails.setContentView(R.layout.viewpatientdetailsdialog);
            patientDetails.setTitle("Patient Details");
            patientDetails.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            TextView name = (TextView) patientDetails.findViewById(R.id.txtVPDName);
            TextView phone = (TextView) patientDetails.findViewById(R.id.txtVPDPhone);
            TextView age = (TextView) patientDetails.findViewById(R.id.txtVPDAge);
            TextView sex = (TextView) patientDetails.findViewById(R.id.txtVPDSex);
            TextView address = (TextView) patientDetails.findViewById(R.id.txtVPDAddress);
            name.setText(pi.getName());
            phone.setText(pi.getPhone());
            age.setText(pi.getAge());
            sex.setText(pi.getSex());
            address.setText(pi.getAddress());
            try {
                TableLayout patientDetailsTable = (TableLayout) patientDetails.findViewById(R.id.patientDetailsTable);
                patientDetailsTable.removeAllViews();
                LayoutInflater inflater = getLayoutInflater();
                for (appointmentInformation ai : appointmentList) {
                    TableRow tr = (TableRow) inflater.inflate(R.layout.tablerowforpatientdetails, patientDetailsTable, false);
                    TextView adate = (TextView) tr.findViewById(R.id.txtVPDDate);
                    TextView treatment = (TextView) tr.findViewById(R.id.txtVPDTreatmentDone);
                    TextView payment = (TextView) tr.findViewById(R.id.txtVPDPayment);
                    adate.setText(ai.getaDate());
                    treatment.setText(ai.getActualTreatment());
                    if(ai.getPayment()==0)
                        payment.setText("-");
                    else
                        payment.setText(String.valueOf(ai.getPayment()));

                    patientDetailsTable.addView(tr);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            patientDetails.show();
            clearAllSelection();
        }
    }


    public void displayAllExistingPatients() {
        TableLayout pt = (TableLayout) findViewById(R.id.patientTable);
        pt.removeAllViews();
        patientDatabaseHandler db = new patientDatabaseHandler(this);
        List<patientInformation> patientList = db.getAllPatientInfo();
        LayoutInflater inflat = getLayoutInflater();
        boolean color=false;
        for(patientInformation pi : patientList){
            TableRow row = (TableRow) inflat.inflate(R.layout.tablerowforpatient, pt, false);
            if(color == false){
                color=true;
                row.setBackgroundResource(R.drawable.shapeofpatientrowdark);
            }else{
                color=false;
                row.setBackgroundResource(R.drawable.shapeofpatientrowlight);
            }
            final CheckBox cb = (CheckBox)row.findViewById(R.id.rownumber);
            TextView recnum = (TextView)row.findViewById(R.id.txtPatientRecordID);
            recnum.setText(Integer.toString( pi.getID()));
            TextView name = (TextView)row.findViewById(R.id.name) ;
            name.setText(pi.getName());
            TextView phone = (TextView)row.findViewById(R.id.phone);
            phone.setText(pi.getPhone());
            pt.addView(row);
        }
        clearAllSelection();
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
            clearAllSelection();
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
        int patientID=-1;
        TableLayout pt = (TableLayout) findViewById(R.id.patientTable);
        int i =0;
        try {
            while (i < pt.getChildCount()) {
                TableRow tr = (TableRow) pt.getChildAt(i);
                CheckBox cb = (CheckBox) tr.getChildAt(0);
                TextView tv = (TextView) tr.getChildAt(1);
                if (cb.isChecked()) {
                    patientID = Integer.parseInt(tv.getText().toString());
                    i = i + 1;
                    break;
                }
                i = i + 1;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return patientID;
    }

    public void deletePatient()
    {
        int pID = getSelectedPatientID();
        if (pID==-1){
            Toast.makeText(getApplicationContext(), "Select Record", Toast.LENGTH_SHORT).show();
        }else {
            patientDatabaseHandler db = new patientDatabaseHandler(this);
            db.deletePatientInfo(pID);
            db.close();
        }
    }

    public void addNewPatientDialog()
    {
        final patientDatabaseHandler pdb = new patientDatabaseHandler(this);
        final patientInformation pi = new patientInformation();
        final int recordNumber = pdb.getLastRecordID() + 1;
        final Dialog addNP = new Dialog(this);
        addNP.setContentView(R.layout.addnewpatientdialog);
        addNP.setTitle("ADD PATIENT");
        final EditText name = (EditText)addNP.findViewById(R.id.txtANPDPatientName);
        final EditText phone = (EditText)addNP.findViewById(R.id.txtANPDPatientPhone);
        final EditText address = (EditText)addNP.findViewById(R.id.txtANPDPatientAddress);
        final EditText age = (EditText)addNP.findViewById(R.id.txtANPDPatientAge);
        final RadioButton sexM = (RadioButton)addNP.findViewById(R.id.rdbANPDmale);
        final RadioButton sexF = (RadioButton)addNP.findViewById(R.id.rdbANPDfemale);
        Button addButton = (Button)addNP.findViewById(R.id.butANPDialogNew);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().length() != 0) {
                    if (phone.getText().length() == 10) {
                        if (address.getText().length() != 0) {
                            if (age.getText().length() != 0 && Integer.parseInt(age.getText().toString()) > 1 && Integer.parseInt(age.getText().toString()) < 100 ) {
                                if(sexM.isChecked() || sexF.isChecked()) {
                                    pi.setID(recordNumber);
                                    pi.setName(name.getText().toString());
                                    pi.setPhone(phone.getText().toString());
                                    pi.setAddress(address.getText().toString());
                                    pi.setAge(age.getText().toString());
                                    if (sexM.isChecked())
                                        pi.setSex(sexM.getText().toString());
                                    else
                                        pi.setSex(sexF.getText().toString());
                                    pdb.addPatientInfo(pi);
                                    addNP.dismiss();
                                    Toast.makeText(getApplicationContext(), "Record Added", Toast.LENGTH_SHORT).show();
                                    displayAllExistingPatients();

                                }else
                                    Toast.makeText(getApplicationContext(), "Please select Sex", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(getApplicationContext(), "Please enter correct Age", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(getApplicationContext(), "Please enter Address", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getApplicationContext(), "Please enter 10 digit phone", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), "Please enter Name", Toast.LENGTH_SHORT).show();
            }
        });
        addNP.show();
        pdb.close();
    }


    public void editPatient()
    {
        int pID = getSelectedPatientID();
        if (pID==-1){
            Toast.makeText(getApplicationContext(), "Select Record", Toast.LENGTH_SHORT).show();
        }else {
            final patientDatabaseHandler pdb = new patientDatabaseHandler(this);
            final patientInformation pi = pdb.getPatientInfoByID(pID);
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
            if (pi.getSex().equals("M"))
                sexM.setChecked(true);
            else
                sexF.setChecked(true);
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
                    if (sexM.isChecked())
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
        }
    }
}
