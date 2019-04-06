package com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Models;

import java.io.Serializable;

public class PersonalProfile implements Serializable {
    @Override
    public String toString() {
        return
                "Name : " + Name +
                "\nGender : " + gender +
                "\nDateofbirth : " + dateofbirth +
                "\nAddress : " + address +
                "\nId : " + id +
                "\nEircode : " + eircode +
                "\nMobile : " + mobile +
                "\nEmail : " + email +
                "\nHome : " + home +
                "\nBloodgroup : " + bloodgroup;



    }

    public void setName(String name) {
        Name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEircode(String eircode) {
        this.eircode = eircode;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public String getName() {
        return Name;
    }

    public String getGender() {
        return gender;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public String getAddress() {
        return address;
    }

    public String getEircode() {
        return eircode;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getHome() {
        return home;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setTokengiven(String tokengiven) {
        this.tokengiven = tokengiven;
    }

    public String getTokengiven() {
        return tokengiven;
    }

    private String id;
    private String eircode;
    private String mobile;
    private String email;
    private String home;
    private String bloodgroup;
    private String Name;
    private String gender;
    private String dateofbirth;
    private String address;
    private String tokengiven;

}
