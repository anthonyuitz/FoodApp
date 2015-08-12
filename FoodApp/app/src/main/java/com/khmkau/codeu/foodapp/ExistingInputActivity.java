package com.khmkau.codeu.foodapp;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.khmkau.codeu.foodapp.data.FoodContract;

import java.util.ArrayList;

public class ExistingInputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_input);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_existing_input, menu);
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

    @Override
    protected void onResume() {
        super.onResume();
        String[] proj = {FoodContract.InfoEntry.COLUMN_FOOD_NAME};
        ArrayList<String> food_names_array = new ArrayList<String>();

        Cursor cursor = getContentResolver().query(FoodContract.InfoEntry.CONTENT_URI,
                proj,
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            do {
                String s = cursor.getString(0);
                if(!food_names_array.contains(s))
                    food_names_array.add(s);
            } while (cursor.moveToNext());
        }

        String[] food_names = food_names_array.toArray(new String[food_names_array.size()]);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, food_names);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.searchInput);
        textView.setAdapter(adapter);
    }

    public void search(View view) {

        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.searchInput);
        String searchTerm = textView.getText().toString();

        String[] proj = {
                FoodContract.InfoEntry.COLUMN_FOOD_NAME,
                FoodContract.InfoEntry.COLUMN_FOOD_GROUP,
                FoodContract.InfoEntry.COLUMN_SERVING_UNIT,
                FoodContract.InfoEntry.COLUMN_CALORIES,
                FoodContract.InfoEntry.COLUMN_SATURATED_FAT,
                FoodContract.InfoEntry.COLUMN_TRANS_FAT,
                FoodContract.InfoEntry.COLUMN_TOTAL_FAT,
                FoodContract.InfoEntry.COLUMN_CHOLESTEROL,
                FoodContract.InfoEntry.COLUMN_SODIUM,
                FoodContract.InfoEntry.COLUMN_TOTAL_CARBOHYDRATE,
                FoodContract.InfoEntry.COLUMN_SUGAR,
                FoodContract.InfoEntry.COLUMN_PROTEIN
        };

        String selection = FoodContract.InfoEntry.TABLE_NAME + "." + FoodContract.InfoEntry.COLUMN_FOOD_NAME + " = ?";
        String[] selectArgs = {searchTerm};

        Cursor cursor = getContentResolver().query(FoodContract.InfoEntry.CONTENT_URI,
                proj,
                selection,
                selectArgs,
                null);

        if (cursor.moveToFirst()) {
            String s = cursor.getString(0);
            if(searchTerm.equals(s)) {
                Toast.makeText(getApplicationContext(), "IMA INPUT THIS I SWEARZ IT",
                        Toast.LENGTH_SHORT).show();
            }

        }
        else {
            Toast.makeText(getApplicationContext(), "No existing items with that name.",
                    Toast.LENGTH_SHORT).show();
        }

    }
}
