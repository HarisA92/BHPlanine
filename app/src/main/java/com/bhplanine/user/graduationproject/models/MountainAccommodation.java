package com.bhplanine.user.graduationproject.models;

public enum MountainAccommodation {

    BJELASNICA("Bjelasnica Accommodation"),
    JAHORINA("Jahorina Accommodation"),
    RAVNAPLANINA("Ravna Planina Accommodation"),
    VLASIC("Vlasic Accommodation"),
    IGMAN("Igman Accommodation");

    private String value;

    MountainAccommodation(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
