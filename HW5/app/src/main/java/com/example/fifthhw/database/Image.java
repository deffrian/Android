package com.example.fifthhw.database;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Image {

    public Image(String url, byte[] image) {
        this.url = url;
        this.image = image;
    }

    @PrimaryKey(autoGenerate = false)
    @NonNull
    public String url;

    public byte[] image;

}
