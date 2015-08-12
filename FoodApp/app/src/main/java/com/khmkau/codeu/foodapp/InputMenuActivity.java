package com.khmkau.codeu.foodapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class InputMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_input_menu, menu);
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

    public void featureNotImplemented(View view) {
        Toast.makeText(getApplicationContext(), "This feature is not yet implemented. Sorry!",
                Toast.LENGTH_SHORT).show();
    }

    public void receiptView(View view) {
        Intent intent = new Intent(this, ReceiptInputActivity.class);
        startActivity(intent);
    }

    public void manualView(View view)
    {
        Intent intent = new Intent(this, ManualInputActivity.class);
        startActivity(intent);
    }

    public void existingView(View view)
    {
        Intent intent = new Intent(this, ExistingInputActivity.class);
        startActivity(intent);
    }
}
