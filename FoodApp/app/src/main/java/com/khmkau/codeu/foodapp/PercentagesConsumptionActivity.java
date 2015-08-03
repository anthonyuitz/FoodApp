package com.khmkau.codeu.foodapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

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
        HorizontalBarChart chart = (HorizontalBarChart) findViewById(R.id.hchart);

        BarData data = new BarData(getXAxisValues(), getDataSet());
        chart.setData(data);
        chart.setDescription("");
        // chart.setVisibleXRange(3);
        // chart.fitScreen();

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        LimitLine line = new LimitLine(100.000f);
        line.setTextColor(Color.RED);
        line.setTextSize(20f);
        xAxis.addLimitLine(line);

        chart.animateXY(2000, 2000);
        chart.invalidate();
    }

    public void changeView(View view)
    {
        Intent intent = new Intent(this, ServingsConsumptionActivity.class);
        startActivity(intent);
        Log.i("Consumption Activity", "Switching activities");
    }

    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;

        // Consumed values
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();

        // Loop through food groups and compute consumption (convert to a percentage)

//        for(int i = 0; i < 6; i++){
//            // compute value
//            float value = 0;
//            valueSet1.add(new BarEntry(value, i));
//        }

        BarEntry v1e1 = new BarEntry(110.000f, 0); // Jan
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(40.000f, 1); // Feb
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(60.000f, 2); // Mar
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(30.000f, 3); // Apr
        valueSet1.add(v1e4);
        BarEntry v1e5 = new BarEntry(90.000f, 4); // May
        valueSet1.add(v1e5);
        BarEntry v1e6 = new BarEntry(100.000f, 5); // Jun
        valueSet1.add(v1e6);

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Consumption % (Compared to Recommended Values)");
        // barDataSet1.setColor(Color.rgb(70, 137, 253));
        int[] colorArray = {Color.rgb(70, 137, 253), Color.rgb(86, 250, 152)};


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

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float density = getResources().getDisplayMetrics().density;
        float dpwidth = outMetrics.widthPixels/density;

        Log.i("ConsumptionActivity", "density: " + dpwidth);

        float scalingFactor = getResources().getDisplayMetrics().density;
        Log.i("ConsumptionActivity", "Scaling factor: " + scalingFactor);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE); // the results will be higher than using the activity context object or the getWindowManager() shortcut
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        // int screenHeight = displayMetrics.heightPixels;

        Log.i("ConsumptionActivity", "screenWidth: " + screenWidth);



        // somehow differentiate between screen widths...
            // Don't use abbreviations if it is Nexus 7/10?
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