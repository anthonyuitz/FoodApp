package com.khmkau.codeu.foodapp;

import android.app.DatePickerDialog;
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

import java.util.Calendar;

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
