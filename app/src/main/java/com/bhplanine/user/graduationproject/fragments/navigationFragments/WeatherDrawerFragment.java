package com.bhplanine.user.graduationproject.fragments.navigationFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bhplanine.user.graduationproject.retrofit.client.WeatherClient;
import com.bhplanine.user.graduationproject.retrofit.model.Weather;
import com.bhplanine.user.graduationproject.retrofit.model.WeatherDay;
import com.bhplanine.user.graduationproject.retrofit.model.WeatherResult;
import com.bhplanine.user.graduationproject.utils.InternetConnection;
import com.bhplanine.user.graduationproject.BuildConfig;
import com.bhplanine.user.graduationproject.R;
import com.bhplanine.user.graduationproject.databinding.FragmentWeatherDrawerBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WeatherDrawerFragment extends Fragment {

    private List<WeatherDay> days;
    private Typeface weatherFont;
    private RecyclerView mRecyclerView;
    private WeatherClient weatherClient = new WeatherClient(getActivity());
    private InternetConnection connection = new InternetConnection();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ProgressBar mProgressCircle;
    private Disposable disposableBjelasnica, disposableJahorina, disposableIgman, disposableVlasic, disposableRavnaPlanina;
    private FragmentWeatherDrawerBinding binding;
    private WeatherDay weatherDay = new WeatherDay();
    private double temp;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = DataBindingUtil.inflate(inflater, R.layout.fragment_weather_drawer, container, false).getRoot();

        mProgressCircle = v.findViewById(R.id.progress_circle);
        weatherFont = Typeface.createFromAsset(Objects.requireNonNull(getActivity()).getAssets(), getResources().getString(R.string.PATH_TO_WEATHER_FONT));
        getWeatherRetrofit();
        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getWeatherRetrofit(){
        if (connection.getInternetConnection()) {
            /*disposableBjelasnica = weatherClient.getWeatherService().getWeather("Pervizi", BuildConfig.ApiKey_Weather, getResources().getString(R.string.METRIC_UNITS))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(weatherResult -> {
                        days = weatherResult.getList();
                        //d = days.get(0).getWeather().get(1).getDescription();
                        temp = days.get(0).getMain().getTemp();
                        binding.temperatureBjelasnica.setText((int) temp);
                        saveUserReportPreferences(days);
                        //mProgressCircle.setVisibility(View.INVISIBLE);
                    }, throwable -> {
                        Toast.makeText(getContext(), getResources().getString(R.string.error) + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    });*/
            disposableIgman = weatherClient.getWeatherService().getWeather("Pale", BuildConfig.ApiKey_Weather, getResources().getString(R.string.METRIC_UNITS))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(weatherResult -> {
                        days = weatherResult.getList();
                        temp = days.get(3).getMain().getTemp();
                        binding.temperatureBjelasnica.setText((int) temp);
                        //mProgressCircle.setVisibility(View.INVISIBLE);
                    }, throwable -> {
                        Toast.makeText(getContext(), getResources().getString(R.string.error) + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    });
            /*disposableJahorina = weatherClient.getWeatherService().getWeather("Jahorina", BuildConfig.ApiKey_Weather, getResources().getString(R.string.METRIC_UNITS))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(weatherResult -> {
                        days = weatherResult.getList();
                        buildRecyclerAdapter();
                        saveUserReportPreferences(days);
                        mProgressCircle.setVisibility(View.INVISIBLE);
                    }, throwable -> {
                        Toast.makeText(getContext(), getResources().getString(R.string.error) + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    });
            disposableVlasic = weatherClient.getWeatherService().getWeather("Travnik", BuildConfig.ApiKey_Weather, getResources().getString(R.string.METRIC_UNITS))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(weatherResult -> {
                        days = weatherResult.getList();
                        buildRecyclerAdapter();
                        saveUserReportPreferences(days);
                        mProgressCircle.setVisibility(View.INVISIBLE);
                    }, throwable -> {
                        Toast.makeText(getContext(), getResources().getString(R.string.error) + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    });
            disposableRavnaPlanina = weatherClient.getWeatherService().getWeather("Pale", BuildConfig.ApiKey_Weather, getResources().getString(R.string.METRIC_UNITS))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(weatherResult -> {
                        days = weatherResult.getList();
                        buildRecyclerAdapter();
                        saveUserReportPreferences(days);
                        mProgressCircle.setVisibility(View.INVISIBLE);
                    }, throwable -> {
                        Toast.makeText(getContext(), getResources().getString(R.string.error) + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    });*/
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.connect_internet), Toast.LENGTH_SHORT).show();
            loadUserReportPreferences();
            int a = 0;
            //mProgressCircle.setVisibility(View.INVISIBLE);
        }
        //compositeDisposable.add(disposableBjelasnica);
        compositeDisposable.add(disposableIgman);
        /*compositeDisposable.add(disposableVlasic);
        compositeDisposable.add(disposableJahorina);
        compositeDisposable.add(disposableRavnaPlanina);*/

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<WeatherDay> getDay(List<WeatherDay> list){
        return list.stream().limit(1).collect(Collectors.toList());
    }

    private void saveUserReportPreferences(List<WeatherDay> days) {
        if (getActivity() != null) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getResources().getString(R.string.sharedPreferencesWeather), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(days);
            editor.putString(getResources().getString(R.string.sharedPreferencesWeather_list), json);
            editor.apply();
        }
    }

    private void loadUserReportPreferences() {
        if (getActivity() != null) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getResources().getString(R.string.sharedPreferencesWeather), Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString(getResources().getString(R.string.sharedPreferencesWeather_list), null);
            Type type = new TypeToken<ArrayList<WeatherDay>>() {
            }.getType();
            days = gson.fromJson(json, type);
        }
    }
}
