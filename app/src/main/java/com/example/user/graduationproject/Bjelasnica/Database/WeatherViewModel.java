package com.example.user.graduationproject.Bjelasnica.Database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class WeatherViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<WeatherTable>> allWeather;
    public WeatherViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allWeather = repository.getAllWeather();
    }

    public void insert(WeatherTable weatherTable){
        repository.insert(weatherTable);
    }

    public LiveData<List<WeatherTable>> getAllWeather(){
        return allWeather;
    }

}
