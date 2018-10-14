package com.example.user.graduationproject.Bjelasnica.Utils;

public enum LiveStream {
    BJELASNICA_WEB_CAMS("Bjelasnica_livestream"),
    JAHORINA_WEB_CAMS("Jahorina_livestream");

    private String value;

    LiveStream(String value){
        this.value = value;
    }
    public String getValue() {
        return value;
    }

}
