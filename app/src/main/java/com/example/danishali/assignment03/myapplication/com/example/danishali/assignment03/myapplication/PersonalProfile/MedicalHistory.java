package com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.PersonalProfile;



public class MedicalHistory {

    public String getIllness() {
        return illness;
    }

    public String getDate() {
        return date;
    }

    public String getDetails() {
        return details;
    }

    public void setIllness(String illness) {
        this.illness = illness;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MedicalHistory{" +
                "illness='" + illness + '\'' +
                ", date='" + date + '\'' +
                ", details='" + details + '\'' +
                '}';
    }

    private String id;
    private String illness;
    private String date;
    private String details;


}
