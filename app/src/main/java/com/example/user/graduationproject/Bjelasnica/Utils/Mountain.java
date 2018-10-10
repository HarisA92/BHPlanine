package com.example.user.graduationproject.Bjelasnica.Utils;

public enum Mountain {
    BJELASNICA("Bjelasnica"),
    JAHORINA("Jahorina");

    private String value;

    Mountain(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
