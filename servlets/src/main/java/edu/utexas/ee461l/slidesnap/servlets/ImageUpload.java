package edu.utexas.ee461l.slidesnap.servlets; /**
 * Created by Thomas on 10/30/2014.
 */

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImageUpload extends HttpServlet{
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        String blobUploadURL = blobstoreService.createUploadURL("/uploaded");

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("text/plain");

        PrintWriter out = resp.getWriter();
        out.print(blobUploadURL);
    }
}
