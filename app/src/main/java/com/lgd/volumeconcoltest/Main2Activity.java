package com.lgd.volumeconcoltest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.volumekey.AExecuteAsRoot;
import com.example.volumekey.CallBackInBG;
import com.example.volumekey.VolumeService;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    private Intent mIntent;
    private String msg = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
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


//         MyConn mConn = new MyConn();
        mIntent = new Intent(this,MyService.class);
        startService(mIntent);
        finish();
//        bindService(mIntent, mConn, Context.BIND_AUTO_CREATE);
    }
//    private class MyConn implements ServiceConnection {
//
//        @Override
//        public void onServiceConnected(ComponentName arg0, IBinder service ) {
//
//            VolumeService.MyBinder mBinder = (VolumeService.MyBinder) service;
//            VolumeService mVolumeService = 	mBinder.getService();
//            mVolumeService.SetCallBackInBG(new CallBackInBG() {
//
//                @Override
//                public void up() {
//                    // TODO Auto-generated method stub
//                    Log.i("qweqwe", msg+"up");
//                }
//
//                @Override
//                public void down() {
//                    // TODO Auto-generated method stub
//                    Log.i("qweqwe", msg+"down");
//                }
//            });}
//
//        @Override
//        public void onServiceDisconnected(ComponentName arg0) {
//            // TODO Auto-generated method stub
//
//        }
//
//    }
  }
