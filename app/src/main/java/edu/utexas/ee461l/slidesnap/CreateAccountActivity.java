package edu.utexas.ee461l.slidesnap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;


public class CreateAccountActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }

    public void cancelButtonPress2(View v){
        startActivity(new Intent(CreateAccountActivity.this,InitialActivity.class));
    }

    public void createAccount(View v){
        EditText userNameEditText = (EditText) findViewById(R.id.userName2);
        EditText passwordEditText = (EditText) findViewById(R.id.password2);
        String userName = userNameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        ParseUser user = new ParseUser();
        user.setUsername(userName);
        user.setPassword(password);
        user.put("trophies",0);
        try{
            user.signUp();
            startActivity(new Intent(CreateAccountActivity.this,MainActivity.class));
        } catch(ParseException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

}
