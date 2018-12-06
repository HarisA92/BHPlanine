package com.bhplanine.user.graduationproject.models;

public class Upload {

    private String mName;
    private String imageUrl;
    private String mKey;
    private String mSnow;
    private String mTrail;
    private String date;
    private String email;

    public Upload() {
    }

    public Upload(String name, String mImageUrl, String key, String snow, String trail, String Date, String userEmail) {
        mName = name;
        imageUrl = mImageUrl;
        mKey = key;
        mSnow = snow;
        mTrail = trail;
        date = Date;
        email = userEmail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getmSnow() {
        return mSnow;
    }

    public String getmTrail() {
        return mTrail;
    }

    public String getmKey() {
        return mKey;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
