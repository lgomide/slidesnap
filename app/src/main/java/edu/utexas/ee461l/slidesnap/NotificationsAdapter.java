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

/**
 * Created by Jeanne on 12/8/14.
 */
public class NotificationsAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final String[] values;

        public NotificationsAdapter(Context context, String[] values) {
            super(context, R.layout.activity_notifications, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.activity_notifications, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.label);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
            textView.setText(values[position]);
            // Change the icon for Windows and iPhone
            String s = values[position];
            if (s.startsWith("Windows7") || s.startsWith("iPhone")
                    || s.startsWith("Solaris")) {
               // imageView.setImageResource(no);
            } else {
               // imageView.setImageResource(ok);
            }

            return rowView;
        }
    }
