package edu.utexas.ee461l.slidesnap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;


public class SelectFriendsActivity extends Activity {

    public SelectFriendsAdapter adapter;
    ParseUser currentUser;
    ListView listView;
    ArrayList<String> friends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUser = ParseUser.getCurrentUser();
        setContentView(R.layout.activity_select_friends);
        friends = getFriends();
        listView = (ListView) findViewById(R.id.friendsList);
        adapter = new SelectFriendsAdapter(this, R.layout.activity_select_friends_rows, friends);
        listView.setAdapter(adapter);
    }


    public void SendPictureToServer(View v){
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String filePath = extras.getString("pathUri");
            if(filePath.contains("file:")){
                filePath = filePath.substring(7);
            }
            ParseUser currentUser = ParseUser.getCurrentUser();
            String username = currentUser.getUsername();

            Bitmap bmp = BitmapFactory.decodeFile(filePath);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();

            for (int i = 0; i < adapter.selectedFriends.size(); i++) {
                ParseObject imageData = new ParseObject("imageData");
                imageData.put("status", "unopened");
                imageData.put("to", adapter.selectedFriends.get(i));
                imageData.put("from", username);
                ParseFile imageFile = new ParseFile(filePath.substring(filePath.lastIndexOf("/") + 1), imageBytes);
                imageFile.saveInBackground();
                imageData.put("image", imageFile);
                imageData.saveInBackground();
            }
        }
        adapter.selectedFriends.clear();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.select_friends, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public ArrayList<String> getFriends(){
        ArrayList<String> friendList = new ArrayList<String>();
        try{
            JSONArray friendsList = currentUser.getJSONArray("friends");
            if(friendsList != null){
                for(int i = 0; i <friendsList.length(); i++){
                    friendList.add(friendsList.get(i).toString());
                }
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        Collections.sort(friendList, String.CASE_INSENSITIVE_ORDER);
        return friendList;
    }
}
