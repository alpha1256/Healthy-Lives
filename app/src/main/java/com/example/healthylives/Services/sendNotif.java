package com.example.healthylives.Services;

import android.app.Activity;
import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class sendNotif extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_FOO = "com.example.healthylives.Services.action.FOO";
    public static final String ACTION_BAZ = "com.example.healthylives.Services.action.BAZ";

    // TODO: Rename parameters
    public static final String EXTRA_PARAM1 = "com.example.healthylives.Services.extra.PARAM1";
    public static final String EXTRA_PARAM2 = "com.example.healthylives.Services.extra.PARAM2";
    private List<workoutPlan> worklist= new ArrayList<>();

    public sendNotif() {
        super("sendNotif");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            intent.getAction();
            //Bundle args = intent.getBundleExtra(viewPlanActivity.Notef);
            worklist = (ArrayList<workoutPlan>) intent.getSerializableExtra(viewPlanActivity.Notef);
            onNotif();
        }
    }

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
                worklist.get(i).setCheckMark(true);
                Toast.makeText(this,"Marked for deletion",Toast.LENGTH_SHORT).show();
            }
            //else
            //    Toast.makeText(this,"Not true", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.

    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }*/

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.

    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }*/
}
