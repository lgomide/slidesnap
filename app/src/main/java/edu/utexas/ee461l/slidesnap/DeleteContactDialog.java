package edu.utexas.ee461l.slidesnap;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.DialogFragment;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.Arrays;


/**
 * Created by Jeanne on 12/10/14.
 */
public class DeleteContactDialog extends DialogFragment{


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        final String deleteFriend = savedInstanceState.getString("friend");
        final ParseUser currentUser = ParseUser.getCurrentUser();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Delete " + deleteFriend + "?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try{
                            currentUser.removeAll("friends", Arrays.asList(deleteFriend));
                            currentUser.save();
                        }catch(ParseException e){
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //stays on current activity
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
