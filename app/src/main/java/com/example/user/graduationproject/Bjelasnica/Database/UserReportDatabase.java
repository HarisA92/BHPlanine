package com.example.user.graduationproject.Bjelasnica.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {UserReport.class}, version = 1)

public abstract class UserReportDatabase extends RoomDatabase {

    public abstract UserDao myDao();

}
