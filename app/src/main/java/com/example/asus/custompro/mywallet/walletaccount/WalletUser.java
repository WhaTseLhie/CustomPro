package com.example.asus.custompro.mywallet.walletaccount;

public class WalletUser {

    int userid;
    String totbalance;

    public WalletUser(int userid, String totbalance) {
        this.userid = userid;
        this.totbalance = totbalance;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getTotbalance() {
        return totbalance;
    }

    public void setTotbalance(String totbalance) {
        this.totbalance = totbalance;
    }
}
