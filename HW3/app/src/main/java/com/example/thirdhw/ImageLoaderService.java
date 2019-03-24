package com.example.thirdhw;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class ImageLoaderService extends IntentService {

    final public static String URL_PARAM = "url";
    final public static String RECEIVER_PARAM = "receiver";

    final private static String TAG = "ImageLoaderService";

    public ImageLoaderService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String url = intent.getStringExtra(URL_PARAM);
        ResultReceiver receiver = intent.getParcelableExtra(RECEIVER_PARAM);
        try {
            URL imageURL = new URL(url);
            StorageManager.write(url,BitmapFactory.decodeStream(imageURL.openConnection().getInputStream()), getApplicationContext());
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        receiver.send(Activity.RESULT_OK, new Bundle());
    }
}
