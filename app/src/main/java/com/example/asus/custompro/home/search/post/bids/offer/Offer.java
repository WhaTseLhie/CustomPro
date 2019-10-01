package com.example.asus.custompro.home.search.post.bids.offer;

public class Offer {

    int nwoid, userId, postId, shopId;
    String shopPic, shopName, offerPrice, offerDate, offerComment, offerStatus;

    public Offer(int nwoid, int userId, int postId, int shopId, String shopPic, String shopName, String offerPrice, String offerDate, String offerComment, String offerStatus) {
        this.nwoid = nwoid;
        this.userId = userId;
        this.postId = postId;
        this.shopId = shopId;
        this.shopPic = shopPic;
        this.shopName = shopName;
        this.offerPrice = offerPrice;
        this.offerDate = offerDate;
        this.offerComment = offerComment;
        this.offerStatus = offerStatus;

    }

    public int getNwoid() {
        return nwoid;
    }

    public void setNwoid(int nwoid) {
        this.nwoid = nwoid;
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

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getShopPic() {
        return shopPic;
    }

    public void setShopPic(String shopPic) {
        this.shopPic = shopPic;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(String offerPrice) {
        this.offerPrice = offerPrice;
    }

    public String getOfferDate() {
        return offerDate;
    }

    public void setOfferDate(String offerDate) {
        this.offerDate = offerDate;
    }

    public String getOfferComment() {
        return offerComment;
    }

    public void setOfferComment(String offerComment) {
        this.offerComment = offerComment;
    }

    public String getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(String offerStatus) {
        this.offerStatus = offerStatus;
    }
}