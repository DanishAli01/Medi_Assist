package com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Models;



public class Medication {
    private String date;
    private String name;
    private String prescribed_by;
    private String treatment_for;
    private String map;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrescribed_by() {
        return prescribed_by;
    }

    public void setPrescribed_by(String prescribed_by) {
        this.prescribed_by = prescribed_by;
    }

    public String getTreatment_for() {
        return treatment_for;
    }

    public void setTreatment_for(String treatment_for) {
        this.treatment_for = treatment_for;
    }
}
