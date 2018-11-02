package com.example.user.graduationproject.Bjelasnica.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.user.graduationproject.Bjelasnica.JSONWeather.Client.WeatherClient;
import com.example.user.graduationproject.Bjelasnica.JSONWeather.Model.WeatherDay;
import com.example.user.graduationproject.Bjelasnica.JSONWeather.Model.WeatherResult;
import com.example.user.graduationproject.Bjelasnica.JSONWeather.RecyclerWeather;
import com.example.user.graduationproject.Bjelasnica.utils.InternetConnection;
import com.example.user.graduationproject.Bjelasnica.utils.SkiResortHolder;
import com.example.user.graduationproject.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Weather extends Fragment {

    private List<WeatherDay> days;
    private List<WeatherDay> day;
    private Typeface weatherFont;
    private String getMountain;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_weather3, container, false);


        getMountain = String.valueOf(getActivity().getTitle());

        weatherFont = Typeface.createFromAsset(Objects.requireNonNull(getActivity()).getAssets(), getResources().getString(R.string.PATH_TO_WEATHER_FONT));
        WeatherClient weatherClient = new WeatherClient(getActivity());
        InternetConnection connection = new InternetConnection();
        final ProgressBar mProgressCircle = v.findViewById(R.id.progress_circle);
        if (connection.getInternetConnection()) {
            weatherClient.getWeather(getLocation(v))
                    .enqueue(new Callback<WeatherResult>() {
                        @Override
                        public void onResponse(@NonNull final Call<WeatherResult> call, @NonNull final Response<WeatherResult> response) {
                            if (response.body() != null) {
                                days = response.body().getList();
                                day = new ArrayList<>();
                                WeatherDay prviDan = days.get(0);
                                WeatherDay drugiDan = days.get(7);
                                WeatherDay treciDan = days.get(15);
                                WeatherDay cetvrtiDan = days.get(23);
                                WeatherDay petiDan = days.get(31);
                                day.add(prviDan);
                                day.add(drugiDan);
                                day.add(treciDan);
                                day.add(cetvrtiDan);
                                day.add(petiDan);
                                try {
                                    buildRecyclerView(v);
                                    saveUserReportPreferences(day);
                                } catch (Exception ignored) {
                                }
                            }
                            mProgressCircle.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onFailure(@NonNull final Call<WeatherResult> call, @NonNull final Throwable t) {
                        }
                    });
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.connect_internet), Toast.LENGTH_SHORT).show();
            try {
                loadUserReportPreferences();
                buildRecyclerView(v);
                mProgressCircle.setVisibility(View.INVISIBLE);
            } catch (Exception ignored) {
            }
        }

        return v;
    }

    private static String getLocation(View v) {
        return String.format(v.getResources().getString(R.string.LOCATION_AND_COUNTRY_CODE), SkiResortHolder.getSkiResort().getCity());
    }

    private void buildRecyclerView(View v) {
        RecyclerWeather recyclerWeather = new RecyclerWeather(day, getContext(), weatherFont);
        RecyclerView mRecyclerView = v.findViewById(R.id.weather_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(recyclerWeather);
    }

    private void saveUserReportPreferences(List<WeatherDay> days) {
        if (getActivity() != null) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getMountain + getResources().getString(R.string.sharedPreferencesWeather), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(days);
            editor.putString(getMountain + getResources().getString(R.string.sharedPreferencesWeather_list), json);
            editor.apply();
        }
    }

    private void loadUserReportPreferences() {
        if (getActivity() != null) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getMountain + getResources().getString(R.string.sharedPreferencesWeather), Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString(getMountain + getResources().getString(R.string.sharedPreferencesWeather_list), null);
            Type type = new TypeToken<ArrayList<WeatherDay>>() {
            }.getType();
            day = gson.fromJson(json, type);
        }
    }

}