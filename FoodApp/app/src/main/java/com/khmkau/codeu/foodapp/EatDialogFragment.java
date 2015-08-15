package com.khmkau.codeu.foodapp;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.khmkau.codeu.foodapp.data.FoodContract;

import java.util.Date;

public class EatDialogFragment extends DialogFragment {

    public View rootView;
    public int foodID;

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        rootView = inflater.inflate(R.layout.eat_dialog, null);
        foodID = getArguments().getInt("foodIDKey");

        builder.setTitle(R.string.eat_label)
        .setView(rootView)
                .setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // refresh the menu!
                        // int updatedQty = currentQty;
                        // negative input: no change
                        // eatQty >= currentQty: delete the entire DB row
                        // eatQty < currentQty: update the DB row
                        EditText input = (EditText) rootView.findViewById(R.id.eat_request);
                        String quantity = input.getText().toString();

                        if (!quantity.equals("")) {
                            int eatQty = Integer.parseInt(quantity);

                            Uri currentUri = FoodContract.CurrentEntry.CONTENT_URI;
                            String[] projection = {FoodContract.CurrentEntry.TABLE_NAME + "." + FoodContract.CurrentEntry._ID, FoodContract.CurrentEntry.COLUMN_QUANTITY};
                            String selection = FoodContract.CurrentEntry.TABLE_NAME + "." + FoodContract.CurrentEntry._ID + " = ?";
                            String[] selectionArgs = {Integer.toString(foodID)};

                            Cursor cursor = getActivity().getContentResolver().query(
                                    currentUri, // uri
                                    projection, // projection
                                    selection, // selection
                                    selectionArgs, // selection args
                                    null); // sortOrder
                            // int currentQty = currentCursor.getInt(cursor.getColumnIndex(FoodContract.CurrentEntry.COLUMN_QUANTITY));
                            int currentQty = 0;
                            if (cursor.moveToFirst()) {
                                do {
                                    int i = cursor.getInt(0);
                                    if (i == foodID) {
                                        currentQty = cursor.getInt(1);
                                        break;
                                    }
                                } while (cursor.moveToNext());
                            }

                            Uri consumedUri = FoodContract.ConsumedEntry.CONTENT_URI;

                            String consumedSelection = FoodContract.ConsumedEntry.TABLE_NAME + "." + FoodContract.ConsumedEntry._ID + " = ?";
                            String[] consumedArgs = {Integer.toString(foodID)};

                            Cursor consumedCursor = getActivity().getContentResolver().query(
                                    currentUri, // uri
                                    null, // projection
                                    selection, // selection
                                    selectionArgs, // selection args
                                    null); // sortOrder

                            if (eatQty >= currentQty) {
                                // step 1: update CONSUMED table to include info from entire deleted row
                                ContentValues consumedInfo = new ContentValues();
                                DatabaseUtils.cursorRowToContentValues(cursor, consumedInfo);
                                consumedInfo.put(FoodContract.ConsumedEntry.COLUMN_DATE_CONSUMED, FoodContract.normalizeDate((new Date()).getTime()));

                                if (consumedCursor != null) {    // if already exists: update
                                    getActivity().getContentResolver().update(
                                            FoodContract.ConsumedEntry.CONTENT_URI,
                                            consumedInfo,
                                            consumedSelection,
                                            consumedArgs);
                                } else { // does not exist in consumed table
                                    getActivity().getContentResolver().insert(consumedUri, consumedInfo);
                                }

                                // step 2: delete entire row in CURRENT table
                                getActivity().getContentResolver().delete(currentUri, selection, selectionArgs);
                                Toast.makeText(getActivity(), "Fridge updated", Toast.LENGTH_SHORT).show();
                            } else if (eatQty < currentQty && eatQty > 0) {
                                // step 1: change updateQty so that it reflects amount
                                currentQty -= eatQty;

                                // step 2: update CURRENT table
                                ContentValues updatedInfo = new ContentValues();
                                updatedInfo.put(FoodContract.CurrentEntry.COLUMN_QUANTITY, currentQty);
                                getActivity().getContentResolver().update(FoodContract.CurrentEntry.CONTENT_URI, updatedInfo, selection, selectionArgs);

                                // step 3: update the eaten database
                                ContentValues consumedInfo = new ContentValues();
                                DatabaseUtils.cursorRowToContentValues(cursor, consumedInfo);
                                consumedInfo.put(FoodContract.ConsumedEntry.COLUMN_DATE_CONSUMED, FoodContract.normalizeDate((new Date()).getTime()));

                                if (consumedCursor != null) {    // if already exists: update
                                    getActivity().getContentResolver().update(
                                            FoodContract.ConsumedEntry.CONTENT_URI,
                                            consumedInfo,
                                            consumedSelection,
                                            consumedArgs);
                                } else { // does not exist in consumed table
                                    getActivity().getContentResolver().insert(consumedUri, consumedInfo);
                                }

                                // step 4: wrap up branch with "updated" toast
                                Toast.makeText(getActivity(), "Fridge Updated", Toast.LENGTH_SHORT).show();
                            }

                            // Toast.makeText(getActivity(), "Fridge Updated", Toast.LENGTH_SHORT).show();
                        }
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