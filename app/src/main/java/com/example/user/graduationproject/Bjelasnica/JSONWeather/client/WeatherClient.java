package com.example.user.graduationproject.Bjelasnica.JSONWeather.client;

import com.example.user.graduationproject.Bjelasnica.JSONWeather.Model.WeatherResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;

public class WeatherClient {
    private static final String BASE_URL = "http://api.openweathermap.org";
    private static final String APP_ID = "f286ae1cbd2243b2ef4d50959abb4b57";
    private static final String METRIC_UNITS = "Metric";

    private final WeatherService weatherService =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(WeatherService.class);

    public Call<WeatherResult> getWeather(final String location) {
        return weatherService.getWeather(location, APP_ID, METRIC_UNITS);
    }
}
