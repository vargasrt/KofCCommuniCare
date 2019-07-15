package com.example.vargasrt.kofccommunicare.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Profile extends RealmObject {

    @PrimaryKey
    private long id;
    private String lastname;
    private String firstname;
    private String middlename;
    private String imageUrl;
    private String fccode;
    private String pincode;
    private boolean registered;

    // Standard getters & setters generated by your IDE…
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }
    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFccode(){ return  fccode; }
    public void setFccode(String fccode) { this.fccode = fccode; }

    public String getPincode(){ return  pincode; }
    public void setPincode(String pincode) { this.pincode = pincode; }

    public boolean getRegistered() {
        return registered;
    }
    public void setRegistered(boolean registered) {
        this.registered = registered;
    }
}

