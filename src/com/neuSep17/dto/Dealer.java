package com.neuSep17.dto;

public class Dealer {
    private String id;
    private String name;
    private String url;
    private String password;

    public Dealer(String id, String name, String url){
    		this.id= id;
    		this.name=name;
    		this.url=url;
    }

    public Dealer(String id, String name, String url, String password){
        this.id= id;
        this.name=name;
        this.url=url;
        this.password=password;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}