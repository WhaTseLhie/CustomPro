package com.example.asus.custompro.myrequest;

import android.net.Uri;

public class Request {

    int userId, requestId, makerId, requestedQty;
    Uri userPic, requestPic;
    String userName, requestDetails, requestCategory, requestType, requestSize, requestColor, requestedDate, requestStatus;

    public Request(int userId, int requestId, int makerId, Uri userPic, Uri requestPic, String userName, String requestDetails, String requestCategory, String requestType, String requestSize, int requestedQty, String requestColor, String requestedDate, String requestStatus) {
        this.userId = userId;
        this.requestId = requestId;
        this.makerId = makerId;
        this.userPic = userPic;
        this.requestPic = requestPic;
        this.userName = userName;
        this.requestDetails = requestDetails;
        this.requestCategory = requestCategory;
        this.requestType = requestType;
        this.requestSize = requestSize;
        this.requestedQty = requestedQty;
        this.requestColor = requestColor;
        this.requestedDate = requestedDate;
        this.requestStatus = requestStatus;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
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

    public Uri getRequestPic() {
        return requestPic;
    }

    public void setRequestPic(Uri requestPic) {
        this.requestPic = requestPic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRequestDetails() {
        return requestDetails;
    }

    public void setRequestDetails(String requestDetails) {
        this.requestDetails = requestDetails;
    }

    public String getRequestCategory() {
        return requestCategory;
    }

    public void setRequestCategory(String requestCategory) {
        this.requestCategory = requestCategory;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType= requestType;
    }

    public String getRequestSize() {
        return requestSize;
    }

    public void setRequestSize(String requestSize) {
        this.requestSize= requestSize;
    }

    public int getRequestedQty() {
        return requestedQty;
    }

    public void setRequestedQty(int requestedQty) {
        this.requestedQty = requestedQty;
    }

    public String getRequestColor() {
        return requestColor;
    }

    public void setRequestColor(String requestColor) {
        this.requestColor = requestColor;
    }

    public String getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(String requestedDate) {
        this.requestedDate = requestedDate;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }
}
