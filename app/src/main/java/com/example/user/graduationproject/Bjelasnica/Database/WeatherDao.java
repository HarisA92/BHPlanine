package com.example.user.graduationproject.Bjelasnica.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface WeatherDao {
    @Query("SELECT * FROM weather_table")
    LiveData<List<WeatherTable>> getAllWeather();

    @Insert
    void insertAll(WeatherTable weatherTable);
}
