package com.bhplanine.user.graduationproject.utils;

import com.bhplanine.user.graduationproject.retrofit.model.WeatherResult;

public class RetrofitHolder {
    public WeatherResult bjelasnica;
    public WeatherResult jahorina;
    public WeatherResult ravnaplanina;
    public WeatherResult vlasic;
    public WeatherResult igman;

    public RetrofitHolder(WeatherResult bjelasnica, WeatherResult jahorina, WeatherResult ravnaplanina, WeatherResult vlasic, WeatherResult igman) {
        this.bjelasnica = bjelasnica;
        this.jahorina = jahorina;
        this.ravnaplanina = ravnaplanina;
        this.vlasic = vlasic;
        this.igman = igman;
    }
}
