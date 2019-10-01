package com.example.asus.custompro;

import android.net.Uri;

public class Account {

    private int userId;
    private Uri profilePic;
    private String username, password, firstname, lastname, gender, emailAdd, contactnum, address, status;

    public Account(int userId, Uri profilepic, String username, String password , String fname, String lname, String gender, String email, String contact, String address, String status) {
        this.userId = userId;
        this.profilePic = profilepic;
        this.username = username;
        this.password = password;
        this.firstname = fname;
        this.lastname = lname;
        this.gender = gender;
        this.emailAdd = email;
        this.contactnum = contact;
        this.address = address;
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Uri getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(Uri profilePic) {
        this.profilePic = profilePic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getGender(){
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmailAdd() {
        return emailAdd;
    }

    public void setEmailAdd(String emailAdd) {
        this.emailAdd = emailAdd;
    }

    public String getContactnum() {
        return contactnum;
    }

    public void setContactnum(String contactnum) {
        this.contactnum = contactnum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}