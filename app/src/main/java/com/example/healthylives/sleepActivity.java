package com.example.healthylives;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.example.healthylives.Services.SleepTimer;

import static com.example.healthylives.MainActivity.sleepMin;
import static com.example.healthylives.Services.SleepTimer.time;

/**
 * Sleep activity which starts timer
 */
public class sleepActivity extends AppCompatActivity {

    TextView recordSleep;
    private boolean recording;
    //private int seconds=0;
    //private String time=" ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);
        setTitle("Sleep");
        recordSleep = (TextView) findViewById(R.id.recordSleep);
        recording=false;
        /*if(savedInstanceState!=null)
        {
            seconds=savedInstanceState.getInt("seconds");
            recording=savedInstanceState.getBoolean("recording");
        }*/
        Timer();
    }

    /*public void onSaveInstanceState(Bundle saveInstanceState)
    {
        super.onSaveInstanceState(saveInstanceState);
        saveInstanceState.putInt("seconds", seconds);
        saveInstanceState.putBoolean("recording", recording);
    }*/

    public void onClickStart(View v)
    {
        //recordSleep.setText(String.valueOf("recording"));
        if(!recording)
        {
            startService(new Intent(this, SleepTimer.class));
        }
        recording=true;
    }

    public void onClickStop (View v)
    {
        //recordSleep.setText(" ");
        //sleepMin=time;
        if(recording)
        {
            stopService(new Intent(this, SleepTimer.class));
        }
        recording=false;
        //seconds=0;
    }

    private void Timer()
    {
        final TextView timeView=(TextView) findViewById(R.id.recordSleep);
        final Handler handler=new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                //int hours=seconds/3600;
                //int minutes= (seconds%3600)/60;
                //int secs=seconds%60;

                //time=String.format("%d:%02d:%02d", hours, minutes, secs);


                timeView.setText(sleepMin);

                if(recording)
                {
                    sleepMin=time;
                }

                handler.postDelayed(this, 1000);
            }
        });
    }


}
