package edu.utexas.ee461l.slidesnap;

/**
 * Created by Leo on 12/10/2014.
 */
public class PasswordException extends Exception {
    private String message;

    public PasswordException(String msg){
        message = msg;
    }

    public String getMessage(){
        return message;
    }
}
