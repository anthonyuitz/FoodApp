package com.khmkau.codeu.foodapp;

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
import android.widget.AdapterView;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class ServingsConsumptionActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servings_consumption);

        chart = (BarChart) findViewById(R.id.chart);

        // Get Settings
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        age = Integer.parseInt(SP.getString("age", "18"));
        // boolean gender = SP.getBoolean("gender", true);
        weight = Integer.parseInt(SP.getString("weight","120"));
        Log.i("Settings", age + " " + " " + weight);

        spinner = (Spinner)findViewById(R.id.spinner);

        recommendedValues = computeRecommendedValues();

        selection = "Day"; // default value
        data = new BarData(getXAxisValues(), getDataSet());

        data.setGroupSpace(30f);

        chart.setData(data);
        chart.setNoDataText("");
        chart.setDescription("Food Group");
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

                chart.setData(data);
                chart.setNoDataText("");
                chart.setDescription("Food Group");
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
        // selection = String.valueOf(spinner.getSelectedItem());
        Log.i("onCreate: ", "Value of Selection is: " + selection);

    }

    private String selection;
    private Spinner spinner;
    private BarChart chart;
    private BarData data;
    private XAxis xAxis;

    private int age;
    private int weight;

    public void changeView(View view)
    {
        Intent intent = new Intent(this, PercentagesConsumptionActivity.class);
        startActivity(intent);
        Log.i("Consumption Activity", "Switching activities");
    }

    // TODO: implement using calls to the database
    // stub implementation
    public float[] computeConsumedValues() {
        return new float[]{4.3f, 2.8f, 4f, 1f, 3.6f, 8f};
    }

    private ArrayList<BarDataSet> getDataSet() {

        ArrayList<BarDataSet> dataSets;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();

        float[] consumedValues = computeConsumedValues();
        for (int i = 0; i < consumedValues.length; i++)
        {
            valueSet1.add(new BarEntry(consumedValues[i], i));
        }

        // default: day; if the setting is week, multiply recommendedValues[i]*7, etc.
        ArrayList<BarEntry> valueSet2 = new ArrayList<>();

        for (int i = 0; i < recommendedValues.length; i++)
        {
            switch(selection){
                case "Week":
                    valueSet2.add(new BarEntry(recommendedValues[i]*7f, i));
                    break;
                case "Month":
                    valueSet2.add(new BarEntry(recommendedValues[i]*30f, i));
                    break;
                default:
                    valueSet2.add(new BarEntry(recommendedValues[i], i));
            }
        }

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Consumed");
        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Recommended*");
        barDataSet1.setColor(Color.rgb(255,87,71));
        barDataSet2.setColor(Color.rgb(255, 179, 71));

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);
        return dataSets;
    }

    private float[] recommendedValues;


    // returns an array of the recommended amt of servings (vegetables, fruit, grain, protein, dairy, water)
    public float[] computeRecommendedValues() {

        // TODO: get values from the settings

        boolean male = true; // false if female

        float waterRec = (0.5f * weight)/8f;

        if(age <= 2)
            return new float[]{2.5f, .5f, 4f, 1f, 1.25f, waterRec};
        else if(age <= 3)
            return new float[]{2.5f, 1f, 4f, 1f, 1.5f, waterRec};

        // male
        else if(male == true) {
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

    private ArrayList<String> getXAxisValues() {

        ArrayList<String> xAxis = new ArrayList<>();

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float density = getResources().getDisplayMetrics().density;
        float dpwidth = outMetrics.widthPixels/density;

        if(dpwidth < 500) {
            xAxis.add("Veg");
            xAxis.add("Fr");
            xAxis.add("Gr");
            xAxis.add("Pr");
            xAxis.add("Dry");
            xAxis.add("H2O");
        }
        else
        {
            xAxis.add("Vegetables");
            xAxis.add("Fruits");
            xAxis.add("Grains");
            xAxis.add("Proteins");
            xAxis.add("Dairy");
            xAxis.add("Water");
        }
        return xAxis;
    }
}