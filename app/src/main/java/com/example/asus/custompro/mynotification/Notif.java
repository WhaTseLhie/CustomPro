package com.example.asus.custompro.mynotification;

public class Notif {

    int userId, shopid, requestid;
    String type, date, message;

    public Notif(int userId, int shopid, int requestid, String date, String type, String message) {
        this.userId = userId;
        this.shopid = shopid;
        this.requestid = requestid;
        this.date = date;
        this.type = type;
        this.message = message;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getShopid() {
        return shopid;
    }

    public void setShopid(int shopid) {
        this.shopid = shopid;
    }

    public int getRequestid() {
        return requestid;
    }

    public void setRequestid(int requestid) {
        this.requestid = requestid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
