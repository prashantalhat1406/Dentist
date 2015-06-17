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
import android.view.WindowManager;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class viewAppiontmentPage extends ActionBarActivity {
    int y, m, d,h,mi;
    Button bd;
    TextView currentDate;
    RadioButton dayA,weekA,monthA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Calendar cal;
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
        }catch (Exception e){e.printStackTrace();}
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
            if(dayA.isChecked())
                displayAppointmentForDate(currentDate.getText().toString());
            if(weekA.isChecked())
                displayAppointmentForWeek(currentDate.getText().toString());
            if(monthA.isChecked())
                displayAppointmentForMonth(currentDate.getText().toString());
        }catch (Exception e){e.printStackTrace();}
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


    public void displayEditAppointmentScreen() {

        final Dialog test = new Dialog(this);
        test.setContentView(R.layout.testdialog);
        test.setTitle("Test Date Dialog");
        test.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, 450);

        Button b = (Button)test.findViewById(R.id.buttestdialog);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(0);
            }
        });

        /*
        int aID = getSelectedAppointmentID();
        if (aID == -1) {
            Toast.makeText(getApplicationContext(), "Select Record", Toast.LENGTH_SHORT).show();
        } else {
            Intent i = new Intent(this, editAppointment.class);
            i.putExtra("aid", String.valueOf(aID));
            startActivity(i);
            if (dayA.isChecked())
                displayAppointmentForDate(currentDate.getText().toString());
            if (weekA.isChecked())
                displayAppointmentForWeek(currentDate.getText().toString());
            if (monthA.isChecked())
                displayAppointmentForMonth(currentDate.getText().toString());
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
            if (dayA.isChecked())
                displayAppointmentForDate(currentDate.getText().toString());
            if (weekA.isChecked())
                displayAppointmentForWeek(currentDate.getText().toString());
            if (monthA.isChecked())
                displayAppointmentForMonth(currentDate.getText().toString());
        }
    }

    public String getPatientNameFromAID(int aID){
        appointmentDatabaseHandler adb = new appointmentDatabaseHandler(this);
        patientDatabaseHandler pdb = new patientDatabaseHandler(this);
        appointmentInformation ai = adb.getAppointmentInfoByID(aID);
        patientInformation pi = pdb.getPatientInfoByID(ai.getPID());
        return pi.getName();
    }



    public void addPaymentDetails() {
        final int aID = getSelectedAppointmentID();
        if(aID==-1){
            Toast.makeText(getApplicationContext(), "Select Record", Toast.LENGTH_SHORT).show();
        }else {
            final appointmentDatabaseHandler adb = new appointmentDatabaseHandler(this);
            final Dialog addPayment = new Dialog(this);
            addPayment.setContentView(R.layout.addpaymentdialog);
            addPayment.setTitle("Add Payment");
            addPayment.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, 450);
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
                }
            });
            addPayment.show();
        }
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
            for(int index=0;index<myCal.getActualMaximum(Calendar.DAY_OF_MONTH);index++){
                TableRow trDate = (TableRow) inflater.inflate(R.layout.tablerowappointmentdayheader, aptTable, false);
                TextView tvDayOfWeek = (TextView) trDate.findViewById(R.id.txtVARowDayHeaderDate);
                TextView tvAptCount = (TextView)trDate.findViewById(R.id.txtVARowDayHeaderCount);
                tvDayOfWeek.setText(df.format(myCal.getTime()));
                List<appointmentInformation> appointmentList = adb.getAppointmentInfoByDate(df.format(myCal.getTime()));
                tvAptCount.setText(String.valueOf( appointmentList.size()));
                trDate.setBackgroundResource(R.drawable.shapeofappointmentdayheader);
                if (appointmentList.size()!=0)
                    aptTable.addView(trDate);
                boolean color = false;
                for (appointmentInformation ai : appointmentList) {
                    TableRow tr = (TableRow) inflater.inflate(R.layout.tablerowforappointment2, aptTable, false);
                    if(!color ){
                        color=true;
                        tr.setBackgroundResource(R.drawable.shapeforappointmentrowdark);
                    }else{
                        color=false;
                        tr.setBackgroundResource(R.drawable.shapeforappointmentrow);
                    }
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
                myCal.add(Calendar.DAY_OF_YEAR, 1);
            }
            pdb.close();
            adb.close();
        }catch (Exception e){e.printStackTrace();}
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
            for(int index=0;index<7;index++){
                TableRow trDate = (TableRow) inflater.inflate(R.layout.tablerowappointmentdayheader, aptTable, false);
                TextView tvDayOfWeek = (TextView) trDate.findViewById(R.id.txtVARowDayHeaderDate);
                TextView tvAptCount = (TextView)trDate.findViewById(R.id.txtVARowDayHeaderCount);
                tvDayOfWeek.setText(df.format(myCal.getTime()));
                List<appointmentInformation> appointmentList = adb.getAppointmentInfoByDate(df.format(myCal.getTime()));
                tvAptCount.setText(String.valueOf( appointmentList.size()));
                trDate.setBackgroundResource(R.drawable.shapeofappointmentdayheader);
                if (appointmentList.size()!=0)
                aptTable.addView(trDate);
                boolean color=false;
                for (appointmentInformation ai : appointmentList) {
                    TableRow tr = (TableRow) inflater.inflate(R.layout.tablerowforappointment2, aptTable, false);
                    if(!color ){
                        color=true;
                        tr.setBackgroundResource(R.drawable.shapeforappointmentrowdark);
                    }else{
                        color=false;
                        tr.setBackgroundResource(R.drawable.shapeforappointmentrow);
                    }
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
                myCal.add(Calendar.DAY_OF_YEAR, 1);
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
            boolean color=false;
            for (appointmentInformation ai : appointmentList) {
                TableRow tr = (TableRow) inflater.inflate(R.layout.tablerowforappointment2, aptTable, false);
                if(!color){
                    color=true;
                    tr.setBackgroundResource(R.drawable.shapeforappointmentrowdark);
                }else{
                    color=false;
                    tr.setBackgroundResource(R.drawable.shapeforappointmentrow);
                }
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
        }catch (Exception e){e.printStackTrace();}
    }



}
