package edu.utexas.ee461l.slidesnap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.internal.no;
import com.google.android.gms.internal.ok;
import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Jeanne on 12/8/14.
 */
public class NotificationsAdapter extends ArrayAdapter<String> {
        Context context;
        ArrayList<String> values;
      //  LayoutInflater inflater;

        public NotificationsAdapter(Context context, ArrayList<String> values) {
            super(context, R.layout.activity_rows, R.id.textView, values);
            this.context = context;
            this.values = values;
            //inflater = LayoutInflater.from(context);
        }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_rows, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.textView);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView);
        textView.setText(values.get(position));
        // Change the icon for Windows and iPhone
        String s = values.get(position);
        if (s.equals("Sent and Unopened")) {
            imageView.setImageResource(R.drawable.sender_arrow);
        }
        if (s.equals("Sent and Solved")) {
            imageView.setImageResource(R.drawable.sender_check);
        }
        if (s.equals("Sent and Unsolved")) {
            imageView.setImageResource(R.drawable.sender_x);
        }
        if (s.equals("Received and Unopened")) {
            imageView.setImageResource(R.drawable.receiver_arrow);
        }
        if (s.equals("Received and Solved")) {
            imageView.setImageResource(R.drawable.receiver_check);
        }
        if (s.equals("Received and Unsolved")) {
            imageView.setImageResource(R.drawable.receiver_x);
        }
        if (s.equals("Received Working")) {
            imageView.setImageResource(R.drawable.countdown);
        }
        return rowView;
    }
}
