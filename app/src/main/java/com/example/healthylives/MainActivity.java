package com.example.healthylives;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthylives.Database.DaysContract;
import com.example.healthylives.Database.DaysDbHelper;
import com.example.healthylives.Services.SendDataToDB;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static java.sql.Types.NULL;


public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private int waterCount=0;
    private int steps =0;
    private int counterSteps =0;
    private String date;
  
    private int activeMin=0;
    public static String sleepMin="00:00";
  
    private SensorManager mSensormanager;
    private Sensor mSensor;
    private DaysDbHelper mHelper;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    public final static String STEPS = "Steps for today";
    public final static String WATER = "Intake for today";
    public final static String SLEEP = "Sleep for today";
    public final static String ACTIVE = "Active min for today";
    public final static String COUNTSTEPS = "Counter steps";
    public static final String BROADCAST_RECEIVER= "RECEIVER";
    SleepBroadCast receiveSleep = new SleepBroadCast();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensormanager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensormanager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        mSensormanager.registerListener(this, mSensor,SensorManager.SENSOR_DELAY_NORMAL);
        mHelper=new DaysDbHelper(this);
        getDate();
        onTwentyFour();
        registerReceiver();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.hr)
        {
            Intent intent = new Intent(this, workoutPlanActivity.class);
            startActivity(intent);
            return false;
        }

        if (id == R.id.workout)
        {
            Intent intent = new Intent(this, workoutActivity.class);
            startActivity(intent);
            return false;
        }

        else if (id == R.id.sleep)
        {
            Intent intent = new Intent(this, sleepActivity.class);
            startActivity(intent);
            return false;
        }

        else if (id == R.id.social)
        {
            Intent intent = new Intent(this, socialActivity.class);
            startActivity(intent);
            return false;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickAdd(View v)
    {
        TextView water = (TextView) findViewById(R.id.waterCounter);
        waterCount = waterCount +1;
        water.setText(String.valueOf(waterCount));
    }

    public void onClickSubtract(View v)
    {
        TextView water = (TextView) findViewById(R.id.waterCounter);
        waterCount= waterCount -1;
        if (waterCount <= 0)
        {
            water.setText(String.valueOf(0));
            waterCount =0;
        }
        else
            water.setText(String.valueOf(waterCount));
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy)
    {
        //TODO
    }

    /**
     * This is the step counting algorithm
     * @param event
     */
    @Override
    public final void onSensorChanged(SensorEvent event)
    {
        //float step = event.values[0];
        if (counterSteps < 1)
            counterSteps = (int) event.values[0];
        steps = (int) event.values[0] - counterSteps;
        activeMin = steps / 60;
        TextView active = findViewById(R.id.activeMin);
        active.setText(String.valueOf(activeMin));
        TextView stepCount = (TextView) findViewById(R.id.stepCounter);
        stepCount.setText(String.valueOf(steps));
    }


    @Override
    public void onPause()
    {
        super.onPause();
        registerReceiver();
        SharedPreferences sp = getSharedPreferences("Localdata", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(STEPS,steps);
        editor.putInt(COUNTSTEPS, counterSteps);
        if (waterCount > 0)
            editor.putInt(WATER,waterCount);
        if (activeMin > 0)
            editor.putInt(ACTIVE,activeMin);
        editor.commit();
        unregisterReceiver(receiveSleep);
        //Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show();
    }

    /****/
    @Override
    public void onResume()
    {
        super.onResume();

        SharedPreferences sp = getSharedPreferences("Localdata", Context.MODE_PRIVATE);
        if (sp.getInt(STEPS,0) == NULL)
        {
            //Toast.makeText(this,"NULL", Toast.LENGTH_SHORT).show();
        }
        else{
            steps = sp.getInt(STEPS,1);
            counterSteps = sp.getInt(COUNTSTEPS,0);
            if (sp.getInt(WATER,0) != NULL)
            {
                waterCount = sp.getInt(WATER,0);
                TextView waterTxt = findViewById(R.id.waterCounter);
                waterTxt.setText(String.valueOf(waterCount));
            }

            if (sp.getInt(ACTIVE,0) != NULL)
            {
                activeMin = sp.getInt(ACTIVE,0);
                TextView active = findViewById(R.id.activeMin);
                active.setText(String.valueOf(activeMin));
            }
        }

    }

    /**
    @Override
    public void onStop()
    {
        super.onStop();
        mSensormanager.unregisterListener(this);
    }**/

    /**
     * Every twenty four hours update db and reset step and water
     */
    public void onTwentyFour()
    {
        alarmMgr = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, SendDataToDB.class);
        intent.putExtra(SLEEP, sleepMin);
        intent.putExtra(WATER, waterCount);
        intent.putExtra(ACTIVE, activeMin);
        intent.putExtra(STEPS, steps);
        intent.putExtra("DATE", date);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 24);
        calendar.set(Calendar.MINUTE, 59);


        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, alarmIntent);
        //TODO move the db to SendDataToDB.java in services
        /**SQLiteDatabase db=mHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(DaysContract.DayEntry.COL_DAY_DATE, date);
        values.put(DaysContract.DayEntry.COL_DAY_STEP, steps);
        values.put(DaysContract.DayEntry.COL_DAY_MIN, activeMin);
        values.put(DaysContract.DayEntry.COL_DAY_CUP, waterCount);
        values.put(DaysContract.DayEntry.COL_DAY_SLEEP, sleepMin);
        db.insertWithOnConflict(DaysContract.DayEntry.TABLE1, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();**/

        getDate();
        waterCount =0;
        steps =0;
        counterSteps =0;
        activeMin=0;
        sleepMin="00:00";
        stopService(new Intent(this,SendDataToDB.class));
    }

    public void getDate()
    {
        SimpleDateFormat sdf= new SimpleDateFormat("dd-MM-yy");
        date=sdf.format(new Date());
    }



    private void registerReceiver()
    {
        try{
            IntentFilter intFilter = new IntentFilter();
            intFilter.addAction(BROADCAST_RECEIVER);
            registerReceiver(receiveSleep, intFilter);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    class SleepBroadCast extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent)
        {
            intent.getAction();
            sleepMin = intent.getStringExtra("SLEEP");
        }
    }

    //TODO add method to retrieve this data from the database
}
