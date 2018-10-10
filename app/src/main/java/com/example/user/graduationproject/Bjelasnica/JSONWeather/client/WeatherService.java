package com.example.user.graduationproject.Bjelasnica.JSONWeather.client;

import com.example.user.graduationproject.Bjelasnica.JSONWeather.Model.WeatherResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    @GET("/data/2.5/forecast")
    Call<WeatherResult> getWeather(@Query("q") String location,
                                   @Query("appid") String appId,
                                   @Query("units") String units);
}
