package com.example.thirdhw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public abstract class StorageManager {

    private static final String TAG = "StorageManager";

    public static synchronized boolean exists(String url, Context context) {

        String[] files = context.fileList();
        if (files != null && Arrays.asList(files).contains(getFileName(url))) {
            return true;
        }
        return false;
    }

    public static synchronized void write(String url, Bitmap image, Context context) {
        String fileName = getFileName(url);
        if (exists(url, context)) {
            return;
        }
        try {
            ByteArrayOutputStream tmp = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 0, tmp);
            OutputStream stream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            stream.write(tmp.toByteArray());
            stream.close();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public static synchronized Bitmap get(String url, Context context) {
        String fileName = getFileName(url);
        try {
            InputStream stream = context.openFileInput(fileName);
            return BitmapFactory.decodeStream(stream);
        } catch (IOException e) {
            Log.e(TAG + "get", e.getMessage());
            return null;
        }
    }

    private static String getFileName(String url) {
        return Integer.toString(url.hashCode());
    }
}
