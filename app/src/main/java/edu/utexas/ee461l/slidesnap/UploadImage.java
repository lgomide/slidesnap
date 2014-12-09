package edu.utexas.ee461l.slidesnap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.parse.ParseFile;
import com.parse.ParseObject;

import java.io.ByteArrayOutputStream;


/**
 * Created by Thomas on 10/30/2014.
 */
public class UploadImage extends AsyncTask<Void, Void, Void>{

    String filePath;
    String to;
    String from;

    public UploadImage(String filePath, String to, String from){
        if(filePath.contains("file:")){
            filePath = filePath.substring(7);
        }
        this.filePath = filePath;
        this.to = to;
        this.from = from;
    }
    @Override
    protected Void doInBackground(Void... param) {
        ParseObject imageData = new ParseObject("imageData");
        imageData.put("status","unopened");
        imageData.put("to",to);
        imageData.put("from", from);Bitmap bmp = BitmapFactory.decodeFile(filePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] imageBytes = baos.toByteArray();
        ParseFile imageFile = new ParseFile( filePath.substring(filePath.lastIndexOf("/")+1), imageBytes);
        imageFile.saveInBackground();
        imageData.put("image", imageFile);
        imageData.saveInBackground();
        return null;

    }
}
