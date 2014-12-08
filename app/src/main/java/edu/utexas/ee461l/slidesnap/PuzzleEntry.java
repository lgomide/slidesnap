package edu.utexas.ee461l.slidesnap;

import java.net.URI;

/**
 * Created by Leo on 12/8/2014.
 */
public class PuzzleEntry {
    private String opened; // status of the puzzle. UnopenedSend, UnopenedReceive, ReceiveWrong, ReceiveRight, SentWrong, SentRight
    private URI path;
    private String user;

    public PuzzleEntry(String opened, URI path, String user){
        this.opened = opened;
        this.path = path;
        this.user = user;
    }

    public String getOpened(){
        return opened;
    }

    public URI getPath(){
        return path;
    }

    public String getUser(){
        return user;
    }
}
