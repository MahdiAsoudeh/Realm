package com.mahdi20.realm.model;

// mahdi20.com
public class PhoneBook {

    private String id;
    private String name;
    private String tell;

    public PhoneBook(String name, String tell) {
        this.name = name;
        this.tell = tell;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setTell(String tell) {
        this.tell = tell;
    }

    public String getTell() {
        return tell;
    }
}