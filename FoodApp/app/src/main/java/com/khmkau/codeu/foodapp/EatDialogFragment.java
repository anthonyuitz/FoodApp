package com.khmkau.codeu.foodapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.khmkau.codeu.foodapp.data.FoodContract;

public class EatDialogFragment extends DialogFragment {

    public View rootView;
    public int foodID;

    static final int COL_QTY = 0;

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        rootView = inflater.inflate(R.layout.eat_dialog, null);
        foodID = getArguments().getInt("foodIDKey");
        Toast.makeText(getActivity(), Integer.toString(foodID), Toast.LENGTH_SHORT).show();
        builder.setTitle(R.string.eat_label)

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        .setView(rootView)
                // Add action buttons

                .setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // update the database: CURRENT table and CONSUMED table
                        // refresh the menu!

                        // int updatedQty = currentQty;
                        // negative input: no change
                        // eatQty >= currentQty: delete the entire DB row
                        // eatQty < currentQty: update the DB row
                        EditText input = (EditText) rootView.findViewById(R.id.eat_request);
                        int eatQty = Integer.parseInt(input.getText().toString());

//                        // need to know the current quantity of the selected item...
                        Uri currentUri = FoodContract.CurrentEntry.CONTENT_URI;
                        String[] projection = {FoodContract.CurrentEntry.TABLE_NAME + "." + FoodContract.CurrentEntry._ID, FoodContract.CurrentEntry.COLUMN_QUANTITY};
                        String selection = FoodContract.CurrentEntry.TABLE_NAME + "." + FoodContract.CurrentEntry._ID + " = ?";
                        String[] selectionArgs = {Integer.toString(foodID)};
                        Cursor cursor = getActivity().getContentResolver().query(
                                currentUri,
                                projection,
                                selection,
                                selectionArgs,
                                null);
//                        cursor.moveToFirst();
//                        int currentQty = currentCursor.getInt(cursor.getColumnIndex(FoodContract.CurrentEntry.COLUMN_QUANTITY));
                        if (cursor.moveToFirst()) {
                            do {
                                int i = cursor.getInt(0);
                                if (i == foodID) {
                                    Toast.makeText(getActivity(), "Quantity: " + cursor.getInt(1) + "|" + cursor.getCount(), Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            } while (cursor.moveToNext());
                        }

//                        Uri consumedUri = FoodContract.ConsumedEntry.CONTENT_URI;
//                        if (eatQty >= currentQty) {
//                            // step 1: update CONSUMED table to include info from entire deleted row
//                            ContentValues consumedInfo = new ContentValues();
//                            DatabaseUtils.cursorRowToContentValues(currentCursor, consumedInfo);
//                            getActivity().getContentResolver().insert(consumedUri, consumedInfo);
//
//                            // step 2: delete entire row in CURRENT table
//                            // Toast.makeText(getActivity(), "GOOD JOB THE QUERY FOR CURRENT WORKS", Toast.LENGTH_SHORT).show();
//                            getActivity().getContentResolver().delete(currentUri, selection, selectionArgs);
//                        } else if (eatQty < currentQty && eatQty > 0) {
//                            // step 1: change updateQty so that it reflects amount
//                            // updatedQty -= eatQty;
//                            // step 2: update CURRENT table
//                            // step 2 update the eaten database
//                        }
//
//                        // Toast.makeText(getActivity(), "Fridge Updated", Toast.LENGTH_SHORT).show();
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