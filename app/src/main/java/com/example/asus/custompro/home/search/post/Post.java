package com.example.asus.custompro.home.search.post;

import android.net.Uri;

public class Post {

    int userId, postId, postQty;
    Uri userPic, postPic;
    String userName, postDetails, postCategory, postType, postSize, postColor, postDate, postestdate, postoptone, postopttwo;

    public Post(int userId, int postId, Uri userPic, String userName, Uri postPic, String postDetails, String postCategory, String postType, String postSize, int postQty, String postColor, String postDate, String postestdate, String postoptone, String postopttwo) {
        this.userId = userId;
        this.postId = postId;
        this.userPic = userPic;
        this.userName = userName;
        this.postPic = postPic;
        this.postDetails = postDetails;
        this.postCategory = postCategory;
        this.postType = postType;
        this.postSize = postSize;
        this.postQty = postQty;
        this.postColor = postColor;
        this.postDate = postDate;
        this.postestdate = postestdate;
        this.postoptone = postoptone;
        this.postopttwo = postopttwo;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
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

    public Uri getPostPic() {
        return postPic;
    }

    public void setPostPic(Uri postPic) {
        this.postPic = postPic;
    }

    public String getPostDetails() {
        return postDetails;
    }

    public void setPostDetails(String postDetails) {
        this.postDetails = postDetails;
    }

    public String getPostCategory() {
        return postCategory;
    }

    public void setPostCategory(String postCategory) {
        this.postCategory = postCategory;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getPostSize() {
        return postSize;
    }

    public void setPostSize(String postSize) {
        this.postSize = postSize;
    }

    public int getPostQty() {
        return postQty;
    }

    public void setPostQty(int postQty) {
        this.postQty = postQty;
    }

    public String getPostColor() {
        return postColor;
    }

    public void setPostColor(String postColor) {
        this.postColor = postColor;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getPostestdate() {
        return postestdate;
    }

    public void setPostestdate(String postestdate) {
        this.postestdate = postestdate;
    }

    public String getPostoptone() {
        return postoptone;
    }

    public void setPostoptone(String postoptone) {
        this.postoptone = postoptone;
    }

    public String getPostopttwo() {
        return postopttwo;
    }

    public void setPostopttwo(String postopttwo) {
        this.postopttwo = postopttwo;
    }
}
