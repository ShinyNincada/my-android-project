package com.example.ontapdemo;

import java.io.Serializable;

public class Contact_Minh implements Serializable {
    int id;
    String fullName;
    String phoneNumber;
    int imgID;

    public Contact_Minh() {
    }

    public Contact_Minh(int id, String fullName, String phoneNumber) {
        this.id = id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }

    public Contact_Minh(String fullName, String phoneNumber, int imgID) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.imgID = imgID;
    }

    public Contact_Minh(int id, String fullName, String phoneNumber, int imgID) {
        this.id = id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.imgID = imgID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getImgID() {
        return imgID;
    }

    public void setImgID(int imgID) {
        this.imgID = imgID;
    }

    @Override
    public String toString() {
        return "Contact_Minh{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", imgID=" + imgID +
                '}';
    }

}
