package com.example.asus.custompro.home.search.shops;

import android.net.Uri;

public class Maker {

    int userId;
    Uri shopPic;
    float shopRating;
    String shopName, shopSpecialty, shopContact, shopEmail, shopAddress, shopSubscription;

    public Maker(int userId, Uri shopPic, String shopName, String shopSpecialty, float shopRating, String shopContact, String shopEmail, String shopAddress, String shopSubscription) {
        this.userId = userId;
        this.shopPic = shopPic;
        this.shopName = shopName;
        this.shopSpecialty = shopSpecialty;
        this.shopRating = shopRating;
        this.shopContact = shopContact;
        this.shopEmail = shopEmail;
        this.shopAddress = shopAddress;
        this.shopSubscription = shopSubscription;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Uri getShopPic() {
        return shopPic;
    }

    public void setShopPic(Uri shopPic) {
        this.shopPic = shopPic;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public float getShopRating() {
        return shopRating;
    }

    public void setShopRating(float shopRating) {
        this.shopRating = shopRating;
    }

    public String getShopSpecialty() {
        return shopSpecialty;
    }

    public void setShopSpecialty(String shopSpecialty) {
        this.shopSpecialty = shopSpecialty;
    }

    public String getShopContact() {
        return shopContact;
    }

    public void setShopContact(String shopContact) {
        this.shopContact = shopContact;
    }

    public String getShopEmail() {
        return shopEmail;
    }

    public void setShopEmail(String shopEmail) {
        this.shopEmail = shopEmail;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopSubscription() {
        return shopSubscription;
    }

    public void setShopSubscription(String shopSubscription) {
        this.shopSubscription = shopSubscription;
    }
}