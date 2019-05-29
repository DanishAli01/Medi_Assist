package com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Models;


public class Vital {

    private  String vitalbloodpressure;
    private String vitalweightkgsbmi;
    private String vitalheartbeat;
    private String vitalcholesterol;
    private String vitalbodytemperature;
    private String vitalheartrate;
    private String vitalrespiratoryrate;
    private String time;


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String map;

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getVitalbloodpressure() {
        return vitalbloodpressure;
    }

    public String getVitalweightkgsbmi() {
        return vitalweightkgsbmi;
    }

    public String getVitalheartbeat() {
        return vitalheartbeat;
    }

    public String getVitalcholesterol() {
        return vitalcholesterol;
    }

    public String getVitalbodytemperature() {
        return vitalbodytemperature;
    }

    public String getVitalheartrate() {
        return vitalheartrate;
    }

    public String getVitalrespiratoryrate() {
        return vitalrespiratoryrate;
    }

    public void setVitalbloodpressure(String vitalbloodpressure) {
        this.vitalbloodpressure = vitalbloodpressure;
    }

    public void setVitalweightkgsbmi(String vitalweightkgsbmi) {
        this.vitalweightkgsbmi = vitalweightkgsbmi;
    }

    public void setVitalheartbeat(String vitalheartbeat) {
        this.vitalheartbeat = vitalheartbeat;
    }

    public void setVitalcholesterol(String vitalcholesterol) {
        this.vitalcholesterol = vitalcholesterol;
    }

    public void setVitalbodytemperature(String vitalbodytemperature) {
        this.vitalbodytemperature = vitalbodytemperature;
    }

    public void setVitalheartrate(String vitalheartrate) {
        this.vitalheartrate = vitalheartrate;
    }

    public void setVitalrespiratoryrate(String vitalrespiratoryrate) {
        this.vitalrespiratoryrate = vitalrespiratoryrate;
    }

    @Override
    public String toString() {
        return "Vital{" +
                "vitalbloodpressure='" + vitalbloodpressure + '\'' +
                ", vitalweightkgsbmi='" + vitalweightkgsbmi + '\'' +
                ", vitalheartbeat='" + vitalheartbeat + '\'' +
                ", vitalcholesterol='" + vitalcholesterol + '\'' +
                ", vitalbodytemperature='" + vitalbodytemperature + '\'' +
                ", vitalheartrate='" + vitalheartrate + '\'' +
                ", vitalrespiratoryrate='" + vitalrespiratoryrate + '\'' +
                ", time='" + time + '\'' +
                ", map='" + map + '\'' +
                '}';
    }
}

