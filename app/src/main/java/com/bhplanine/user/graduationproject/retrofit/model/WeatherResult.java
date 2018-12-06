package com.bhplanine.user.graduationproject.retrofit.model;

import java.util.ArrayList;
import java.util.List;

public class WeatherResult {

    private String message;
    private List<WeatherDay> list = new ArrayList<>();

    public List<WeatherDay> getList() {
        return list;
    }

    public void setList(List<WeatherDay> list) {
        this.list = list;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
