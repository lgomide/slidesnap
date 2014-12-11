package edu.utexas.ee461l.slidesnap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.parse.Parse;
import com.parse.ParseUser;


public class InitialActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        Parse.initialize(this, "U126GWgnHbXeMFba1NEtFfrJ1NcC1koXE694jCPY", "Po6h6V5LOGDrCm6YsHXVTwHLUEVZep04Lyh4uqjK");
        ParseUser currentUser = ParseUser.getCurrentUser();
        if(currentUser != null){
            startActivity(new Intent(InitialActivity.this,MainActivity.class));
        }
    }

    public void initLogInButtonPress(View v){
        startActivity(new Intent(InitialActivity.this,LoginActivity.class));
    }

    public void initCreateAccountButtonPress(View v){
        startActivity(new Intent(InitialActivity.this,CreateAccountActivity.class));
    }

}
