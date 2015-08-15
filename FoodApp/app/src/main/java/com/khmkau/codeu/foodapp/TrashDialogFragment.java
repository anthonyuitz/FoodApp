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

public class TrashDialogFragment extends DialogFragment {

    public View rootView;
    public int foodID;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        rootView = inflater.inflate(R.layout.trash_dialog, null);
        foodID = getArguments().getInt("foodIDKey");

        builder.setTitle(R.string.trash_label)
                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                .setView(rootView)
                        // Add action buttons
                .setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        EditText input = (EditText) rootView.findViewById(R.id.trash_request);
                        String quantity = input.getText().toString();

                        if (!quantity.equals("")) {
                            int trashQty = Integer.parseInt(quantity);

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

                            Uri trashedUri = FoodContract.ThrownEntry.CONTENT_URI;

                            String trashedSelection = FoodContract.ThrownEntry.TABLE_NAME + "." + FoodContract.ThrownEntry._ID + " = ?";
                            String[] trashArgs = {Integer.toString(foodID)};

                            Cursor trashedCursor = getActivity().getContentResolver().query(
                                    trashedUri, // uri
                                    null, // projection
                                    trashedSelection, // selection
                                    trashArgs, // selection args
                                    null); // sortOrder

                            if (trashQty >= currentQty) {
                                // step 1: update TRASH table to include info from entire deleted row
                                ContentValues trashedInfo = new ContentValues();
                                DatabaseUtils.cursorRowToContentValues(cursor, trashedInfo);
                                trashedInfo.put(FoodContract.ThrownEntry.COLUMN_DATE_THROWN, FoodContract.normalizeDate((new Date()).getTime()));

                                if (trashedCursor != null) {    // if already exists: update
                                    getActivity().getContentResolver().update(
                                            FoodContract.ThrownEntry.CONTENT_URI,
                                            trashedInfo,
                                            trashedSelection,
                                            trashArgs);
                                } else { // does not exist in consumed table
                                    getActivity().getContentResolver().insert(trashedUri, trashedInfo);
                                }

                                // step 2: delete entire row in CURRENT table
                                getActivity().getContentResolver().delete(currentUri, selection, selectionArgs);
                                Toast.makeText(getActivity(), "Fridge updated", Toast.LENGTH_SHORT).show();
                            } else if (trashQty < currentQty && trashQty > 0) {
                                // step 1: change updateQty so that it reflects amount
                                currentQty -= trashQty;

                                // step 2: update CURRENT table
                                ContentValues updatedInfo = new ContentValues();
                                updatedInfo.put(FoodContract.CurrentEntry.COLUMN_QUANTITY, currentQty);
                                getActivity().getContentResolver().update(FoodContract.CurrentEntry.CONTENT_URI, updatedInfo, selection, selectionArgs);

                                // step 3: update the eaten database
                                ContentValues trashedInfo = new ContentValues();
                                DatabaseUtils.cursorRowToContentValues(cursor, trashedInfo);
                                trashedInfo.put(FoodContract.ThrownEntry.COLUMN_DATE_THROWN, FoodContract.normalizeDate((new Date()).getTime()));

                                if (trashedCursor != null) {    // if already exists: update
                                    getActivity().getContentResolver().update(
                                            FoodContract.ThrownEntry.CONTENT_URI,
                                            trashedInfo,
                                            trashedSelection,
                                            trashArgs);
                                } else { // does not exist in consumed table
                                    getActivity().getContentResolver().insert(trashedUri, trashedInfo);
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
                        TrashDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}