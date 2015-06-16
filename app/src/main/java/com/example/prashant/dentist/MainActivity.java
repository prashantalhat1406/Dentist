package com.example.prashant.dentist;


import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bReports = (Button)findViewById(R.id.reports);
        bReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoReportScreen();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    public void displayPatientHomePage(View view)
    {

        //Intent i = new Intent(this,testingGraphs.class);
        Intent i = new Intent(this,patientTabs.class);
        startActivity(i);
    }

    public void  gotoReportScreen(){
        Intent i = new Intent(this,reportGraphs.class);
        startActivity(i);
    }
}
