package edu.utexas.ee461l.slidesnap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;


public class LoginActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void logInUser(View v){
        EditText userNameTextField = (EditText) findViewById(R.id.userName);
        EditText passwordTextField = (EditText) findViewById(R.id.password);
        String userName = userNameTextField.getText().toString();
        String password = passwordTextField.getText().toString();
        try{
            ParseUser.logIn(userName,password);
            Toast.makeText(this,"User is logged in",Toast.LENGTH_LONG).show();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        } catch(ParseException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public void cancelButtonPress(View v){
        startActivity(new Intent(LoginActivity.this,InitialActivity.class));
    }


}
