package com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Models;

import java.util.Objects;

public class BookTrack {

    private String time;
    private String date;
    private String email;
    private String type;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "BookTrack{" +
                "time='" + time + '\'' +
                ", date='" + date + '\'' +
                ", email='" + email + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookTrack)) return false;
        BookTrack bookTrack = (BookTrack) o;
        return Objects.equals(getTime(), bookTrack.getTime()) &&
                Objects.equals(getDate(), bookTrack.getDate()) &&
                Objects.equals(getEmail(), bookTrack.getEmail()) &&
                Objects.equals(getType(), bookTrack.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTime(), getDate(), getEmail(), getType());
    }
}
