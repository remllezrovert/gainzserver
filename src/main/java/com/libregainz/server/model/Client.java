package com.libregainz.server.model;

public class Client {

    private Integer id;
    private String title;

    public Client(){}
    public Client(int id ){
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }



}
