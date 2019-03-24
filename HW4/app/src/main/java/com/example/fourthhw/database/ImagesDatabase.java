package com.example.fourthhw.database;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Image.class}, version = 1)
public abstract class ImagesDatabase extends RoomDatabase {
    public abstract ImageDao imageDao();
}
