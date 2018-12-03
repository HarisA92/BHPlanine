package com.bhplanine.user.graduationproject.fragments.navigationFragments;

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

import com.bhplanine.user.graduationproject.BuildConfig;
import com.bhplanine.user.graduationproject.R;
import com.bhplanine.user.graduationproject.adapters.WeatherAdapter;
import com.bhplanine.user.graduationproject.adapters.WeatherDrawerAdapter;
import com.bhplanine.user.graduationproject.retrofit.client.WeatherClient;
import com.bhplanine.user.graduationproject.retrofit.model.WeatherDay;
import com.bhplanine.user.graduationproject.retrofit.model.WeatherResult;
import com.bhplanine.user.graduationproject.utils.InternetConnection;
import com.bhplanine.user.graduationproject.utils.RetrofitHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WeatherDrawerFragment extends Fragment {

    private List<WeatherDay> days = new ArrayList<>();
    private List<WeatherDay> bjelasnicaList, jahorinaList, ravnaplaninaList, igmanList, vlasicList;
    private Typeface weatherFont;
    private WeatherClient weatherClient = new WeatherClient(getActivity());
    private InternetConnection connection = new InternetConnection();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RecyclerView mRecyclerView;
    private ProgressBar progressBar;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_weather_drawer, container, false);
        buildRecyclerView(v);
        Objects.requireNonNull(getActivity()).setTitle("Weather");
        progressBar = v.findViewById(R.id.progress_bar_weather_drawer);
        weatherFont = Typeface.createFromAsset(Objects.requireNonNull(getActivity()).getAssets(), getResources().getString(R.string.PATH_TO_WEATHER_FONT));
        getAPI();
        return v;
    }

    private void buildRecyclerView(View v) {
        mRecyclerView = v.findViewById(R.id.drawer_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void buildRecyclerAdapter() {
        WeatherDrawerAdapter weatherDrawerAdapter = new WeatherDrawerAdapter(days, getContext(), weatherFont);
        mRecyclerView.setAdapter(weatherDrawerAdapter);
    }

    private void getAPI() {
        if(connection.getInternetConnection()) {
            Observable<WeatherResult> bjelasnica = weatherClient.getWeatherService()
                    .getWeather("Pervizi", BuildConfig.ApiKey_Weather, getResources().getString(R.string.METRIC_UNITS))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            Observable<WeatherResult> jahorina = weatherClient.getWeatherService()
                    .getWeather("Pervizi", BuildConfig.ApiKey_Weather, getResources().getString(R.string.METRIC_UNITS))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            Observable<WeatherResult> ravnaplanina = weatherClient.getWeatherService()
                    .getWeather("Jahorina", BuildConfig.ApiKey_Weather, getResources().getString(R.string.METRIC_UNITS))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            Observable<WeatherResult> vlasic = weatherClient.getWeatherService()
                    .getWeather("Pale", BuildConfig.ApiKey_Weather, getResources().getString(R.string.METRIC_UNITS))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            Observable<WeatherResult> igman = weatherClient.getWeatherService()
                    .getWeather("Travnik", BuildConfig.ApiKey_Weather, getResources().getString(R.string.METRIC_UNITS))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

            Observable<RetrofitHolder> combined = Observable.zip(bjelasnica, jahorina, ravnaplanina, vlasic, igman, RetrofitHolder::new);
            combined.subscribe(new Observer<RetrofitHolder>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onNext(RetrofitHolder retrofitHolder) {
                    bjelasnicaList = retrofitHolder.bjelasnica.getList();
                    jahorinaList = retrofitHolder.jahorina.getList();
                    ravnaplaninaList = retrofitHolder.ravnaplanina.getList();
                    igmanList = retrofitHolder.igman.getList();
                    vlasicList = retrofitHolder.vlasic.getList();
                    days.addAll(getWeatherList(bjelasnicaList));
                    days.addAll(getWeatherList(jahorinaList));
                    days.addAll(getWeatherList(ravnaplaninaList));
                    days.addAll(getWeatherList(igmanList));
                    days.addAll(getWeatherList(vlasicList));
                    buildRecyclerAdapter();
                    saveUserReportPreferences(days);
                    progressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onError(Throwable e) {
                    Toast.makeText(getContext(), getResources().getString(R.string.error) + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onComplete() {

                }
            });
        }
        else{
            loadUserReportPreferences();
            buildRecyclerAdapter();
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getActivity(), getResources().getString(R.string.connect_internet), Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<WeatherDay> getWeatherList(List<WeatherDay> weatherDays) {
        return IntStream.range(0, weatherDays.size())
                .filter(n -> n % 8 == 0)
                .limit(1)
                .mapToObj(weatherDays::get)
                .collect(Collectors.toList());
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
}
