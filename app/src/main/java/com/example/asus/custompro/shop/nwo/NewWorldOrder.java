package com.example.asus.custompro.shop.nwo;

import android.net.Uri;

public class NewWorldOrder {

    int nwoId, userId, makerId, nwoQty;
    Uri nwoPic;
    String nwoDetails, nwoCategory, nwoType, nwoSize, nwoColor, nwoDate;
    String nwoEndDate, nwoMade, nwoStatus, postestdate, postoptone, postopttwo;

    public NewWorldOrder(int nwoId, int userId, int makerId, int nwoQty, Uri nwoPic, String nwoDetails, String nwoCategory, String nwoType, String nwoSize, String nwoColor, String nwoDate, String nwoEndDate, String nwoMade, String nwoStatus, String postestdate, String postoptone, String postopttwo) {
        this.nwoId = nwoId;
        this.userId = userId;
        this.makerId = makerId;
        this.nwoQty = nwoQty;
        this.nwoPic = nwoPic;
        this.nwoDetails = nwoDetails;
        this.nwoCategory = nwoCategory;
        this.nwoType = nwoType;
        this.nwoSize = nwoSize;
        this.nwoColor = nwoColor;
        this.nwoDate = nwoDate;
        this.nwoEndDate = nwoEndDate;
        this.nwoMade = nwoMade;
        this.nwoStatus = nwoStatus;

        this.postestdate = postestdate;
        this.postoptone = postoptone;
        this.postopttwo = postopttwo;
    }

    public int getNwoId() {
        return nwoId;
    }

    public void setNwoId(int nwoId) {
        this.nwoId = nwoId;
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

    public int getNwoQty() {
        return nwoQty;
    }

    public void setNwoQty(int nwoQty) {
        this.nwoQty = nwoQty;
    }

    public Uri getNwoPic() {
        return nwoPic;
    }

    public void setNwoPic(Uri nwoPic) {
        this.nwoPic = nwoPic;
    }

    public String getNwoDetails() {
        return nwoDetails;
    }

    public void setNwoDetails(String nwoDetails) {
        this.nwoDetails = nwoDetails;
    }

    public String getNwoCategory() {
        return nwoCategory;
    }

    public void setNwoCategory(String nwoCategory) {
        this.nwoCategory = nwoCategory;
    }

    public String getNwoType() {
        return nwoType;
    }

    public void setNwoType(String nwoType) {
        this.nwoType = nwoType;
    }

    public String getNwoSize() {
        return nwoSize;
    }

    public void setNwoSize(String nwoSize) {
        this.nwoSize = nwoSize;
    }

    public String getNwoColor() {
        return nwoColor;
    }

    public void setNwoColor(String nwoColor) {
        this.nwoColor = nwoColor;
    }

    public String getNwoDate() {
        return nwoDate;
    }

    public void setNwoDate(String nwoDate) {
        this.nwoDate = nwoDate;
    }

    public String getNwoEndDate() {
        return nwoEndDate;
    }

    public void setNwoEndDate(String nwoEndDate) {
        this.nwoEndDate = nwoEndDate;
    }

    public String getNwoMade() {
        return nwoMade;
    }

    public void setNwoMade(String nwoMade) {
        this.nwoMade = nwoMade;
    }

    public String getNwoStatus() {
        return nwoStatus;
    }

    public void setNwoStatus(String nwoStatus) {
        this.nwoStatus = nwoStatus;
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