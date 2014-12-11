package edu.utexas.ee461l.slidesnap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.internal.no;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class ContactsListActivity extends Activity {

    ParseUser currentUser;
    ListView listView;
    ContactsAdapter adapter;
    ArrayList<String> friends;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUser = ParseUser.getCurrentUser();
        setContentView(R.layout.activity_contacts_list);

        View contentView = (View)findViewById(R.id.activityContactsList);
        contentView.setOnTouchListener(new OnSwipeTouchListener(this) {

            @Override
            public void onSwipeRight() {
                startActivity(new Intent(ContactsListActivity.this, MainActivity.class));
            }

        });

        friends = getFriends();
        listView = (ListView) findViewById(R.id.friendsList);
        adapter = new ContactsAdapter(this, R.layout.activity_friend_row, friends);
        listView.setAdapter(adapter);

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

    public void addContact(View view){
        EditText text = (EditText) findViewById(R.id.newContact);
        addFriend(text.getText().toString());
        friends = getFriends();
        adapter.clear();
        adapter.addAll(friends);
    }

    public void SendPictureToServer(View v){
        //call server method
        Bundle extras = getIntent().getExtras();
        String fileUri = extras.getString("pathUri");
        System.out.println(fileUri); //
        UploadImage uploadTask = new UploadImage(fileUri, "saijelmokashi@gmail.com", "leo.1993gomide@gmail.com");
        uploadTask.execute();
        Toast.makeText(this,"Image and data have been stored",Toast.LENGTH_SHORT);
    }

    public void addFriend(String friend){
        ParseQuery<ParseUser> checkFriend = ParseUser.getQuery();
        checkFriend.whereEqualTo("username", friend);
        try {
            int count = checkFriend.count();
            if(count == 0){
                Toast.makeText(this, "User does not exist!", Toast.LENGTH_LONG).show();
            }else{
                currentUser.addUnique("friends",friend);
                currentUser.save();
            }
        } catch (ParseException e){
            e.printStackTrace();
        }
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
