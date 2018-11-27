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
import android.widget.Toast;

import com.bhplanine.user.graduationproject.BuildConfig;
import com.bhplanine.user.graduationproject.R;
import com.bhplanine.user.graduationproject.databinding.FragmentWeatherDrawerBinding;
import com.bhplanine.user.graduationproject.retrofit.client.WeatherClient;
import com.bhplanine.user.graduationproject.retrofit.model.WeatherDay;
import com.bhplanine.user.graduationproject.retrofit.model.WeatherResult;
import com.bhplanine.user.graduationproject.utils.InternetConnection;
import com.bhplanine.user.graduationproject.utils.RetrofitHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function5;
import io.reactivex.schedulers.Schedulers;

public class WeatherDrawerFragment extends Fragment {

    private List<WeatherDay> days, days1, days2;
    private List<WeatherDay> bjelasnicaList, jahorinaList, ravnaplaninaList, igmanList, vlasicList;
    private Typeface weatherFont;
    private WeatherClient weatherClient = new WeatherClient(getActivity());
    private InternetConnection connection = new InternetConnection();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Disposable disposableBjelasnica, disposableJahorina, disposableIgman, disposableVlasic, disposableRavnaPlanina;
    private FragmentWeatherDrawerBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_weather_drawer, container, false);
        View v = binding.getRoot();

        weatherFont = Typeface.createFromAsset(Objects.requireNonNull(getActivity()).getAssets(), getResources().getString(R.string.PATH_TO_WEATHER_FONT));
        getAPI();

        return v;
    }


    private void getAPI() {
        Observable<WeatherResult> bjelasnica = weatherClient.getWeatherService()
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
        Observable<WeatherResult> igman = weatherClient.getWeatherService()
                .getWeather("Pervizi", BuildConfig.ApiKey_Weather, getResources().getString(R.string.METRIC_UNITS))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<RetrofitHolder> combined = Observable.zip(bjelasnica, jahorina, ravnaplanina, vlasic, igman, RetrofitHolder::new);
        combined.subscribe(new Observer<RetrofitHolder>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(RetrofitHolder retrofitHolder) {
                bjelasnicaList = retrofitHolder.bjelasnica.getList();
                setDataBjelasnica(bjelasnicaList);
                jahorinaList = retrofitHolder.jahorina.getList();
                setDataJahorina(jahorinaList);
                ravnaplaninaList = retrofitHolder.ravnaplanina.getList();
                setDataRavnaPlanina(ravnaplaninaList);
                igmanList = retrofitHolder.igman.getList();
                setDataIgman(igmanList);
                vlasicList = retrofitHolder.vlasic.getList();
                setDataVlasic(vlasicList);
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getWeatherRetrofit() {
        if (connection.getInternetConnection()) {
            disposableBjelasnica = weatherClient.getWeatherService().getWeather("Pervizi",
                    BuildConfig.ApiKey_Weather, getResources().getString(R.string.METRIC_UNITS))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(weatherResult -> {
                        days = weatherResult.getList();
                        setDataBjelasnica(days);
                    }, throwable -> {
                        Toast.makeText(getContext(), getResources().getString(R.string.error) + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    });
            disposableIgman = weatherClient.getWeatherService().getWeather("Jahorina", BuildConfig.ApiKey_Weather, getResources().getString(R.string.METRIC_UNITS))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(weatherResult -> {
                        days = weatherResult.getList();
                        setDataJahorina(days);
                    }, throwable -> {
                        Toast.makeText(getContext(), getResources().getString(R.string.error) + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    });
            disposableJahorina = weatherClient.getWeatherService().getWeather("Jahorina", BuildConfig.ApiKey_Weather, getResources().getString(R.string.METRIC_UNITS))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(weatherResult -> {
                        days = weatherResult.getList();

                    }, throwable -> {
                        Toast.makeText(getContext(), getResources().getString(R.string.error) + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    });
            disposableVlasic = weatherClient.getWeatherService().getWeather("Travnik", BuildConfig.ApiKey_Weather, getResources().getString(R.string.METRIC_UNITS))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(weatherResult -> {
                        days = weatherResult.getList();

                    }, throwable -> {
                        Toast.makeText(getContext(), getResources().getString(R.string.error) + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    });
            disposableRavnaPlanina = weatherClient.getWeatherService().getWeather("Pale", BuildConfig.ApiKey_Weather, getResources().getString(R.string.METRIC_UNITS))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(weatherResult -> {
                        days = weatherResult.getList();

                    }, throwable -> {
                        Toast.makeText(getContext(), getResources().getString(R.string.error) + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.connect_internet), Toast.LENGTH_SHORT).show();
            loadUserReportPreferences();
            int a = 0;
            //mProgressCircle.setVisibility(View.INVISIBLE);
        }
        compositeDisposable.add(disposableBjelasnica);
        compositeDisposable.add(disposableIgman);
        compositeDisposable.add(disposableVlasic);
        compositeDisposable.add(disposableJahorina);
        compositeDisposable.add(disposableRavnaPlanina);

    }

    private void setDataVlasic(List<WeatherDay> list) {
        String temperature, setDescription, getDescription, icon;
        binding.weatherIconTextVlasic.setTypeface(weatherFont);
        switch (list.get(0).getWeather().get(0).getIcon()) {
            case "01d":
                binding.weatherIconTextVlasic.setText(R.string.wi_day_sunny);
                break;
            case "02d":
                binding.weatherIconTextVlasic.setText(R.string.wi_cloudy_gusts);
                break;
            case "03d":
                binding.weatherIconTextVlasic.setText(R.string.wi_cloud_down);
                break;
            case "10d":
                binding.weatherIconTextVlasic.setText(R.string.wi_day_rain_mix);
                break;
            case "11d":
                binding.weatherIconTextVlasic.setText(R.string.wi_day_thunderstorm);
                break;
            case "13d":
                binding.weatherIconTextVlasic.setText(R.string.wi_day_snow);
                break;
            case "01n":
                binding.weatherIconTextVlasic.setText(R.string.wi_night_clear);
                break;
            case "04d":
                binding.weatherIconTextVlasic.setText(R.string.wi_cloudy);
                break;
            case "04n":
                binding.weatherIconTextVlasic.setText(R.string.wi_night_cloudy);
                break;
            case "02n":
                binding.weatherIconTextVlasic.setText(R.string.wi_night_cloudy);
                break;
            case "03n":
                binding.weatherIconTextVlasic.setText(R.string.wi_night_cloudy_gusts);
                break;
            case "10n":
                binding.weatherIconTextVlasic.setText(R.string.wi_night_cloudy_gusts);
                break;
            case "11n":
                binding.weatherIconTextVlasic.setText(R.string.wi_night_rain);
                break;
            case "13n":
                binding.weatherIconTextVlasic.setText(R.string.wi_night_snow);
                break;
        }

        long timestamp = list.get(0).getDt();
        Date expiry = new Date(timestamp * 1000);
        String date = expiry.toString();
        String[] parts = date.split(" ");
        String day = parts[0];
        String Date = parts[1];
        String dateInMonth = parts[2];
        binding.dateAndTimeVlasic.setText(day + " " + Date + " " + dateInMonth);

        int temp = (int) list.get(0).getMain().getTemp();
        temperature = String.valueOf(temp);

        setDescription = list.get(0).getWeather().get(0).getDescription();
        getDescription = setDescription.substring(0, 1).toUpperCase() + setDescription.substring(1);

        binding.descriptionVlasic.setText(getDescription);
        binding.temperatureVlasic.setText(temperature + "°C");
    }

    private void setDataIgman(List<WeatherDay> list) {
        String temperature, setDescription, getDescription, icon;
        binding.weatherIconTextIgman.setTypeface(weatherFont);
        switch (list.get(0).getWeather().get(0).getIcon()) {
            case "01d":
                binding.weatherIconTextIgman.setText(R.string.wi_day_sunny);
                break;
            case "02d":
                binding.weatherIconTextIgman.setText(R.string.wi_cloudy_gusts);
                break;
            case "03d":
                binding.weatherIconTextIgman.setText(R.string.wi_cloud_down);
                break;
            case "10d":
                binding.weatherIconTextIgman.setText(R.string.wi_day_rain_mix);
                break;
            case "11d":
                binding.weatherIconTextIgman.setText(R.string.wi_day_thunderstorm);
                break;
            case "13d":
                binding.weatherIconTextIgman.setText(R.string.wi_day_snow);
                break;
            case "01n":
                binding.weatherIconTextIgman.setText(R.string.wi_night_clear);
                break;
            case "04d":
                binding.weatherIconTextIgman.setText(R.string.wi_cloudy);
                break;
            case "04n":
                binding.weatherIconTextIgman.setText(R.string.wi_night_cloudy);
                break;
            case "02n":
                binding.weatherIconTextIgman.setText(R.string.wi_night_cloudy);
                break;
            case "03n":
                binding.weatherIconTextIgman.setText(R.string.wi_night_cloudy_gusts);
                break;
            case "10n":
                binding.weatherIconTextIgman.setText(R.string.wi_night_cloudy_gusts);
                break;
            case "11n":
                binding.weatherIconTextIgman.setText(R.string.wi_night_rain);
                break;
            case "13n":
                binding.weatherIconTextIgman.setText(R.string.wi_night_snow);
                break;
        }

        long timestamp = list.get(0).getDt();
        Date expiry = new Date(timestamp * 1000);
        String date = expiry.toString();
        String[] parts = date.split(" ");
        String day = parts[0];
        String Date = parts[1];
        String dateInMonth = parts[2];
        binding.dateAndTimeIgman.setText(day + " " + Date + " " + dateInMonth);

        int temp = (int) list.get(0).getMain().getTemp();
        temperature = String.valueOf(temp);

        setDescription = list.get(0).getWeather().get(0).getDescription();
        getDescription = setDescription.substring(0, 1).toUpperCase() + setDescription.substring(1);

        binding.descriptionIgman.setText(getDescription);
        binding.temperatureIgman.setText(temperature + "°C");
    }

    private void setDataRavnaPlanina(List<WeatherDay> list) {
        String temperature, setDescription, getDescription, icon;
        binding.weatherIconTextRavnaplanina.setTypeface(weatherFont);
        switch (list.get(0).getWeather().get(0).getIcon()) {
            case "01d":
                binding.weatherIconTextRavnaplanina.setText(R.string.wi_day_sunny);
                break;
            case "02d":
                binding.weatherIconTextRavnaplanina.setText(R.string.wi_cloudy_gusts);
                break;
            case "03d":
                binding.weatherIconTextRavnaplanina.setText(R.string.wi_cloud_down);
                break;
            case "10d":
                binding.weatherIconTextRavnaplanina.setText(R.string.wi_day_rain_mix);
                break;
            case "11d":
                binding.weatherIconTextRavnaplanina.setText(R.string.wi_day_thunderstorm);
                break;
            case "13d":
                binding.weatherIconTextRavnaplanina.setText(R.string.wi_day_snow);
                break;
            case "01n":
                binding.weatherIconTextRavnaplanina.setText(R.string.wi_night_clear);
                break;
            case "04d":
                binding.weatherIconTextRavnaplanina.setText(R.string.wi_cloudy);
                break;
            case "04n":
                binding.weatherIconTextRavnaplanina.setText(R.string.wi_night_cloudy);
                break;
            case "02n":
                binding.weatherIconTextRavnaplanina.setText(R.string.wi_night_cloudy);
                break;
            case "03n":
                binding.weatherIconTextRavnaplanina.setText(R.string.wi_night_cloudy_gusts);
                break;
            case "10n":
                binding.weatherIconTextRavnaplanina.setText(R.string.wi_night_cloudy_gusts);
                break;
            case "11n":
                binding.weatherIconTextRavnaplanina.setText(R.string.wi_night_rain);
                break;
            case "13n":
                binding.weatherIconTextRavnaplanina.setText(R.string.wi_night_snow);
                break;
        }

        long timestamp = list.get(0).getDt();
        Date expiry = new Date(timestamp * 1000);
        String date = expiry.toString();
        String[] parts = date.split(" ");
        String day = parts[0];
        String Date = parts[1];
        String dateInMonth = parts[2];
        binding.dateAndTimeRavnaplanina.setText(day + " " + Date + " " + dateInMonth);

        int temp = (int) list.get(0).getMain().getTemp();
        temperature = String.valueOf(temp);

        setDescription = list.get(0).getWeather().get(0).getDescription();
        getDescription = setDescription.substring(0, 1).toUpperCase() + setDescription.substring(1);

        binding.descriptionRavnaplanina.setText(getDescription);
        binding.temperatureRavnaplanina.setText(temperature + "°C");
    }

    private void setDataJahorina(List<WeatherDay> list) {
        String temperature, setDescription, getDescription, icon;
        binding.weatherIconTextJahorina.setTypeface(weatherFont);
        switch (list.get(0).getWeather().get(0).getIcon()) {
            case "01d":
                binding.weatherIconTextJahorina.setText(R.string.wi_day_sunny);
                break;
            case "02d":
                binding.weatherIconTextJahorina.setText(R.string.wi_cloudy_gusts);
                break;
            case "03d":
                binding.weatherIconTextJahorina.setText(R.string.wi_cloud_down);
                break;
            case "10d":
                binding.weatherIconTextJahorina.setText(R.string.wi_day_rain_mix);
                break;
            case "11d":
                binding.weatherIconTextJahorina.setText(R.string.wi_day_thunderstorm);
                break;
            case "13d":
                binding.weatherIconTextJahorina.setText(R.string.wi_day_snow);
                break;
            case "01n":
                binding.weatherIconTextJahorina.setText(R.string.wi_night_clear);
                break;
            case "04d":
                binding.weatherIconTextJahorina.setText(R.string.wi_cloudy);
                break;
            case "04n":
                binding.weatherIconTextJahorina.setText(R.string.wi_night_cloudy);
                break;
            case "02n":
                binding.weatherIconTextJahorina.setText(R.string.wi_night_cloudy);
                break;
            case "03n":
                binding.weatherIconTextJahorina.setText(R.string.wi_night_cloudy_gusts);
                break;
            case "10n":
                binding.weatherIconTextJahorina.setText(R.string.wi_night_cloudy_gusts);
                break;
            case "11n":
                binding.weatherIconTextJahorina.setText(R.string.wi_night_rain);
                break;
            case "13n":
                binding.weatherIconTextJahorina.setText(R.string.wi_night_snow);
                break;
        }

        long timestamp = list.get(0).getDt();
        Date expiry = new Date(timestamp * 1000);
        String date = expiry.toString();
        String[] parts = date.split(" ");
        String day = parts[0];
        String Date = parts[1];
        String dateInMonth = parts[2];
        binding.dateAndTimeJahorina.setText(day + " " + Date + " " + dateInMonth);

        int temp = (int) list.get(0).getMain().getTemp();
        temperature = String.valueOf(temp);

        setDescription = list.get(0).getWeather().get(0).getDescription();
        getDescription = setDescription.substring(0, 1).toUpperCase() + setDescription.substring(1);

        binding.descriptionJahorina.setText(getDescription);
        binding.temperatureJahorina.setText(temperature + "°C");
    }

    private void setDataBjelasnica(List<WeatherDay> list) {
        String temperature, setDescription, getDescription, icon;
        binding.weatherIconTextBjelasnica.setTypeface(weatherFont);
        switch (list.get(0).getWeather().get(0).getIcon()) {
            case "01d":
                binding.weatherIconTextBjelasnica.setText(R.string.wi_day_sunny);
                break;
            case "02d":
                binding.weatherIconTextBjelasnica.setText(R.string.wi_cloudy_gusts);
                break;
            case "03d":
                binding.weatherIconTextBjelasnica.setText(R.string.wi_cloud_down);
                break;
            case "10d":
                binding.weatherIconTextBjelasnica.setText(R.string.wi_day_rain_mix);
                break;
            case "11d":
                binding.weatherIconTextBjelasnica.setText(R.string.wi_day_thunderstorm);
                break;
            case "13d":
                binding.weatherIconTextBjelasnica.setText(R.string.wi_day_snow);
                break;
            case "01n":
                binding.weatherIconTextBjelasnica.setText(R.string.wi_night_clear);
                break;
            case "04d":
                binding.weatherIconTextBjelasnica.setText(R.string.wi_cloudy);
                break;
            case "04n":
                binding.weatherIconTextBjelasnica.setText(R.string.wi_night_cloudy);
                break;
            case "02n":
                binding.weatherIconTextBjelasnica.setText(R.string.wi_night_cloudy);
                break;
            case "03n":
                binding.weatherIconTextBjelasnica.setText(R.string.wi_night_cloudy_gusts);
                break;
            case "10n":
                binding.weatherIconTextBjelasnica.setText(R.string.wi_night_cloudy_gusts);
                break;
            case "11n":
                binding.weatherIconTextBjelasnica.setText(R.string.wi_night_rain);
                break;
            case "13n":
                binding.weatherIconTextBjelasnica.setText(R.string.wi_night_snow);
                break;
        }

        long timestamp = list.get(0).getDt();
        Date expiry = new Date(timestamp * 1000);
        String date = expiry.toString();
        String[] parts = date.split(" ");
        String day = parts[0];
        String Date = parts[1];
        String dateInMonth = parts[2];
        binding.dateAndTimeBjelasnica.setText(day + " " + Date + " " + dateInMonth);

        int temp = (int) list.get(0).getMain().getTemp();
        temperature = String.valueOf(temp);

        setDescription = list.get(0).getWeather().get(0).getDescription();
        getDescription = setDescription.substring(0, 1).toUpperCase() + setDescription.substring(1);

        binding.descriptionBjelasnica.setText(getDescription);
        binding.temperatureBjelasnica.setText(temperature + "°C");

        binding.descriptionJahorina.setText(getDescription);
        binding.temperatureJahorina.setText(temperature);

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
