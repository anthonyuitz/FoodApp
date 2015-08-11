package com.khmkau.codeu.foodapp;

import android.app.DatePickerDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.Toast;

import com.khmkau.codeu.foodapp.data.FoodContract;
import com.khmkau.codeu.foodapp.data.FoodDbHelper;

import java.util.Calendar;
import java.util.Date;

public class ManualInputActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_input);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Spinner tableSpinner = (Spinner) findViewById(R.id.tableSpinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.table_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        tableSpinner.setAdapter(adapter);
        tableSpinner.setOnItemSelectedListener(this);

        Spinner quantitySpinner = (Spinner) findViewById(R.id.quantitySpinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        adapter = ArrayAdapter.createFromResource(this,
                R.array.quantity_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        quantitySpinner.setAdapter(adapter);
        quantitySpinner.setAdapter(adapter);
        quantitySpinner.setOnItemSelectedListener(this);

        Spinner editFoodGroup = (Spinner) findViewById(R.id.editFoodGroup);
// Create an ArrayAdapter using the string array and a default spinner layout
        adapter = ArrayAdapter.createFromResource(this,
                R.array.food_groups, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        editFoodGroup.setAdapter(adapter);

        EditText purchaseByEditText = (EditText)findViewById(R.id.editPurchasedBy);
        purchaseByEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar mCurrentDate = Calendar.getInstance();
                int mYear = mCurrentDate.get(Calendar.YEAR);
                int mMonth = mCurrentDate.get(Calendar.MONTH);
                int mDay = mCurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(ManualInputActivity.
                        this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        EditText c = (EditText) findViewById(R.id.editPurchasedBy);
                        c.setText("" + (selectedmonth + 1) + "/" + selectedday + "/" + selectedyear);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }
        });

        EditText expiredByEditText = (EditText)findViewById(R.id.editExpiresBy);
        expiredByEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar mCurrentDate = Calendar.getInstance();
                int mYear = mCurrentDate.get(Calendar.YEAR);
                int mMonth = mCurrentDate.get(Calendar.MONTH);
                int mDay = mCurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog( ManualInputActivity.
                        this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        EditText c = (EditText)findViewById(R.id.editExpiresBy);
                        c.setText("" + (selectedmonth+1) + "/" + selectedday + "/" + selectedyear);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }
        });

        EditText consumedByEditText = (EditText)findViewById(R.id.editConsumedBy);
        consumedByEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar mCurrentDate = Calendar.getInstance();
                int mYear = mCurrentDate.get(Calendar.YEAR);
                int mMonth = mCurrentDate.get(Calendar.MONTH);
                int mDay = mCurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog( ManualInputActivity.
                        this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        EditText c = (EditText)findViewById(R.id.editConsumedBy);
                        c.setText("" + (selectedmonth+1) + "/" + selectedday + "/" + selectedyear);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }
        });

        EditText thrownByEditText = (EditText)findViewById(R.id.editThrownBy);
        thrownByEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar mCurrentDate = Calendar.getInstance();
                int mYear = mCurrentDate.get(Calendar.YEAR);
                int mMonth = mCurrentDate.get(Calendar.MONTH);
                int mDay = mCurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog( ManualInputActivity.
                        this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        EditText c = (EditText)findViewById(R.id.editThrownBy);
                        c.setText("" + (selectedmonth+1) + "/" + selectedday + "/" + selectedyear);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        switch(parent.getId()) {
            case R.id.tableSpinner:
                TableRow rowConsumed = (TableRow) findViewById(R.id.rowConsumed);
                TableRow rowThrown = (TableRow) findViewById(R.id.rowThrown);
                switch (pos) {
                    case 0:
                        rowConsumed.setVisibility(View.GONE);
                        rowThrown.setVisibility(View.GONE);
                        break;
                    case 1:
                        rowConsumed.setVisibility(View.VISIBLE);
                        rowThrown.setVisibility(View.GONE);
                        break;
                    case 2:
                        rowConsumed.setVisibility(View.GONE);
                        rowThrown.setVisibility(View.VISIBLE);
                        break;
                }
                break;
            case R.id.quantitySpinner:
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public void inputFood(View view) {
        //Toast.makeText(getApplicationContext(), "This feature is not yet implemented. Sorry!",
        //        Toast.LENGTH_SHORT).show();

        ContentValues infoValues = new ContentValues();

        ContentValues foodValues = new ContentValues();

        EditText nameTextView = (EditText)findViewById(R.id.editName);
        EditText quantityTextView = (EditText)findViewById(R.id.editQuantity);
        EditText purchasedByTextView = (EditText)findViewById(R.id.editPurchasedBy);
        EditText expiredByTextView = (EditText)findViewById(R.id.editExpiresBy);
        Spinner foodgroupTextView = (Spinner)findViewById(R.id.editFoodGroup);
        EditText totalCostTextView = (EditText)findViewById(R.id.editTotalCost);
        EditText caloriesTextView = (EditText)findViewById(R.id.editCalories);
        EditText totalFatTextView = (EditText)findViewById(R.id.editTotalFat);
        EditText satFatTextView = (EditText)findViewById(R.id.editSatFat);
        EditText transFatTextView = (EditText)findViewById(R.id.editTransFat);
        EditText cholesterolTextView = (EditText)findViewById(R.id.editCholesterol);
        EditText sodiumTextView = (EditText)findViewById(R.id.editSodium);
        EditText totalCarbsTextView = (EditText)findViewById(R.id.editTotalCarbs);
        EditText sugarTextView = (EditText)findViewById(R.id.editSugar);
        EditText proteinTextView = (EditText)findViewById(R.id.editProtein);
        Spinner servingTextView = (Spinner)findViewById(R.id.quantitySpinner);

        String nameText = nameTextView.getText().toString();
        String quantityText = quantityTextView.getText().toString();
        String purchasedByText = purchasedByTextView.getText().toString();
        String expiredByText = expiredByTextView.getText().toString();
        String foodgroupText = foodgroupTextView.getSelectedItem().toString();
        String totalCostText = totalCostTextView.getText().toString();
        String caloriesText = caloriesTextView.getText().toString();
        String totalFatText = totalFatTextView.getText().toString();
        String satFatText = satFatTextView.getText().toString();
        String transFatText = transFatTextView.getText().toString();
        String cholesterolText = cholesterolTextView.getText().toString();
        String sodiumText = sodiumTextView.getText().toString();
        String totalCarbsText = totalCarbsTextView.getText().toString();
        String sugarText = sugarTextView.getText().toString();
        String proteinText = proteinTextView.getText().toString();
        String servingText = servingTextView.getSelectedItem().toString();

        Spinner tableTextView = (Spinner)findViewById(R.id.tableSpinner);
        String tableText = tableTextView.getSelectedItem().toString();

        String consumedByText = "";
        String thrownByText = "";

        boolean isValid = true;

        if(!Utility.isNumeric(quantityText) || quantityText.equals("")) {
            isValid = false;
            quantityTextView.setHint(R.string.quantity_error);
            quantityTextView.setHintTextColor(Color.RED);
        }
        int[] purchasedByArray = Utility.validateDate(purchasedByText);
        if(purchasedByArray[0] == -1) {
            isValid = false;
            purchasedByTextView.setHint(R.string.purchased_by_error);
            purchasedByTextView.setHintTextColor(Color.RED);
        }
        int[] expiredByArray = Utility.validateDate(expiredByText);
        if(expiredByArray[0] == -1) {
            isValid = false;
            expiredByTextView.setHint(R.string.expires_by_error);
            expiredByTextView.setHintTextColor(Color.RED);
        }
        if(!totalCarbsText.equals("")) {
            if (totalCostText.charAt(0) == '$') {
                totalCostText = totalCostText.substring(1);
            }
        }
        if(!Utility.isDouble(totalCostText) || totalCostText.equals("")) {
            isValid = false;
            totalCostTextView.setHint(R.string.total_cost_error);
            totalCostTextView.setHintTextColor(Color.RED);
        }
        if(!Utility.isNumeric(caloriesText) || caloriesText.equals("")) {
            isValid = false;
            caloriesTextView.setHint(R.string.calories_error);
            caloriesTextView.setHintTextColor(Color.RED);
        }
        if(!Utility.isNumeric(totalFatText)) {
            isValid = false;
            totalFatTextView.setHint(R.string.total_fat_error);
            totalFatTextView.setHintTextColor(Color.RED);
        }
        if(!Utility.isNumeric(satFatText)) {
            isValid = false;
            satFatTextView.setHint(R.string.saturated_fat_error);
            satFatTextView.setHintTextColor(Color.RED);
        }
        if(!Utility.isNumeric(transFatText)) {
            isValid = false;
            transFatTextView.setHint(R.string.trans_fat_error);
            transFatTextView.setHintTextColor(Color.RED);
        }
        if(!Utility.isNumeric(cholesterolText)) {
            isValid = false;
            cholesterolTextView.setHint(R.string.cholesterol_error);
            cholesterolTextView.setHintTextColor(Color.RED);
        }
        if(!Utility.isNumeric(sodiumText)) {
            isValid = false;
            sodiumTextView.setHint(R.string.sodium_error);
            sodiumTextView.setHintTextColor(Color.RED);
        }
        if(!Utility.isNumeric(totalCarbsText)) {
            isValid = false;
            totalCarbsTextView.setHint(R.string.total_carbs_error);
            totalCarbsTextView.setHintTextColor(Color.RED);
        }
        if(!Utility.isNumeric(sugarText)) {
            isValid = false;
            sugarTextView.setHint(R.string.sugar_error);
            sugarTextView.setHintTextColor(Color.RED);
        }
        if(!Utility.isNumeric(proteinText)) {
            isValid = false;
            proteinTextView.setHint(R.string.protein_error);
            proteinTextView.setHintTextColor(Color.RED);
        }

        if(isValid) {
            infoValues.put(FoodContract.InfoEntry.COLUMN_FOOD_NAME, nameText);
            infoValues.put(FoodContract.InfoEntry.COLUMN_FOOD_GROUP, foodgroupText);
            infoValues.put(FoodContract.InfoEntry.COLUMN_SERVING_UNIT, servingText);
            infoValues.put(FoodContract.InfoEntry.COLUMN_CALORIES, Integer.parseInt(caloriesText));
            if(!satFatText.equals("")) {
                infoValues.put(FoodContract.InfoEntry.COLUMN_SATURATED_FAT, Integer.parseInt(satFatText));
            }
            if(!satFatText.equals("")) {
                infoValues.put(FoodContract.InfoEntry.COLUMN_TRANS_FAT, Integer.parseInt(transFatText));
            }
            if(!satFatText.equals("")) {
                infoValues.put(FoodContract.InfoEntry.COLUMN_TOTAL_FAT, Integer.parseInt(totalFatText));
            }
            if(!satFatText.equals("")) {
                infoValues.put(FoodContract.InfoEntry.COLUMN_CHOLESTEROL, Integer.parseInt(cholesterolText));
            }
            if(!satFatText.equals("")) {
                infoValues.put(FoodContract.InfoEntry.COLUMN_SODIUM, Integer.parseInt(sodiumText));
            }
            if(!satFatText.equals("")) {
                infoValues.put(FoodContract.InfoEntry.COLUMN_TOTAL_CARBOHYDRATE, Integer.parseInt(totalCarbsText));
            }
            if(!satFatText.equals("")) {
                infoValues.put(FoodContract.InfoEntry.COLUMN_SUGAR, Integer.parseInt(sugarText));
            }
            if(!satFatText.equals("")) {
                infoValues.put(FoodContract.InfoEntry.COLUMN_PROTEIN, Integer.parseInt(proteinText));
            }

            long food_id = ContentUris.parseId(getContentResolver().insert(FoodContract.InfoEntry.CONTENT_URI, infoValues));

            //check which table is being inserted to and get relevant fields
            if (tableText.equals("Consumed")) {
                EditText consumedByTextView = (EditText) findViewById(R.id.editConsumedBy);
                consumedByText = consumedByTextView.getText().toString();
                int[] consumedDates = Utility.validateDate(consumedByText);
                if (consumedDates[0] == -1) {
                    consumedByTextView.setHintTextColor(Color.RED);
                    consumedByTextView.setHint(R.string.consumed_by_error);
                    consumedByTextView.setText("");
                    isValid = false;
                } else {
                    Date consumedDateTemp = new Date(consumedDates[2], consumedDates[0], consumedDates[1]);
                    foodValues.put(FoodContract.ConsumedEntry.COLUMN_DATE_CONSUMED, FoodContract.normalizeDate(consumedDateTemp.getTime()));
                }
                if (isValid) {
                    Date purchasedByDate = new Date(purchasedByArray[2], purchasedByArray[0], purchasedByArray[1]);
                    foodValues.put(FoodContract.ConsumedEntry.COLUMN_DATE_PURCHASED, FoodContract.normalizeDate(purchasedByDate.getTime()));
                    Date expiredByDate = new Date(expiredByArray[2], expiredByArray[0], expiredByArray[1]);
                    foodValues.put(FoodContract.ConsumedEntry.COLUMN_EXPIRATION_DATE, FoodContract.normalizeDate(expiredByDate.getTime()));
                    double totalcost = Double.parseDouble(totalCostText) * 100;
                    foodValues.put(FoodContract.ConsumedEntry.COLUMN_VALUE, (int) totalcost);
                    foodValues.put(FoodContract.ConsumedEntry.COLUMN_QUANTITY, Integer.parseInt(quantityText));
                    foodValues.put(FoodContract.ConsumedEntry.COLUMN_FOOD_KEY, food_id);
                    getContentResolver().insert(FoodContract.ConsumedEntry.CONTENT_URI, foodValues);
                }

            } else if (tableText.equals("Thrown")) {
                EditText thrownByTextView = (EditText) findViewById(R.id.editThrownBy);
                thrownByText = thrownByTextView.getText().toString();
                int[] thrownDates = Utility.validateDate(thrownByText);
                if (thrownDates[0] == -1) {
                    thrownByTextView.setHintTextColor(Color.RED);
                    thrownByTextView.setHint(R.string.thrown_by_error);
                    thrownByTextView.setText("");
                    isValid = false;
                } else {
                    Date thrownDateTemp = new Date(thrownDates[2], thrownDates[0], thrownDates[1]);
                    foodValues.put(FoodContract.ThrownEntry.COLUMN_DATE_THROWN, FoodContract.normalizeDate(thrownDateTemp.getTime()));
                }
                if (isValid) {
                    Date purchasedByDate = new Date(purchasedByArray[2], purchasedByArray[0], purchasedByArray[1]);
                    foodValues.put(FoodContract.ThrownEntry.COLUMN_DATE_PURCHASED, FoodContract.normalizeDate(purchasedByDate.getTime()));
                    Date expiredByDate = new Date(expiredByArray[2], expiredByArray[0], expiredByArray[1]);
                    foodValues.put(FoodContract.ThrownEntry.COLUMN_EXPIRATION_DATE, FoodContract.normalizeDate(expiredByDate.getTime()));
                    double totalcost = Double.parseDouble(totalCostText) * 100;
                    foodValues.put(FoodContract.ThrownEntry.COLUMN_VALUE, (int) totalcost);
                    foodValues.put(FoodContract.ThrownEntry.COLUMN_QUANTITY, Integer.parseInt(quantityText));
                    foodValues.put(FoodContract.ThrownEntry.COLUMN_FOOD_KEY, food_id);
                    getContentResolver().insert(FoodContract.ThrownEntry.CONTENT_URI, foodValues);
                }
            } else {
                if (isValid) {
                    Date purchasedByDate = new Date(purchasedByArray[2], purchasedByArray[0], purchasedByArray[1]);
                    foodValues.put(FoodContract.CurrentEntry.COLUMN_DATE_PURCHASED, FoodContract.normalizeDate(purchasedByDate.getTime()));
                    Date expiredByDate = new Date(expiredByArray[2], expiredByArray[0], expiredByArray[1]);
                    foodValues.put(FoodContract.CurrentEntry.COLUMN_EXPIRATION_DATE, FoodContract.normalizeDate(expiredByDate.getTime()));
                    double totalcost = Double.parseDouble(totalCostText) * 100;
                    foodValues.put(FoodContract.CurrentEntry.COLUMN_VALUE, (int) totalcost);
                    foodValues.put(FoodContract.CurrentEntry.COLUMN_QUANTITY, Integer.parseInt(quantityText));
                    foodValues.put(FoodContract.CurrentEntry.COLUMN_FOOD_KEY, food_id);
                    getContentResolver().insert(FoodContract.CurrentEntry.CONTENT_URI, foodValues);
                }
            }
        }
        if(isValid) {
            Toast.makeText(getApplicationContext(), "Food added to " + tableText + ".",
                    Toast.LENGTH_SHORT).show();
            finish();
        }
        else {
            Toast.makeText(getApplicationContext(), "Failed to add food item.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_manual_input, menu);
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
