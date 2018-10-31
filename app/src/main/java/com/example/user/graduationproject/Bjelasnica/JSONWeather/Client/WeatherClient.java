package com.example.user.graduationproject.Bjelasnica.JSONWeather.Client;

import android.content.Context;

import com.example.user.graduationproject.Bjelasnica.JSONWeather.Model.WeatherResult;
import com.example.user.graduationproject.BuildConfig;
import com.example.user.graduationproject.R;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherClient {

    private Context context;

    private final WeatherService weatherService =
            new Retrofit.Builder()
                    .baseUrl("http://api.openweathermap.org")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(WeatherService.class);

    public WeatherClient(Context context) {
        this.context = context;
    }

    public Call<WeatherResult> getWeather(final String location) {
        return weatherService.getWeather(location, BuildConfig.ApiKey_Weather, context.getResources().getString(R.string.METRIC_UNITS));
    }
}
