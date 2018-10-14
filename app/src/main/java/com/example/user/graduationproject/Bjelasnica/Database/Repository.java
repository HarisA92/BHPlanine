package com.example.user.graduationproject.Bjelasnica.Database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class Repository {

    private WeatherDao weatherDao;
    private LiveData<List<WeatherTable>> weatherReport;

    public Repository(Application application){
        WeatherDatabase weatherDatabase = WeatherDatabase.getInstance(application);
        weatherDao = weatherDatabase.dao();
        weatherReport = weatherDao.getAllWeather();
    }

    public void insert (WeatherTable weatherTable){
        new InsertUserReportAsyncTask(weatherDao).execute(weatherTable);
    }

    public LiveData<List<WeatherTable>> getAllWeather(){
        return weatherReport;
    }

    private static class InsertUserReportAsyncTask extends AsyncTask<WeatherTable, Void, Void>{
        private WeatherDao dao;

        private InsertUserReportAsyncTask(WeatherDao weatherDao){
            this.dao = weatherDao;
        }
        @Override
        protected Void doInBackground(WeatherTable... weatherTables) {
            dao.insertAll(weatherTables[0]);
            return null;
        }
    }

}
