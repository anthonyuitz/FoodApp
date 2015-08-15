package com.khmkau.codeu.foodapp;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.khmkau.codeu.foodapp.data.FoodContract;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ExistingInputActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Cursor searchResults = null;

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

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        switch(parent.getId()) {
            case R.id.tableSpinner:
                TableRow rowConsumed = (TableRow) findViewById(R.id.rowConsumed);
                TableRow rowThrown = (TableRow) findViewById(R.id.rowThrown);
                Button submitButton = (Button)findViewById(R.id.submitButton);
                Spinner tableSpinner = (Spinner)findViewById(R.id.tableSpinner);
                String submitText = submitButton.getText().toString();
                submitButton.setText(submitText.substring(0, submitText.lastIndexOf(' ') + 1) + tableSpinner.getSelectedItem().toString());

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

        EditText purchaseByEditText = (EditText)findViewById(R.id.editPurchasedBy);
        purchaseByEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar mCurrentDate = Calendar.getInstance();
                int mYear = mCurrentDate.get(Calendar.YEAR);
                int mMonth = mCurrentDate.get(Calendar.MONTH);
                int mDay = mCurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(ExistingInputActivity.
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

                DatePickerDialog mDatePicker = new DatePickerDialog(ExistingInputActivity.
                        this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        EditText c = (EditText) findViewById(R.id.editExpiresBy);
                        c.setText("" + (selectedmonth + 1) + "/" + selectedday + "/" + selectedyear);
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

                DatePickerDialog mDatePicker = new DatePickerDialog(ExistingInputActivity.
                        this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        EditText c = (EditText) findViewById(R.id.editConsumedBy);
                        c.setText("" + (selectedmonth + 1) + "/" + selectedday + "/" + selectedyear);
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

                DatePickerDialog mDatePicker = new DatePickerDialog(ExistingInputActivity.
                        this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        EditText c = (EditText) findViewById(R.id.editThrownBy);
                        c.setText("" + (selectedmonth + 1) + "/" + selectedday + "/" + selectedyear);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }
        });



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
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, food_names);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.searchInput);
        textView.setAdapter(adapter2);



    }

    public void search(View view) {

        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.searchInput);
        String searchTerm = textView.getText().toString();

        new SearchTask().execute(searchTerm);
    }

    public void insertFromExisting(View view) {
        if(searchResults == null) {
            Toast.makeText(getApplicationContext(), "Please wait while task executes.",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            int food_id = -1;
            if (searchResults.moveToFirst()) {
                food_id = searchResults.getInt(0);
            }
            if (food_id != -1) {
                ContentValues foodValues = new ContentValues();

                EditText quantityTextView = (EditText) findViewById(R.id.editQuantity);
                EditText purchasedByTextView = (EditText) findViewById(R.id.editPurchasedBy);
                EditText expiredByTextView = (EditText) findViewById(R.id.editExpiresBy);
                EditText totalCostTextView = (EditText) findViewById(R.id.editTotalCost);

                String quantityText = quantityTextView.getText().toString();
                String purchasedByText = purchasedByTextView.getText().toString();
                String expiredByText = expiredByTextView.getText().toString();
                String totalCostText = totalCostTextView.getText().toString();

                Spinner tableTextView = (Spinner) findViewById(R.id.tableSpinner);
                String tableText = tableTextView.getSelectedItem().toString();

                String consumedByText = "";
                String thrownByText = "";

                boolean isValid = true;

                if (!Utility.isNumeric(quantityText) || quantityText.equals("")) {
                    isValid = false;
                    quantityTextView.setHint(R.string.quantity_error);
                    quantityTextView.setHintTextColor(Color.RED);
                }
                int[] purchasedByArray = Utility.validateDate(purchasedByText);
                if (purchasedByArray[0] == -1) {
                    isValid = false;
                    purchasedByTextView.setHint(R.string.purchased_by_error);
                    purchasedByTextView.setHintTextColor(Color.RED);
                }
                int[] expiredByArray = Utility.validateDate(expiredByText);
                if (expiredByArray[0] == -1) {
                    isValid = false;
                    expiredByTextView.setHint(R.string.expires_by_error);
                    expiredByTextView.setHintTextColor(Color.RED);
                }
                if (!Utility.isDouble(totalCostText) || totalCostText.equals("")) {
                    isValid = false;
                    totalCostTextView.setHint(R.string.total_cost_error);
                    totalCostTextView.setHintTextColor(Color.RED);
                }
                if(isValid) {
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
        }
    }


    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public class SearchTask extends AsyncTask<String, Void, Cursor> {
        String inputFood = "";

        @Override
        protected Cursor doInBackground(String... params) {
            inputFood = params[0];
            if(inputFood.equals("")) {
                return null;
            }
            String[] proj = {
                    FoodContract.InfoEntry._ID,
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
            String[] selectArgs = {inputFood};

            Cursor cursor = getContentResolver().query(FoodContract.InfoEntry.CONTENT_URI,
                    proj,
                    selection,
                    selectArgs,
                    null);


            if (cursor.getCount() > 0) {
                return cursor;
            }
            else {
                return null;
            }

        }

        @Override
        protected void onPostExecute(Cursor result) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            if(result == null) {
                Toast.makeText(getApplicationContext(), "Sorry, no food items with that name exist.",
                        Toast.LENGTH_SHORT).show();

                TableLayout table = (TableLayout)findViewById(R.id.mainTable);
                table.setVisibility(View.GONE);

                Button submitButton = (Button)findViewById(R.id.submitButton);
                submitButton.setVisibility(View.GONE);
            }
            else {
                TableLayout table = (TableLayout)findViewById(R.id.mainTable);
                table.setVisibility(View.VISIBLE);

                Spinner tableSpinner = (Spinner)findViewById(R.id.tableSpinner);

                Button submitButton = (Button)findViewById(R.id.submitButton);
                submitButton.setText("Add " + inputFood + " to " + tableSpinner.getSelectedItem().toString());
                submitButton.setVisibility(View.VISIBLE);
                searchResults = result;
            }
        }
    }
}
