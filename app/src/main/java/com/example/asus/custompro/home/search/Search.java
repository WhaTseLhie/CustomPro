package com.example.asus.custompro.home.search;

public class Search {

    int userid;
    String type;

    public Search(int userid, String type) {
        this.userid = userid;
        this.type = type;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}