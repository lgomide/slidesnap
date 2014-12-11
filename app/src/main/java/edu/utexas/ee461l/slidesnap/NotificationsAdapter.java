package edu.utexas.ee461l.slidesnap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.internal.no;
import com.google.android.gms.internal.ok;
import com.google.common.base.Strings;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Jeanne on 12/8/14.
 */
public class NotificationsAdapter extends ArrayAdapter<PuzzleEntry> {

    Context context;
    int layoutResourceId;
    PuzzleEntryHolder holder;
    ParseUser currentUser;
    ArrayList<PuzzleEntry> values;

    public NotificationsAdapter(Context context, int layoutResourceId, ArrayList<PuzzleEntry> values) {
        super(context, R.layout.activity_rows, R.id.textView, values);
        this.context = context;
        this.values = values;
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new PuzzleEntryHolder();
            holder.user = (TextView) row.findViewById(R.id.friendName);
            holder.status = (ImageView) row.findViewById(R.id.statusImage);
            holder.button = (ImageButton) row.findViewById(R.id.openButton);
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PuzzleEntryHolder w = (PuzzleEntryHolder) v.getTag();
                    Intent i = new Intent(v.getContext(),SlidePuzzleActivity.class);
                    i.putExtra("objectID", values.get(w.position).getObjectId());
                    i.putExtra("pathUri", values.get(w.position).getPath());
                    v.getContext().startActivity(i);
                }
            });
            row.setTag(holder);

        } else {
            holder = (PuzzleEntryHolder) row.getTag();
        }
        holder.button.setTag(holder);
        String friendName = values.get(position).getUser();
        holder.user.setText(friendName);
        holder.position=position;
        String status = values.get(position).getStatus();
        if (status.equals("UnopenedSend")) {
            holder.status.setImageResource(R.drawable.sender_arrow);
            holder.button.setVisibility(View.INVISIBLE);
        }else if (status.equals("SendRight")) {
            holder.status.setImageResource(R.drawable.sender_check);
            holder.button.setVisibility(View.INVISIBLE);
        }else if (status.equals("SendWrong")) {
            holder.status.setImageResource(R.drawable.sender_x);
            holder.button.setVisibility(View.INVISIBLE);
        }else if (status.equals("ReceivedUnopened")) {
            holder.status.setImageResource(R.drawable.receiver_arrow);
        }else if (status.equals("ReceiveRight")) {
            holder.status.setImageResource(R.drawable.receiver_check);
            holder.button.setVisibility(View.INVISIBLE);
        }else if (status.equals("ReceiveWrong")) {
            holder.status.setImageResource(R.drawable.receiver_x);
            holder.button.setVisibility(View.INVISIBLE);
        }
//        if (s.equals("Received Working")) {
//            imageView.setImageResource(R.drawable.countdown);
//        }

        return row;
    }



    static class PuzzleEntryHolder {
        ImageView status;
        TextView user;
        ImageButton button;
        int position;
    }
}
