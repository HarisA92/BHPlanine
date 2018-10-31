package com.example.user.graduationproject.Bjelasnica.JSONWeather.Client;

import com.example.user.graduationproject.Bjelasnica.JSONWeather.Model.WeatherResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    @GET("/data/2.5/forecast")
    Call<WeatherResult> getWeather(@Query("q") String location,
                                   @Query("appid") String appId,
                                   @Query("lang") String lang,
                                   @Query("units") String units);
}
