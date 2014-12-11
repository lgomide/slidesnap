package edu.utexas.ee461l.slidesnap;

import android.app.DialogFragment;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Jeanne on 12/10/14.
 */
public class ContactsAdapter extends ArrayAdapter<String> {

    Context context;
    int layoutResourceId;
    FriendHolder holder;
    ParseUser currentUser;
    ArrayList<String> friends;


    public ContactsAdapter(Context context, int layoutResourceId, ArrayList<String> friends) {
        super(context, layoutResourceId, friends);
        this.context = context;
        this.friends = friends;
        this.layoutResourceId = layoutResourceId;
        currentUser = ParseUser.getCurrentUser();

    }

    public void UpdateList(ArrayList<String> friends) {
        this.friends = friends;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new FriendHolder();
            holder.name = (TextView) row.findViewById(R.id.textView);
            holder.button = (ImageButton) row.findViewById(R.id.imageButton);
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FriendHolder w = (FriendHolder) v.getTag();
                    String deleteFriend = friends.get(w.position);
                    try{
                        currentUser.removeAll("friends", Arrays.asList(deleteFriend));
                        currentUser.save();
                        ContactsAdapter.this.remove(deleteFriend);
                    }catch(ParseException e){
                        e.printStackTrace();
                    }
                }
            });
            row.setTag(holder);

        } else {
            holder = (FriendHolder) row.getTag();
        }

        holder.button.setTag(holder);
        String name1 = friends.get(position);
        holder.name.setText(name1);
        holder.position=position;

        return row;
    }



    static class FriendHolder {
        TextView name;
        ImageButton button;
        int position;
    }
}

