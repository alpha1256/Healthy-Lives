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
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.healthylives.Adapter.workoutAdapter;
import com.example.healthylives.Adapter.workoutPlan;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class viewPlanActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<workoutPlan> worklist= new ArrayList<>();
    private workoutAdapter adapter;

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
        onNotif();
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
            //adapter.notifyDataSetChanged();
            //recyclerView
            //Log.d("UpList", String.valueOf(counter));
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    //TODO send notification when day is here
    void onNotif()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
        String currentDateandTime = sdf.format(new Date());
        for(int i =0; i < worklist.size(); i++)
        {
            String temp = worklist.get(i).getDate().trim();
            //if (i==0)
            //   Toast.makeText(this,temp,Toast.LENGTH_SHORT).show();
            if(currentDateandTime.equals(temp))
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
                        .setContentText("It " + currentDateandTime);
                        //.setPriority(NotificationCompat.PRIORITY_DEFAULT)
                manager.notify(0,builder.build());
                Toast.makeText(this,"true",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
