package com.example.user.graduationproject.Bjelasnica.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "weather_table")
public class WeatherTable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "temperature")
    private String temperature;

    @ColumnInfo(name = "time")
    private String time;

    @ColumnInfo(name = "weather_icon")
    private String weather_icon;


    public WeatherTable(String description, String temperature, String time, String weather_icon){
        this.description = description;
        this.temperature = temperature;
        this.time = time;
        this.weather_icon = weather_icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWeather_icon() {
        return weather_icon;
    }

    public void setWeather_icon(String weather_icon) {
        this.weather_icon = weather_icon;
    }
}
