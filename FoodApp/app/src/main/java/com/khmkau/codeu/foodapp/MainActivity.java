package com.khmkau.codeu.foodapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, PreferencesActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void foodView(View view)
    {
        Intent intent = new Intent(this, InputMenuActivity.class);
        startActivity(intent);
    }

    public void fridgeView(View view)
    {
        Intent intent = new Intent(this, FridgeActivity.class);
        startActivity(intent);
    }

    public void consumptionView(View view)
    {
        Intent intent = new Intent(this, ServingsConsumptionActivity.class);
        startActivity(intent);
    }

    public void nutritionView(View view)
    {
        Intent intent = new Intent(this, PercentagesConsumptionActivity.class);
        startActivity(intent);
    }


//    public void settingsView(View view)
//    {
//        Intent intent = new Intent(this, PreferencesActivity.class);
//        startActivity(intent);
//    }

}