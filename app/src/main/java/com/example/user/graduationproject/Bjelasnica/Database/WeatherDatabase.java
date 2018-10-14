package com.example.user.graduationproject.Bjelasnica.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

@Database(entities = {WeatherTable.class},version = 1)
@TypeConverters({Converters.class})

public abstract class WeatherDatabase extends RoomDatabase {
    private static WeatherDatabase instance;
    private static List<String> list = new ArrayList<>();

    public abstract WeatherDao dao();

    public static synchronized WeatherDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    WeatherDatabase.class, "weather_table")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            new WeatherDatabase.PopulateDbAsyncTask(instance).execute();
            super.onCreate(db);
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private WeatherDao weatherDao;

        private PopulateDbAsyncTask(WeatherDatabase db){
            weatherDao = db.dao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            weatherDao.insertAll(new WeatherTable(" ", " ", " ", " "));


            return null;
        }
    }
}
