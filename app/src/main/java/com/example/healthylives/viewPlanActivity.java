package com.example.healthylives;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.healthylives.Adapter.workoutAdapter;
import com.example.healthylives.Adapter.workoutPlan;
import com.example.healthylives.Services.sendNotif;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class viewPlanActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private  List<workoutPlan> worklist= new ArrayList<workoutPlan>();
    private workoutAdapter adapter;
    public static final String Notef = "This contains the worklist";
    public static final String BROADCAST_ACTION= "RECEIVER";
    MyBroadCastReceiver myBroadCastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_plan);
        setTitle("All Workout Plans");
        //Adapter
        recyclerView = findViewById(R.id.viewPlanRecycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        adapter = new workoutAdapter(worklist);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        prepareTestDate();

        //onNotif();

        startPlanNotify();
        //TODO check on what is crashing reciever
        registerMyReceiver();
    }

    public void prepareTestDate()
    {
        String data = "";
        try{
            InputStream fis = new FileInputStream(new File(getFilesDir(), "all_workout.txt"));
            DataInputStream in = new DataInputStream(fis);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            while((data =br.readLine()) != null)
            {
                String delims = "[/]+";
                String[] temp = data.split(delims);
                String tempDate = new String();
                String tempTime = new String();
                String tempNote = new String();

                for (int i =0; i < temp.length; i++)
                {
                    if (i ==0)
                    {
                        tempDate=temp[i];
                    }
                    else if (i==1)
                    {
                        tempTime = temp[i];
                    }

                    else if (i==2)
                    {
                        tempNote = temp[i];
                    }
                }
                workoutPlan work = new workoutPlan(tempDate,tempTime,tempNote);
                worklist.add(work);
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop()
    {
        super.onStop();
        onUpdate();
        stopService(new Intent(this, sendNotif.class));
        unregisterReceiver(myBroadCastReceiver);
    }

    void onUpdate()
    {
        try {
            FileOutputStream stream = new FileOutputStream(new File(getFilesDir(),"all_workout.txt"));
            int counter =0;
            for (int i =0; i < worklist.size(); i++)
            {

                String total = worklist.get(i).getDate() + " / " + worklist.get(i).getTime() + " / " + worklist.get(i).getNote() + "\n";
                stream.write(total.getBytes());
                counter++;
            }
            stream.close();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    //TODO move this to a service so  it always running
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

    //TODO work on receiving updated information from service
    public void startPlanNotify()
    {
        Intent intent = new Intent(this, sendNotif.class);
        //Bundle args = new Bundle();
        //args.putSerializable("Array", (Serializable)worklist);
        intent.putExtra(Notef, (Serializable)worklist);
        startService(intent);
    }

    private void registerMyReceiver() {

        try
        {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(BROADCAST_ACTION);
            registerReceiver(myBroadCastReceiver, intentFilter);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    class MyBroadCastReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            intent.getAction();
            worklist = (ArrayList<workoutPlan>) intent.getSerializableExtra("Notef");
        }
    }

}
