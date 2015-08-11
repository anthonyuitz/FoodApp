package com.khmkau.codeu.foodapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

// horizontal bar graph
public class PercentagesConsumptionActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_percentage_consumption);

        // Get Settings

        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        age = Integer.parseInt(SP.getString("age", "18"));
        weight = Integer.parseInt(SP.getString("weight", "150"));
        String downloadType = SP.getString("downloadType","1");
        gender = Integer.parseInt(downloadType);
        Log.i("Settings", age + " " + weight + " " + downloadType);

        chart = (HorizontalBarChart) findViewById(R.id.hchart);
        spinner = (Spinner)findViewById(R.id.spinner);
        recommendedValues = computeRecommendedValues();
        selection = "Day"; // default value

        data = new BarData(getXAxisValues(), getDataSet());

        data.setGroupSpace(30f);
        chart.setData(data);
        chart.setDescription("Percentage");
        chart.setDescriptionTextSize(16f);
        chart.setDescriptionColor(Color.DKGRAY);

        xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        chart.animateXY(2000, 2000);
        chart.invalidate();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                selection = String.valueOf(spinner.getSelectedItem());
                data = new BarData(getXAxisValues(), getDataSet());

                data.setGroupSpace(30f);

                // BarChart chart = (BarChart) findViewById(R.id.chart);

                chart.setData(data);
                chart.setNoDataText("");
                chart.setDescription("Percentage");
                chart.setDescriptionTextSize(16f);
                chart.setDescriptionColor(Color.DKGRAY);

                xAxis = chart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                chart.animateXY(2000, 2000);
                chart.invalidate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                selection = "Day";
                Log.i("nothingSelected: ", "Value of Selection is: " + selection);
            }

        });
    }

    private String selection;
    private Spinner spinner;
    private HorizontalBarChart chart;
    private BarData data;
    private XAxis xAxis;

    private int age;
    private int weight;
    private int gender;

    public void changeView(View view)
    {
        Intent intent = new Intent(this, ServingsConsumptionActivity.class);
        startActivity(intent);
    }

    private float[] recommendedValues;

    public float[] computeRecommendedValues() {

        // TODO: get values from the settings
        // boolean male = true; // false if female

        float waterRec = (0.5f * weight)/8f;

        if(age <= 2)
            return new float[]{2.5f, .5f, 4f, 1f, 1.25f, waterRec};
        else if(age <= 3)
            return new float[]{2.5f, 1f, 4f, 1f, 1.5f, waterRec};

            // male
        else if(gender == 1) {
            if(age <=8)
                return new float[]{4.5f, 1.5f, 4f, 1.5f, 2f, waterRec};
            else if(age <= 11)
                return new float[]{5f, 2f, 5f, 2.5f, 2.5f, waterRec};
            else if(age <= 13)
                return new float[]{5.5f, 2f, 6f, 2.5f, 3.5f, waterRec};
            else if(age <= 18)
                return new float[]{5.5f, 2f, 7f, 2.5f, 3.5f, waterRec};
            else if(age <= 50)
                return new float[]{6f, 2f, 6f, 3f, 2.5f, waterRec};
            else if(age <= 70)
                return new float[]{5.5f, 2f, 6f, 2.5f, 2.5f, waterRec};
            else
                return new float[]{5f, 2f, 4.5f, 2.5f, 3.5f, waterRec};
        }

        // female
        else {
            if(age <= 8)
                return new float[]{4.5f, 1.5f, 4f, 1.5f, 1.5f, waterRec};
            else if(age <= 11)
                return new float[]{5f, 2f, 4f, 2.5f, 2.5f, waterRec};
            else if(age <= 13)
                return new float[]{5f, 2f, 5f, 2.5f, 3.5f, waterRec};
            else if(age <= 18)
                return new float[]{5f, 2f, 7f, 2.5f, 3.5f, waterRec};
            else if(age <= 50)
                return new float[]{5f, 2f, 6f, 2.5f, 2.5f, waterRec};
            else if(age <= 70)
                return new float[]{5f, 2f, 4f, 2f, 4f, waterRec};
            else
                return new float[]{5f, 2f, 3f, 2f, 4f, waterRec};
        }

    }

    // TODO: implement this using calls to the database
    // stub implementation
    public float[] computeConsumedValues() {
        return new float[]{4.3f, 2.8f, 4f, 1f, 3.6f, 8f};
    }



    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;

        // Consumed values
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();

        // TODO: compute percentages -> loop through the percentages to initialize valueSet1

        float[] consumedValues = computeConsumedValues();

        int multiplier = 1;
        if (selection.equals("Week"))
            multiplier = 7;
        else if(selection.equals("Month"))
            multiplier = 30;

        int index = (consumedValues.length - 1);
        for(int i = 0; i < consumedValues.length; i++){
            // compute percentage
            float percentage = consumedValues[i]/(recommendedValues[i]*multiplier)*100;
            valueSet1.add(new BarEntry(percentage, index));
            index--;
        }

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Consumption % (Compared to Recommended* Values)");
        // barDataSet1.setColor(Color.rgb(70, 137, 253));
        int[] colorArray = {Color.rgb(80, 255, 150), Color.rgb(70, 137, 253)};

        barDataSet1.setColors(colorArray);

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE); // the results will be higher than using the activity context object or the getWindowManager() shortcut
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;

        if(screenWidth < 0) {
            xAxis.add("h2o");
            xAxis.add("Dry");
            xAxis.add("Pr");
            xAxis.add("Gr");
            xAxis.add("Fr");
            xAxis.add("Veg");
        }
        else
        {
            xAxis.add("Water");
            xAxis.add("Dairy");
            xAxis.add("Protein");
            xAxis.add("Grains");
            xAxis.add("Fruits");
            xAxis.add("Vegetables");
        }
        return xAxis;
    }
}