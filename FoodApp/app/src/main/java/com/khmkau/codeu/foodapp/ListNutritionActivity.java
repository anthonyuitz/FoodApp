package com.khmkau.codeu.foodapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by kellyhosokawa on 8/2/15.
 */
public class ListNutritionActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_nutrition);

        spinner = (Spinner)findViewById(R.id.spinner);

        selection = "Day"; // default value

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                table.removeAllViews();
                selection = String.valueOf(spinner.getSelectedItem());
                addValues();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

                selection = "Day";

            }

        });

        addValues();
    }

    private String selection;
    private Spinner spinner;


    public void changeView(View view)
    {
        Intent intent = new Intent(this, PercentagesNutritionActivity.class);
        startActivity(intent);
        Log.i("Consumption Activity", "Switching activities");
    }


    // TODO: implement this function (stub implementation)
    public float[] computeConsumedValues()
    {
          return new float[] {1800, 68, 25, 310, 2600, 280, 25, 55, 1025, 20, 5675, 48};
    }

    public float[] computeRecommendedValues()
    {
        float multiplier = 1;
        if (selection.equals("Week"))
            multiplier = 7;
        else if(selection.equals("Month"))
            multiplier = 30;

        return new float[] {2000*multiplier, 65*multiplier, 20*multiplier, 300*multiplier,
                2400*multiplier, 300*multiplier, 25*multiplier, 50*multiplier, 1000*multiplier,
                18*multiplier, 5000*multiplier, 60*multiplier};
    }

    public TableLayout table;

    public void addValues()
    {
        table = (TableLayout)findViewById(R.id.tableLayout1);

        TableRow row;
        TextView label, value, recValue;

        String [] categories = {"Calories", "Total Fat", "Saturated Fat", "Cholesterol", "Sodium",
                "Total Carbohydrates", "Dietary Fiber", "Protein", "Calcium", "Iron", "Vitamin A", "Vitamin C"};
        float [] consumedValues = computeConsumedValues();
        float [] recommendedValues = computeRecommendedValues();
        String [] units = {"Cal", "g", "g", "mg", "mg", "g", "g", "g", "mg", "mg", "IU", "mg"};

        // Add headers

        row = new TableRow(this);
        //row.setId(100+i);
        row.setLayoutParams(new LinearLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        row.setGravity(Gravity.CENTER);
        row.setPadding(10, 10, 10, 10);

        label = new TextView(this);
        label.setText("Category");
        label.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT));
        label.setGravity(Gravity.CENTER);
        label.setTypeface(null, Typeface.BOLD);
        label.setPadding(10, 10, 10, 10);
        row.addView(label);

        value = new TextView(this);
        value.setText("Consumed");
        value.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT));
        value.setGravity(Gravity.CENTER);
        value.setPadding(10, 10, 10, 10);
        value.setTypeface(null, Typeface.BOLD);
        row.addView(value);

        recValue = new TextView(this);
        recValue.setText("Recommended*");
        recValue.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT));
        recValue.setGravity(Gravity.CENTER);
        recValue.setPadding(10, 10, 10, 10);
        recValue.setTypeface(null, Typeface.BOLD);
        row.addView(recValue);

        row.setBackgroundColor(0xffd3d3d3);
        table.addView(row);

        for (int i = 0; i < recommendedValues.length; i++)
        {
            row = new TableRow(this);
            //row.setId(100+i);
            row.setLayoutParams(new LinearLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            row.setGravity(Gravity.CENTER);
            row.setPadding(10, 10, 10, 10);

            label = new TextView(this);
            label.setText(categories[i]);
            label.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT));
            label.setGravity(Gravity.CENTER);
            label.setPadding(10, 10, 10, 10);
            row.addView(label);

            value = new TextView(this);
            value.setText(Float.toString(consumedValues[i]) + " " + units[i]);
            value.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT));
            value.setGravity(Gravity.CENTER);
            value.setPadding(10, 10, 10, 10);
            row.addView(value);

            recValue = new TextView(this);
            recValue.setText(Float.toString(recommendedValues[i]) + " " + units[i]);
            recValue.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT));
            recValue.setGravity(Gravity.CENTER);
            recValue.setPadding(10, 10, 10, 10);
            row.addView(recValue);

            if(i%2 == 0)
                row.setBackgroundColor(0xffffffff);
            else
                row.setBackgroundColor(0xffd3d3d3);

            table.addView(row);
        }
    }

}
