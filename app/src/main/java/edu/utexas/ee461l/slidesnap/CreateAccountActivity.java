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
        try{
            checkPassword(password);
            ParseUser user = new ParseUser();
            user.setUsername(userName);
            user.setPassword(password);
            user.put("trophies",0);
            user.signUp();
            startActivity(new Intent(CreateAccountActivity.this,MainActivity.class));
        } catch(ParseException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        } catch(PasswordException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public void checkPassword(String password) throws PasswordException{
        if(password.length() < 6 || password.length() > 12){
            throw new PasswordException("Password must be greater than 6 and less than 12 characters");
        }
        boolean hasNum = false;
        boolean hasSpecial = false;
        boolean hasLetters = false;
        for(int i = 0; i < password.length(); i++){
            char c = password.charAt(i);
            if(Character.isDigit(c)){
                hasNum = true;
            }else if(Character.isLetter(c)){
                hasLetters = true;
            }else{
                hasSpecial = true;
            }
        }
        if(!hasNum){
            throw new PasswordException("Password must contain at least 1 number");
        }
        if(!hasLetters){
            throw new PasswordException("Password must contain at least 1 letter");
        }
        if(hasSpecial){
            throw new PasswordException("Password can only contain letters or numbers");
        }
    }

}
