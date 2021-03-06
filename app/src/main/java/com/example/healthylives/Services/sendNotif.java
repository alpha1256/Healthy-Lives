package com.example.healthylives.Services;

import android.app.Activity;
import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.healthylives.Adapter.workoutPlan;
import com.example.healthylives.R;
import com.example.healthylives.viewPlanActivity;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * A {@link IntentService} service which displays a notification when it time for a workout (i.e. it the current time and date)
 **/
public class sendNotif extends Service {

    private List<workoutPlan> worklist= new ArrayList<>();
    private boolean check = false;
    private int numbValue;

    @Override
    public void onCreate()
    {
        Toast.makeText(this, "Started", Toast.LENGTH_SHORT).show();
    }

    /**
     * Checks onNotif and then sends a broadcast to visual_workoutActivity with the current workout value
     * @param intent
     * @param flags
     * @param startid
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags ,int startid)
    {
        intent.getAction();
        //Bundle args = intent.getBundleExtra(viewPlanActivity.Notef);
        while (true) {
            worklist = (ArrayList<workoutPlan>) intent.getSerializableExtra(viewPlanActivity.Notef);
            onNotif();
            if (check == true) {
                Intent broadCastIntent = new Intent();

                broadCastIntent.setAction(viewPlanActivity.BROADCAST_ACTION);

                //broadCastIntent.putExtra("Notef", (Serializable) worklist);
                broadCastIntent.putExtra("Notef", numbValue);
                sendBroadcast(broadCastIntent);
                Log.d("BroadCast0", "BroadCast received");
            }

        return START_STICKY;
        }
    }

    @Override
    public void onDestroy()
    {
        Toast.makeText(this, "Stopped", Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    /**
     * Checks the workoutplan array list if it the current date and time then displays a notification
     */
    public void onNotif()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("d-M-yyyy");
        String currentDateandTime = sdf.format(new Date());
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("h:mm aa");
        String currTime = mdformat.format(calendar.getTime()).trim();
        //Toast.makeText(this,currentDateandTime,Toast.LENGTH_SHORT).show();


        for(int i =0; i < worklist.size(); i++)
        {
            String temp = worklist.get(i).getDate().trim();
            String tempTime = worklist.get(i).getTime().trim();

            if(currentDateandTime.equals(temp) && currTime.equals(tempTime))
            {
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                String CHANNEL = "notification_work";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                {
                    String name ="Channel name";
                    String description = "This is the channel for the workout planner";
                    int importance = NotificationManager.IMPORTANCE_DEFAULT;
                    NotificationChannel channel = new NotificationChannel(CHANNEL, name,importance);
                    channel.setDescription(description);
                    manager.createNotificationChannel(channel);
                }

                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL)
                        .setSmallIcon(R.drawable.running_cm)
                        .setContentTitle("Your Scheduled Workout")
                        .setContentText(worklist.get(i).getNote());
                //.setPriority(NotificationCompat.PRIORITY_DEFAULT)
                manager.notify(0,builder.build());
                check = true;
                numbValue = i;
                //worklist.get(i).setCheckMark(true);
            }
            Log.d("Note", "1");
        }
    }
}
