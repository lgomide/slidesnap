package edu.utexas.ee461l.slidesnap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Jeanne on 12/11/14.
 */
public class SelectFriendsAdapter extends ArrayAdapter<String> {

    Context context;
    int layoutResourceId;
    FriendHolder holder;
    ParseUser currentUser;
    ArrayList<String> friends;
    ArrayList<String> selectedFriends;


    public SelectFriendsAdapter(Context context, int layoutResourceId, ArrayList<String> friends) {
        super(context, layoutResourceId, friends);
        this.context = context;
        this.friends = friends;
        this.layoutResourceId = layoutResourceId;
        currentUser = ParseUser.getCurrentUser();
        selectedFriends = new ArrayList<String>();

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
            holder.checkBox = (CheckBox) row.findViewById(R.id.checkBox);
            holder.checkBox.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    FriendHolder w = (FriendHolder) v.getTag();
                    String selectedFriend = friends.get(w.position);
                    boolean checked = ((CheckBox) v).isChecked();
                    if(checked) {
                        selectedFriends.add(selectedFriend);
                    }
                    else{
                        selectedFriends.remove(selectedFriend);
                    }
                }
            });
            row.setTag(holder);

        } else {
            holder = (FriendHolder) row.getTag();
        }

        holder.checkBox.setTag(holder);
        String name1 = friends.get(position);
        holder.name.setText(name1);
        holder.position=position;

        return row;
    }

    static class FriendHolder {
        TextView name;
        CheckBox checkBox;
        int position;
    }

}
