package com.example.fifthhw;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.fifthhw.api.Item;
import com.example.fifthhw.api.PostModel;
import com.example.fifthhw.api.VkApi;
import com.example.fifthhw.database.Image;
import com.example.fifthhw.database.ImagesDatabase;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class IOManager {

    final static public String BASE_API_URL = "https://api.vk.com/";

    final static public String ACCESS_TOKEN = "95c1d11595c1d11595c1d115fc95a8dac6995c195c1d115c9ba09e2e81f4ea1408d7f34";

    final static public String DATABASE_NAME = "ImageDB";

    private static ImagesDatabase database = null;

    static Observable<List<String>> getImagesList() {
        VkApi api = new Retrofit.Builder()
                .baseUrl(BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
                .create(VkApi.class);

        return api.getImages("1", "profile", ACCESS_TOKEN, "5.92")
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<PostModel, List<String>>() {
                    @Override
                    public List<String> apply(PostModel response) throws Exception {
                        List<String> urls = new ArrayList<>();
                        for (Item i : response.getResponse().getItems()) {
                            urls.add(i.getSizes().get(0).getUrl());
                        }
                        return urls;
                    }
                });
    }


    static Single<Bitmap> getImage(final String url, Context appContext) {
        if (database == null) {
            database = Room.databaseBuilder(appContext, ImagesDatabase.class, DATABASE_NAME).build();
        }
        return database.imageDao().getByUrl(url)
                .subscribeOn(Schedulers.io())
                .map(new Function<List<Image>, Bitmap>() {
                    @Override
                    public Bitmap apply(List<Image> images) throws Exception {
                        if (images.size() == 0) {
                            URL imageUrl = new URL(url);
                            Bitmap bitmap = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());
                            ByteArrayOutputStream tmp = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 0, tmp);
                            database.imageDao().insert(new Image(url, tmp.toByteArray()));
                            return bitmap;
                        } else {
                            return BitmapFactory.decodeByteArray(images.get(0).image, 0, images.get(0).image.length);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }
}
