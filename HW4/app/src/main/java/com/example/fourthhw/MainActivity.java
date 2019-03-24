package com.example.fourthhw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.fourthhw.api.PostModel;
import com.example.fourthhw.api.VkApi;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity implements Callback<PostModel> {

    final public String TAG = "MainActivity";

    final public String BASE_API_URL = "https://api.vk.com/";

    final public String ACCESS_TOKEN = "95c1d11595c1d11595c1d115fc95a8dac6995c195c1d115c9ba09e2e81f4ea1408d7f34";

    private VkApi api;

    public Retrofit retrofit = null;

    private RecyclerView recyclerView;

    @Override
    public void onResponse(Call<PostModel> call, Response<PostModel> response) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ImageListAdapter(this, response.body()));
    }

    @Override
    public void onFailure(Call<PostModel> call, Throwable t) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.textViewRecycler);

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            api = retrofit.create(VkApi.class);
        }

        api.getImages("1", "profile", ACCESS_TOKEN, "5.92").enqueue(this);

        Log.e(TAG, "OK");

    }
}