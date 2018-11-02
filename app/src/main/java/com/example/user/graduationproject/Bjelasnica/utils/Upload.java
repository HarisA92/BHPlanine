package com.example.user.graduationproject.Bjelasnica.utils;

public class Upload {

    private String mName;
    private String mImageUrl;
    private String mKey;
    private String mSnow;
    private String mTrail;
    private String date;
    private String email;

    public Upload() {
    }

    public Upload(String name, String imageUrl, String key, String snow, String trail, String Date, String userEmail) {
        mName = name;
        mImageUrl = imageUrl;
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

    public void setmSnow(String mSnow) {
        this.mSnow = mSnow;
    }

    public String getmTrail() {
        return mTrail;
    }

    public void setmTrail(String mTrail) {
        this.mTrail = mTrail;
    }

    public String getmKey() {
        return mKey;
    }

    public void setmKey(String mKey) {
        this.mKey = mKey;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
