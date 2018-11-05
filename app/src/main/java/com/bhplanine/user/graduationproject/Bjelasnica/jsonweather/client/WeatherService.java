package com.bhplanine.user.graduationproject.Bjelasnica.jsonweather.client;

import com.bhplanine.user.graduationproject.Bjelasnica.jsonweather.model.WeatherResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    @GET("/data/2.5/forecast")
    Call<WeatherResult> getWeather(@Query("q") String location,
                                   @Query("appid") String appId,
                                   @Query("units") String units);
}
