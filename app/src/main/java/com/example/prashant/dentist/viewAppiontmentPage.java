package com.example.prashant.dentist;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
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
import android.widget.EditText;
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


public class viewAppiontmentPage extends ActionBarActivity {
    int y, m, d,h,mi;
    Button bd;
    TextView currentDate;
    RadioButton dayA,weekA,monthA;
    EditText EADdate, EADtime;
    boolean currentDateFlag;

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
                String []temp = currentDate.getText().toString().split("/");
                String dateInYYYYMMDD = temp[2] + "/"+temp[1] + "/"+temp[0];
                //displayAppointmentForDate(currentDate.getText().toString());
                displayAppointmentForDate(dateInYYYYMMDD);
            }
        });
        weekA = (RadioButton)findViewById(R.id.rdbVAWeek);
        weekA.setChecked(false);
        weekA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String []temp = currentDate.getText().toString().split("/");
                String dateInYYYYMMDD = temp[2] + "/"+temp[1] + "/"+temp[0];
                //displayAppointmentForWeek(currentDate.getText().toString());
                displayAppointmentForWeek(dateInYYYYMMDD);
            }
        });
        monthA = (RadioButton)findViewById(R.id.rdbVAMonth);
        monthA.setChecked(false);
        monthA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(monthA.isChecked()){
                    String []temp = currentDate.getText().toString().split("/");
                    String dateInYYYYMMDD = temp[2] + "/"+temp[1] + "/"+temp[0];
                    //displayAppointmentForMonth(currentDate.getText().toString());
                    displayAppointmentForMonth(dateInYYYYMMDD);
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
            String []temp = currentDate.getText().toString().split("/");
            String dateInYYYYMMDD = temp[2] + "/"+temp[1] + "/"+temp[0];
            //displayAppointmentForDate(currentDate.getText().toString());
            displayAppointmentForDate(dateInYYYYMMDD);
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
                        String []temp = currentDate.getText().toString().split("/");
                        String dateInYYYYMMDD = temp[2] + "/"+temp[1] + "/"+temp[0];
                        //displayAppointmentForDate(currentDate.getText().toString());
                        displayAppointmentForDate(dateInYYYYMMDD);
                    }
                    if(weekA.isChecked()){
                        myCal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                        myCal.add(Calendar.DATE, -7);
                        currentDate.setText(df.format(myCal.getTime()));
                        String []temp = currentDate.getText().toString().split("/");
                        String dateInYYYYMMDD = temp[2] + "/"+temp[1] + "/"+temp[0];

                        //displayAppointmentForWeek(currentDate.getText().toString());
                        displayAppointmentForWeek(dateInYYYYMMDD);
                    }
                    if(monthA.isChecked()){
                        myCal.add(Calendar.MONTH, -1);
                        currentDate.setText(df.format(myCal.getTime()));
                        String []temp = currentDate.getText().toString().split("/");
                        String dateInYYYYMMDD = temp[2] + "/"+temp[1] + "/"+temp[0];
                        //displayAppointmentForMonth(currentDate.getText().toString());
                        displayAppointmentForMonth(dateInYYYYMMDD);
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
                        String []temp = currentDate.getText().toString().split("/");
                        String dateInYYYYMMDD = temp[2] + "/"+temp[1] + "/"+temp[0];
                        //displayAppointmentForDate(currentDate.getText().toString());
                        displayAppointmentForDate(dateInYYYYMMDD);
                    }
                    if(weekA.isChecked()){
                        myCal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                        myCal.add(Calendar.DATE, 7);
                        currentDate.setText(df.format(myCal.getTime()));
                        String []temp = currentDate.getText().toString().split("/");
                        String dateInYYYYMMDD = temp[2] + "/"+temp[1] + "/"+temp[0];
                        //displayAppointmentForWeek(currentDate.getText().toString());
                        displayAppointmentForWeek(dateInYYYYMMDD);
                    }

                    if(monthA.isChecked()){
                        myCal.add(Calendar.MONTH, 1);
                        currentDate.setText(df.format(myCal.getTime()));
                        String []temp = currentDate.getText().toString().split("/");
                        String dateInYYYYMMDD = temp[2] + "/"+temp[1] + "/"+temp[0];
                        //displayAppointmentForMonth(currentDate.getText().toString());
                        displayAppointmentForMonth(dateInYYYYMMDD);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.menuVAEdit) {
            //displayEditAppointmentScreen();
            displayEDITAppointmentDialog();
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
            clearAllSelection();
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            Date dObj = df.parse(d + "/" + (m + 1) + "/" + y);
            Calendar myCal = Calendar.getInstance();
            myCal.setTime(dObj);
            currentDate.setText(df.format(myCal.getTime()));
            String []temp = currentDate.getText().toString().split("/");
            String dateInYYYYMMDD = temp[2] + "/"+temp[1] + "/"+temp[0];
            //currentDate.setText(d + "/" + (m + 1) + "/" + y);
            if(dayA.isChecked())
                //displayAppointmentForDate(currentDate.getText().toString());
                displayAppointmentForDate(dateInYYYYMMDD);
            if(weekA.isChecked())
                //displayAppointmentForWeek(currentDate.getText().toString());
                displayAppointmentForWeek(dateInYYYYMMDD);
            if(monthA.isChecked())
                //displayAppointmentForMonth(currentDate.getText().toString());
                displayAppointmentForMonth(dateInYYYYMMDD);
        }catch (Exception e){e.printStackTrace();}
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        if(id==0) {
            return new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    try {
                        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                        Date dObj = df.parse(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        Calendar myCal = Calendar.getInstance();
                        myCal.setTime(dObj);
                        currentDate.setText(df.format(myCal.getTime()));
                        String []temp = currentDate.getText().toString().split("/");
                        String dateInYYYYMMDD = temp[2] + "/"+temp[1] + "/"+temp[0];
                        //currentDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        if (dayA.isChecked()) {
                            //currentDate.setText(df.format(myCal.getTime()));
                            //displayAppointmentForDate(currentDate.getText().toString());
                            displayAppointmentForDate(dateInYYYYMMDD);
                        }
                        if (weekA.isChecked()) {
                            //currentDate.setText(df.format(myCal.getTime()));
                            //displayAppointmentForWeek(currentDate.getText().toString());
                            displayAppointmentForWeek(dateInYYYYMMDD);
                        }
                        if (monthA.isChecked()) {
                            //currentDate.setText(df.format(myCal.getTime()));
                            //displayAppointmentForMonth(currentDate.getText().toString());
                            displayAppointmentForMonth(dateInYYYYMMDD);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, y, m, d);
        } /*DONE THIS LINE */
        else {
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
                                                        EADtime.setText(hourOfDay + ":0" + minute);
                                                    else
                                                        EADtime.setText(hourOfDay + ":" + minute);
                                                }
                                                if (hourOfDay > 12) {
                                                    hourOfDay = hourOfDay - 12;
                                                    if (String.valueOf(minute).length() == 1)
                                                        EADtime.setText(hourOfDay + ":0" + minute);
                                                    else
                                                        EADtime.setText(hourOfDay + ":" + minute);
                                                }
                                            }
                                        } else {
                                            if (hourOfDay <= 12) {
                                                if (String.valueOf(minute).length() == 1)
                                                    EADtime.setText(hourOfDay + ":0" + minute);
                                                else
                                                    EADtime.setText(hourOfDay + ":" + minute);
                                            }
                                            if (hourOfDay > 12) {
                                                hourOfDay = hourOfDay - 12;
                                                if (String.valueOf(minute).length() == 1)
                                                    EADtime.setText(hourOfDay + ":0" + minute);
                                                else
                                                    EADtime.setText(hourOfDay + ":" + minute);
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
        }
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_appiontment_page, menu);
        return true;
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

    public void clearAllSelection(){
        TableLayout pt = (TableLayout) findViewById(R.id.apptTable);
        int i =0;
        try {
            while (i < pt.getChildCount()) {
                TableRow tr = (TableRow) pt.findViewById(R.id.trAppointment);
                CheckBox cb = (CheckBox) tr.findViewById(R.id.cbVAR);
                if (cb.isChecked()) {cb.setChecked(false);}
                i = i + 1;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void displayEDITAppointmentDialog() {
        final int aID = getSelectedAppointmentID();
        if (aID == -1) {
            Toast.makeText(getApplicationContext(), "Select Record", Toast.LENGTH_SHORT).show();
        } else {
            final Dialog editAppointment = new Dialog(this);
            editAppointment.setContentView(R.layout.editappointmentdialog);
            editAppointment.setTitle("Edit Appointment");
            editAppointment.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

            final appointmentDatabaseHandler adb = new appointmentDatabaseHandler(this);
            appointmentInformation ai = adb.getAppointmentInfoByID(aID);

            patientDatabaseHandler pdb = new patientDatabaseHandler(this);
            patientInformation pi = pdb.getPatientInfoByID(ai.getPID());

            TextView name = (TextView)editAppointment.findViewById(R.id.autotxtEADName);
            name.setText(pi.getName());

            EADdate = (EditText)editAppointment.findViewById(R.id.txtEADDate);

            try {
                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                Date dObj = df.parse(ai.getaDate());
                Calendar myCal = Calendar.getInstance();
                myCal.setTime(dObj);
                df = new SimpleDateFormat("dd/MM/yyyy");
                EADdate.setText(df.format(myCal.getTime()));
            }catch (Exception e){e.printStackTrace();}

            EADtime = (EditText)editAppointment.findViewById(R.id.txtEADTime);
            EADtime.setText(ai.getaTime());

            final Spinner treatment = (Spinner)editAppointment.findViewById(R.id.spnEADTreamnet);
            ArrayAdapter<CharSequence> adapterpt = ArrayAdapter.createFromResource(this,R.array.ProposedTreatment, android.R.layout.simple_spinner_item);
            treatment.setAdapter(adapterpt);
            treatment.setSelection(adapterpt.getPosition(ai.getProposedTreatment()));

            //final EditText toothdetails = (EditText)editAppointment.findViewById(R.id.txtEADToothDetails);
            //toothdetails.setText(ai.getToothDetails());

            Button bdateDialog = (Button)editAppointment.findViewById(R.id.butEADShowDate);
            bdateDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(2);
                }
            });
            Button btimeDialog = (Button)editAppointment.findViewById(R.id.butEADShowTime);
            btimeDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(3);
                }
            });


            Button bEdit = (Button) editAppointment.findViewById(R.id.butEADButton);
            bEdit.setText("EDIT");
            bEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    appointmentInformation ai = adb.getAppointmentInfoByID(aID);
                    if (EADdate.getText().length() == 0) {
                        Toast.makeText(getApplicationContext(), "Enter Date", Toast.LENGTH_SHORT).show();
                    } else {
                        if (EADtime.getText().length() == 0) {
                            Toast.makeText(getApplicationContext(), "Enter Time", Toast.LENGTH_SHORT).show();
                        } else {
                            /*if (toothdetails.getText().length() == 0) {
                                Toast.makeText(getApplicationContext(), "Enter ToothDetails", Toast.LENGTH_SHORT).show();
                            } else {*/
                                String []temp = EADdate.getText().toString().split("/");
                                String dateInYYYYMMDD = temp[2] + "/"+temp[1] + "/"+temp[0];
                                //ai = new appointmentInformation(ai.getAID(), ai.getPID(), EADdate.getText().toString(), EADtime.getText().toString(), treatment.getSelectedItem().toString(), toothdetails.getText().toString());
                                ai = new appointmentInformation(ai.getAID(), ai.getPID(), dateInYYYYMMDD, EADtime.getText().toString(), treatment.getSelectedItem().toString());//, toothdetails.getText().toString());
                                adb.updateAppointmentInfo(ai);
                                Toast.makeText(getApplicationContext(), "Record Edited", Toast.LENGTH_SHORT).show();
                                editAppointment.dismiss();
                            //}
                        }
                    }

                    //Display Appointments as per selection
                    String []temp = currentDate.getText().toString().split("/");
                    String dateInYYYYMMDD = temp[2] + "/"+temp[1] + "/"+temp[0];
                    if (dayA.isChecked())
                        displayAppointmentForDate(dateInYYYYMMDD);
                    if (weekA.isChecked())
                        displayAppointmentForWeek(dateInYYYYMMDD);
                    if (monthA.isChecked())
                        displayAppointmentForMonth(dateInYYYYMMDD);
                    /*
                    if (dayA.isChecked())
                        displayAppointmentForDate(currentDate.getText().toString());
                    if (weekA.isChecked())
                        displayAppointmentForWeek(currentDate.getText().toString());
                    if (monthA.isChecked())
                        displayAppointmentForMonth(currentDate.getText().toString());
                       */
                }
            });

            editAppointment.show();
            clearAllSelection();
        }


    }



    public void deleteAppointment(){
        int aID = getSelectedAppointmentID();
        if(aID==-1){
            Toast.makeText(getApplicationContext(), "Select Record", Toast.LENGTH_SHORT).show();
        }else {
            appointmentDatabaseHandler adb = new appointmentDatabaseHandler(this);
            adb.deleteAppointmentInfo(aID);
            adb.close();
            String []temp = currentDate.getText().toString().split("/");
            String dateInYYYYMMDD = temp[2] + "/"+temp[1] + "/"+temp[0];
            if (dayA.isChecked())
                displayAppointmentForDate(dateInYYYYMMDD);
            if (weekA.isChecked())
                displayAppointmentForWeek(dateInYYYYMMDD);
            if (monthA.isChecked())
                displayAppointmentForMonth(dateInYYYYMMDD);
            /*
            if (dayA.isChecked())
                displayAppointmentForDate(currentDate.getText().toString());
            if (weekA.isChecked())
                displayAppointmentForWeek(currentDate.getText().toString());
            if (monthA.isChecked())
                displayAppointmentForMonth(currentDate.getText().toString());
                */
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
            addPayment.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT );
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
            clearAllSelection();
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
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            Date dObj = df.parse(currentD);
            Calendar myCal = Calendar.getInstance();
            myCal.setTime(dObj);
            myCal.set(Calendar.DAY_OF_MONTH, 1);
            for(int index=0;index<myCal.getActualMaximum(Calendar.DAY_OF_MONTH);index++){
                TableRow trDate = (TableRow) inflater.inflate(R.layout.tablerowappointmentdayheader, aptTable, false);
                TextView tvDayOfWeek = (TextView) trDate.findViewById(R.id.txtVARowDayHeaderDate);
                TextView tvAptCount = (TextView)trDate.findViewById(R.id.txtVARowDayHeaderCount);
                df = new SimpleDateFormat("dd/MM/yyyy");
                tvDayOfWeek.setText(df.format(myCal.getTime()));
                df = new SimpleDateFormat("yyyy/MM/dd");
                //List<appointmentInformation> appointmentList = adb.getAppointmentInfoByDate(df.format(myCal.getTime()));
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
                    //dt.setText(ai.getaDate());
                    df = new SimpleDateFormat("yyyy/MM/dd");
                    dObj = df.parse(ai.getaDate());
                    myCal.setTime(dObj);
                    df = new SimpleDateFormat("dd/MM/yyyy");
                    dt.setText(df.format(myCal.getTime()));
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
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            Date dObj = df.parse(currentD);
            Calendar myCal = Calendar.getInstance();
            myCal.setTime(dObj);
            myCal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            for(int index=0;index<7;index++){
                TableRow trDate = (TableRow) inflater.inflate(R.layout.tablerowappointmentdayheader, aptTable, false);
                TextView tvDayOfWeek = (TextView) trDate.findViewById(R.id.txtVARowDayHeaderDate);
                TextView tvAptCount = (TextView)trDate.findViewById(R.id.txtVARowDayHeaderCount);
                df = new SimpleDateFormat("dd/MM/yyyy");
                tvDayOfWeek.setText(df.format(myCal.getTime()));
                df = new SimpleDateFormat("yyyy/MM/dd");
                //List<appointmentInformation> appointmentList = adb.getAppointmentInfoByDate(df.format(myCal.getTime()));
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
                    //dt.setText(ai.getaDate());
                    df = new SimpleDateFormat("yyyy/MM/dd");
                    dObj = df.parse(ai.getaDate());
                    myCal.setTime(dObj);
                    df = new SimpleDateFormat("dd/MM/yyyy");
                    dt.setText(df.format(myCal.getTime()));
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
            //List<appointmentInformation> appointmentList = adb.getAppointmentInfoByDate(myCal.YEAR + "/" + myCal.MONTH + "/" + myCal.DAY_OF_MONTH);
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
                //dt.setText(ai.getaDate());
                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                Date dObj = df.parse(ai.getaDate());
                Calendar myCal = Calendar.getInstance();
                myCal.setTime(dObj);
                df = new SimpleDateFormat("dd/MM/yyyy");
                dt.setText(df.format(myCal.getTime()));
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

