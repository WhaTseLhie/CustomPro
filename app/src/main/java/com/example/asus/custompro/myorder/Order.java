package com.example.asus.custompro.myorder;

public class Order {

    int userId, requestId, makerId;
    String orderDate, orderMade, orderStatus;

    public Order(int userId, int requestId, int makerId, String orderDate, String orderMade, String orderStatus) {
        this.userId = userId;
        this.requestId = requestId;
        this.makerId = makerId;
        this.orderDate = orderDate;
        this.orderMade = orderMade;
        this.orderStatus = orderStatus;
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

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderMade() {
        return orderMade;
    }

    public void setOrderMade(String orderMade) {
        this.orderMade = orderMade;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
