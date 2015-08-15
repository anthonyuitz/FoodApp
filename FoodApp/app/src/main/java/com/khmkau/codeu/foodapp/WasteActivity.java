package com.khmkau.codeu.foodapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by kellyhosokawa on 8/11/15.
 */
public class WasteActivity extends ActionBarActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waste);

        spinner = (Spinner)findViewById(R.id.spinner);

        selection = "Day"; // default value

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

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

    public double getAmountWasted()
    {
        if (selection.equals("Week"))
        {
            return 40.37;
        }
        else if (selection.equals("Month"))
        {
            return 189.89;
        }
        else // default = day
        {
            return 8.52;
        }
    }

    // cnn.com - a meal for a child in Africa costs $0.25
    public int computeMealsWasted()
    {
        return (int)(getAmountWasted() * 4);
    }

    public void addValues()
    {
        TextView amountWasted = (TextView)findViewById(R.id.amountWasted);
        amountWasted.setText("$ " + Double.toString(getAmountWasted()));
        TextView mealsWasted = (TextView)findViewById(R.id.mealsWasted);
        mealsWasted.setText(Integer.toString(computeMealsWasted()));
    }
}
