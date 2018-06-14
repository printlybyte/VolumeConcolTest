package com.lgd.volumeconcoltest;

import java.util.ArrayList;
import java.util.List;

import com.example.volumekey.AExecuteAsRoot;
import com.example.volumekey.CallBackInBG;
import com.example.volumekey.VolumeService;
import com.example.volumekey.VolumeService.MyBinder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class MainActivity extends Activity {

    private Intent mIntent;
    private String msg = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final List<String> cmds = new ArrayList<String>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < VolumeService.THREAD_NUM; i++) {
                    cmds.add("chmod 664 /dev/input/event" + i);
                }
            }
        }).start();


        boolean ret = AExecuteAsRoot.execute(cmds);


        MyConn mConn = new MyConn();
        mIntent = new Intent(this,VolumeService.class);
//        startService(mIntent);
        bindService(mIntent, mConn,Context.BIND_AUTO_CREATE);
    }
    private class MyConn implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName arg0, IBinder service ) {

            MyBinder mBinder = (MyBinder) service;
            VolumeService mVolumeService = 	mBinder.getService();
            mVolumeService.SetCallBackInBG(new CallBackInBG() {

                @Override
                public void up() {
                    // TODO Auto-generated method stub
                    Log.i("qweqwe", msg+"up");
                }

                @Override
                public void down() {
                    // TODO Auto-generated method stub
                    Log.i("qweqwe", msg+"down");
                }
            });}

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            // TODO Auto-generated method stub

        }

    }}
