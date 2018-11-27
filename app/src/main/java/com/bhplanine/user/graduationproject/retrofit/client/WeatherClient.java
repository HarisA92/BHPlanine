package com.bhplanine.user.graduationproject.retrofit.client;

import android.content.Context;

import com.bhplanine.user.graduationproject.BuildConfig;
import com.bhplanine.user.graduationproject.R;
import com.bhplanine.user.graduationproject.retrofit.model.WeatherResult;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherClient {

    private Context context;

    public WeatherClient(Context context) {
        this.context = context;
    }

    private static Retrofit retrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public WeatherService getWeatherService() {
        return retrofit().create(WeatherService.class);
    }



}
