package edu.utexas.ee461l.slidesnap.servlets;

/**
 * Created by Thomas on 10/30/2014.
 */
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
@Entity
public class ImageGAE {
    @Id Long id;
    String from;
    String to;
    String blobKey;
    String servingUrl;

    public ImageGAE(String from, String to, String blobKey, String servingUrl){
        this.from = from;
        this.to = to;
        this.blobKey = blobKey;
        this.servingUrl = servingUrl;
    }
}
