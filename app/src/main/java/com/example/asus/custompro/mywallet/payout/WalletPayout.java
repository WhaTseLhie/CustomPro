package com.example.asus.custompro.mywallet.payout;

public class WalletPayout {

    int userid, makerid;
    String booking_id, payout_date, payout_fee;

    public WalletPayout(int userid, int makerid, String booking_id, String payout_date, String payout_fee) {
        this.userid = userid;
        this.makerid = makerid;
        this.booking_id = booking_id;
        this.payout_date = payout_date;
        this.payout_fee = payout_fee;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getMakerid() {
        return makerid;
    }

    public void setMakerid(int makerid) {
        this.makerid = makerid;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getPayout_date() {
        return payout_date;
    }

    public void setPayout_date(String payout_date) {
        this.payout_date = payout_date;
    }

    public String getPayout_fee() {
        return payout_fee;
    }

    public void setPayout_fee(String payout_fee) {
        this.payout_fee = payout_fee;
    }
}