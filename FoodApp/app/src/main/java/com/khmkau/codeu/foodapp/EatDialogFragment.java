package com.khmkau.codeu.foodapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

public class EatDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setTitle(R.string.eat_label)
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        .setView(inflater.inflate(R.layout.eat_dialog, null))
                // Add action buttons
                .setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {


//                        ContentValues currentValues = new ContentValues();
//
//                        // int foodKey = getCurrentFoodKey thru query?
//                        // int currentQty = getCurrentQuantity thru query?
//                        // long datePurch = getDatePurch thru query?
//                        // long expDate = getExpDate thru query?
//                        // String value = getValue thru query?
//
//                         int updatedQty = currentQty;
//                        // negative input: no change
//                        // input >= currentQty: delete the entire DB row
//                        // input < currentQty: update the DB row
//                        // savedInstanceState...?
//                        EditText input = (EditText)rootView.findViewById(R.id.eat_request);
//                        int inputQty = Integer.parseInt(input.getText().toString());
//                        if (inputQty >= currentQty) {
//                            // step 1: delete entire DB row
//
//                            // step 2: update the eaten database to include info from deleted row
//
//                        } else if (inputQty) < currentQty && Integer.parseInt(input.getText()) > 0) {
//                            // step 1: change updateQty so that it reflects amount
//                            updatedQty -= inputQty;
//
//                            // step 2 update the eaten database
//                        }
//
//                        // Then add the data, along with the corresponding name of the data type,
//                        // so the content provider knows what kind of value is being inserted.
//                        currentValues.put(FoodContract.CurrentEntry.COLUMN_FOOD_KEY, "1121");
//                        currentValues.put(FoodContract.CurrentEntry.COLUMN_QUANTITY, updatedQty);
//                        currentValues.put(FoodContract.CurrentEntry.COLUMN_DATE_PURCHASED, "1439088879355");
//                        currentValues.put(FoodContract.CurrentEntry.COLUMN_EXPIRATION_DATE, "1439088879355");
//                        currentValues.put(FoodContract.CurrentEntry.COLUMN_VALUE, "$4.99");
//
//                        // Finally, insert location data into the database.
//                        getActivity().getContentResolver().insert(
//                                FoodContract.CurrentEntry.CONTENT_URI,
//                                currentValues
//                        );
                    }
                })
                .setNegativeButton(R.string.cancel_label, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EatDialogFragment.this.getDialog().cancel();
            }
        });
        return builder.create();
    }
}