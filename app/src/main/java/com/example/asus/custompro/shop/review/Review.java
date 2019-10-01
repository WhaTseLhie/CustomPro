package com.example.asus.custompro.shop.review;

import android.net.Uri;

public class Review {

    int requestId, userId, makerId;
    Uri userPic;
    float rating;
    String userName, date, comment;

    public Review(int requestId, int userId, int makerId, Uri userPic, String userName, String date, float rating, String comment) {
        this.requestId = requestId;
        this.userId = userId;
        this.makerId = makerId;
        this.userPic = userPic;
        this.userName = userName;
        this.date = date;
        this.rating = rating;
        this.comment = comment;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMakerId() {
        return makerId;
    }

    public void setMakerId(int makerId) {
        this.makerId = makerId;
    }

    public Uri getUserPic() {
        return userPic;
    }

    public void setUserPic(Uri userPic) {
        this.userPic = userPic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}