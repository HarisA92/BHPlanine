package com.example.user.graduationproject.Bjelasnica.utils;

public class SkiResortHolder {

    private static SkiResort skiResort;

    public static SkiResort getSkiResort() {
        return skiResort;
    }

    public static void setSkiResort(final SkiResort skiResort) {
        SkiResortHolder.skiResort = skiResort;
    }

}
