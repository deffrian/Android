package com.example.thirdhw;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;


public class ServiceCallback extends ResultReceiver {
    public interface Receiver {
        void onReceiveResult();
    }

    private Receiver receiver;

    public ServiceCallback(Handler handler) {
        super(handler);
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        receiver.onReceiveResult();
    }
}
