package com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.PersonalProfile;

public class GivenAddress {


        private String street;
        private String House;
        private String county;
        private String eircode;

        public GivenAddress(String street, String city, String county, String eircode) {
            this.street = street;
            this.House = city;
            this.county = county;
            this.eircode = eircode;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getHouse() {
            return House;
        }

        public void setHouse(String house) {
            this.House = house;
        }

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public String getEircode() {
            return eircode;
        }

        public void setEircode(String eircode) {
            this.eircode = eircode;
        }


}
