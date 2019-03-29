package com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.PersonalProfile;

public class PersonProfile {

        private String id;
        private GivenContact contact;
        private String bloodgroup;
        private String Name;
        private String gender;
        private String dateofbirth;
        private GivenAddress address;

    public PersonProfile(String id, GivenContact contact, String bloodgroup, String name, String gender, String dateofbirth, GivenAddress address) {
        this.id = id;
        this.contact = contact;
        this.bloodgroup = bloodgroup;
        Name = name;
        this.gender = gender;
        this.dateofbirth = dateofbirth;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GivenContact getContact() {
        return contact;
    }

    public void setContact(GivenContact contact) {
        this.contact = contact;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public GivenAddress getAddress() {
        return address;
    }

    public void setAddress(GivenAddress address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "PersonProfile{" +
                "id='" + id + '\'' +
                ", contact=" + contact +
                ", bloodgroup='" + bloodgroup + '\'' +
                ", Name='" + Name + '\'' +
                ", gender='" + gender + '\'' +
                ", dateofbirth='" + dateofbirth + '\'' +
                ", address=" + address +
                '}';
    }
}

