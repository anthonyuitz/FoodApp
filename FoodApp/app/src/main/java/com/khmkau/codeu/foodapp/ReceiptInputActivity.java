package com.khmkau.codeu.foodapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.khmkau.codeu.foodapp.data.FoodContract;

public class ReceiptInputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_input);
    }

    @Override
    protected void onResume() {
        super.onResume();
        long firstIndex = -1;
        for(int x = 0; x < 20; x++) {
            ContentValues infoValues = Utility.generateFoodValues();
            if(firstIndex == -1) {
                firstIndex = ContentUris.parseId(getContentResolver().insert(FoodContract.InfoEntry.CONTENT_URI, infoValues));
            }
            else {
                getContentResolver().insert(FoodContract.InfoEntry.CONTENT_URI, infoValues);
            }
        }
        for(int x = 0; x < 100; x++) {
            ContentValues foodValues = Utility.generateTableValues("Current");
            foodValues.put(FoodContract.CurrentEntry.COLUMN_FOOD_KEY, (int)(Math.random()*20 + firstIndex));
            getContentResolver().insert(FoodContract.CurrentEntry.CONTENT_URI, foodValues);
        }
        for(int x = 0; x < 100; x++) {
            ContentValues foodValues = Utility.generateTableValues("Consumed");
            foodValues.put(FoodContract.ConsumedEntry.COLUMN_FOOD_KEY, (int)(Math.random()*20 + firstIndex));
            getContentResolver().insert(FoodContract.ConsumedEntry.CONTENT_URI, foodValues);
        }
        for(int x = 0; x < 100; x++) {
            ContentValues foodValues = Utility.generateTableValues("Thrown");
            foodValues.put(FoodContract.ThrownEntry.COLUMN_FOOD_KEY, (int)(Math.random()*20 + firstIndex));
            getContentResolver().insert(FoodContract.ThrownEntry.CONTENT_URI, foodValues);
        }
        Toast.makeText(getApplicationContext(), "Generated Mock Data",
                Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_receipt_input, menu);
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
