package com.bhplanine.user.graduationproject.Bjelasnica.jsonweather.client;

import android.content.Context;

import com.bhplanine.user.graduationproject.Bjelasnica.jsonweather.model.WeatherResult;
import com.bhplanine.user.graduationproject.BuildConfig;
import com.bhplanine.user.graduationproject.R;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherClient {

    private final WeatherService weatherService =
            new Retrofit.Builder()
                    .baseUrl("http://api.openweathermap.org")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(WeatherService.class);
    private Context context;

    public WeatherClient(Context context) {
        this.context = context;
    }

    public Call<WeatherResult> getWeather(final String location) {
        return weatherService.getWeather(location, BuildConfig.ApiKey_Weather, context.getResources().getString(R.string.METRIC_UNITS));
    }
}
