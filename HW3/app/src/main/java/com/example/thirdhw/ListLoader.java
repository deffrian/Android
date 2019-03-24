package com.example.thirdhw;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.URL;

class ListLoader extends AsyncTask<String, Void, Void> {

    final public static int MAX_NUM_OF_IMAGES = 1000;

    final private WeakReference<ImageLoaderCallback> callback;

    ListLoader(ImageLoaderCallback callback) {
        this.callback = new WeakReference<>(callback);
    }

    @Override
    protected Void doInBackground(String... strings) {
        if (Global.urlList.size() != 0) {
            return null;
        }
        try {
            URL listUrl = new URL(strings[0]);
            BufferedReader reader = new BufferedReader(new InputStreamReader(listUrl.openConnection().getInputStream()));
            String tmp;
            while ((tmp = reader.readLine()) != null && Global.urlList.size() < MAX_NUM_OF_IMAGES) {
                Global.urlList.add(tmp);
            }
            reader.close();
        } catch (Exception e) {
            Log.d("ListLoader", e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void res) {
        ImageLoaderCallback tmp = callback.get();
        if (tmp != null) {
            tmp.onLoad();
        }
    }
}