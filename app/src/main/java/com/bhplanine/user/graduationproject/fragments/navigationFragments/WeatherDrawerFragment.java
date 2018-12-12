package com.bhplanine.user.graduationproject.fragments.navigationFragments;

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

import com.bhplanine.user.graduationproject.BuildConfig;
import com.bhplanine.user.graduationproject.R;
import com.bhplanine.user.graduationproject.adapters.WeatherDrawerAdapter;
import com.bhplanine.user.graduationproject.retrofit.client.WeatherClient;
import com.bhplanine.user.graduationproject.retrofit.model.WeatherDay;
import com.bhplanine.user.graduationproject.retrofit.model.WeatherResult;
import com.bhplanine.user.graduationproject.utils.InternetConnection;
import com.bhplanine.user.graduationproject.utils.RetrofitHolder;
import com.bhplanine.user.graduationproject.utils.SelectedFragment;
import com.google.firebase.perf.metrics.AddTrace;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WeatherDrawerFragment extends Fragment {

    private List<WeatherDay> days = new ArrayList<>();
    private List<WeatherDay> bjelasnicaList, jahorinaList, ravnaplaninaList, igmanList, vlasicList;
    private Typeface weatherFont;
    private WeatherClient weatherClient;
    private InternetConnection connection;
    private CompositeDisposable compositeDisposable;
    private RecyclerView mRecyclerView;
    private ProgressBar progressBar;

    @AddTrace(name = "onCreateWeatherDrawerFragment")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_weather_drawer, container, false);
        Objects.requireNonNull(getActivity()).setTitle("Weather");
        ((SelectedFragment) getActivity()).selectDrawerFragment(R.id.nav_weather);
        buildRecyclerView(v);
        progressBar = v.findViewById(R.id.progress_bar_weather_drawer);
        weatherClient = new WeatherClient(getActivity());
        connection = new InternetConnection();
        compositeDisposable = new CompositeDisposable();
        weatherFont = Typeface.createFromAsset(Objects.requireNonNull(getActivity()).getAssets(), getResources().getString(R.string.PATH_TO_WEATHER_FONT));
        getAPI();
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRecyclerView.setAdapter(null);
        mRecyclerView.setLayoutManager(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    private void buildRecyclerView(View v) {
        mRecyclerView = v.findViewById(R.id.drawer_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void buildRecyclerAdapter() {
        if (days != null) {
            WeatherDrawerAdapter weatherDrawerAdapter = new WeatherDrawerAdapter(days, weatherFont);
            mRecyclerView.setAdapter(weatherDrawerAdapter);
        }
    }

    private void getAPI() {
        if (connection.getInternetConnection()) {
            Observable<WeatherResult> bjelasnica = weatherClient.getWeatherService()
                    .getWeather("Pervizi", BuildConfig.ApiKey_Weather, getResources().getString(R.string.METRIC_UNITS))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            Observable<WeatherResult> igman = weatherClient.getWeatherService()
                    .getWeather("Pervizi", BuildConfig.ApiKey_Weather, getResources().getString(R.string.METRIC_UNITS))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            Observable<WeatherResult> jahorina = weatherClient.getWeatherService()
                    .getWeather("Jahorina", BuildConfig.ApiKey_Weather, getResources().getString(R.string.METRIC_UNITS))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            Observable<WeatherResult> ravnaplanina = weatherClient.getWeatherService()
                    .getWeather("Pale", BuildConfig.ApiKey_Weather, getResources().getString(R.string.METRIC_UNITS))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            Observable<WeatherResult> vlasic = weatherClient.getWeatherService()
                    .getWeather("Travnik", BuildConfig.ApiKey_Weather, getResources().getString(R.string.METRIC_UNITS))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

            compositeDisposable.add(Observable.zip(bjelasnica, igman, jahorina, ravnaplanina, vlasic, RetrofitHolder::new)
                    .subscribe(retrofitHolder -> {
                        bjelasnicaList = retrofitHolder.bjelasnica.getList();
                        igmanList = retrofitHolder.igman.getList();
                        jahorinaList = retrofitHolder.jahorina.getList();
                        ravnaplaninaList = retrofitHolder.ravnaplanina.getList();
                        vlasicList = retrofitHolder.vlasic.getList();
                        days.add(bjelasnicaList.get(0));
                        days.add(igmanList.get(0));
                        days.add(jahorinaList.get(0));
                        days.add(ravnaplaninaList.get(0));
                        days.add(vlasicList.get(0));
                        buildRecyclerAdapter();
                        saveUserReportPreferences(days);
                        progressBar.setVisibility(View.INVISIBLE);
                    }, throwable -> {
                        Toast.makeText(getContext(), getResources().getString(R.string.error) + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }));
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.connect_internet), Toast.LENGTH_SHORT).show();
            loadUserReportPreferences();
            buildRecyclerAdapter();
            progressBar.setVisibility(View.INVISIBLE);
        }
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
