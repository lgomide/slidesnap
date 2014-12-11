package edu.utexas.ee461l.slidesnap;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class NotificationsActivity extends Activity {
    ListView listView ;
    ParseUser currentUser;
    NotificationsAdapter adapter;
    ArrayList<PuzzleEntry> entries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(SlidePuzzleActivity.gameOver) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            GameOverDialog dialog = new GameOverDialog();
            dialog.show(ft, "dialog");
        }
        currentUser = ParseUser.getCurrentUser();
        setContentView(R.layout.activity_notifications);
        View contentView = (View)findViewById(R.id.NotificationsActivity);
        contentView.setOnTouchListener(new OnSwipeTouchListener(this) {

            @Override
            public void onSwipeLeft() {
                startActivity(new Intent(NotificationsActivity.this, MainActivity.class));
            }

        });
        entries = getAllEntries();
        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);
        adapter = new NotificationsAdapter(this, R.layout.activity_notifications_row,entries);
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

    public ArrayList<PuzzleEntry> getAllEntries(){
        ArrayList<PuzzleEntry> allEntries = new ArrayList<PuzzleEntry>();
        ParseQuery<ParseObject> sentQuery = ParseQuery.getQuery("imageData");
        sentQuery.whereEqualTo("from", currentUser.getUsername());
        sentQuery.addDescendingOrder("createdAt");
        ParseQuery<ParseObject> receivedQuery = ParseQuery.getQuery("imageData");
        receivedQuery.whereEqualTo("to", currentUser.getUsername());
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
                    File mediaFileDir = new File(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_DCIM), ".SlideSnap");
                    if(!mediaFileDir.exists()){
                        mediaFileDir.mkdirs();
                    }
                    File mediaFile = new File(mediaFileDir.getPath()+ File.separator + image.getName());
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
                    allEntries.add(new PuzzleEntry(status,mediaFile.getPath(),(String) entry.get("from"),entry.getObjectId()));
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
                        File mediaFileDir = new File(Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_DCIM), ".SlideSnap");
                        if(!mediaFileDir.exists()){
                            mediaFileDir.mkdirs();
                        }
                        File mediaFile = new File(mediaFileDir.getPath()+ File.separator + image.getName());
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
                        allEntries.add(new PuzzleEntry(status,mediaFile.getPath(),(String) entry.get("from"),entry.getObjectId()));
                    }
                }
        } catch (com.parse.ParseException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return allEntries;
    }

    public void refreshNotifications(View v) {
        entries = getAllEntries();
        adapter.clear();
        adapter.addAll(entries);
    }

}
