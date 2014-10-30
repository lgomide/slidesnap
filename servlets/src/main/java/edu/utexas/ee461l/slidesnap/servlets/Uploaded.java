package edu.utexas.ee461l.slidesnap.servlets;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.api.images.ImagesService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Thomas on 10/30/2014.
 */
public class Uploaded extends HttpServlet {
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        try{
            List<BlobKey> blobs = BlobstoreService.getUploads(req).get("file");
            BlobKey blobKey = blobs.get(0);

            ImagesService imageService = ImagesServiceFactory.getImagesService();
            ServingUrlOptions servingOptions = ServingUrlOptions.Builder.withBlobKey(blobKey);

            String servingUrl = imageService.getServingUrl(servingOptions);

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("application/json");

            JSONObject json = new JSONObject();
            json.put("servingUrl", servingUrl);
            json.put("blobKey", blobKey.getKeySting());

            PrintWriter out = resp.getWriter();
            out.print(json.toString());
            out.flush();
            out.close();
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
