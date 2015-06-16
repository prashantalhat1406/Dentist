package com.example.prashant.dentist;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.androidplot.Plot;
import com.androidplot.ui.LayoutManager;
import com.androidplot.ui.SizeLayoutType;
import com.androidplot.ui.SizeMetric;
import com.androidplot.ui.SizeMetrics;
import com.androidplot.ui.widget.TextLabelWidget;
import com.androidplot.xy.BarFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;


public class reportGraphs extends ActionBarActivity {
    private Calendar cal;
    TextView currentDate;
    int y, m, d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_graphs);

        currentDate = (TextView)findViewById(R.id.txtVRCurrentDate);

        cal = Calendar.getInstance();
        y=cal.get(Calendar.YEAR);
        m=cal.get(Calendar.MONTH);
        d=cal.get(Calendar.DAY_OF_MONTH);

        Button bAppointmentwise = (Button)findViewById(R.id.butVRAppointmentwise);
        bAppointmentwise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayAppointmentWiseReport();
            }
        });

        Button bDate = (Button)findViewById(R.id.butVRDateDialog);
        bDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {showDialog(0);
            }
        });


/*
        appointmentDatabaseHandler adb = new appointmentDatabaseHandler(this);
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Calendar myCal = Calendar.getInstance();

        Number[]yValues = adb.getAppointmentCountMonthWise(df.format(myCal.getTime()));
        Number[]xValues = {1,2,3,4,5,6,7,8,9,10,11,12};

        XYPlot plot = (XYPlot)findViewById(R.id.plotarea);
        XYSeries data = new SimpleXYSeries(Arrays.asList(xValues),Arrays.asList(yValues),"MonthWise");
        BarFormatter barFormatter = new BarFormatter(Color.RED,Color.YELLOW);


        plot.setTitle("Appointment Count MonthWise");



        plot.addSeries(data,barFormatter);
        */



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
/*
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
                    */

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, y, m, d);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_report_graphs, menu);
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

    public void displayAppointmentWiseReport(){
        try {
            appointmentDatabaseHandler adb = new appointmentDatabaseHandler(this);
            int[] paymentInfo = adb.getPaymentInfoMonthWise(currentDate.getText().toString());
            int[] appointmentCount = adb.getAppointmentCountMonthWise(currentDate.getText().toString());
            String[] monthNames = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};

            LayoutInflater inflater = getLayoutInflater();

            TableLayout rptTable = (TableLayout) findViewById(R.id.reportTable);

            rptTable.removeAllViews();
            boolean color = false;
            for (int i = 0; i < 12; i++) {

                TableRow tr = (TableRow) inflater.inflate(R.layout.tablerowforreport, rptTable, false);

                TextView ref = (TextView) tr.findViewById(R.id.txtVRReference);
                ref.setText(monthNames[i]);

                TextView stat1 = (TextView) tr.findViewById(R.id.txtVRStat1);
                stat1.setText(String.valueOf(appointmentCount[i]));

                TextView stat2 = (TextView) tr.findViewById(R.id.txtVRStat2);
                stat2.setText(String.valueOf(paymentInfo[i]));

                if (color == false) {
                    tr.setBackgroundResource(R.drawable.shapeofreportrowdark);
                    ref.setBackgroundResource(R.drawable.shapeofreportrowdark);
                    stat1.setBackgroundResource(R.drawable.shapeofreportrowdark);
                    stat2.setBackgroundResource(R.drawable.shapeofreportrowdark);
                    color = true;
                } else {
                    tr.setBackgroundResource(R.drawable.shapeofreportrowlight);
                    ref.setBackgroundResource(R.drawable.shapeofreportrowlight);
                    stat1.setBackgroundResource(R.drawable.shapeofreportrowlight);
                    stat2.setBackgroundResource(R.drawable.shapeofreportrowlight);
                    color = false;
                }




                rptTable.addView(tr);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
