package com.khmkau.codeu.foodapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
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
public class PercentagesNutritionActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_percentage_nutrition);
        chart = (HorizontalBarChart) findViewById(R.id.hchart);

        spinner = (Spinner)findViewById(R.id.spinner);

        recommendedValues = computeRecommendedValues();

        selection = "Day"; // default value

        data = new BarData(getXAxisValues(), getDataSet());

        data.setGroupSpace(50f);
        chart.setData(data);
        chart.setDescription("Percentage");
        chart.setDescriptionTextSize(16f);
        chart.setDescriptionColor(Color.DKGRAY);

        xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        LimitLine line = new LimitLine(100.000f);
        line.setTextColor(Color.RED);
        line.setTextSize(20f);
        xAxis.addLimitLine(line);

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

    public void changeView(View view) {
        Intent intent = new Intent(this, ListNutritionActivity.class);
        startActivity(intent);
        Log.i("Consumption Activity", "Switching activities");
    }

    private float[] recommendedValues;

    // stub implementation
    public float[] computeConsumedValues()
    {
        // TODO: compute consumed values using calls to the database
        return new float[] {1800, 68, 25, 310, 2600, 280, 25, 55, 1025, 20, 5675, 48};
    }

    public float[] computeRecommendedValues() {

        return new float[] {2000, 65, 20, 300, 2400, 300, 25, 50, 1000, 18, 5000, 60};
        // String [] units = {"Cal", "g", "g", "mg", "mg", "g", "g", "g", "mg", "mg", "IU", "mg"};

    }

    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;

        // Consumed value
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();

        // TODO: compute percentages -> loop through the percentages to initialize valueSet1

        float[] consumedValues = computeConsumedValues();
        float[] recommendedValues = computeRecommendedValues();

        int multiplier = 1;
        if (selection.equals("Week"))
            multiplier = 7;
        else if(selection.equals("Month"))
            multiplier = 30;

        // need to iterate backwards because of the backwards order?
        int index = (consumedValues.length - 1);
        for(int i = 0; i < consumedValues.length; i++){
            // compute percentage
            float percentage = (float)(consumedValues[i]/(recommendedValues[i]*multiplier)*100);
            valueSet1.add(new BarEntry(percentage, index));
            index--;
        }


        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Consumption % (Compared to Recommended* Values)");
        // barDataSet1.setColor(Color.rgb(70, 137, 253)); // Color.rgb(80, 255, 150),  (green)
        int[] colorArray = {Color.rgb(80, 255, 150), Color.rgb(70, 137, 253)}; // Color.rgb(123,104,238)} (purple)


        barDataSet1.setColors(colorArray);
//        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Recommended");
//        barDataSet2.setColor(Color.rgb(86, 250, 152));

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        // dataSets.add(barDataSet2);
        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();

        xAxis.add("Vitamin C");
        xAxis.add("Vitamin A");
        xAxis.add("Iron");
        xAxis.add("Calcium");
        xAxis.add("Protein");
        xAxis.add("Dietary Fiber");
        xAxis.add("Total Carbs");
        xAxis.add("Sodium");
        xAxis.add("Cholesterol");
        xAxis.add("Saturated Fat");
        xAxis.add("Total Fat");
        xAxis.add("Calories");

        return xAxis;
    }
}