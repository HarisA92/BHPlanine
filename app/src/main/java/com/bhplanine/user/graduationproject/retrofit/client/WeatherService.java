package com.bhplanine.user.graduationproject.retrofit.client;

import com.bhplanine.user.graduationproject.retrofit.model.WeatherResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    @GET("/data/2.5/forecast")
    Observable<WeatherResult> getWeather(@Query("q") String location,
                                         @Query("appid") String appId,
                                         @Query("units") String units);
}
