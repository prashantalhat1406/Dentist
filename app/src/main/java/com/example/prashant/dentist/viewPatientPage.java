package com.example.prashant.dentist;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class viewPatientPage extends ActionBarActivity {

    int y, m, d,h,mi;
    EditText EADdate, EADtime;
    boolean currentDateFlag,enableMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient_page);
        displayAllExistingPatients();
        enableMenu=false;

        //Button bSearch = (Button)findViewById(R.id.butPatientSearch);
        ImageButton bSearch = (ImageButton)findViewById(R.id.butPatientSearch);
        bSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchPatientByName();
            }
        });

        invalidateOptionsMenu();


/*
        Button bNewPatient = (Button)findViewById(R.id.butVPNEW);
        bNewPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewPatientDialog();

            }
        });
*/
        /*
        Button bAppointmeent = (Button)findViewById(R.id.butViewPatientAppointment);
        bAppointmeent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoNewAppointmentScreen();
            }
        });
        */
/*
        Button bDetails = (Button)findViewById(R.id.butVPDetails);
        bDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPatientDetails();
            }
        });
*/

        Calendar cal = Calendar.getInstance();
        y=cal.get(Calendar.YEAR);
        m=cal.get(Calendar.MONTH);
        d=cal.get(Calendar.DAY_OF_MONTH);
        h=cal.get(Calendar.HOUR_OF_DAY);
        mi=cal.get(Calendar.MINUTE);
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
                TableRow tr = (TableRow) pt.findViewById(R.id.tableRowForPatient);
                CheckBox cb = (CheckBox) tr.findViewById(R.id.rownumber);
                if (cb.isChecked()) {cb.setChecked(false);}
                i = i + 1;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }



    public void gotoNewAppointmentScreen ()
    {
        final int pID = getSelectedPatientID();
        if (pID==-1){
            Toast.makeText(getApplicationContext(), "Select Record", Toast.LENGTH_SHORT).show();
        }else {
            /*Intent i = new Intent(this, addNewAppointment.class);
            i.putExtra("pid", String.valueOf(pID));
            startActivity(i);*/
            patientDatabaseHandler pdb = new patientDatabaseHandler(this);
            patientInformation pi = pdb.getPatientInfoByID(pID);

            final Dialog addAppointment = new Dialog(this);
            addAppointment.setContentView(R.layout.editappointmentdialog);
            addAppointment.setTitle("Add Appointment");
            addAppointment.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

            final TextView name = (TextView)addAppointment.findViewById(R.id.autotxtEADName);
            name.setText(pi.getName());

            final Spinner treatment = (Spinner)addAppointment.findViewById(R.id.spnEADTreamnet);
            ArrayAdapter<CharSequence> adapterpt = ArrayAdapter.createFromResource(this,R.array.ProposedTreatment, android.R.layout.simple_list_item_single_choice);
            treatment.setAdapter(adapterpt);

            EADdate = (EditText)addAppointment.findViewById(R.id.txtEADDate);
            EADdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(2);
                }
            });
            EADtime = (EditText)addAppointment.findViewById(R.id.txtEADTime);
            EADtime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showDialog(3);
                }
            });

            //final EditText toothdetails = (EditText)addAppointment.findViewById(R.id.txtEADToothDetails);

            ImageButton bdateDialog = (ImageButton)addAppointment.findViewById(R.id.butEADShowDate);
            bdateDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(2);
                }
            });
            ImageButton btimeDialog = (ImageButton)addAppointment.findViewById(R.id.butEADShowTime);
            btimeDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(3);
                }
            });

            final appointmentDatabaseHandler adb= new appointmentDatabaseHandler(this);

            Button bAdd = (Button) addAppointment.findViewById(R.id.butEADButton);
            bAdd.setText("ADD");
            bAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int lastApptID;
                    try {
                        if (name.getText().length() == 0) {
                            Toast.makeText(getApplicationContext(), "Enter Name", Toast.LENGTH_SHORT).show();
                        } else {
                            if (EADdate.getText().length() == 0) {
                                Toast.makeText(getApplicationContext(), "Enter Date", Toast.LENGTH_SHORT).show();
                            } else {
                                if (EADtime.getText().length() == 0) {
                                    Toast.makeText(getApplicationContext(), "Enter Time", Toast.LENGTH_SHORT).show();
                                } else {/*
                                    if (toothdetails.getText().length() == 0) {
                                        Toast.makeText(getApplicationContext(), "Enter ToothDetails", Toast.LENGTH_SHORT).show();
                                    } else {*/
                                        if (adb.getAppointmentCountForDateTime(EADdate.getText().toString(), EADtime.getText().toString()) == 0) {

                                            lastApptID = adb.getLastAppointmentID() + 1;
                                            String []temp = EADdate.getText().toString().split("/");
                                            String dateInYYYYMMDD = temp[2] + "/"+temp[1] + "/"+temp[0];
                                            //appointmentInformation ai = new appointmentInformation(lastApptID, pID, EADdate.getText().toString(), EADtime.getText().toString(), treatment.getSelectedItem().toString(), toothdetails.getText().toString());
                                            appointmentInformation ai = new appointmentInformation(lastApptID, pID, dateInYYYYMMDD, EADtime.getText().toString(), treatment.getSelectedItem().toString());//, toothdetails.getText().toString());
                                            adb.addAppointmentInfo(ai);
                                            adb.close();
                                            Toast.makeText(getApplicationContext(), "Record Added", Toast.LENGTH_SHORT).show();
                                            name.setText("");
                                            EADdate.setText("");
                                            EADtime.setText("");
                                            //toothdetails.setText("");
                                            addAppointment.dismiss();
                                            clearAllSelection();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Appointment Already existed", Toast.LENGTH_SHORT).show();
                                            EADdate.setText("");
                                            EADtime.setText("");
                                        }
                                    /*}*/
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            addAppointment.show();
            clearAllSelection();
        }
    }

    /*public void viewPatientDetails(){
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
            TextView totalpayment = (TextView) patientDetails.findViewById(R.id.txtVPDTotalPayment);
            name.setText(pi.getName());
            phone.setText(pi.getPhone());
            age.setText(pi.getAge());
            sex.setText(pi.getSex());
            address.setText(pi.getAddress());
            totalpayment.setText(String.valueOf(adb.getTotalPaymentForPatient(pi.getID())));
            try {
                TableLayout patientDetailsTable = (TableLayout) patientDetails.findViewById(R.id.patientDetailsTable);
                patientDetailsTable.removeAllViews();
                LayoutInflater inflater = getLayoutInflater();
                for (appointmentInformation ai : appointmentList) {
                    TableRow tr = (TableRow) inflater.inflate(R.layout.tablerowforpatientdetails, patientDetailsTable, false);
                    TextView adate = (TextView) tr.findViewById(R.id.txtVPDDate);
                    TextView treatment = (TextView) tr.findViewById(R.id.txtVPDTreatmentDone);
                    TextView payment = (TextView) tr.findViewById(R.id.txtVPDPayment);

                    SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                    Date dObj = df.parse(ai.getaDate());
                    Calendar myCal = Calendar.getInstance();
                    myCal.setTime(dObj);
                    df = new SimpleDateFormat("dd/MM/yyyy");
                    //dt.setText(df.format(myCal.getTime()));
                    //adate.setText(ai.getaDate());
                    adate.setText(df.format(myCal.getTime()));
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
    }*/


    public void viewPatientDetails(int pID){
        /*int pID = getSelectedPatientID();
        if (pID==-1){
            Toast.makeText(getApplicationContext(), "Select Record", Toast.LENGTH_SHORT).show();
        }else {*/
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
            TextView totalpayment = (TextView) patientDetails.findViewById(R.id.txtVPDTotalPayment);
            name.setText(pi.getName());
            phone.setText(pi.getPhone());
            age.setText(pi.getAge());
            sex.setText(pi.getSex());
            address.setText(pi.getAddress());
            totalpayment.setText(String.valueOf(adb.getTotalPaymentForPatient(pi.getID())));
            try {
                TableLayout patientDetailsTable = (TableLayout) patientDetails.findViewById(R.id.patientDetailsTable);
                patientDetailsTable.removeAllViews();
                LayoutInflater inflater = getLayoutInflater();
                for (appointmentInformation ai : appointmentList) {
                    TableRow tr = (TableRow) inflater.inflate(R.layout.tablerowforpatientdetails, patientDetailsTable, false);
                    TextView adate = (TextView) tr.findViewById(R.id.txtVPDDate);
                    TextView proposedtreatment = (TextView) tr.findViewById(R.id.txtVPDPropsedTreatment);
                    TextView actualtreatment = (TextView) tr.findViewById(R.id.txtVPDActualTreatment);
                    TextView details = (TextView) tr.findViewById(R.id.txtVPDAppointmentDetails);
                    TextView payment = (TextView) tr.findViewById(R.id.txtVPDPayment);

                    SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                    Date dObj = df.parse(ai.getaDate());
                    Calendar myCal = Calendar.getInstance();
                    myCal.setTime(dObj);
                    df = new SimpleDateFormat("dd/MM/yyyy");
                    //dt.setText(df.format(myCal.getTime()));
                    //adate.setText(ai.getaDate());
                    adate.setText(df.format(myCal.getTime()));
                    proposedtreatment.setText(ai.getProposedTreatment());
                    actualtreatment.setText(ai.getActualTreatment());
                    details.setText(ai.getToothDetails());
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

        /*}*/
    }

    public void searchPatientByName() {
        EditText searchname = (EditText) findViewById(R.id.txtSearchName);
        TableLayout pt = (TableLayout) findViewById(R.id.patientTable);
        pt.removeAllViews();
        patientDatabaseHandler db = new patientDatabaseHandler(this);
        List<patientInformation> patientList = db.getPatientInfoByName(searchname.getText().toString());
        LayoutInflater inflat = getLayoutInflater();
        boolean color = false;
        if(patientList.size()!=0) {
            for (final patientInformation pi : patientList) {
                TableRow row = (TableRow) inflat.inflate(R.layout.tablerowforpatient, pt, false);
                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPatientDetails(pi.getID());
                    }
                });
                if (!color) {
                    color = true;
                    row.setBackgroundResource(R.drawable.shapeofpatientrowdark);
                } else {
                    color = false;
                    row.setBackgroundResource(R.drawable.shapeofpatientrowlight);
                }
                TextView recnum = (TextView) row.findViewById(R.id.txtPatientRecordID);
                recnum.setText(Integer.toString(pi.getID()));
                TextView name = (TextView) row.findViewById(R.id.name);
                name.setText(pi.getName());
                TextView phone = (TextView) row.findViewById(R.id.phone);
                phone.setText(pi.getPhone());
                pt.addView(row);
            }
        }else
            Toast.makeText(getApplicationContext(), "No Records Found", Toast.LENGTH_SHORT).show();
        searchname.setText("");
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

    }


    public void displayAllExistingPatients() {
        TableLayout pt = (TableLayout) findViewById(R.id.patientTable);
        pt.removeAllViews();
        patientDatabaseHandler db = new patientDatabaseHandler(this);
        List<patientInformation> patientList = db.getAllPatientInfo();
        LayoutInflater inflat = getLayoutInflater();
        boolean color=false;
        for(final patientInformation pi : patientList){
            TableRow row = (TableRow) inflat.inflate(R.layout.tablerowforpatient, pt, false);
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPatientDetails(pi.getID());
                }
            });
            if(!color){
                color=true;
                row.setBackgroundResource(R.drawable.shapeofpatientrowdark);
            }else{
                color=false;
                row.setBackgroundResource(R.drawable.shapeofpatientrowlight);
            }
            final CheckBox cb = (CheckBox) row.findViewById(R.id.rownumber);
            cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cb.isChecked())
                        enableMenu=true;
                    else
                        enableMenu=false;
                    invalidateOptionsMenu();
                }
            });
            TextView recnum = (TextView)row.findViewById(R.id.txtPatientRecordID);
            recnum.setText(Integer.toString( pi.getID()));
            TextView name = (TextView)row.findViewById(R.id.name) ;
            name.setText(pi.getName());
            TextView phone = (TextView)row.findViewById(R.id.phone);
            phone.setText(pi.getPhone());
            pt.addView(row);
        }
        clearAllSelection();
        enableMenu=false;
        invalidateOptionsMenu();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.


        getMenuInflater().inflate(R.menu.menu_view_patient_page, menu);
        MenuItem mi;
        if(!enableMenu)
        {
            mi=menu.findItem(R.id.menuVPEdit);
            mi.setVisible(false);
            mi=menu.findItem(R.id.menuVPDelete);
            mi.setVisible(false);
            mi=menu.findItem(R.id.menuVPAppointment);
            mi.setVisible(false);
        }
        else
        {
            mi=menu.findItem(R.id.menuVPEdit);
            mi.setVisible(true);
            mi=menu.findItem(R.id.menuVPDelete);
            mi.setVisible(true);
            mi=menu.findItem(R.id.menuVPAppointment);
            mi.setVisible(true);
        }
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

        if (id == R.id.menuVPAdd) {
            addNewPatientDialog();
            displayAllExistingPatients();
            return true;
        }

        if (id == R.id.menuVPAppointment) {
            gotoNewAppointmentScreen();
            displayAllExistingPatients();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected Dialog onCreateDialog(int id) {

            if (id == 2) {
                return new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        try {
                            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                            Date dObj = df.parse(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            Calendar tempCal = Calendar.getInstance();
                            tempCal.set(Calendar.HOUR_OF_DAY, 0);
                            tempCal.set(Calendar.MINUTE, 0);
                            tempCal.set(Calendar.SECOND, 0);
                            tempCal.set(Calendar.MILLISECOND, 0);
                            if (!dObj.before(tempCal.getTime())) {
                                currentDateFlag = dObj.equals(tempCal.getTime());
                                Calendar myCal = Calendar.getInstance();
                                myCal.setTime(dObj);
                                EADdate.setText(df.format(myCal.getTime()));
                            } else {
                                Toast.makeText(getApplicationContext(), "Entered Date should not be past date", Toast.LENGTH_SHORT).show();
                                EADdate.setText("");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, y, m, d);
            } else {
                if (id == 3) {
                    return new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            try {
                                if (hourOfDay >= 10 && hourOfDay <= 20) {
                                    Calendar timeCheck = Calendar.getInstance();
                                    timeCheck.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                    timeCheck.set(Calendar.MINUTE, minute);
                                    if (EADdate.getText().length() == 0) {
                                        Toast.makeText(getApplicationContext(), "Please enter Date first", Toast.LENGTH_SHORT).show();
                                        EADtime.setText("");
                                    } else {
                                        if (currentDateFlag) {
                                            if (Calendar.getInstance().after(timeCheck)) {
                                                Toast.makeText(getApplicationContext(), "Entered Time should be after current Time", Toast.LENGTH_SHORT).show();
                                                EADtime.setText("");
                                            } else {
                                                if (hourOfDay <= 12) {
                                                    if (String.valueOf(minute).length() == 1)
                                                        EADtime.setText(hourOfDay + ":0" + minute + " AM");
                                                    else
                                                        EADtime.setText(hourOfDay + ":" + minute+ " AM" );
                                                }
                                                if (hourOfDay > 12) {
                                                    hourOfDay = hourOfDay - 12;
                                                    if (String.valueOf(minute).length() == 1)
                                                        EADtime.setText(hourOfDay + ":0" + minute+ " PM");
                                                    else
                                                        EADtime.setText(hourOfDay + ":" + minute + " PM");
                                                }
                                            }
                                        } else {
                                            if (hourOfDay <= 12) {
                                                if (String.valueOf(minute).length() == 1)
                                                    EADtime.setText(hourOfDay + ":0" + minute+ " AM");
                                                else
                                                    EADtime.setText(hourOfDay + ":" + minute+ " AM");
                                            }
                                            if (hourOfDay > 12) {
                                                hourOfDay = hourOfDay - 12;
                                                if (String.valueOf(minute).length() == 1)
                                                    EADtime.setText(hourOfDay + ":0" + minute+ " PM");
                                                else
                                                    EADtime.setText(hourOfDay + ":" + minute+ " PM");
                                            }
                                        }
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Clinic working hours 10am-9pm", Toast.LENGTH_SHORT).show();
                                    EADtime.setText("");
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, h, mi, false);
                }
            }

        return null;
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
        sexM.setChecked(true);
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
                                    if (!pdb.isPatientAlreadyExist(name.getText().toString(),phone.getText().toString())) {
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
                                    }else{
                                        Toast.makeText(getApplicationContext(), "Duplicate Record", Toast.LENGTH_SHORT).show();
                                    }


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
            editDialog.getWindow().setTitleColor(R.drawable.buttonblue);
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
