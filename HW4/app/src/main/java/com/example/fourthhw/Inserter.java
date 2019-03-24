package com.example.fourthhw;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.example.fourthhw.database.*;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.List;

class Inserter extends AsyncTask<String, Void, Bitmap> {

    WeakReference<InserterCallback> callbackRef;

    Inserter(InserterCallback callback) {
        callbackRef = new WeakReference<>(callback);
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        ImageDao tmpDao = App.getInstance().getDatabase().imageDao();
        List<Image> list = tmpDao.getByUrl(strings[0]);
        if (list.size() != 0) {
            Log.d("Database", "OK");
            return BitmapFactory.decodeByteArray(list.get(0).image, 0, list.get(0).image.length);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        InserterCallback callback = callbackRef.get();
        if (callback != null) {
            callback.callback(bitmap);
        }
    }
}