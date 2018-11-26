package com.bhplanine.user.graduationproject.retrofit.model;

import java.util.ArrayList;
import java.util.List;

public class WeatherResult {

    private City city;
    private String cod;
    private String message;
    private List<WeatherDay> list = new ArrayList<>();

    public List<WeatherDay> getList() {
        return list;
    }

    public void setList(List<WeatherDay> list) {
        this.list = list;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
