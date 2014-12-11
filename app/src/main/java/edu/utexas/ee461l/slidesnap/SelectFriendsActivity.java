package edu.utexas.ee461l.slidesnap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class SelectFriendsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_friends);
    }

    public void SendToFriends(View view){
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String pathUri = extras.getString("pathUri");
            Intent i = new Intent(SelectFriendsActivity.this, SlidePuzzleActivity.class);
            i.putExtra("pathUri",pathUri);
            startActivity(i);
        }
    }


    public void SendPictureToServer(View v){
        //call server method
        Bundle extras = getIntent().getExtras();
        String fileUri = extras.getString("pathUri");
        System.out.println(fileUri); //
        UploadImage uploadTask = new UploadImage(fileUri, "saijelmokashi@gmail.com", "leo.1993gomide@gmail.com");
        uploadTask.execute();
        Toast.makeText(this, "Image and data have been stored", Toast.LENGTH_SHORT);
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
}
