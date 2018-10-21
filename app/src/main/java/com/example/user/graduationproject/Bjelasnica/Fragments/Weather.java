package com.example.user.graduationproject.Bjelasnica.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.user.graduationproject.Bjelasnica.Adapters.DatabaseAdapter;
import com.example.user.graduationproject.Bjelasnica.Adapters.ImageReportAdapter;
import com.example.user.graduationproject.Bjelasnica.Database.Converters;
import com.example.user.graduationproject.Bjelasnica.Database.UserViewModel;
import com.example.user.graduationproject.Bjelasnica.Database.WeatherTable;
import com.example.user.graduationproject.Bjelasnica.Utils.InternetConnection;
import com.example.user.graduationproject.Bjelasnica.Utils.SkiResortHolder;
import com.example.user.graduationproject.Bjelasnica.JSONWeather.Model.WeatherDay;
import com.example.user.graduationproject.Bjelasnica.JSONWeather.Model.WeatherResult;
import com.example.user.graduationproject.Bjelasnica.JSONWeather.RecyclerWeather;
import com.example.user.graduationproject.Bjelasnica.JSONWeather.Client.WeatherClient;
import com.example.user.graduationproject.Bjelasnica.Utils.Upload;
import com.example.user.graduationproject.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Weather extends Fragment {
    private final static String PATH_TO_WEATHER_FONT = "fonts/weather.ttf";
    private final static String LOCATION_AND_COUNTRY_CODE = "%s,BA";
    private final static WeatherClient weatherClient = new WeatherClient();
    private RecyclerWeather recyclerWeather;
    private InternetConnection connection = new InternetConnection();
    private UserViewModel userViewModel;
    private List<WeatherDay> days;
    private List<WeatherDay> day;
    private Typeface weatherFont;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_weather3, container, false);
        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), PATH_TO_WEATHER_FONT);

        if(connection.getInternetConnection() == true){
            weatherClient.getWeather(getLocation())
                    .enqueue(new Callback<WeatherResult>() {
                        @Override
                        public void onResponse(final Call<WeatherResult> call, final Response<WeatherResult> response) {
                            days = response.body().getList();
                            day = new ArrayList<>();
                            WeatherDay prviDan = days.get(0);
                            WeatherDay drugiDan = days.get(7);
                            WeatherDay treciDan = days.get(15);
                            WeatherDay cetvrtiDan = days.get(23);
                            WeatherDay petiDan = days.get(38);
                            day.add(prviDan);
                            day.add(drugiDan);
                            day.add(treciDan);
                            day.add(cetvrtiDan);
                            day.add(petiDan);
                            try{
                                buildRecyclerView(v);
                                saveUserReportPreferences(day);
                            }catch(Exception e){ }
                        }
                        @Override
                        public void onFailure(final Call<WeatherResult> call, final Throwable t) {}
                    });
        }
        else{
            Toast.makeText(getActivity(), "Please connect to the internet", Toast.LENGTH_SHORT).show();
            try{
                loadUserReportPreferences();
                buildRecyclerView(v);
            }catch(Exception e){ }

        }

        return v;
    }

    private void buildRecyclerView(View v){
        recyclerWeather = new RecyclerWeather(day, getContext(), weatherFont);
        mRecyclerView = v.findViewById(R.id.weather_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(recyclerWeather);

    }

    private void saveUserReportPreferences(List<WeatherDay> days){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(days);
        editor.putString("task", json);
        editor.apply();
    }

    private void loadUserReportPreferences(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task", null);
        Type type = new TypeToken<ArrayList<WeatherDay>>(){}.getType();
        day = gson.fromJson(json, type);
    }

    private static String getLocation() {
        return String.format(LOCATION_AND_COUNTRY_CODE, SkiResortHolder.getSkiResort().getCity());
    }

}