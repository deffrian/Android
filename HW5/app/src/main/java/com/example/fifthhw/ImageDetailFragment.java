package com.example.fifthhw;

import android.graphics.Bitmap;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import io.reactivex.observers.DisposableSingleObserver;

public class ImageDetailFragment extends Fragment {
    final public static String TAG = "ImageDetailFragment";

    final public static String IMAGE_URL_PARAM = "ImageUrlParam";

    private String imageUrl;

    private ImageView imageView;

    DisposableSingleObserver<Bitmap> imageListener = new DisposableSingleObserver<Bitmap>() {
        @Override
        public void onSuccess(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, e.getMessage());
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageUrl = getArguments().getString(IMAGE_URL_PARAM);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.image_detail_fragment, container, false);
        imageView = rootView.findViewById(R.id.imageViewDetailFragment);
        IOManager.getImage(imageUrl, getContext()).subscribe(imageListener);
        return rootView;
    }

}
