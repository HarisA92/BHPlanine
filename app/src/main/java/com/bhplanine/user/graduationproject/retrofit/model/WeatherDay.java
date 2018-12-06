package com.bhplanine.user.graduationproject.retrofit.model;

import java.util.ArrayList;

public class WeatherDay {

    private long dt;
    private Main main;
    private java.util.List<Weather> weather = new ArrayList<>();

    public long getDt() {
        return dt;
    }

    public Main getMain() {
        return main;
    }

    public java.util.List<Weather> getWeather() {
        return weather;
    }

}
