package edu.utexas.ee461l.slidesnap;

import java.net.URI;

/**
 * Created by Leo on 12/8/2014.
 */
public class PuzzleEntry {
    private String status; // status of the puzzle. UnopenedSend, UnopenedReceive, ReceiveWrong, ReceiveRight, SendWrong, SendRight
    private String path;
    private String user;
    private String objectId;

    public PuzzleEntry(String opened, String path, String user, String objectId){
        this.status = opened;
        this.path = path;
        this.user = user;
        this.objectId = objectId;
    }

    public String getStatus(){
        return status;
    }

    public String getPath(){
        return path;
    }

    public String getUser(){
        return user;
    }

    public String getObjectId(){
        return objectId;
    }
}
