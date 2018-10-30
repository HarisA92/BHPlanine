package com.example.user.graduationproject.Bjelasnica.Utils;

public enum Mountain {

    BJELASNICA("Bjelasnica"),
    JAHORINA("Jahorina"),
    RAVNAPLANINA("Ravna Planina"),
    VLASIC("Vlasic"),
    IGMAN("Igman");

    private String value;

    Mountain(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
