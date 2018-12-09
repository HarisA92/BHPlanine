package com.bhplanine.user.graduationproject.models;

public enum Mountain {

    BJELASNICA("Bjelašnica"),
    JAHORINA("Jahorina"),
    RAVNAPLANINA("Ravna Planina"),
    VLASIC("Vlašić"),
    IGMAN("Igman");

    private String value;

    Mountain(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
