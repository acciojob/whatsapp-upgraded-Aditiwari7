package com.driver;

import java.util.Date;

public class Message {
    private int id;
    private String content;
    private Date timestamp;

    public Message(int Id, String Content){
        this.id = Id;
        this.content = Content;
        this.timestamp = new Date();
    }

    public Message(int Id, String Content, Date timestamp){
        this.id = Id;
        this.content = Content;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public Date getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
