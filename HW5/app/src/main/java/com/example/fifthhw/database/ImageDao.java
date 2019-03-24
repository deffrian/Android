package com.example.fifthhw.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface ImageDao {

    @Query("SELECT * FROM image WHERE url = :url")
    Single<List<Image>> getByUrl(String url);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Image image);
}
