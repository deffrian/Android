package com.example.fifthhw;

import android.arch.persistence.room.InvalidationTracker;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.fifthhw.api.Item;
import com.example.fifthhw.api.PostModel;
import com.example.fifthhw.api.VkApi;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    final public String TAG = "MainActivity";

    private RecyclerView recyclerView;

    Observer<List<String>> imageListListener = new Observer<List<String>>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(List<String> strings) {
            ((ImageListAdapter)recyclerView.getAdapter()).updateList(strings);
            recyclerView.getAdapter().notifyDataSetChanged();
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, e.getMessage());
        }

        @Override
        public void onComplete() {
            Log.d("imageListListener", "loaded");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.textViewRecycler);

        recyclerView.setAdapter(new ImageListAdapter(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        IOManager.getImagesList().subscribe(imageListListener);
    }
}