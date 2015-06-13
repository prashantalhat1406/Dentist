package com.example.prashant.dentist;

import android.app.DatePickerDialog;
import android.app.Dialog;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;

import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class viewAppiontmentPage extends ActionBarActivity {

    private Calendar cal;
    int y, m, d,h,mi;
    Button bd,bt;
    TextView currentDate;
    RadioButton dayA,weekA,monthA;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appiontment_page);

        dayA = (RadioButton)findViewById(R.id.rdbVADay);
        dayA.setChecked(true);
        dayA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayAppointmentForDate(currentDate.getText().toString());
            }
        });

        weekA = (RadioButton)findViewById(R.id.rdbVAWeek);
        weekA.setChecked(false);
        weekA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayAppointmentForWeek(currentDate.getText().toString());
            }
        });
        monthA = (RadioButton)findViewById(R.id.rdbVAMonth);
        monthA.setChecked(false);
        monthA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(monthA.isChecked()){
                    displayAppointmentForMonth(currentDate.getText().toString());
                }
            }
        });

        cal = Calendar.getInstance();
        y=cal.get(Calendar.YEAR);
        m=cal.get(Calendar.MONTH);
        d=cal.get(Calendar.DAY_OF_MONTH);
        h=cal.get(Calendar.HOUR_OF_DAY);
        mi=cal.get(Calendar.MINUTE);



        currentDate = (TextView)findViewById(R.id.txtViewAppointmentCurrentDate);
        try {
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            Date dObj = df.parse(d + "/" + (m + 1) + "/" + y);
            Calendar myCal = Calendar.getInstance();
            myCal.setTime(dObj);
            currentDate.setText(df.format(myCal.getTime()));
            displayAppointmentForDate(currentDate.getText().toString());
        }catch (Exception e){
            e.printStackTrace();
        }

        bd = (Button)findViewById(R.id.butViewAppiontmentDateDialog);
        bd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {showDialog(0);
            }
        });




        Button previousApt = (Button)findViewById(R.id.butVAPrevious);
        previousApt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    Date dObj = df.parse(currentDate.getText().toString());
                    Calendar myCal = Calendar.getInstance();
                    myCal.setTime(dObj);
                    if(dayA.isChecked()){
                        myCal.add(Calendar.DATE, -1);
                        currentDate.setText(df.format(myCal.getTime()));
                        displayAppointmentForDate(currentDate.getText().toString());
                    }

                    if(weekA.isChecked()){
                        myCal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                        myCal.add(Calendar.DATE, -7);
                        currentDate.setText(df.format(myCal.getTime()));
                        displayAppointmentForWeek(currentDate.getText().toString());
                    }

                    if(monthA.isChecked()){
                        myCal.add(Calendar.MONTH,-1);
                        currentDate.setText(df.format(myCal.getTime()));
                        displayAppointmentForMonth(currentDate.getText().toString());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        Button nextApt = (Button)findViewById(R.id.butVANext);
        nextApt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    Date dObj = df.parse(currentDate.getText().toString());
                    Calendar myCal = Calendar.getInstance();
                    myCal.setTime(dObj);
                    if(dayA.isChecked()) {
                        myCal.add(Calendar.DATE, 1);
                        currentDate.setText(df.format(myCal.getTime()));
                        displayAppointmentForDate(currentDate.getText().toString());
                    }
                    if(weekA.isChecked()){
                        myCal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
                        myCal.add(Calendar.DATE, 7);
                        currentDate.setText(df.format(myCal.getTime()));
                        displayAppointmentForWeek(currentDate.getText().toString());
                    }

                    if(monthA.isChecked()){
                        myCal.add(Calendar.MONTH, 1);
                        currentDate.setText(df.format(myCal.getTime()));
                        displayAppointmentForMonth(currentDate.getText().toString());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }



    @Override
    protected Dialog onCreateDialog(int id) {
            return new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                    try {
                        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                        Date dObj = df.parse(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        Calendar myCal = Calendar.getInstance();
                        myCal.setTime(dObj);
                        currentDate.setText(df.format(myCal.getTime()));

                        if(dayA.isChecked()) {
                            currentDate.setText(df.format(myCal.getTime()));
                            displayAppointmentForDate(currentDate.getText().toString());
                        }
                        if(weekA.isChecked()){
                            currentDate.setText(df.format(myCal.getTime()));
                            displayAppointmentForWeek(currentDate.getText().toString());
                        }
                        if(monthA.isChecked()){
                            currentDate.setText(df.format(myCal.getTime()));
                            displayAppointmentForMonth(currentDate.getText().toString());
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }, y, m, d);
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
        if (id == R.id.menuVAEdit) {
            displayEditAppointmentScreen();
            return true;
        }
        if (id == R.id.menuVADelete) {
            deleteAppointment();
            return true;
        }

        if (id == R.id.menuVAPayment) {
            addPaymentDetails();
            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date dObj = df.parse(d + "/" + (m + 1) + "/" + y);
        Calendar myCal = Calendar.getInstance();
        myCal.setTime(dObj);

        currentDate.setText(df.format(myCal.getTime()));
        if(dayA.isChecked()) {
            displayAppointmentForDate(currentDate.getText().toString());
        }
        if(weekA.isChecked()){
            displayAppointmentForWeek(currentDate.getText().toString());
        }
        if(monthA.isChecked()){
            displayAppointmentForMonth(currentDate.getText().toString());
        }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int getSelectedAppointmentID(){
        int rowIndex=0,aID=-1;
        TableLayout aptTable = (TableLayout)findViewById(R.id.apptTable);
        while (rowIndex < aptTable.getChildCount()) {
            TableRow tr = (TableRow) aptTable.getChildAt(rowIndex);
            CheckBox cb = (CheckBox) tr.findViewById(R.id.cbVAR);

            if (cb.isChecked()) {
                TextView aid = (TextView) tr.findViewById(R.id.txtVARaptID);
                aID =Integer.parseInt(aid.getText().toString());
                break;
            }
            rowIndex=rowIndex+1;
        }
        return aID;
    }

    public void displayEditAppointmentScreen(){
        int aID = getSelectedAppointmentID();

        if(aID==-1){
            Toast.makeText(getApplicationContext(), "Select Record", Toast.LENGTH_SHORT).show();
        }else {
            appointmentDatabaseHandler adb = new appointmentDatabaseHandler(this);
            Intent i = new Intent(this, editAppointment.class);
            i.putExtra("aid", String.valueOf(aID));
            startActivity(i);
            adb.close();
            if (dayA.isChecked()) {
                displayAppointmentForDate(currentDate.getText().toString());
            }
            if (weekA.isChecked()) {
                displayAppointmentForWeek(currentDate.getText().toString());
            }
            if (monthA.isChecked()) {
                displayAppointmentForMonth(currentDate.getText().toString());
            }
        }
        /*
        int rowIndex=1;
        TableLayout aptTable = (TableLayout)findViewById(R.id.apptTable);


        try {
            while (rowIndex < aptTable.getChildCount()) {
                TableRow tr = (TableRow) aptTable.getChildAt(rowIndex);
                    //CheckBox cb = (CheckBox) tr.getChildAt(0);
                CheckBox cb = (CheckBox) tr.findViewById(R.id.cbVAR);
                    //TextView aid = (TextView) tr.getChildAt(1);
                TextView aid = (TextView) tr.findViewById(R.id.txtVARaptID);
                    if (cb.isChecked()) {
                        appointmentDatabaseHandler adb = new appointmentDatabaseHandler(this);
                        Intent i = new Intent(this, editAppointment.class);
                        i.putExtra("aid", aid.getText().toString());
                        startActivity(i);
                        adb.close();
                        if(dayA.isChecked()) {
                            displayAppointmentForDate(currentDate.getText().toString());
                        }
                        if(weekA.isChecked()){
                            displayAppointmentForWeek(currentDate.getText().toString());
                        }
                        if(monthA.isChecked()){
                            displayAppointmentForMonth(currentDate.getText().toString());
                        }
                    }
                rowIndex = rowIndex + 1;
                }
        }catch(Exception e){
            e.printStackTrace();
        }
        */
    }

    public void deleteAppointment(){
        int aID = getSelectedAppointmentID();

        if(aID==-1){
            Toast.makeText(getApplicationContext(), "Select Record", Toast.LENGTH_SHORT).show();
        }else {
            appointmentDatabaseHandler adb = new appointmentDatabaseHandler(this);
            adb.deleteAppointmentInfo(aID);
            adb.close();
            if (dayA.isChecked()) {
                displayAppointmentForDate(currentDate.getText().toString());
            }
            if (weekA.isChecked()) {
                displayAppointmentForWeek(currentDate.getText().toString());
            }
            if (monthA.isChecked()) {
                displayAppointmentForMonth(currentDate.getText().toString());
            }
        }
        /*
        int rowIndex=1;
        TableLayout aptTable = (TableLayout)findViewById(R.id.apptTable);
        try {
            while (rowIndex < aptTable.getChildCount()) {
                TableRow tr = (TableRow) aptTable.getChildAt(rowIndex);
                //CheckBox cb = (CheckBox) tr.getChildAt(0);
                CheckBox cb = (CheckBox) tr.findViewById(R.id.cbVAR);
                //TextView aid = (TextView) tr.getChildAt(1);
                TextView aid = (TextView) tr.findViewById(R.id.txtVARaptID);
                if (cb.isChecked()) {
                    appointmentDatabaseHandler adb = new appointmentDatabaseHandler(this);
                    adb.deleteAppointmentInfo(Integer.parseInt(aid.getText().toString()));
                    adb.close();
                    if(dayA.isChecked()) {
                        displayAppointmentForDate(currentDate.getText().toString());
                    }
                    if(weekA.isChecked()){
                        displayAppointmentForWeek(currentDate.getText().toString());
                    }
                    if(monthA.isChecked()){
                        displayAppointmentForMonth(currentDate.getText().toString());
                    }
                }
                rowIndex=rowIndex+1;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        */
    }

    public String getPatientNameFromAID(int aID){
        appointmentDatabaseHandler adb = new appointmentDatabaseHandler(this);
        patientDatabaseHandler pdb = new patientDatabaseHandler(this);
        appointmentInformation ai = adb.getAppointmentInfoByID(aID);
        patientInformation pi = pdb.getPatientInfoByID(ai.getPID());
        return pi.getName();
    }

    public void clearAllSelection(){

        int rowIndex = 1;
        TableLayout aptTable = (TableLayout) findViewById(R.id.apptTable);
        while (rowIndex < aptTable.getChildCount()) {
            TableRow tr = (TableRow) aptTable.getChildAt(rowIndex);
            CheckBox cb = (CheckBox) tr.findViewById(R.id.cbVAR);
            if (cb.isChecked()) {cb.setChecked(false);}
        }

    }

    public void addPaymentDetails() {
        final int aID = getSelectedAppointmentID();

        if(aID==-1){
            Toast.makeText(getApplicationContext(), "Select Record", Toast.LENGTH_SHORT).show();
        }else {
            final appointmentDatabaseHandler adb = new appointmentDatabaseHandler(this);
            patientDatabaseHandler pdb = new patientDatabaseHandler(this);

            final Dialog addPayment = new Dialog(this);
            addPayment.setContentView(R.layout.addpaymentdialog);
            addPayment.setTitle("Add Payment");

            TextView nameOfPatient = (TextView) addPayment.findViewById(R.id.txtAPDName);
            nameOfPatient.setText(getPatientNameFromAID(aID));

            final Spinner paymentInfo = (Spinner) addPayment.findViewById(R.id.spnAPDPayment);
            ArrayAdapter<CharSequence> adapterPayment = ArrayAdapter.createFromResource(viewAppiontmentPage.this, R.array.PaymentDenominations, android.R.layout.simple_spinner_item);
            paymentInfo.setAdapter(adapterPayment);

            final Spinner actualTreatmentInfo = (Spinner) addPayment.findViewById(R.id.spnAPDActualTreatment);
            ArrayAdapter<CharSequence> adapterTreatmentt = ArrayAdapter.createFromResource(viewAppiontmentPage.this, R.array.ProposedTreatment, android.R.layout.simple_spinner_item);
            actualTreatmentInfo.setAdapter(adapterTreatmentt);


            Button addButton = (Button)addPayment.findViewById(R.id.butAPDAdd);
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    appointmentInformation ai = adb.getAppointmentInfoByID(aID);
                    ai.setActualTreatment(actualTreatmentInfo.getSelectedItem().toString());
                    ai.setPayment(Integer.parseInt(paymentInfo.getSelectedItem().toString()));
                    adb.updateAppointmentInfo(ai);
                    Toast.makeText(getApplicationContext(), "Payment Added", Toast.LENGTH_SHORT).show();
                    addPayment.dismiss();
                    //clearAllSelection();
                }
            });
            addPayment.show();

        }

/*
        int rowIndex = 1;
        TableLayout aptTable = (TableLayout) findViewById(R.id.apptTable);


            while (rowIndex < aptTable.getChildCount()) {
                TableRow tr = (TableRow) aptTable.getChildAt(rowIndex);
                //CheckBox cb = (CheckBox) tr.getChildAt(0);
                CheckBox cb = (CheckBox) tr.findViewById(R.id.cbVAR);
                //TextView n = (TextView) tr.getChildAt(3);
                TextView n = (TextView) tr.findViewById(R.id.txtVARnamephone);
                //final TextView aid = (TextView) tr.getChildAt(1);
                final TextView aid = (TextView) tr.findViewById(R.id.txtVARaptID);
                final appointmentDatabaseHandler adb = new appointmentDatabaseHandler(this);
                if (cb.isChecked()) {

                    final Dialog addPayment = new Dialog(this);
                    addPayment.setContentView(R.layout.addpaymentdialog);
                    addPayment.setTitle("Add Payment");

                    TextView nameOfPatient = (TextView)addPayment.findViewById(R.id.txtAPDName);
                    String tempArr[] = n.getText().toString().split(" ");
                    nameOfPatient.setText(tempArr[0]);

                    final Spinner paymentInfo = (Spinner) addPayment.findViewById(R.id.spnAPDPayment);
                    ArrayAdapter<CharSequence> adapterPayment = ArrayAdapter.createFromResource(viewAppiontmentPage.this, R.array.PaymentDenominations, android.R.layout.simple_spinner_item);
                    paymentInfo.setAdapter(adapterPayment);

                    final Spinner actualTreatmentInfo = (Spinner) addPayment.findViewById(R.id.spnAPDActualTreatment);
                    ArrayAdapter<CharSequence> adapterTreatmentt = ArrayAdapter.createFromResource(viewAppiontmentPage.this, R.array.ProposedTreatment, android.R.layout.simple_spinner_item);
                    actualTreatmentInfo.setAdapter(adapterTreatmentt);


                    Button addButton = (Button)addPayment.findViewById(R.id.butAPDAdd);
                    addButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            appointmentInformation ai = adb.getAppointmentInfoByID(Integer.parseInt(aid.getText().toString()));
                            ai.setActualTreatment(actualTreatmentInfo.getSelectedItem().toString());
                            ai.setPayment(Integer.parseInt(paymentInfo.getSelectedItem().toString()));
                            adb.updateAppointmentInfo(ai);
                            Toast.makeText(getApplicationContext(), "Payment Added", Toast.LENGTH_SHORT).show();
                            addPayment.dismiss();
                        }
                    });

                    cb.setChecked(false);
                    addPayment.show();
                }
                adb.close();
                rowIndex=rowIndex+1;
            }
*/

    }



    public void displayAppointmentForMonth(String currentD)
    {
        try{
            TableLayout aptTable = (TableLayout) findViewById(R.id.apptTable);
            aptTable.removeAllViews();
            appointmentDatabaseHandler adb = new appointmentDatabaseHandler(this);
            patientDatabaseHandler pdb = new patientDatabaseHandler(this);

            LayoutInflater inflater = getLayoutInflater();

            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            Date dObj = df.parse(currentD);
            Calendar myCal = Calendar.getInstance();
            myCal.setTime(dObj);
            myCal.set(Calendar.DAY_OF_MONTH, 1);

            //TableRow trHeader = (TableRow) inflater.inflate(R.layout.tablerowappointmentstaticheader,aptTable,false);
            //aptTable.addView(trHeader);

            for(int index=0;index<myCal.getActualMaximum(Calendar.DAY_OF_MONTH);index++){
                //add day of the Month row
                TableRow trDate = (TableRow) inflater.inflate(R.layout.tablerowappointmentdayheader, aptTable, false);
                TextView tvDayOfWeek = (TextView) trDate.findViewById(R.id.txtVARowDayHeaderDate);
                TextView tvAptCount = (TextView)trDate.findViewById(R.id.txtVARowDayHeaderCount);
                tvDayOfWeek.setText(df.format(myCal.getTime()));
                List<appointmentInformation> appointmentList = adb.getAppointmentInfoByDate(df.format(myCal.getTime()));
                tvAptCount.setText(String.valueOf( appointmentList.size()));
                trDate.setBackgroundResource(R.color.material_blue_grey_800);
                if (appointmentList.size()!=0)
                    aptTable.addView(trDate);
                //add all appointments for given day

                boolean color = false;

                for (appointmentInformation ai : appointmentList) {
                    /*
                    TableRow tr = (TableRow) inflater.inflate(R.layout.tablerowforappointment, aptTable, false);
                    CheckBox cb = (CheckBox) tr.findViewById(R.id.aptcheckbox);

                    TextView aptID = (TextView) tr.findViewById(R.id.aptAptID);
                    aptID.setText(String.valueOf(ai.getAID()));

                    patientInformation pi = pdb.getPatientInfoByID(ai.getPID());
                    TextView name = (TextView) tr.findViewById(R.id.aptpatientName);
                    name.setText(pi.getName());

                    TextView dt = (TextView) tr.findViewById(R.id.aptdateTime);
                    dt.setText(ai.getaTime());

                    TextView pt = (TextView) tr.findViewById(R.id.aptproposedaction);
                    pt.setText(ai.getProposedTreatment());

                    aptTable.addView(tr);
                    */

                    TableRow tr = (TableRow) inflater.inflate(R.layout.tablerowforappointment2, aptTable, false);

                    if(color == false){
                        color=true;
                        tr.setBackgroundResource(R.drawable.shapeforappointmentrowdark);
                    }else{
                        color=false;
                        tr.setBackgroundResource(R.drawable.shapeforappointmentrow);
                    }
                    CheckBox cb = (CheckBox) tr.findViewById(R.id.cbVAR);

                    TextView aptID = (TextView) tr.findViewById(R.id.txtVARaptID);
                    aptID.setText(String.valueOf(ai.getAID()));

                    patientInformation pi = pdb.getPatientInfoByID(ai.getPID());
                    TextView name = (TextView) tr.findViewById(R.id.txtVARnamephone);
                    name.setText(pi.getName()+ " , " + pi.getPhone());

                    TextView dt = (TextView) tr.findViewById(R.id.txtVARdate);
                    dt.setText(ai.getaDate());

                    TextView tm = (TextView) tr.findViewById(R.id.txtVARtime);
                    tm.setText(ai.getaTime());

                    TextView pt = (TextView) tr.findViewById(R.id.txtVARtreatment);
                    pt.setText(ai.getProposedTreatment());

                    aptTable.addView(tr);
                }


                myCal.add(Calendar.DAY_OF_YEAR, 1);//next Day
            }
            pdb.close();
            adb.close();




        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void displayAppointmentForWeek(String currentD){
        try{
            TableLayout aptTable = (TableLayout) findViewById(R.id.apptTable);
            aptTable.removeAllViews();
            appointmentDatabaseHandler adb = new appointmentDatabaseHandler(this);
            patientDatabaseHandler pdb = new patientDatabaseHandler(this);

            LayoutInflater inflater = getLayoutInflater();

            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            Date dObj = df.parse(currentD);
            Calendar myCal = Calendar.getInstance();
            myCal.setTime(dObj);
            myCal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            //myCal.add(Calendar.DATE, 7);

            //add static row
            //TableRow trHeader = (TableRow) inflater.inflate(R.layout.tablerowappointmentstaticheader,aptTable,false);
            //aptTable.addView(trHeader);

            for(int index=0;index<7;index++){
                //add day of the week row
                TableRow trDate = (TableRow) inflater.inflate(R.layout.tablerowappointmentdayheader, aptTable, false);
                TextView tvDayOfWeek = (TextView) trDate.findViewById(R.id.txtVARowDayHeaderDate);
                TextView tvAptCount = (TextView)trDate.findViewById(R.id.txtVARowDayHeaderCount);
                tvDayOfWeek.setText(df.format(myCal.getTime()));
                List<appointmentInformation> appointmentList = adb.getAppointmentInfoByDate(df.format(myCal.getTime()));
                tvAptCount.setText(String.valueOf( appointmentList.size()));
                trDate.setBackgroundResource(R.color.material_blue_grey_800);
                if (appointmentList.size()!=0)
                aptTable.addView(trDate);
                //add all appointments for given day
                boolean color=false;

                for (appointmentInformation ai : appointmentList) {
                    /*
                    TableRow tr = (TableRow) inflater.inflate(R.layout.tablerowforappointment, aptTable, false);
                    CheckBox cb = (CheckBox) tr.findViewById(R.id.aptcheckbox);

                    TextView aptID = (TextView) tr.findViewById(R.id.aptAptID);
                    aptID.setText(String.valueOf(ai.getAID()));

                    patientInformation pi = pdb.getPatientInfoByID(ai.getPID());
                    TextView name = (TextView) tr.findViewById(R.id.aptpatientName);
                    name.setText(pi.getName());

                    TextView dt = (TextView) tr.findViewById(R.id.aptdateTime);
                    dt.setText(ai.getaTime());

                    TextView pt = (TextView) tr.findViewById(R.id.aptproposedaction);
                    pt.setText(ai.getProposedTreatment());

                    aptTable.addView(tr);*/
                    TableRow tr = (TableRow) inflater.inflate(R.layout.tablerowforappointment2, aptTable, false);
                    if(color == false){
                        color=true;
                        tr.setBackgroundResource(R.drawable.shapeforappointmentrowdark);
                    }else{
                        color=false;
                        tr.setBackgroundResource(R.drawable.shapeforappointmentrow);
                    }

                    CheckBox cb = (CheckBox) tr.findViewById(R.id.cbVAR);

                    TextView aptID = (TextView) tr.findViewById(R.id.txtVARaptID);
                    aptID.setText(String.valueOf(ai.getAID()));

                    patientInformation pi = pdb.getPatientInfoByID(ai.getPID());
                    TextView name = (TextView) tr.findViewById(R.id.txtVARnamephone);
                    name.setText(pi.getName()+ " , " + pi.getPhone());

                    TextView dt = (TextView) tr.findViewById(R.id.txtVARdate);
                    dt.setText(ai.getaDate());

                    TextView tm = (TextView) tr.findViewById(R.id.txtVARtime);
                    tm.setText(ai.getaTime());

                    TextView pt = (TextView) tr.findViewById(R.id.txtVARtreatment);
                    pt.setText(ai.getProposedTreatment());

                    aptTable.addView(tr);
                }


                myCal.add(Calendar.DAY_OF_YEAR, 1);//next Day
            }
            pdb.close();
            adb.close();

        }catch (Exception e){e.printStackTrace();
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
            //TableRow trHeader = (TableRow) inflater.inflate(R.layout.tablerowappointmentstaticheader,aptTable,false);
            //aptTable.addView(trHeader);
            boolean color=false;

            for (appointmentInformation ai : appointmentList) {
                /*
                TableRow tr = (TableRow) inflater.inflate(R.layout.tablerowforappointment, aptTable, false);
                CheckBox cb = (CheckBox) tr.findViewById(R.id.aptcheckbox);

                TextView aptID = (TextView) tr.findViewById(R.id.aptAptID);
                aptID.setText(String.valueOf(ai.getAID()));

                patientInformation pi = pdb.getPatientInfoByID(ai.getPID());
                TextView name = (TextView) tr.findViewById(R.id.aptpatientName);
                name.setText(pi.getName());

                TextView dt = (TextView) tr.findViewById(R.id.aptdateTime);
                dt.setText(ai.getaTime());

                TextView pt = (TextView) tr.findViewById(R.id.aptproposedaction);
                pt.setText(ai.getProposedTreatment());
                */
                TableRow tr = (TableRow) inflater.inflate(R.layout.tablerowforappointment2, aptTable, false);
                if(color == false){
                    color=true;
                    tr.setBackgroundResource(R.drawable.shapeforappointmentrowdark);
                }else{
                    color=false;
                    tr.setBackgroundResource(R.drawable.shapeforappointmentrow);
                }
                CheckBox cb = (CheckBox) tr.findViewById(R.id.cbVAR);

                TextView aptID = (TextView) tr.findViewById(R.id.txtVARaptID);
                aptID.setText(String.valueOf(ai.getAID()));

                patientInformation pi = pdb.getPatientInfoByID(ai.getPID());
                TextView name = (TextView) tr.findViewById(R.id.txtVARnamephone);
                name.setText(pi.getName()+ " , " + pi.getPhone());

                TextView dt = (TextView) tr.findViewById(R.id.txtVARdate);
                dt.setText(ai.getaDate());

                TextView tm = (TextView) tr.findViewById(R.id.txtVARtime);
                tm.setText(ai.getaTime());

                TextView pt = (TextView) tr.findViewById(R.id.txtVARtreatment);
                pt.setText(ai.getProposedTreatment());

                aptTable.addView(tr);
            }

            pdb.close();
            adb.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
/* Not requried hav function to display apt as per dates
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
                //dt.setText(ai.getaDate() + " - " + ai.getaTime());
                dt.setText(ai.getaTime());

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
    */
/*
    public void displayEditAppointmentDialog()
    {
        int rowIndex=0;
        TableLayout aptTable = (TableLayout)findViewById(R.id.apptTable);


        try{
            while (rowIndex < aptTable.getChildCount()){
                TableRow tr = (TableRow)aptTable.getChildAt(rowIndex);
                CheckBox cb = (CheckBox)tr.getChildAt(0);
                TextView currentTime = (TextView)tr.getChildAt(2);
                TextView ptName = (TextView)tr.getChildAt(3);

                if(cb.isChecked()){
                    final appointmentDatabaseHandler adb = new appointmentDatabaseHandler(this);
                    final appointmentInformation ai = adb.getAppointmentInfoByDateTime(currentDate.getText().toString(),currentTime.getText().toString());

                    final Dialog editAppointment = new Dialog(this);
                    editAppointment.setContentView(R.layout.editappointmentdialog);
                    editAppointment.setTitle("Edit Appointment");

                    patientDatabaseHandler pdb = new patientDatabaseHandler(this);
                    List<patientInformation> patientList = pdb.getAllPatientInfo();
                    String [] patientNames = new String[pdb.getPatientCount()];
                    int i=0;
                    for(patientInformation pi : patientList){patientNames[i]=pi.getName() + "-" + pi.getID() ;i=i+1;
                    }
                    final AutoCompleteTextView actv = (AutoCompleteTextView)editAppointment.findViewById(R.id.autotxtANAPatientName);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,patientNames);
                    actv.setAdapter(adapter);
                    actv.setText(ptName.getText().toString());

                    final EditText edate = (EditText)editAppointment.findViewById(R.id.txtANADate);
                    edate.setText(ai.getaDate());

                    final EditText etime = (EditText)editAppointment.findViewById(R.id.txtANATime);
                    etime.setText(ai.getaTime());

                    final EditText ept = (EditText)editAppointment.findViewById(R.id.txtANApt);
                    ept.setText(ai.getProposedTreatment());

                    final EditText etd = (EditText)editAppointment.findViewById(R.id.txtANAtd);
                    etd.setText(ai.getToothDetails());

                    Button editDateButton = (Button)editAppointment.findViewById(R.id.showANADateDialog);
                    editDateButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showDialog(1);
                        }
                    });

                    Button editTimeButton = (Button)editAppointment.findViewById(R.id.showANATimeDialog);
                    editTimeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {showDialog(2);
                        }
                    });



                    editedDate = (EditText)editAppointment.findViewById(R.id.txtANADate);
                    editedDate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {showDialog(1);
                        }
                    });

                    editedTime=(EditText)editAppointment.findViewById(R.id.txtANATime);
                    editedTime.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {showDialog(2);
                        }
                    });

                    Button editA = (Button)editAppointment.findViewById(R.id.butANAEditAppointment);
                    editA.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String pid = actv.getText().toString().split("-")[1];
                            appointmentInformation newAI = new appointmentInformation(ai.getAID(),Integer.parseInt(pid),edate.getText().toString(),etime.getText().toString(),ept.getText().toString(),etd.getText().toString());
                            adb.updateAppointmentInfo(newAI);
                            adb.close();
                            editAppointment.dismiss();
                            displayAppointmentForDate(currentDate.getText().toString());
                        }
                    });

                    editAppointment.show();
                    rowIndex=rowIndex+1;
                }

                rowIndex=rowIndex+1;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
*/


}
