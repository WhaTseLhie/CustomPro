package com.example.asus.custompro.shop.product;

import android.net.Uri;

public class Product {

    int userId;
    int productId;
    Uri productPic;
    String productName, productDetails;

    public Product(int userId, int productId, Uri productPic, String productName, String productDetails) {
        this.userId = userId;
        this.productId = productId;
        this.productPic = productPic;
        this.productName = productName;
        this.productDetails = productDetails;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Uri getProductPic() {
        return productPic;
    }

    public void setProductPic(Uri productPic) {
        this.productPic = productPic;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(String productDetails) {
        this.productDetails = productDetails;
    }
}