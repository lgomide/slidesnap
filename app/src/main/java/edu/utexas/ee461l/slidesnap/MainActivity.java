package edu.utexas.ee461l.slidesnap;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.parse.ParseUser;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jeanne on 11/11/14.
 */

public class MainActivity extends Activity {

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private Uri fileUri;
    public static final int MEDIA_TYPE_IMAGE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View view = (View) findViewById(R.id.MainActivity);
        view.setOnTouchListener(new OnSwipeTouchListener(this){
           @Override
        public void onSwipeLeft(){
            startActivity(new Intent(MainActivity.this, ContactsListActivity.class));
            }
            @Override
        public void onSwipeRight(){
            Intent i = new Intent(MainActivity.this, NotificationsActivity.class);
            startActivity(new Intent(MainActivity.this, NotificationsActivity.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                startActivity(new Intent(MainActivity.this, SelectFriendsActivity.class).putExtra("pathUri", fileUri.toString()));
                System.out.println("file path " + fileUri);
            } else if (resultCode == RESULT_CANCELED) {
            } else {
            }
        }
    }

    public void sendToNotifications(View view){
        startActivity(new Intent(MainActivity.this, NotificationsActivity.class));
    }

    public void sendToContacts(View view){
        startActivity(new Intent(MainActivity.this, ContactsListActivity.class));
    }

    public void sentToTrophies(View view){
        startActivity(new Intent(MainActivity.this, TrophyActivity.class));
    }


    public void safeCameraOpenInView(View v) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
        if(fileUri!=null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
            intent.putExtra("return-data", true);
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }

    }

    public void logOut(View v) {
        ParseUser currentUser = ParseUser.getCurrentUser();
        currentUser.logOut();
        startActivity(new Intent(MainActivity.this, InitialActivity.class));
    }
    /**
     * Create a file Uri for saving an image or video
     */
    private static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * Create a File for saving an image or video
     */
    private static File getOutputMediaFile(int type) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Slidesnap");
        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWriteable = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }
        return mediaFile;
    }

}

