package edu.utexas.ee461l.slidesnap;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.parse.GetDataCallback;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.google.common.base.Strings;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import java.util.ArrayList;
import java.util.List;

public class NotificationsActivity extends Activity {
    ListView listView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        View contentView = (View)findViewById(R.id.NotificationsActivity);
        contentView.setOnTouchListener(new OnSwipeTouchListener(this) {

            @Override
            public void onSwipeLeft() {
                startActivity(new Intent(NotificationsActivity.this, MainActivity.class));
            }

        });

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
        values.add("Sent and Unopened");
        values.add("Received and Solved");
        values.add("Received and Unsolved");

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);
        NotificationsAdapter adapter = new NotificationsAdapter(this, values);
        listView.setAdapter(adapter);

    }

    public void backToMainPage(View view){
        startActivity(new Intent(NotificationsActivity.this, MainActivity.class));
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

    public ArrayList<PuzzleEntry> getAllEntries(){
        ArrayList<PuzzleEntry> allEntries = new ArrayList<PuzzleEntry>();
        ParseQuery<ParseObject> sentQuery = ParseQuery.getQuery("imageData");
        sentQuery.whereEqualTo("from", "leo.1993gomide@gmail.com");
        sentQuery.addDescendingOrder("createdAt");
        ParseQuery<ParseObject> receivedQuery = ParseQuery.getQuery("imageData");
        receivedQuery.whereEqualTo("to", "leo.1993gomide@gmail.com");
        receivedQuery.addDescendingOrder("createdAt");
        try{
            List<ParseObject> sentResults = sentQuery.find();
            List<ParseObject> receivedResults = receivedQuery.find();
            while(!sentResults.isEmpty() || !receivedResults.isEmpty()){
                if(receivedResults.isEmpty()){
                    //do sent
                    ParseObject entry = sentResults.remove(0);
                    String status = (String) entry.get("status");
                    if(status.equals("unopened")){
                        status="UnopenedSend";
                    }else if(status.equals("correct")){
                        status = "SendRight";
                    }else if(status.equals("wrong")){
                        status = "SendWrong";
                    }
                    allEntries.add(new PuzzleEntry(status,null,(String) entry.get("to"),entry.getObjectId()));
                }else if(sentResults.isEmpty()) {
                    //do received
                    ParseObject entry = receivedResults.remove(0);
                    ParseFile image = (ParseFile) entry.get("image");
                    File mediaFile = new File(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_DCIM), ".SlideSnap" + File.separator + image.getName());
                    byte[] imageBytes = image.getData();
                    if(!mediaFile.exists()){
                        FileOutputStream fos = new FileOutputStream(mediaFile.getPath());
                        fos.write(imageBytes);
                        fos.close();
                    }
                    String status = (String) entry.get("status");
                    if(status.equals("unopened")){
                        status="UnopenedReceive";
                    }else if(status.equals("correct")){
                        status = "ReceiveRight";
                    }else if(status.equals("wrong")){
                        status = "ReceiveWrong";
                    }
                    allEntries.add(new PuzzleEntry(status,mediaFile.toURI(),(String) entry.get("from"),entry.getObjectId()));
                }else if((sentResults.get(0).getCreatedAt().after(receivedResults.get(0).getCreatedAt()))) {
                        //do sent
                        ParseObject entry = sentResults.remove(0);
                        String status = (String) entry.get("status");
                        if(status.equals("unopened")){
                            status="UnopenedSend";
                        }else if(status.equals("correct")){
                            status = "SendRight";
                        }else if(status.equals("wrong")){
                            status = "SendWrong";
                        }
                        allEntries.add(new PuzzleEntry(status,null,(String) entry.get("to"),entry.getObjectId()));
                    }else {
                        //do received
                        ParseObject entry = receivedResults.remove(0);
                        ParseFile image = (ParseFile) entry.get("image");
                        File mediaFile = new File(Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_DCIM), ".SlideSnap" + File.separator + image.getName());
                        byte[] imageBytes = image.getData();
                        if(!mediaFile.exists()){
                            FileOutputStream fos = new FileOutputStream(mediaFile.getPath());
                            fos.write(imageBytes);
                            fos.close();
                        }
                        String status = (String) entry.get("status");
                        if(status.equals("unopened")){
                            status="UnopenedReceive";
                        }else if(status.equals("correct")){
                            status = "ReceiveRight";
                        }else if(status.equals("wrong")){
                            status = "ReceiveWrong";
                        }
                        allEntries.add(new PuzzleEntry(status,mediaFile.toURI(),(String) entry.get("from"),entry.getObjectId()));
                    }
                }
        } catch (com.parse.ParseException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return allEntries;
    }

    public void testFunction(View v){
        getAllEntries();

    }
}
