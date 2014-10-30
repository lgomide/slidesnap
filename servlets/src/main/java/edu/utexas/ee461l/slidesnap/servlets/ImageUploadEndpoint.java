package edu.utexas.ee461l.slidesnap.servlets;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.logging.Logger;

import javax.inject.Named;

/** An endpoint class we are exposing */
@Api(name = "imageUploadEndpoint", version = "v1", namespace = @ApiNamespace(ownerDomain = "servlets.slidesnap.ee461l.utexas.edu", ownerName = "servlets.slidesnap.ee461l.utexas.edu", packagePath=""))
public class ImageUploadEndpoint {

    // Make sure to add this endpoint to your web.xml file if this is a web application.

    private static final Logger LOG = Logger.getLogger(ImageUploadEndpoint.class.getName());

    /**
     * This method gets the <code>ImageUpload</code> object associated with the specified <code>id</code>.
     * @param id The id of the object to be returned.
     * @return The <code>ImageUpload</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "getImageUpload")
    public ImageUpload getImageUpload(@Named("id") Long id) {
        // Implement this function

        LOG.info("Calling getImageUpload method");
        return null;
    }

    /**
     * This inserts a new <code>ImageUpload</code> object.
     * @param imageUpload The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertImageUpload")
    public ImageUpload insertImageUpload(ImageUpload imageUpload) {
        // Implement this function

        LOG.info("Calling insertImageUpload method");
        return imageUpload;
    }
}