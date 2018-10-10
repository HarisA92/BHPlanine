package com.example.user.graduationproject.Bjelasnica.Utils;

public class SkiResort {

    private final Mountain mountain;
    private final String city;

    public Mountain getMountain() {
        return mountain;
    }

    public String getCity() {
        return city;
    }

    public SkiResort(Mountain mountain, String city) {
        this.mountain = mountain;
        this.city = city;
    }
}
