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