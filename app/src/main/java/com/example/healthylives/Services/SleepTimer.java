package com.example.healthylives.Services;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;

import com.example.healthylives.MainActivity;

/**
 * Sleep timer
 */
public class SleepTimer extends Service {

    private boolean recording;
    private int seconds=0;
    public static String time=" ";

    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        final Handler handler=new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours=seconds/3600;
                int minutes= (seconds%3600)/60;
                int secs=seconds%60;

                time=String.format("%d:%02d:%02d", hours, minutes, secs);
                seconds++;

                handler.postDelayed(this, 1000);
            }
        });
        return START_STICKY;
    }

    @Override
    public void onDestroy()
    {
        seconds=0;

        Intent broadCast = new Intent();
        broadCast.setAction(MainActivity.BROADCAST_RECEIVER);
        broadCast.putExtra("SLEEP", time);
        sendBroadcast(broadCast);

        super.onDestroy();
    }

    public String getTime()
    {
        return time;
    }
}
