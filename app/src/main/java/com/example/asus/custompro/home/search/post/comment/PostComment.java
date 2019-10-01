package com.example.asus.custompro.home.search.post.comment;

import android.net.Uri;

public class PostComment {

    int commentId, postId, userId;
    Uri commentPic;
    String commentName, comment, date;

    public PostComment(int postId, int userId, Uri commentPic, String commentName, String comment, String date) {
        this.postId = postId;
        this.userId = userId;
        this.commentPic = commentPic;
        this.commentName = commentName;
        this.comment = comment;
        this.date = date;
    }

    public PostComment(int commentId, int postId, int userId, Uri commentPic, String commentName, String comment, String date) {
        this.commentId = commentId;
        this.postId = postId;
        this.userId = userId;
        this.commentPic = commentPic;
        this.commentName = commentName;
        this.comment = comment;
        this.date = date;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Uri getCommentPic() {
        return commentPic;
    }

    public void setCommentPic(Uri commentPic) {
        this.commentPic = commentPic;
    }

    public String getCommentName() {
        return commentName;
    }

    public void setCommentName(String commentName) {
        this.commentName = commentName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}