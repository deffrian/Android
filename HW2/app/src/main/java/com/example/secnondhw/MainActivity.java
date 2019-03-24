package com.example.secnondhw;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends AppCompatActivity implements ImageLoaderCallback {

    final public String URL = "https://pastebin.com/raw/7Ku9E8mR";
    final public String TAG = "MainActivity";

    private RecyclerView recyclerView;

    @Override
    public void onLoad() {
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.textViewRecycler);

        if (Global.urlList == null) {
            Global.urlList = Collections.synchronizedList(new ArrayList<String>());
        }
        if (Global.imageList == null) {
            Global.imageList = Collections.synchronizedList(new ArrayList<Bitmap>());
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ImageListAdapter(this));

        ListLoader loader = new ListLoader(this);
        loader.execute(URL);
    }
}