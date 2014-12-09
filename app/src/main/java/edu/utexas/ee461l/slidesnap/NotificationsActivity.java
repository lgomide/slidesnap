package edu.utexas.ee461l.slidesnap;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.List;

public class NotificationsActivity extends Activity {
    ListView listView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        // Defined Array values to show in ListView
       ArrayList<String> values = new ArrayList<String>();
        values.add("Sent and Unopened");
        values.add("Sent and Solved");
        values.add("Sent and Unsolved");
        values.add("Received and Unopened");
        values.add( "Received and Unsolved");
        values.add("Received Working");
        values.add("Sent and Unopened");
        values.add("Sent and Solved");
        values.add("Sent and Unopened");
        values.add("Received and Solved");
        values.add("Received and Unsolved");

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);
        NotificationsAdapter adapter = new NotificationsAdapter(this, values);
        listView.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notifications, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
