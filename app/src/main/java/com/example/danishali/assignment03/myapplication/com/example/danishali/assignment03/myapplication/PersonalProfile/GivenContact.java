package com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.PersonalProfile;
import android.provider.ContactsContract;

public class GivenContact {

    private String home;
    private String mobile;
    private String email;

    public GivenContact(String home, String mobile, String email) {
        this.home = home;
        this.mobile = mobile;
        this.email = email;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "home='" + home + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email=" + email +
                '}';
    }
}
