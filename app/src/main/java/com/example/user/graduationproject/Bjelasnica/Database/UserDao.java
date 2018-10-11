package com.example.user.graduationproject.Bjelasnica.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM report_table")
    public List<UserReport> getAll();

    @Insert
    public void insertAll(UserReport userReport);
}
