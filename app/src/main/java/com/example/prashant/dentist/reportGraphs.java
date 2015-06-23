package com.example.prashant.dentist;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Calendar;



public class reportGraphs extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_graphs);


        Button bAppointmentwise = (Button)findViewById(R.id.butVRAppointmentwise);
        bAppointmentwise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayAppointmentWiseReport();
            }
        });

        Button bTreatmentwise = (Button)findViewById(R.id.butVRTreatmentwise);
        bTreatmentwise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayTreatmentWiseReport();

            }
        });
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


        return super.onOptionsItemSelected(item);
    }
    public void displayTreatmentWiseReport(){
        Toast.makeText(getApplicationContext(), "Work In Progress", Toast.LENGTH_SHORT).show();
        TableLayout rptTable = (TableLayout) findViewById(R.id.reportTable);
        rptTable.removeAllViews();
    }

    public void displayAppointmentWiseReport(){
        try {
            appointmentDatabaseHandler adb = new appointmentDatabaseHandler(this);

            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            Calendar myCal = Calendar.getInstance();

            int[] paymentInfo = adb.getPaymentInfoMonthWise(df.format(myCal.getTime()));
            int[] appointmentCount = adb.getAppointmentCountMonthWise(df.format(myCal.getTime()));
            String[] monthNames = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};

            LayoutInflater inflater = getLayoutInflater();

            TableLayout rptTable = (TableLayout) findViewById(R.id.reportTable);
            rptTable.removeAllViews();

            TableRow trhead = (TableRow) inflater.inflate(R.layout.tablerowforreport, rptTable, false);
            trhead.setBackgroundResource(R.drawable.rectangleyellow);

            TextView refhead = (TextView) trhead.findViewById(R.id.txtVRReference);
            refhead.setText("Month");
            refhead.setBackgroundResource(R.drawable.shapeofreportrowdark);
            TextView stat1head = (TextView) trhead.findViewById(R.id.txtVRStat1);
            stat1head.setText("Appointments");
            stat1head.setBackgroundResource(R.drawable.shapeofreportrowdark);
            TextView stat2head = (TextView) trhead.findViewById(R.id.txtVRStat2);
            stat2head.setText("Payment");
            stat2head.setBackgroundResource(R.drawable.shapeofreportrowdark);

            rptTable.addView(trhead);

            boolean color = false;
            for (int i = 0; i < 12; i++) {
                TableRow tr = (TableRow) inflater.inflate(R.layout.tablerowforreport, rptTable, false);
                TextView ref = (TextView) tr.findViewById(R.id.txtVRReference);
                ref.setText(monthNames[i]);
                TextView stat1 = (TextView) tr.findViewById(R.id.txtVRStat1);
                stat1.setText(String.valueOf(appointmentCount[i]));
                TextView stat2 = (TextView) tr.findViewById(R.id.txtVRStat2);
                stat2.setText(String.valueOf(paymentInfo[i]));
                if (!color) {
                    tr.setBackgroundResource(R.drawable.shapeofreportrowdark);
                    color = true;
                } else {
                    tr.setBackgroundResource(R.drawable.shapeofreportrowlight);
                    color = false;
                }
                rptTable.addView(tr);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
