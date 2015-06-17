package com.example.prashant.dentist;

import android.app.ActionBar;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;


public class patientTabs extends TabActivity implements TabHost.OnTabChangeListener {

    TabHost th;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_tabs);

        th = getTabHost();
        th.setOnTabChangedListener(this);

        TabHost.TabSpec spec;
        Intent intent;

        intent = new Intent().setClass(this,viewAppiontmentPage.class);
        spec = th.newTabSpec("Appointments").setIndicator("Appointments").setContent(intent);
        th.addTab(spec);

        intent = new Intent().setClass(this,viewPatientPage.class);
        spec = th.newTabSpec("Patients").setIndicator("Patients").setContent(intent);
        th.addTab(spec);

        intent = new Intent().setClass(this,reportGraphs.class);
        spec = th.newTabSpec("Reports").setIndicator("Reports").setContent(intent);
        th.addTab(spec);

        th.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.rectanglegreen);
        th.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.rectangleblue);
        th.getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.rectangleyellow);

    }

    @Override
    public void onTabChanged(String tabId) {



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_patient_tabs, menu);
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
}
