package com.lgd.volumeconcoltest;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.example.volumekey.CallBackInBG;
import com.example.volumekey.VolumeService;

public class MyService extends Service {
    private Intent mIntent;
    private String msg = "MainActivity";

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MyConn mConn = new MyConn();
        mIntent = new Intent(this, VolumeService.class);
        startService(mIntent);
        bindService(mIntent, mConn, Context.BIND_AUTO_CREATE);

    }

    private class MyConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName arg0, IBinder service) {

            VolumeService.MyBinder mBinder = (VolumeService.MyBinder) service;
            VolumeService mVolumeService = mBinder.getService();
            mVolumeService.SetCallBackInBG(new CallBackInBG() {

                @Override
                public void up() {
                    // TODO Auto-generated method stub
                    Log.i("qweqwe", msg + "up");
                }

                @Override
                public void down() {
                    // TODO Auto-generated method stub
                    Log.i("qweqwe", msg + "down");
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            // TODO Auto-generated method stub

        }

    }
}
