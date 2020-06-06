package com.example.restaurantrent;


public class Message {

    private long id;
    private long idRent;
    private boolean isOwner;
    private String textMessage;

    public Message() {
    }

    public Message(long idRent, boolean isOwner, String textMessage) {
        this.idRent = idRent;
        this.isOwner = isOwner;
        this.textMessage = textMessage;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdRent() {
        return idRent;
    }

    public void setIdRent(long idRent) {
        this.idRent = idRent;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean isOwner) {
        this.isOwner = isOwner;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }
}
