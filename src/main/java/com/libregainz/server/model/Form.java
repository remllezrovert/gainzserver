package com.libregainz.server.model;

public class Form {


    private int id;
  
    
    private byte[] content;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
