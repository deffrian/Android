package com.example.thirdhw;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImageDetailFragment extends Fragment implements ServiceCallback.Receiver{

    final public static String TAG = "ImageDetailFragment";

    final public static String IMAGE_URL_PARAM = "ImageUrlParam";

    private String imageUrl;

    private ImageView image;

    ServiceCallback callback;

    @Override
    public void onReceiveResult() {
        image.setImageBitmap(StorageManager.get(imageUrl, image.getContext()));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageUrl = getArguments().getString(IMAGE_URL_PARAM);
        callback = new ServiceCallback(new Handler());
        callback.setReceiver(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.image_detail_fragment, container, false);
        image = rootView.findViewById(R.id.imageViewDetailFragment);
        setImage();
        return rootView;
    }

    private void setImage() {
        if (!StorageManager.exists(imageUrl, image.getContext())) {
            Log.d(TAG, "fromInternet");
            Intent intent = new Intent(getActivity(), ImageLoaderService.class);
            intent.putExtra(ImageLoaderService.URL_PARAM, imageUrl);
            intent.putExtra(ImageLoaderService.RECEIVER_PARAM, callback);
            getActivity().startService(intent);
        } else {
            Log.d(TAG, "fromStorage");
            image.setImageBitmap(StorageManager.get(imageUrl, image.getContext()));
        }
    }
}
