package com.bhplanine.user.graduationproject.Bjelasnica.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bhplanine.user.graduationproject.Bjelasnica.adapters.WeatherAdapter;
import com.bhplanine.user.graduationproject.Bjelasnica.retrofit.client.WeatherClient;
import com.bhplanine.user.graduationproject.Bjelasnica.retrofit.model.WeatherDay;
import com.bhplanine.user.graduationproject.Bjelasnica.models.SkiResortHolder;
import com.bhplanine.user.graduationproject.Bjelasnica.utils.InternetConnection;
import com.bhplanine.user.graduationproject.BuildConfig;
import com.bhplanine.user.graduationproject.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WeatherFragment extends Fragment {

    private List<WeatherDay> days;
    private Typeface weatherFont;
    private String getMountain;
    private RecyclerView mRecyclerView;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_weather3, container, false);
        buildRecyclerView(v);

        final ProgressBar mProgressCircle = v.findViewById(R.id.progress_circle);
        CompositeDisposable compositeDisposable = new CompositeDisposable();

        getMountain = String.valueOf(Objects.requireNonNull(getActivity()).getTitle());
        weatherFont = Typeface.createFromAsset(Objects.requireNonNull(getActivity()).getAssets(), getResources().getString(R.string.PATH_TO_WEATHER_FONT));
        WeatherClient weatherClient = new WeatherClient(getActivity());
        InternetConnection connection = new InternetConnection();

        if (connection.getInternetConnection()) {
            compositeDisposable.add(weatherClient.getWeatherService().getWeather(getLocation(v), BuildConfig.ApiKey_Weather, getResources().getString(R.string.METRIC_UNITS))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(weatherResult -> {
                        days = weatherResult.getList();
                        buildRecyclerAdapter();
                        saveUserReportPreferences(days);
                        mProgressCircle.setVisibility(View.INVISIBLE);
                    }, throwable -> {
                        Toast.makeText(getContext(), getResources().getString(R.string.error) + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }));
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.connect_internet), Toast.LENGTH_SHORT).show();
            loadUserReportPreferences();
            buildRecyclerAdapter();
            mProgressCircle.setVisibility(View.INVISIBLE);
        }
        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void buildRecyclerView(View v) {
        mRecyclerView = v.findViewById(R.id.weather_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void buildRecyclerAdapter() {
        WeatherAdapter weatherAdapter = new WeatherAdapter(getWeatherList(days), getContext(), weatherFont);
        mRecyclerView.setAdapter(weatherAdapter);
    }

    private static String getLocation(View v) {
        return String.format(v.getResources().getString(R.string.LOCATION_AND_COUNTRY_CODE), SkiResortHolder.getSkiResort().getCity());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<WeatherDay> getWeatherList(List<WeatherDay> weatherDays) {
        return IntStream.range(0, weatherDays.size())
                .filter(n -> n % 8 == 0)
                .limit(5)
                .mapToObj(weatherDays::get)
                .collect(Collectors.toList());
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
            days = gson.fromJson(json, type);
        }
    }

}