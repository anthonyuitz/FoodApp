package com.khmkau.codeu.foodapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

        spinner = (Spinner)findViewById(R.id.spinner);

        recommendedValues = computeRecommendedValues();

        selection = "Day"; // default value
        data = new BarData(getXAxisValues(), getDataSet());

        data.setGroupSpace(30f);

        chart.setData(data);
        chart.setNoDataText("");
        chart.setDescription("");


        xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.animateXY(2000, 2000);
        chart.invalidate();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                selection = String.valueOf(spinner.getSelectedItem());
                // Log.i("itemSelected: ", "Value of Selection is: " + selection);

                data = new BarData(getXAxisValues(), getDataSet());

                data.setGroupSpace(30f);

                // BarChart chart = (BarChart) findViewById(R.id.chart);

                chart.setData(data);
                chart.setNoDataText("");
                chart.setDescription("");

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

    public void changeView(View view)
    {
        Intent intent = new Intent(this, PercentagesConsumptionActivity.class);
        startActivity(intent);
        Log.i("Consumption Activity", "Switching activities");
    }


    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;

        // Consumed values
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(5.000f, 0); // Fruits
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(5.000f, 1); // Vegetables
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(12.000f, 2); // Grains
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(5.000f, 3); // Protein
        valueSet1.add(v1e4);
        BarEntry v1e5 = new BarEntry(9.000f, 4); // Dairy
        valueSet1.add(v1e5);
        BarEntry v1e6 = new BarEntry(4.000f, 5); // Water
        valueSet1.add(v1e6);


        // default: day; if the setting is week, multiply recommendedValues[i]*7, etc.
        ArrayList<BarEntry> valueSet2 = new ArrayList<>();

        Log.i("SetData: ", "Value of Selection is: " + selection);

        for (int i = 0; i < 6; i++)
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
        barDataSet1.setColor(Color.rgb(70, 137, 253));
        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Recommended");
        barDataSet2.setColor(Color.rgb(86, 250, 152));

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);
        return dataSets;
    }

    private float[] recommendedValues;


    // returns an array of the recommended amt of servings (vegetables, fruit, grain, protein, dairy, water)
    public float[] computeRecommendedValues() {

        // TODO: get values from the settings

        int age = 60;
        boolean male = true; // false if female
        float weight = 150;

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

        // somehow differentiate between screen widths...
        if(dpwidth < 500) {
            // Don't use abbreviations if it is Nexus 7/10?
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