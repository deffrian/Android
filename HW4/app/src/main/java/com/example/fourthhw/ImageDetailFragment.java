package com.example.fourthhw;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.fourthhw.database.Image;
import com.example.fourthhw.database.ImageDao;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class ImageDetailFragment extends Fragment implements InserterCallback {

    final public static String TAG = "ImageDetailFragment";

    final public static String IMAGE_URL_PARAM = "ImageUrlParam";

    private String imageUrl;

    private ImageView imageView;

    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
            imageView.setImageBitmap(bitmap);
            Thread thread = new Thread() {
                @Override
                public void run() {
                    ImageDao tmpDao = App.getInstance().getDatabase().imageDao();
                    ByteArrayOutputStream tmpStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 0, tmpStream);
                    tmpDao.insert(new Image(imageUrl, tmpStream.toByteArray()));
                }
            };
            thread.start();
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    private Inserter inserter;

    @Override
    public void callback(Bitmap bitmap) {
        if (bitmap != null) {
            Log.d("Database", "fromDB");
            imageView.setImageBitmap(bitmap);
        } else {
            Log.d("Database", "fromInternet");
            Picasso.with(getContext()).load(imageUrl).into(target);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageUrl = getArguments().getString(IMAGE_URL_PARAM);
        inserter = new Inserter(this);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.image_detail_fragment, container, false);
        imageView = rootView.findViewById(R.id.imageViewDetailFragment);
        inserter.execute(imageUrl);
        return rootView;
    }

}
