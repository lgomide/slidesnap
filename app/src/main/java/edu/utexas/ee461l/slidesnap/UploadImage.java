package edu.utexas.ee461l.slidesnap;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.mime.MultipartEntity;
//import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Thomas on 10/30/2014.
 */
public class UploadImage extends AsyncTask<Void, Void, Void>{

    String filePath;
    String to;
    String from;

    public UploadImage(String filePath, String to, String from){
        this.filePath = filePath;
        this.to = to;
        this.from = from;
    }
    @Override
    protected Void doInBackground(Void... param) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet("http://www.slidesnapserver.appspot.com/ImageUpload");
        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity urlEntity = response.getEntity();
            InputStream in = urlEntity.getContent();
            String url = "";
            while(true){
                int ch = in.read();
                if(ch == -1)
                    break;
                url += (char) ch;
            }
            HttpPost httppost = new HttpPost(url);
            File f = new File(filePath);
            //FileBody filebody = new FileBody(f);
            //MultipartEntity reqEntity = new MultipartEntity();
            //reqEntity.addPart("file", filebody);
            //httppost.setEntity(reqEntity);
            response = httpClient.execute(httppost);
            urlEntity = response.getEntity();
            in = urlEntity.getContent();
            String str = "";
            while(true){
                int ch = in.read();
                if(ch==-1)
                    break;
                str += (char) ch;
            }
            JSONObject resultJson = new JSONObject(str);
            String blobKey = resultJson.getString("blobKey");
            String servingUrl = resultJson.getString("servingUrl");
            HttpGet httpget = new HttpGet("http://www.slidesnapserver.appspot.com/uploaded?to="+to+"&from="+from+"&blobKey="+blobKey+"&servingUrl="+servingUrl);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }
}
