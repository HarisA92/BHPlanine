package com.example.user.graduationproject.Bjelasnica.Fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.user.graduationproject.Bjelasnica.Utils.InternetConnection;
import com.example.user.graduationproject.Bjelasnica.Utils.SkiResortHolder;
import com.example.user.graduationproject.Bjelasnica.JSONWeather.Model.WeatherDay;
import com.example.user.graduationproject.Bjelasnica.JSONWeather.Model.WeatherResult;
import com.example.user.graduationproject.Bjelasnica.JSONWeather.RecyclerWeather;
import com.example.user.graduationproject.Bjelasnica.JSONWeather.client.WeatherClient;
import com.example.user.graduationproject.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Weather extends Fragment {
    private final static String PATH_TO_WEATHER_FONT = "fonts/weather.ttf";
    private final static String LOCATION_AND_COUNTRY_CODE = "%s,BA";
    private final static WeatherClient weatherClient = new WeatherClient();
    private RecyclerView recyclerView;
    private RecyclerWeather recyclerWeather;
    InternetConnection connection = new InternetConnection();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_weather3, container, false);
        recyclerView = v.findViewById(R.id.weather_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        final Typeface weatherFont = Typeface.createFromAsset(getActivity().getAssets(), PATH_TO_WEATHER_FONT);

        if(connection.getInternetConnection() == true){
            weatherClient.getWeather(getLocation())
                    .enqueue(new Callback<WeatherResult>() {
                        @Override
                        public void onResponse(final Call<WeatherResult> call, final Response<WeatherResult> response) {
                            List<WeatherDay> days = response.body().getList();
                            recyclerWeather = new RecyclerWeather(days, getContext(), weatherFont);
                            recyclerView.setAdapter(recyclerWeather);
                            recyclerWeather.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(final Call<WeatherResult> call, final Throwable t) {
                        }
                    });
        }
        else{
            Toast.makeText(getActivity(), "Please connect to the internet", Toast.LENGTH_SHORT).show();
        }

        return v;
    }

    private static String getLocation() {
        return String.format(LOCATION_AND_COUNTRY_CODE, SkiResortHolder.getSkiResort().getCity());
    }
}