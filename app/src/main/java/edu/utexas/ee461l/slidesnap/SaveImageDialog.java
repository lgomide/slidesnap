package edu.utexas.ee461l.slidesnap;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class SaveImageDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Save image to Camera Roll?")
                .setPositiveButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        SlidePuzzleActivity.PUZZLE_SOLVED = false;
                        getActivity().finish();
                        Intent intent = new Intent(getActivity(), TestActivity.class);
                        startActivity(intent);

                    }
                })
                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // TODO Save the image to the camera roll
                        SlidePuzzleActivity.PUZZLE_SOLVED = false;
                        getActivity().finish();
                        Intent intent = new Intent(getActivity(), TestActivity.class);
                        startActivity(intent);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}