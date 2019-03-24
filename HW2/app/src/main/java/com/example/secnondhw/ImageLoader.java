package com.example.secnondhw;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.lang.ref.WeakReference;
import java.net.URL;

class ImageLoader extends AsyncTask<Integer, Void, Bitmap> {

    final private WeakReference<ImageView> image;
    private boolean isStoped = false;

    public void stop() {
        isStoped = true;
    }

    ImageLoader(ImageView image) {
        this.image = new WeakReference<>(image);
    }

    @Override
    protected Bitmap doInBackground(Integer... integers) {
        if (Global.imageList.get(integers[0]) != null) {
            return Global.imageList.get(integers[0]);
        }
        try {
            URL imageURL = new URL(Global.urlList.get(integers[0]));
            Bitmap bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
            Global.imageList.set(integers[0], bitmap);
            return bitmap;
        } catch (Exception e) {
            Log.d("ImageLoader", e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        ImageView view = image.get();
        if (bitmap != null && view != null && !isStoped) {
            view.setImageBitmap(bitmap);
        }
    }
}