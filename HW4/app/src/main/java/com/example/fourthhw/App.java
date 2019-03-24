package com.example.fourthhw;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.fourthhw.database.ImagesDatabase;

public class App extends Application {

    public static App instance;

    public ImagesDatabase database;

    public static final String DATABASE_NAME = "ImageDatabase";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, ImagesDatabase.class, DATABASE_NAME).build();
    }

    public static App getInstance() {
        return instance;
    }

    public ImagesDatabase getDatabase() {
        return database;
    }
}
